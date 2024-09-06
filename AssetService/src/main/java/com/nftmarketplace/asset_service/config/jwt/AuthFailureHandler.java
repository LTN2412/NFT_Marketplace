package com.nftmarketplace.asset_service.config.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nftmarketplace.asset_service.exception.ErrorCode;
import com.nftmarketplace.asset_service.model.dto.APIResponse;

import jakarta.servlet.http.HttpServletResponse;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.io.IOException;

@NoArgsConstructor
public class AuthFailureHandler {
    public void formatResponse(HttpServletResponse response, ErrorCode errorCode) throws IOException {
        response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(errorCode.getStatusCode().value());
        ObjectMapper objectMapper = new ObjectMapper();
        APIResponse<?> apiResponse = APIResponse.builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .build();
        response.getWriter().write(objectMapper.writeValueAsString(apiResponse));
        response.flushBuffer();
    }
}
