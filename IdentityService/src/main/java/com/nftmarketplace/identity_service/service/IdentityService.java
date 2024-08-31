package com.nftmarketplace.identity_service.service;

import com.nftmarketplace.identity_service.model.Account;
import com.nftmarketplace.identity_service.model.dto.request.IdentityRequest;

import jakarta.servlet.http.HttpServletResponse;

public interface IdentityService {
    Account authenticate(IdentityRequest request);

    String createTokenInCookie(IdentityRequest request, HttpServletResponse response);

    String createToken(IdentityRequest request);

    Boolean checkToken(String token);
}
