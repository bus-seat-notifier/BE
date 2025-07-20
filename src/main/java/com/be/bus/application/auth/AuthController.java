package com.be.bus.application.auth;

import com.be.bus.domain.kakaoSocial.dto.req.KakaoLoginReqDto;
import com.be.bus.domain.kakaoSocial.dto.req.KakaoRegisterReqDto;
import com.be.bus.domain.kakaoSocial.dto.res.KakaoLoginResDto;
import com.be.bus.domain.kakaoSocial.dto.res.KakaoRegisterResDto;
import com.be.bus.domain.kakaoSocial.service.KakaoLoginService;
import com.be.bus.domain.kakaoSocial.service.KakaoRegisterService;
import com.be.bus.global.dto.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final KakaoLoginService kakaoLoginService;
    private final KakaoRegisterService kakaoRegisterService;

    /**
     * 카카오 로그인 요청 (accessToken 받아 회원 조회 및 로그인 처리)
     */
    @PostMapping("/kakao-login")
    public ResponseEntity<SuccessResponse<?>> kakaoLogin(
            @RequestBody KakaoLoginReqDto kakaoLoginReqDto
    ) {
        KakaoLoginResDto resDto = kakaoLoginService.execute(kakaoLoginReqDto);
        return SuccessResponse.ok(resDto);
    }



    /**
     * 카카오 회원가입 요청
     */
    @PostMapping("/kakao-register")
    public ResponseEntity<SuccessResponse<?>> register(
            @RequestBody KakaoRegisterReqDto kakaoRegisterReqDto
    ) {
        KakaoRegisterResDto resDto = kakaoRegisterService.execute(kakaoRegisterReqDto);
        return SuccessResponse.created(resDto);
    }

//    /**
//     * 액세스 토큰 재발급
//     */
//    @PostMapping("/reissue")
//    public ResponseEntity<SuccessResponse<?>> reissue(@RequestBody ReissueJwtTokenReqDto reissueJwtTokenDto) {
//        ReissueJwtTokenResDto resDto = kakaoSocialLoginService.reissue(reissueJwtTokenDto);
//        return SuccessResponse.ok(resDto);
//    }
//
//    /**
//     * 카카오 로그아웃
//     */
//    @PostMapping("/kakao-logout")
//    public ResponseEntity<SuccessResponse<?>> kakaoLogout(@MemberId Long memberId) {
//        kakaoSocialLogoutService.execute(memberId);
//        return SuccessResponse.ok(null);
//    }
}