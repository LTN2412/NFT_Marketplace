package com.nftmarketplace.identity_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nftmarketplace.identity_service.model.Account;

public interface AccountRepository extends JpaRepository<Account, String> {
    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    Optional<Account> findByUsername(String username);
}