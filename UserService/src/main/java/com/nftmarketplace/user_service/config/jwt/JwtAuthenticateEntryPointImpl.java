package com.nftmarketplace.user_service.config.jwt;

import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.web.server.ServerWebExchange;

import com.nftmarketplace.user_service.exception.ErrorCode;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
public class JwtAuthenticateEntryPointImpl implements ServerAuthenticationEntryPoint {

    @Override
    public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException ex) {
        ServerHttpResponse response = exchange.getResponse();
        return new AuthFailureHandler().formatResponse(response, ErrorCode.UNKNOW_ERROR);
    }
}
