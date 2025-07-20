package com.be.bus.global.utils;

import com.be.bus.global.auth.error.AuthErrorCode;
import com.be.bus.global.error.exception.UnauthorizedException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Map;

import static com.be.bus.global.auth.error.AuthErrorCode.*;


@Component
@Slf4j
public class JwtUtil {

    @Value("${jwt.secret-key}")
    private String SECRET_KEY;

    @Value("${jwt.accessToken-validate-time}")
    private long ACCESSTOKEN_VALIDATE_TIME;

    @Value("${jwt.refreshToken-validate-time}")
    private long REFRESHTOKEN_VALIDATE_TIME;


    // access token 발급
    public String createAccessToken(Long userId) {
        LocalDateTime localDate = LocalDateTime.now();
        return Jwts.builder()
                .setHeaderParam("type", "accessToken")
                .claim("userId", userId)
                .claim("role", "USER") // TODO : 관리자 Role 분리
                .setIssuedAt(Timestamp.valueOf(localDate))
                .setExpiration(Timestamp.valueOf(localDate.plusHours(ACCESSTOKEN_VALIDATE_TIME)))
                .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8)), SignatureAlgorithm.HS256)
                .compact();
    }

    // refresh token 발급
    public String createRefreshToken() {
        LocalDateTime localDate = LocalDateTime.now();
        return Jwts.builder()
                .setHeaderParam("type", "refreshToken")
                .setIssuedAt(Timestamp.valueOf(localDate))
                .setExpiration(Timestamp.valueOf(localDate.plusSeconds(REFRESHTOKEN_VALIDATE_TIME)))
                .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8)), SignatureAlgorithm.HS256)
                .compact();
    }

    public String getTokenFromHeader(String authorizationHeader) {
        return authorizationHeader.substring(7);
    }

    public Long getUserIdFromAccessToken(String accessToken){
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY.getBytes())
                .build()
                .parseClaimsJws(accessToken)
                .getBody()
                .get("userId", Long.class);
    }

    public Long getRoleFromAccessToken(String accessToken){
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY.getBytes())
                .build()
                .parseClaimsJws(accessToken)
                .getBody()
                .get("role", Long.class);
    }

    public String getMemberIdFromPayload(String accessToken) {
        String[] parts = accessToken.split("\\.");
        String payload = new String(Base64.getDecoder().decode(parts[1]), StandardCharsets.UTF_8);
        try {
            Map<Long, Object> claims = new ObjectMapper().readValue(payload, Map.class);
            return claims.get("userId").toString();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


    public Long getMemberIdFromAuthorizationHeader(String authorizationHeader) {
        String accessToken = authorizationHeader.substring(7);
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY.getBytes())
                .build()
                .parseClaimsJws(accessToken)
                .getBody()
                .get("userId", Long.class);
    }

    public void verifyMemberRefreshToken(String repositoryRefreshToken, String memberRefreshToken) {
        if (!(repositoryRefreshToken.equals(memberRefreshToken)))
            throw new UnauthorizedException(AuthErrorCode.REFRESH_TOKEN_NOT_FOUND);
    }

    // access 토큰 검증
    public void validateJwtToken(String jwtToken, String type) {
        try {
            if (!getTypeFromJwt(jwtToken).equals(type))
                throw new UnauthorizedException(TOKEN_TYPE_NOT_MATCH);
        } catch (ExpiredJwtException e) {
            log.error(e.getMessage());
            throw new UnauthorizedException(ACCESS_TOKEN_EXPIRED);
        } catch (MalformedJwtException e) {
            log.error(e.getMessage());
            throw new UnauthorizedException(JWT_TOKEN_MALFORMED);
        } catch (UnsupportedJwtException e) {
            log.error(e.getMessage());
            throw new UnauthorizedException(JWT_TOKEN_UNSUPPORTED);
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            throw new UnauthorizedException(JWT_TOKEN_MISSING);
        } catch (JwtException e) {
            log.error(e.getMessage());
            throw new UnauthorizedException(ACCESS_TOKEN_INVALID);
        }
    }

    public String getTypeFromJwt(String jwtToken) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY.getBytes())
                .build()
                .parseClaimsJws(jwtToken)
                .getHeader()
                .get("type")
                .toString();
    }
}