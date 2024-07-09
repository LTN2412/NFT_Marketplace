package com.nftmarketplace.identity_service.controller;

import org.springframework.web.bind.annotation.RestController;

import com.nftmarketplace.identity_service.dto.APIResponse;
import com.nftmarketplace.identity_service.dto.request.IntrospectRequest;
import com.nftmarketplace.identity_service.dto.request.UserRequest;
import com.nftmarketplace.identity_service.dto.response.IntrospectResponse;
import com.nftmarketplace.identity_service.dto.response.TokenResponse;
import com.nftmarketplace.identity_service.service.AuthenticateService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    AuthenticateService authenticateService;

    @PostMapping("/token")
    public APIResponse<TokenResponse> createToken(@RequestBody UserRequest request) {
        String token = new String();
        if (authenticateService.authenticate(request))
            token = authenticateService.createToken(request);
        TokenResponse tokenResponse = TokenResponse.builder()
                .token(token)
                .build();
        return APIResponse.<TokenResponse>builder()
                .result(tokenResponse)
                .build();
    }

    @PostMapping("/introspect")
    public APIResponse<IntrospectResponse> introspect(@RequestBody IntrospectRequest request) {
        Boolean valid = authenticateService.checkToken(request.getToken());
        IntrospectResponse introspectResponse = IntrospectResponse.builder()
                .valid(valid)
                .build();
        return APIResponse.<IntrospectResponse>builder()
                .result(introspectResponse)
                .build();
    }

}
