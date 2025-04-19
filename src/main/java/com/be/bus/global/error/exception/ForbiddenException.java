package com.be.bus.global.error.exception;


import com.be.bus.global.enums.ErrorCode;
import com.be.bus.global.enums.GlobalErrorCode;

public class ForbiddenException extends BusinessException {
    public ForbiddenException() {
        super(GlobalErrorCode.FORBIDDEN);
    }

    public ForbiddenException(ErrorCode errorCode) {
        super(errorCode);
    }
}

