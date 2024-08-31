// package com.nftmarketplace.identity_service.exception;

// import java.io.IOException;

// import org.springframework.http.MediaType;
// import org.springframework.security.access.AccessDeniedException;
// import org.springframework.security.web.access.AccessDeniedHandler;

// import com.fasterxml.jackson.databind.ObjectMapper;
// import com.nftmarketplace.identity_service.dto.APIResponse;

// import jakarta.servlet.ServletException;
// import jakarta.servlet.http.HttpServletRequest;
// import jakarta.servlet.http.HttpServletResponse;

// public class JwtAccessDeniedHandler implements AccessDeniedHandler {
// @Override
// public void handle(HttpServletRequest request, HttpServletResponse response,
// AccessDeniedException accessDeniedException) throws IOException,
// ServletException {
// ErrorCode errorCode = ErrorCode.UNAUTHORIZED;
// response.setStatus(errorCode.getStatus().value());
// response.setContentType(MediaType.APPLICATION_JSON_VALUE);
// APIResponse<?> apiResponse = APIResponse.builder()
// .code(errorCode.getCode())
// .message(errorCode.getMessage())
// .build();

// ObjectMapper objectMapper = new ObjectMapper();

// response.getWriter().write(objectMapper.writeValueAsString(apiResponse));
// response.flushBuffer();
// }
// }
