package com.nftmarketplace.identity_service.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.nftmarketplace.identity_service.model.dto.APIResponse;

@ControllerAdvice
public class GlobalException {
    @ExceptionHandler(value = Exception.class)
    ResponseEntity<APIResponse<?>> handlingAllException(Exception exception) {
        ErrorCode errorCode = ErrorCode.UNKNOW_ERROR;
        APIResponse<?> apiResponse = APIResponse.builder()
                .code(errorCode.getCode())
                .message(exception.getMessage())
                .build();
        return ResponseEntity.badRequest().body(apiResponse);
    }

    @ExceptionHandler(value = AppException.class)
    ResponseEntity<APIResponse<?>> handlingAppException(AppException ex) {
        ErrorCode errorCode = ex.getErrorCode();
        String message = ex.getMessage();
        APIResponse<?> apiResponse = APIResponse.builder()
                .code(errorCode.getCode())
                .message(message != null ? message : errorCode.getMessage())
                .build();
        return ResponseEntity.status(errorCode.getStatus()).body(apiResponse);
    }
}
