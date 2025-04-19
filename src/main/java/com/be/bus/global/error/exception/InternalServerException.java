package com.be.bus.global.error.exception;


import com.be.bus.global.enums.ErrorCode;
import com.be.bus.global.enums.GlobalErrorCode;

public class InternalServerException extends BusinessException {
    public InternalServerException() {
        super(GlobalErrorCode.INTERNAL_SERVER_ERROR);
    }

    public InternalServerException(ErrorCode errorCode) {
        super(errorCode);
    }
}

