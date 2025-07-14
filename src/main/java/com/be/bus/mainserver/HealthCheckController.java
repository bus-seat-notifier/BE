package com.be.bus.mainserver;

import com.be.bus.global.mail.dto.req.EmptySeatSendMailReqDto;
import com.be.bus.global.mail.service.SendMailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/health/main")
@RequiredArgsConstructor
public class HealthCheckController {
    private final SendMailService sendMailService;
    @GetMapping
    public ResponseEntity<String> healthCheck() {
        try {
            sendMailService.execute(
                    EmptySeatSendMailReqDto.of(
                            "wlsdndml213@naver.com",
                            LocalDateTime.now(),
                            "거제서울가즈아",
                            213
                    )
            );
            return ResponseEntity.ok("✅ 메일 발송 시도 완료! 버스 가보자고!! in 메인서버");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("❌ 메일 발송 실패 in 메인서버: " + e.getMessage());
        }
    }

}
