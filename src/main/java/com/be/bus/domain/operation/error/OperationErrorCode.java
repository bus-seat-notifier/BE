package com.be.bus.domain.operation.error;

import com.be.bus.global.enums.ErrorCode;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum OperationErrorCode implements ErrorCode {

    /**
     * 404 Not Found
     */
    OPERATION_ENTITY_NOT_FOUND(HttpStatus.NOT_FOUND, "운행정보를 찾을 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;

}