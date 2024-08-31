package com.nftmarketplace.identity_service.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class AppException extends RuntimeException {
    @NonNull
    ErrorCode errorCode;

    String message;
}
