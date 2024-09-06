package com.nftmarketplace.identity_service.service.impl;

import java.util.HashSet;
import java.util.List;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.nftmarketplace.identity_service.exception.AppException;
import com.nftmarketplace.identity_service.exception.ErrorCode;
import com.nftmarketplace.identity_service.model.Account;
import com.nftmarketplace.identity_service.model.Role;
import com.nftmarketplace.identity_service.model.dto.request.AccountRequest;
import com.nftmarketplace.identity_service.model.kafka_model.CreateAccountKafka;
import com.nftmarketplace.identity_service.repository.AccountRepository;
import com.nftmarketplace.identity_service.repository.RoleRepository;
import com.nftmarketplace.identity_service.service.AccountService;
import com.nftmarketplace.identity_service.utils.AccountMapper;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountServiceImpl implements AccountService {
    AccountRepository accountRepository;
    RoleRepository roleRepository;
    PasswordEncoder passwordEncoder;
    KafkaTemplate<String, String> kafkaTemplate;
    Gson gson = new Gson();

    @Override
    public Account createAccount(AccountRequest request) {
        if (accountRepository.existsByUsername(request.getUsername()))
            throw new AppException(ErrorCode.EXISTED, "Username " + request.getUsername() + " exists");
        if (accountRepository.existsByEmail(request.getEmail()))
            throw new AppException(ErrorCode.EXISTED, "Email " + request.getEmail() + " exists");
        Account requestAccount = AccountMapper.INSTANCE.toAccount(request);
        requestAccount.setPassword(passwordEncoder.encode(request.getPassword()));
        if (request.getRoles() != null) {
            List<Role> roles = roleRepository.findAllById(request.getRoles());
            requestAccount.setRoles(new HashSet<>(roles));
        }
        Account account = accountRepository.save(requestAccount);
        sendMessageCreateAccount(AccountMapper.INSTANCE.toCreateAccounKafka(account));
        return account;
    }

    @Override
    public Account getAccount(String accountId) {
        return accountRepository.findById(accountId).orElseThrow(() -> new AppException(ErrorCode.NOT_EXISTED));
    }

    @Override
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public Account updateAccount(String accountId, AccountRequest request) {
        Account oldAccount = accountRepository.findById(accountId)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_EXISTED));
        if (request.getUsername() != null)
            throw new AppException(ErrorCode.CAN_NOT_UPDATE, "Can't not update username");
        Account newAccount = AccountMapper.INSTANCE.toAccount(request, oldAccount);
        if (request.getPassword() != null) {
            if (!passwordEncoder.matches(request.getPassword(), oldAccount.getPassword()))
                newAccount.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        if (request.getRoles() != null) {
            List<Role> roles = roleRepository.findAllById(request.getRoles());
            newAccount.setRoles(new HashSet<>(roles));
        }
        return accountRepository.save(newAccount);
    }

    @Override
    public String deleteAccount(String accountId) {
        if (!accountRepository.existsById(accountId))
            throw new AppException(ErrorCode.NOT_EXISTED);
        accountRepository.deleteById(accountId);
        return "Delete user account " + accountId + " successfully";
    }

    @Override
    public Void sendMessageCreateAccount(CreateAccountKafka account) {
        String topic = "create_account";
        String message = gson.toJson(account);
        kafkaTemplate.send(topic, message);
        return null;
    }
}
