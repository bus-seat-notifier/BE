package com.be.bus.domain.alertRegisterInfo.error;

import com.be.bus.global.enums.ErrorCode;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum AlertRegisterInfoErrorCode implements ErrorCode {

    /**
     * 409 Conflict
     */
    ALREADY_EXIST_INFO(HttpStatus.CONFLICT, "이미 등록된 알림운행정보 입니다.");

    private final HttpStatus httpStatus;
    private final String message;

}