package com.nftmarketplace.identity_service.exception;

import org.springframework.http.HttpStatus;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public enum ErrorCode {
    UNKNOW_ERROR(999, "Unknow error!", HttpStatus.BAD_REQUEST),
    EXISTED(101, "Existed!", HttpStatus.BAD_REQUEST),
    NOT_EXISTED(102, "Not existed!", HttpStatus.NOT_FOUND),
    LENGTH_SECRET_KEY(103, "Exception with length secret key!", HttpStatus.BAD_REQUEST),
    CAN_NOT_CREATE_TOKEN(104, "Can't create token!", HttpStatus.BAD_REQUEST),
    INVALID_TOKEN(105, "Invalid token!", HttpStatus.BAD_REQUEST),
    TOKEN_EXPIRED(106, "Token has expired!", HttpStatus.UNAUTHORIZED),
    INCORRECT_USERNAME_OR_PWD(107, "Incorrect username or password!", HttpStatus.UNAUTHORIZED),
    UNAUTHENTICATED(108, "Unauthenticated!", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(109, "Not have permission!", HttpStatus.FORBIDDEN);

    int code;
    String message;
    HttpStatus status;
}
