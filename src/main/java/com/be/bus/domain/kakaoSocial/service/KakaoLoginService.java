package com.be.bus.domain.kakaoSocial.service;

import com.be.bus.domain.kakaoSocial.dto.res.KakaoAccessTokenResDto;
import com.be.bus.domain.user.entity.User;
import com.be.bus.global.auth.entity.JwtToken;
import com.be.bus.domain.kakaoSocial.dto.req.KakaoLoginReqDto;
import com.be.bus.domain.kakaoSocial.dto.res.KakaoLoginResDto;
import com.be.bus.domain.kakaoSocial.helper.KakaoSocialHelper;
import com.be.bus.global.auth.helper.RefreshTokenHelper;
import com.be.bus.global.utils.JwtUtil;
import com.be.bus.global.utils.KakaoApiClient;
import com.be.bus.global.utils.KakaoAuthClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class KakaoLoginService {

    private final KakaoSocialHelper kakaoSocialHelper;
//    private final MemberHelper memberHelper;
//    private final ServiceConsentHelper serviceConsentHelper;
    private final RefreshTokenHelper refreshTokenHelper;
    private final KakaoAuthClient kakaoAuthClient;
    private final KakaoApiClient kakaoApiClient;
    private final JwtUtil jwtUtil;

    @Value("${oauth2.kakao.rest-api-key}")
    private String CLIENT_ID;

    @Value("${oauth2.kakao.redirect-uri}")
    private String REDIRECT_URI;

    public KakaoLoginResDto execute(String kakaoAccessCode) {

        // kakaoAccessCode로 kakaoAccessToken 발급받는 로직 추가
        String kakaoAccessToken = getKakaoAccessTokenWithKakaoAccessCode(kakaoAccessCode);
        log.info("카카오 액세스 토큰을 발급받았습니다: {}", kakaoAccessToken);

        Long kakaoMemberId = getKakaoMemberId(kakaoAccessToken);
        log.info("카카오 멤버 ID를 가져왔습니다: {}", kakaoMemberId);

        User user = kakaoSocialHelper.findUserBykakaoMemberIdOrElseThrow(kakaoMemberId);

        // 찾은 userId로 jwt 토큰 생성
        JwtToken jwtToken = createJwtToken(user.getId());

        return KakaoLoginResDto.of(jwtToken, user);
    }

    private String getKakaoAccessTokenWithKakaoAccessCode(String accessCode) {
        KakaoAccessTokenResDto tokenRes = kakaoAuthClient.getKakaoTokenWithKakaoAccessToken(
                "application/x-www-form-urlencoded;charset=utf-8", // Content-Type
                "authorization_code",  // grant_type
                CLIENT_ID, // client_id
                REDIRECT_URI, // redirect_uri
                accessCode
        );

        return tokenRes.access_token();
    }


//
//    public KakaoLoginResDto register(String kakaoAccessToken, KakaoLoginReqDto requestDto) {
//        Long memberId = createMemberAndSaveInfo(getKakaoMemberId(kakaoAccessToken), requestDto).getId();
//        return KakaoLoginResDto.of(createJwtToken(memberId),findMemberById(memberId));
//    }
//
//    public ReissueJwtTokenResDto reissue(ReissueJwtTokenReqDto reissueJwtTokenDto) {
//        Long memberId = Long.valueOf(jwtUtil.getMemberIdFromPayload(reissueJwtTokenDto.accessToken()));
//        RefreshToken refreshToken = refreshTokenHelper.findRefreshTokenOrElseThrow(memberId);
//        jwtUtil.verifyMemberRefreshToken(refreshToken.getRefreshToken(), reissueJwtTokenDto.refreshToken());
//        return ReissueJwtTokenResDto.of(createJwtToken(memberId));
//    }
//
//    public Member findMemberById(Long memberId){
//        return memberHelper.findMemberByIdOrElseThrow(memberId);
//    }


    private JwtToken createJwtToken(Long userId) {
        String accessToken = jwtUtil.createAccessToken(userId);
        String refreshToken = jwtUtil.createRefreshToken();
        JwtToken jwtToken = JwtToken.of(accessToken, refreshToken);

        // RefreshToken 삭제
        deleteRefreshTokenIfExists(userId);

        // RefreshToken 저장
        refreshTokenHelper.createRefreshTokenAndSave(userId, refreshToken);
        return jwtToken;
    }

    private void deleteRefreshTokenIfExists(Long memberId) {
        refreshTokenHelper.findRefreshToken(memberId)
                .ifPresent(refreshTokenHelper::deleteRefreshToken);
    }



    private Long getKakaoMemberId(String kakaoAccessToken) {
        final String CONTENT_TYPE = "application/x-www-form-urlencoded;charset=utf-8";
        String bearerTokenString = "Bearer " + kakaoAccessToken;
        return kakaoApiClient.getKakaoUserInfo(bearerTokenString, CONTENT_TYPE).id();
    }

//
//    private Member createMemberAndSaveInfo(Long kakaoAuth, KakaoLoginReqDto requestDto) {
//        Member member = memberHelper.createMemberAndSave(requestDto);
//        serviceConsentHelper.createServiceConsentAndSave(requestDto, member);
//        kakaoSocialHelper.createKakaoSocialAndSave(kakaoAuth, member);
//        return member;
//    }
}