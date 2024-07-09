package com.nftmarketplace.identity_service.service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Set;
import java.util.StringJoiner;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.nftmarketplace.identity_service.dto.request.UserRequest;
import com.nftmarketplace.identity_service.exception.AppException;
import com.nftmarketplace.identity_service.exception.ErrorCode;
import com.nftmarketplace.identity_service.model.Role;
import com.nftmarketplace.identity_service.model.User;
import com.nftmarketplace.identity_service.repository.UserRepository;
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

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticateService {

    @NonFinal
    @Value("${jwt.secretKey}")
    String secretKey;
    @NonFinal
    @Value("${jwt.accessToken}")
    Long accessTokenExpiredTime;
    UserRepository userRepository;

    public Boolean authenticate(UserRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.INCORRECT_USERNAME_OR_PWD));
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        Boolean result = passwordEncoder.matches(request.getPassword(), user.getPassword());
        if (result) {
            return true;
        }
        throw new AppException(ErrorCode.INCORRECT_USERNAME_OR_PWD);
    }

    public String createToken(UserRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.NOT_EXISTED));
        JWSSigner jwsSigner;

        try {
            jwsSigner = new MACSigner(secretKey);
        } catch (KeyLengthException e) {
            throw new AppException(ErrorCode.LENGTH_SECRET_KEY);
        }
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS256);
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(request.getUsername())
                .issuer("identity_service")
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now().plus(accessTokenExpiredTime, ChronoUnit.SECONDS).toEpochMilli()))
                .claim("scope", createScope(user.getRoles()))
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
