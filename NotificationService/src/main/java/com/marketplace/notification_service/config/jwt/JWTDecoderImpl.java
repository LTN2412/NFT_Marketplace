package com.marketplace.notification_service.config.jwt;

import java.text.ParseException;
import java.time.Instant;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import com.nimbusds.jwt.SignedJWT;

import reactor.core.publisher.Mono;

public class JWTDecoderImpl implements ReactiveJwtDecoder {
    @Override
    public Mono<Jwt> decode(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            if (signedJWT.getJWTClaimsSet().getExpirationTime().toInstant().isBefore(Instant.now()))
                return Mono.error(new JwtException("Token expired"));
            Jwt jwt = new Jwt(token,
                    signedJWT.getJWTClaimsSet().getIssueTime().toInstant(),
                    signedJWT.getJWTClaimsSet().getExpirationTime().toInstant(),
                    signedJWT.getHeader().toJSONObject(),
                    signedJWT.getJWTClaimsSet().getClaims());
            return Mono.just(jwt);
        } catch (ParseException e) {
            return Mono.error(new JwtException("Invalid JWT token"));
        }
    }
}
