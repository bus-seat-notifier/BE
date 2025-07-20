package com.be.bus.global.auth.entity;

import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record JwtToken(
        String accessToken,
        String refreshToken
) {
    public static JwtToken of(String accessToken, String refreshToken) {
        return JwtToken.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}