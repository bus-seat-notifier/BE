package com.be.bus.global.auth.helper;

import com.be.bus.global.auth.entity.RefreshToken;
import com.be.bus.global.auth.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
@RequiredArgsConstructor
public class RefreshTokenHelper {
    private final RefreshTokenRepository refreshTokenRepository;
    public void createRefreshTokenAndSave(Long userId, String refreshToken) {
        RefreshToken refreshTokenEntity = createRefreshToken(userId, refreshToken);
        refreshTokenRepository.save(refreshTokenEntity);
    }

    public Optional<RefreshToken> findRefreshToken(Long userId) {
        return refreshTokenRepository.findById(userId);
    }

    public void deleteRefreshToken(RefreshToken refreshToken) {
        refreshTokenRepository.delete(refreshToken);
    }

    public RefreshToken createRefreshToken(Long userId, String refreshToken) {
        return RefreshToken.createRefreshToken(userId, refreshToken);
    }
}