package com.nftmarketplace.identity_service.service.impl;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Set;
import java.util.StringJoiner;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.nftmarketplace.identity_service.exception.AppException;
import com.nftmarketplace.identity_service.exception.ErrorCode;
import com.nftmarketplace.identity_service.model.Account;
import com.nftmarketplace.identity_service.model.Role;
import com.nftmarketplace.identity_service.model.dto.request.IdentityRequest;
import com.nftmarketplace.identity_service.repository.AccountRepository;
import com.nftmarketplace.identity_service.service.IdentityService;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.KeyLengthException;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class IdentityServiceImpl implements IdentityService {
    AccountRepository accountRepository;
    PasswordEncoder passwordEncoder;

    @NonFinal
    @Value("${jwt.secretKey}")
    String secretKey;

    @NonFinal
    @Value("${jwt.accessToken}")
    Long accessTokenExpiredTime;

    @Override
    public Account authenticate(IdentityRequest request) {
        Account account = accountRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.NOT_EXISTED));
        Boolean result = passwordEncoder.matches(request.getPassword(), account.getPassword());
        if (result)
            return account;
        throw new AppException(ErrorCode.INCORRECT_USERNAME_OR_PWD);
    }

    @Override
    public String createTokenInCookie(IdentityRequest request, HttpServletResponse response) {
        Account account = authenticate(request);
        JWSSigner jwsSigner;
        try {
            jwsSigner = new MACSigner(secretKey);
        } catch (KeyLengthException e) {
            throw new AppException(ErrorCode.LENGTH_SECRET_KEY);
        }
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS256);
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(account.getId())
                .issuer("identity_service")
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now().plus(accessTokenExpiredTime, ChronoUnit.SECONDS).toEpochMilli()))
                .claim("scope", createScope(account.getRoles()))
                .claim("userId", account.getId())
                .build();
        SignedJWT signedJWT = new SignedJWT(header, claimsSet);
        try {
            signedJWT.sign(jwsSigner);
            Cookie tokenCookie = new Cookie("accessToken", signedJWT.serialize());
            tokenCookie.setHttpOnly(false);
            tokenCookie.setSecure(true);
            tokenCookie.setMaxAge(3600);
            tokenCookie.setPath("/");
            response.addCookie(tokenCookie);
            return "Sign in successfully";
        } catch (JOSEException e) {
            throw new AppException(ErrorCode.CAN_NOT_CREATE_TOKEN);
        }
    }

    @Override
    public String createToken(IdentityRequest request) {
        Account account = authenticate(request);
        JWSSigner jwsSigner;
        try {
            jwsSigner = new MACSigner(secretKey);
        } catch (KeyLengthException e) {
            throw new AppException(ErrorCode.LENGTH_SECRET_KEY);
        }
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS256);
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(account.getId())
                .issuer("identity_service")
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now().plus(accessTokenExpiredTime, ChronoUnit.SECONDS).toEpochMilli()))
                .claim("scope", createScope(account.getRoles()))
                .claim("userId", account.getId())
                .build();
        SignedJWT signedJWT = new SignedJWT(header, claimsSet);
        try {
            signedJWT.sign(jwsSigner);
            return signedJWT.serialize();
        } catch (JOSEException e) {
            throw new AppException(ErrorCode.CAN_NOT_CREATE_TOKEN);
        }
    }

    private String createScope(Set<Role> role) {
        StringJoiner stringJoiner = new StringJoiner(" ");
        if (!role.isEmpty())
            role.forEach(s -> stringJoiner.add(s.getName()));
        return stringJoiner.toString();
    }

    @Override
    public Boolean checkToken(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            JWSVerifier verifier = new MACVerifier(secretKey);
            if (signedJWT.verify(verifier)) {
                Date expiredTime = signedJWT.getJWTClaimsSet().getExpirationTime();
                if (expiredTime != null && new Date().before(expiredTime))
                    return true;
                throw new AppException(ErrorCode.TOKEN_EXPIRED);
            }
        } catch (JOSEException e) {
            throw new AppException(ErrorCode.INVALID_TOKEN);
        } catch (ParseException e) {
            throw new AppException(ErrorCode.INVALID_TOKEN);
        }
        return null;
    }
}
