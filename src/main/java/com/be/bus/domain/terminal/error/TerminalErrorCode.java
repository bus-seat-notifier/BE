package com.be.bus.domain.terminal.error;

import com.be.bus.global.enums.ErrorCode;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum TerminalErrorCode implements ErrorCode {

    /**
     * 404 Not Found
     */
    TERMINAL_ENTITY_NOT_FOUND(HttpStatus.NOT_FOUND, "터미널을 찾을 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;

}