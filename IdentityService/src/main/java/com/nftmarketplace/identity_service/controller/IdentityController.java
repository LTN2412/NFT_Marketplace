package com.nftmarketplace.identity_service.controller;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import com.nftmarketplace.identity_service.model.dto.APIResponse;
import com.nftmarketplace.identity_service.model.dto.request.IdentityRequest;
import com.nftmarketplace.identity_service.service.IdentityService;

import jakarta.servlet.http.HttpServletResponse;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/token")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class IdentityController {
    IdentityService identityService;

    @PostMapping
    public APIResponse<?> createToken(@ModelAttribute IdentityRequest request) {
        String message = identityService.createToken(request);
        return APIResponse.builder()
                .message(message)
                .build();
    }

    @PostMapping("/cookie")
    public APIResponse<?> createTokenInCookie(@ModelAttribute IdentityRequest request,
            HttpServletResponse response) {
        String message = identityService.createTokenInCookie(request, response);
        return APIResponse.builder()
                .message(message)
                .build();
    }

    @PostMapping("/introspect")
    public APIResponse<Boolean> introspect(@RequestPart String token) {
        Boolean result = identityService.checkToken(token);
        return APIResponse.<Boolean>builder()
                .result(result)
                .build();
    }
}
