package com.be.bus.global.mail.dto.req;

import lombok.AccessLevel;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder(access = AccessLevel.PRIVATE)
public record EmptySeatSendMailReqDto(
        String receiverMail,
        LocalDateTime departureDtm,
        String routeName,
        Integer numberOfSeat
) {
    static public EmptySeatSendMailReqDto of(String receiverMail, LocalDateTime departureDtm, String routeName, Integer numberOfSeat) {
        return EmptySeatSendMailReqDto.builder()
                .receiverMail(receiverMail)
                .departureDtm(departureDtm)
                .routeName(routeName)
                .numberOfSeat(numberOfSeat)
                .build();
    }
}
