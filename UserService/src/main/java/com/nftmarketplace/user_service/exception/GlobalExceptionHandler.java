package com.nftmarketplace.user_service.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.nftmarketplace.user_service.model.dto.APIResponse;

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
                .message(message != null ? message : errorCode.getMessage())
                .build();
        return ResponseEntity.status(errorCode.getStatus()).body(apiResponse);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<APIResponse<?>> handlilgna(MethodArgumentNotValidException ex) {
        APIResponse<?> apiResponse = APIResponse.builder()
                .code(ErrorCode.FIELD_NOT_VALID.getCode())
                .message(ex.getFieldError().getDefaultMessage())
                .build();
        return ResponseEntity.badRequest().body(apiResponse);
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    ResponseEntity<APIResponse<?>> handlingAccessDeniedException(AccessDeniedException exception) {
        APIResponse<?> apiResponse = APIResponse.builder()
                .code(ErrorCode.UNAUTHORIZED.getCode())
                .message(ErrorCode.UNAUTHORIZED.getMessage())
                .build();
        return ResponseEntity.badRequest().body(apiResponse);
    }
}