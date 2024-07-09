package com.nftmarketplace.asset_service.configuration.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AppException extends RuntimeException {
    ErrorCode errorCode;
}
