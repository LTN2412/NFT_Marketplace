package com.nftmarketplace.identity_service.service;

import java.util.List;

import com.nftmarketplace.identity_service.model.Role;
import com.nftmarketplace.identity_service.model.dto.request.RoleRequest;

public interface RoleService {
    Role createRole(RoleRequest request);

    List<Role> getAllRoles();

    Role updateRole(RoleRequest request);

    String deleteRole(String roleName);
}
