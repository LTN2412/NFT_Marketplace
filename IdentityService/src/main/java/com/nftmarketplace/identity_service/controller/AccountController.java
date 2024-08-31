package com.nftmarketplace.identity_service.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nftmarketplace.identity_service.model.Account;
import com.nftmarketplace.identity_service.model.dto.APIResponse;
import com.nftmarketplace.identity_service.model.dto.request.AccountRequest;
import com.nftmarketplace.identity_service.service.AccountService;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountController {
    AccountService accountService;

    @PostMapping("/register")
    public APIResponse<Account> createAccount(@ModelAttribute @Valid AccountRequest request) {
        Account account = accountService.createAccount(request);
        return APIResponse.<Account>builder()
                .result(account)
                .build();
    }

    @GetMapping
    public APIResponse<Account> getAccount(@RequestParam String accountId) {
        Account account = accountService.getAccount(accountId);
        return APIResponse.<Account>builder()
                .result(account)
                .build();
    }

    @GetMapping("/all")
    public APIResponse<List<Account>> getAllAccounts() {
        List<Account> allAccounts = accountService.getAllAccounts();
        return APIResponse.<List<Account>>builder()
                .result(allAccounts)
                .build();
    }

    @PutMapping
    public APIResponse<Account> updateAccount(@RequestParam String accountId, @ModelAttribute AccountRequest request) {
        Account account = accountService.updateAccount(accountId, request);
        return APIResponse.<Account>builder()
                .result(account)
                .build();
    }

    @DeleteMapping
    public APIResponse<?> deleteAccount(@RequestParam String accountId) {
        String message = accountService.deleteAccount(accountId);
        return APIResponse.<Void>builder()
                .message(message)
                .build();
    }
}
