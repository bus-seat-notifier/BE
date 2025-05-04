package com.be.bus.global.mail.service;

import com.be.bus.global.enums.GlobalErrorCode;
import com.be.bus.global.error.exception.InternalServerException;
import com.be.bus.global.mail.dto.req.EmptySeatSendMailReqDto;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Service
@RequiredArgsConstructor
public class SendMailService {
    private static final String senderMail = "wlsdndml213@gmail.com";

    private final SpringTemplateEngine templateEngine;
    private final JavaMailSender javaMailSender;

    @Async
    public void execute(EmptySeatSendMailReqDto req) {

        try {
            MimeMessage message = createMail(req.receiverMail(), req.departureDtm(), req.routeName(), req.numberOfSeat());
            javaMailSender.send(message);

        } catch (Exception e) {
            log.error("❌ 이메일 발송 실패: {}", e.getMessage(), e); // 로그 추가!

            throw new InternalServerException(GlobalErrorCode.SEND_MAIL_SERVER_ERROR);
        }
    }

    private MimeMessage createMail(String receiverMail, LocalDateTime departureDtm, String routeName, Integer numberOfSeat) {
        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom(senderMail);
            helper.setTo(receiverMail);
            helper.setSubject("[빈자리알림이] 등록하신 버스에 빈자리가 생겼어요!");
            helper.setText(setContext(departureDtm, routeName, numberOfSeat), true);
        } catch (MessagingException e) {
            log.error("❌ 이메일 생성 실패: {}", e.getMessage(), e); // 로그 추가!
            e.printStackTrace();
            throw new InternalServerException(GlobalErrorCode.SEND_MAIL_SERVER_ERROR);
        }

        return message;
    }



    private String setContext(LocalDateTime departureDtm, String routeName, Integer numberOfSeat) {
        final Context context = new Context();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 M월 d일 HH시 mm분");
        context.setVariable("departureDtm", departureDtm.format(formatter));
        context.setVariable("routeName", routeName);
        context.setVariable("numberOfSeat", numberOfSeat);
        return templateEngine.process("send-mail", context);
    }


}