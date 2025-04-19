package com.be.bus.global.error.exception;

import com.be.bus.global.enums.ErrorCode;
import com.be.bus.global.enums.GlobalErrorCode;

public class ConflictException extends BusinessException {
    public ConflictException() {
        super(GlobalErrorCode.CONFLICT);
    }

    public ConflictException(ErrorCode errorCode) {
        super(errorCode);
    }
}


