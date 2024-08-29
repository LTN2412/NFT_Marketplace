package com.marketplace.notification_service.exception;

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
    FIELD_NOT_VALID(101, "Field of request not valid", HttpStatus.BAD_REQUEST),
    EXISTED(102, "Existed!", HttpStatus.BAD_REQUEST),
    NOT_EXISTED(103, "Not existed!", HttpStatus.NOT_FOUND),
    LENGTH_SECRET_KEY(104, "Exception with length secret key!", HttpStatus.BAD_REQUEST),
    CAN_NOT_CREATE_TOKEN(105, "Can't create token!", HttpStatus.BAD_REQUEST),
    INVALID_TOKEN(106, "Invalid token!", HttpStatus.BAD_REQUEST),
    TOKEN_EXPIRED(107, "Token has expired!", HttpStatus.UNAUTHORIZED),
    INCORRECT_USERNAME_OR_PWD(108, "Incorrect username or password!", HttpStatus.UNAUTHORIZED),
    UNAUTHENTICATED(109, "Unauthenticated!", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(110, "Not have permission!", HttpStatus.FORBIDDEN),
    NOT_REQUEST_FRIEND(111, "Not have request to handle friend!", HttpStatus.FORBIDDEN);

    int code;
    String message;
    HttpStatus status;
}
