package com.nftmarketplace.identity_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nftmarketplace.identity_service.model.Permission;

public interface PermissionRepository extends JpaRepository<Permission, String> {
}
