package com.nftmartketplace.identity_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nftmartketplace.identity_service.model.Permission;

public interface PermissionRepository extends JpaRepository<Permission, String> {
}
