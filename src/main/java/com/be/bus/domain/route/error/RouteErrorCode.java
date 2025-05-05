package com.be.bus.domain.route.error;

import com.be.bus.global.enums.ErrorCode;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum RouteErrorCode implements ErrorCode {

    /**
     * 404 Not Found
     */
    ROUTE_ENTITY_NOT_FOUND(HttpStatus.NOT_FOUND, "노선을 찾을 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;

}