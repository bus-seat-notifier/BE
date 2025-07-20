package com.be.bus.domain.kakaoSocial.dto.res;

import com.be.bus.global.auth.entity.JwtToken;
import com.be.bus.domain.user.entity.User;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record KakaoLoginResDto(
        String accessToken,
        String refreshToken,
        Long userId
) {
    public static KakaoLoginResDto of(JwtToken jwtToken, User user) {
        return KakaoLoginResDto
                .builder()
                .accessToken(jwtToken.accessToken())
                .refreshToken(jwtToken.refreshToken())
                .userId(user.getId())
                .build();
    }
}
