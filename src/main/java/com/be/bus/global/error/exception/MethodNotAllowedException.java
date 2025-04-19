package com.be.bus.global.error.exception;


import com.be.bus.global.enums.ErrorCode;
import com.be.bus.global.enums.GlobalErrorCode;

public class MethodNotAllowedException extends BusinessException {
    public MethodNotAllowedException() {
        super(GlobalErrorCode.METHOD_NOT_ALLOWED);
    }

    public MethodNotAllowedException(ErrorCode errorCode) {
        super(errorCode);
    }
}
