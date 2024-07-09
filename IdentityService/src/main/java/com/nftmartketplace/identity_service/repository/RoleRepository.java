package com.nftmartketplace.identity_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nftmartketplace.identity_service.model.Role;

public interface RoleRepository extends JpaRepository<Role, String> {
}
