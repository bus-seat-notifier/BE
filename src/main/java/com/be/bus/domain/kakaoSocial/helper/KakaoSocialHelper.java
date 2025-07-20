package com.be.bus.domain.kakaoSocial.helper;


import com.be.bus.domain.kakaoSocial.entity.KakaoSocial;
import com.be.bus.domain.kakaoSocial.repository.KakaoSocialRepository;
import com.be.bus.domain.user.entity.User;
import com.be.bus.global.error.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.be.bus.global.auth.error.AuthErrorCode.KAKAO_INFO_NOT_FOUND;


@RequiredArgsConstructor
@Component
public class KakaoSocialHelper {

    private final KakaoSocialRepository kakaoSocialRepository;

    public User findUserBykakaoMemberIdOrElseThrow(Long kakaoMemberId) {
        KakaoSocial kakaoSocial = kakaoSocialRepository.findByKakaoMemberId(kakaoMemberId)
                .orElseThrow(() -> new EntityNotFoundException(KAKAO_INFO_NOT_FOUND));

        return kakaoSocial.getUser();
    }
//
//    public void createKakaoSocialAndSave(Long kakaoAuth, Member member) {
//        KakaoSocial kakaoSocial = createKakaoSocial(kakaoAuth, member);
//        kakaoSocialRepository.save(kakaoSocial);
//    }
//
//    public Long findKakaoSocialByMemberOrElseThrow(Member member){
//        return kakaoSocialRepository.findByMember(member)
//                .orElseThrow(() -> new EntityNotFoundException(KAKAO_INFO_NOT_FOUND))
//                .getKakaoAuth();
//    }
}