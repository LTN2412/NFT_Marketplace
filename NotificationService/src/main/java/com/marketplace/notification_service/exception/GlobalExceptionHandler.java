package com.marketplace.notification_service.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.marketplace.notification_service.model.dto.APIResponse;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    ResponseEntity<APIResponse<?>> handlingUnknowException(Exception ex) {
        ErrorCode errorCode = ErrorCode.UNKNOW_ERROR;
        APIResponse<?> apiResponse = APIResponse.builder()
                .code(errorCode.getCode())
                .message(ex.getMessage())
                .build();
        return ResponseEntity.badRequest().body(apiResponse);
    }

    @ExceptionHandler(value = AppException.class)
    ResponseEntity<APIResponse<?>> handlingAppException(AppException ex) {
        ErrorCode errorCode = ex.getErrorCode();
        String message = ex.getMessage();
        APIResponse<?> apiResponse = APIResponse.builder()
                .code(errorCode.getCode())
                .message(message.length() != 0 ? message : ex.getMessage())
                .build();
        return ResponseEntity.status(errorCode.getStatus()).body(apiResponse);
    }
}