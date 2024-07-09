package com.nftmartketplace.identity_service.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.nftmartketplace.identity_service.dto.APIResponse;

@ControllerAdvice
public class GlobalException {
    @ExceptionHandler(value = Exception.class)
    ResponseEntity<APIResponse<?>> handlingAllException(Exception exception) {
        ErrorCode errorCode = ErrorCode.UNKNOW_ERROR;
        APIResponse<?> apiResponse = APIResponse.builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .build();
        return ResponseEntity.badRequest().body(apiResponse);
    }

    @ExceptionHandler(value = AppException.class)
    ResponseEntity<APIResponse<?>> handlingAppException(AppException appException) {
        ErrorCode errorCode = appException.getErrorCode();
        APIResponse<?> apiResponse = APIResponse.builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .build();
        return ResponseEntity.status(errorCode.getStatus()).body(apiResponse);
    }
}
