package com.be.bus.application.auth;

import com.be.bus.domain.kakaoSocial.dto.req.KakaoLoginReqDto;
import com.be.bus.domain.kakaoSocial.dto.res.KakaoLoginResDto;
import com.be.bus.domain.kakaoSocial.service.KakaoLoginService;
import com.be.bus.global.dto.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final KakaoLoginService kakaoSocialLoginService;
    //private final KakaoSocialLogoutService kakaoSocialLogoutService;

        /**
         * 카카오 로그인 요청 (accessToken 받아 회원 조회 및 로그인 처리)
         */
        @PostMapping("/kakao-login")
        public ResponseEntity<SuccessResponse<?>> kakaoLogin(
                @RequestParam("kakaoAccessCode") String kakaoAccessCode
        ) {
            KakaoLoginResDto resDto = kakaoSocialLoginService.execute(kakaoAccessCode);
            return SuccessResponse.ok(resDto);
        }


//
//    /**
//     * 카카오 회원가입 요청
//     */
//    @PostMapping("/register")
//    public ResponseEntity<SuccessResponse<?>> register(
//            @RequestHeader("Authorization") String kakaoAccessToken,
//            @RequestBody KakaoLoginReqDto requestDto
//    ) {
//        KakaoLoginResDto resDto = kakaoSocialLoginService.register(kakaoAccessToken, requestDto);
//        return SuccessResponse.ok(resDto);
//    }

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