package com.nftmarketplace.identity_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nftmarketplace.identity_service.model.Role;

public interface RoleRepository extends JpaRepository<Role, String> {
}
