package com.be.bus.domain.kakaoSocial.dto.res;

import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record KakaoAccessTokenResDto(
        String token_type,
        String access_token,
        String id_token,
        int expires_in,
        String refresh_token,
        int refresh_token_expires_in,
        String scope
) {

}
