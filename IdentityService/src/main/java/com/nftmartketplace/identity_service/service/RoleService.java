package com.nftmartketplace.identity_service.service;

import java.util.HashSet;
import java.util.List;

import org.springframework.stereotype.Service;

import com.nftmartketplace.identity_service.dto.request.RoleRequest;
import com.nftmartketplace.identity_service.exception.AppException;
import com.nftmartketplace.identity_service.exception.ErrorCode;
import com.nftmartketplace.identity_service.mapper.RoleMapper;
import com.nftmartketplace.identity_service.model.Permission;
import com.nftmartketplace.identity_service.model.Role;
import com.nftmartketplace.identity_service.repository.PermissionRepository;
import com.nftmartketplace.identity_service.repository.RoleRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleService {
    RoleRepository roleRepository;
    PermissionRepository permissionRepository;

    public Role createRole(RoleRequest request) {
        if (roleRepository.existsById(request.getName()))
            throw new AppException(ErrorCode.EXISTED);
        Role role = RoleMapper.INSTANCE.toRole(request);
        List<Permission> permissions = permissionRepository.findAllById(request.getPermissions());
        role.setPermissions(new HashSet<>(permissions));
        return roleRepository.save(role);
    }

    public Role getRole(String name) {
        return roleRepository.findById(name).orElseThrow(() -> new AppException(ErrorCode.NOT_EXISTED));
    }

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public Role updateRole(RoleRequest request) {
        if (!roleRepository.existsById(request.getName()))
            throw new AppException(ErrorCode.NOT_EXISTED);
        Role role = RoleMapper.INSTANCE.toRole(request);
        List<Permission> permissions = permissionRepository.findAllById(request.getPermissions());
        role.setPermissions(new HashSet<>(permissions));
        return roleRepository.save(role);
    }

    public Void deleteRole(String name) {
        if (!roleRepository.existsById(name))
            throw new AppException(ErrorCode.NOT_EXISTED);
        roleRepository.deleteById(name);
        return null;
    }

}
