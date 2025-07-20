package com.be.bus.global.utils;

import com.be.bus.domain.kakaoSocial.dto.res.UserInfoFromKakaoResDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "kakao-api-client", url = "https://kapi.kakao.com")
public interface KakaoApiClient {

    @GetMapping("/v2/user/me")
    UserInfoFromKakaoResDto getKakaoUserInfo(
            @RequestHeader("Authorization") String kakaoAccessToken,
            @RequestHeader("Content-Type") String contentType
    );
}
