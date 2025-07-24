package com.be.bus.global.auth.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;


@RedisHash(value = "refresh_token")
@Builder
@AllArgsConstructor
@Getter
public class RefreshToken {

    @Id
    private Long userId;

    private String refreshToken;

    public static RefreshToken createRefreshToken(Long userId, String refreshToken){
        return RefreshToken.builder()
                .userId(userId)
                .refreshToken(refreshToken)
                .build();
    }
}