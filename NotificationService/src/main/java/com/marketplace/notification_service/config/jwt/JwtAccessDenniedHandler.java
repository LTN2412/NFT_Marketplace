package com.marketplace.notification_service.config.jwt;

import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.web.server.ServerWebExchange;

import com.marketplace.notification_service.exception.ErrorCode;

import reactor.core.publisher.Mono;

public class JwtAccessDenniedHandler implements ServerAccessDeniedHandler {
    @Override
    public Mono<Void> handle(ServerWebExchange exchange, AccessDeniedException denied) {
        ServerHttpResponse response = exchange.getResponse();
        return new AuthFailureHandler().formatResponse(response, ErrorCode.UNAUTHENTICATED);
    }
}