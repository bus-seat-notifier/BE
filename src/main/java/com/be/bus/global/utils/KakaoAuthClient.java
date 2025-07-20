package com.be.bus.global.utils;

import com.be.bus.domain.kakaoSocial.dto.res.KakaoAccessTokenResDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "kakao-auth-client", url = "https://kauth.kakao.com")
public interface KakaoAuthClient {
    @PostMapping(value = "/oauth/token", consumes = "application/x-www-form-urlencoded")
    KakaoAccessTokenResDto getKakaoTokenWithKakaoAccessToken(
            @RequestHeader("Content-Type") String contentType,
            @org.springframework.web.bind.annotation.RequestParam("grant_type") String grantType,
            @org.springframework.web.bind.annotation.RequestParam("client_id") String clientId,
            @org.springframework.web.bind.annotation.RequestParam("redirect_uri") String redirectUri,
            @org.springframework.web.bind.annotation.RequestParam("code") String code
    );
}
