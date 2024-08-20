package com.nftmarketplace.user_service.config.jwt;

import java.text.ParseException;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;

import com.nimbusds.jwt.SignedJWT;

import reactor.core.publisher.Mono;

public class JWTDecoderImpl implements ReactiveJwtDecoder {
    @Override
    public Mono<Jwt> decode(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            return Mono.just(new Jwt(token,
                    signedJWT.getJWTClaimsSet().getIssueTime().toInstant(),
                    signedJWT.getJWTClaimsSet().getExpirationTime().toInstant(),
                    signedJWT.getHeader().toJSONObject(),
                    signedJWT.getJWTClaimsSet().getClaims()));
        } catch (ParseException e) {
            return Mono.error(e);
        }
    }
}
