package com.be.bus.domain.kakaoSocial.dto.res;

import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record KakaoRegisterResDto(
        String kakaoAccessCode
) {
    public static KakaoRegisterResDto of(String kakaoAccessCode) {
        return KakaoRegisterResDto
                .builder()
                .kakaoAccessCode(kakaoAccessCode)
                .build();
    }
}
