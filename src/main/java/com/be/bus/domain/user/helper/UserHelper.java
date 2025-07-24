package com.be.bus.domain.user.helper;

import com.be.bus.domain.kakaoSocial.dto.req.KakaoLoginReqDto;
import com.be.bus.domain.kakaoSocial.dto.req.KakaoRegisterReqDto;
import com.be.bus.domain.kakaoSocial.dto.res.KakaoRegisterResDto;
import com.be.bus.domain.user.entity.User;
import com.be.bus.domain.user.error.UserErrorCode;
import com.be.bus.domain.user.repository.UserRepository;
import com.be.bus.global.enums.GlobalErrorCode;
import com.be.bus.global.error.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UserHelper {
    private final UserRepository userRepository;

    public User findByIdOrElseThrow(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(UserErrorCode.USER_ENTITY_NOT_FOUND));
    }

    // 유저 회원가입 처리
    public User createUserAndSave(KakaoRegisterReqDto reqDto) {
        User user = User.create(reqDto.email());
        return userRepository.save(user);
    }
}
