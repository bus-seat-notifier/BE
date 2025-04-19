package com.be.bus.global.error.exception;

import com.be.bus.global.enums.ErrorCode;
import com.be.bus.global.enums.GlobalErrorCode;

public class InvalidFileUploadException extends BusinessException {
    public InvalidFileUploadException() {
        super(GlobalErrorCode.INVALID_FILE_UPLOAD);
    }

    public InvalidFileUploadException(ErrorCode errorCode) {
        super(errorCode);
    }
}

