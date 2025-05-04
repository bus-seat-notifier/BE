package com.be.bus.domain.user.helper;

import com.be.bus.domain.user.entity.User;
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
                .orElseThrow(() -> new EntityNotFoundException(GlobalErrorCode.ENTITY_NOT_FOUND));
    }
}
