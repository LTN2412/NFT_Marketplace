package com.nftmarketplace.user_service.config.jwt;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;

import reactor.core.publisher.Mono;

/**
 * AuthenticationConverterImpl
 */
public class AuthenticationConverterImpl implements Converter<Jwt, Mono<AbstractAuthenticationToken>> {

    @Override
    public Mono<AbstractAuthenticationToken> convert(Jwt source) {
        return null;
    }
}