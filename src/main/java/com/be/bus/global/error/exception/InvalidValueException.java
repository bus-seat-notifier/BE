package com.be.bus.global.error.exception;

import com.be.bus.global.enums.ErrorCode;
import com.be.bus.global.enums.GlobalErrorCode;

public class InvalidValueException extends BusinessException {
    public InvalidValueException() {
        super(GlobalErrorCode.BAD_REQUEST);
    }

    public InvalidValueException(ErrorCode errorCode) {
        super(errorCode);
    }
}
