package com.be.bus.domain.kakaoSocial.service;

import com.be.bus.domain.kakaoSocial.dto.req.KakaoRegisterReqDto;
import com.be.bus.domain.kakaoSocial.dto.res.KakaoLoginResDto;
import com.be.bus.domain.kakaoSocial.dto.res.KakaoRegisterResDto;
import com.be.bus.domain.kakaoSocial.entity.KakaoSocial;
import com.be.bus.domain.kakaoSocial.helper.KakaoSocialHelper;
import com.be.bus.domain.user.entity.User;
import com.be.bus.domain.user.helper.UserHelper;
import com.be.bus.global.auth.entity.JwtToken;
import com.be.bus.global.auth.error.AuthErrorCode;
import com.be.bus.global.auth.helper.RefreshTokenHelper;
import com.be.bus.global.error.exception.ConflictException;
import com.be.bus.global.utils.JwtUtil;
import com.be.bus.domain.kakaoSocial.util.KakaoApiClient;
import com.be.bus.domain.kakaoSocial.util.KakaoAuthClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class KakaoRegisterService {

    private final KakaoSocialHelper kakaoSocialHelper;
    private final UserHelper userHelper;

    private final KakaoAuthClient kakaoAuthClient;
    private final KakaoApiClient kakaoApiClient;

    @Value("${oauth2.kakao.rest-api-key}")
    private String CLIENT_ID;

    @Value("${oauth2.kakao.redirect-uri}")
    private String REDIRECT_URI;

    public KakaoRegisterResDto execute(KakaoRegisterReqDto reqDto) {
        // kakaoAccessCode로 kakaoAccessToken 발급받는 로직
        String kakaoAccessToken = kakaoAuthClient.getKakaoAccessTokenWithKakaoAccessCode(CLIENT_ID, REDIRECT_URI, reqDto.kakaoAccessCode());
        log.info("카카오 액세스 토큰을 발급받았습니다: {}", kakaoAccessToken);

        Long kakaoMemberId = getKakaoMemberId(kakaoAccessToken);
        log.info("카카오 멤버 ID를 가져왔습니다: {}", kakaoMemberId);

        if (kakaoSocialHelper.existsByKakaoMemberId(kakaoMemberId)) {
            throw new ConflictException(AuthErrorCode.KAKAO_ALREADY_EXISTS); // 이미 가입된 유저
        }

        // kakaoMemberId로 KakaoSocial 생성
        User user = userHelper.createUserAndSave(reqDto);
        KakaoSocial kakaoSocial = kakaoSocialHelper.createKakaoSocialAndSave(kakaoMemberId, user);

        return KakaoRegisterResDto.of(reqDto.kakaoAccessCode());
    }



    // TODO : kakaoAuthClient처럼 default로 만들어서 사용
    private Long getKakaoMemberId(String kakaoAccessToken) {
        final String CONTENT_TYPE = "application/x-www-form-urlencoded;charset=utf-8";
        String bearerTokenString = "Bearer " + kakaoAccessToken;
        return kakaoApiClient.getKakaoUserInfo(bearerTokenString, CONTENT_TYPE).id();
    }


}