package com.be.bus.global.error.handler;

import com.be.bus.global.enums.ErrorCode;
import com.be.bus.global.dto.ErrorResponse;
import com.be.bus.global.enums.GlobalErrorCode;
import com.be.bus.global.error.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    /**
     * Valid & Validated annotation의 binding error를 handling합니다.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error(">>> handle: MethodArgumentNotValidException - message: {}, stackTrace: {}", e.getMessage(), e.getStackTrace());
        return createValidErrorResponse(e);
    }

    /**
     * ModelAttribute annotation의 binding error를 handling합니다.
     */
    @ExceptionHandler(BindException.class)
    protected ResponseEntity<ErrorResponse> handleBindException(BindException e) {
        log.error(">>> handle: BindException - message: {}, stackTrace: {}", e.getMessage(), e.getStackTrace());
        return createErrorResponse(GlobalErrorCode.BAD_REQUEST);
    }

    /**
     * RequestParam annotation의 binding error를 handling합니다.
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        log.error(">>> handle: MethodArgumentTypeMismatchException - message: {}, stackTrace: {}", e.getMessage(), e.getStackTrace());
        return createErrorResponse(GlobalErrorCode.BAD_REQUEST_PARAM);
    }

    /**
     * 지원하지 않는 HTTP method로 요청 시 발생하는 error를 handling합니다.
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error(">>> handle: HttpRequestMethodNotSupportedException - message: {}, stackTrace: {}", e.getMessage(), e.getStackTrace());
        return createErrorResponse(GlobalErrorCode.METHOD_NOT_ALLOWED);
    }

    /**
     * BusinessException을 handling합니다.
     */
    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<ErrorResponse> handleBusinessException(final BusinessException e) {
        log.error(">>> handle: BusinessException - message: {}, stackTrace: {}", e.getMessage(), e.getStackTrace());
        final ErrorCode errorCode = e.getErrorCode();
        return createErrorResponse(errorCode);
    }

    /**
     * 위에서 정의한 Exception을 제외한 모든 예외를 handling합니다.
     */
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handleException(Exception e) {
        log.error(">>> handle: Exception - message: {}, stackTrace: {}", e.getMessage(), e.getStackTrace());
        return createErrorResponse(GlobalErrorCode.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<ErrorResponse> createErrorResponse(ErrorCode errorCode) {
        ErrorResponse errorResponse = ErrorResponse.of(errorCode);
        return ResponseEntity.status(errorCode.getHttpStatus()).body(errorResponse);
    }

    private ResponseEntity<ErrorResponse> createValidErrorResponse(MethodArgumentNotValidException e) {
        ErrorResponse errorResponse = ErrorResponse.of(e);
        return ResponseEntity.status(e.getStatusCode()).body(errorResponse);
    }
}