package com.nftmarketplace.identity_service.service;

import java.util.List;

import com.nftmarketplace.identity_service.model.Account;
import com.nftmarketplace.identity_service.model.dto.request.AccountRequest;
import com.nftmarketplace.identity_service.model.kafka_model.CreateAccountKafka;

public interface AccountService {
    Account createAccount(AccountRequest request);

    Account getAccount(String accountId);

    List<Account> getAllAccounts();

    Account updateAccount(String accountId, AccountRequest request);

    String deleteAccount(String accountId);

    Void sendMessageCreateAccount(CreateAccountKafka account);
}
