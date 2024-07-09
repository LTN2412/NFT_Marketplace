package com.nftmarketplace.identity_service.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nftmarketplace.identity_service.dto.request.PermissionRequest;
import com.nftmarketplace.identity_service.exception.AppException;
import com.nftmarketplace.identity_service.exception.ErrorCode;
import com.nftmarketplace.identity_service.mapper.PermissionMapper;
import com.nftmarketplace.identity_service.model.Permission;
import com.nftmarketplace.identity_service.repository.PermissionRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionService {
    PermissionRepository permissionRepository;

    public Permission createPermission(PermissionRequest request) {
        if (permissionRepository.existsById(request.getName()))
            throw new AppException(ErrorCode.EXISTED);
        Permission permission = PermissionMapper.INSTANCE.toPermission(request);
        return permissionRepository.save(permission);
    }

    public Permission getPermission(String name) {
        return permissionRepository.findById(name).orElseThrow(() -> new AppException(ErrorCode.NOT_EXISTED));
    }

    public List<Permission> getAllPermission() {
        return permissionRepository.findAll();
    }

    public Permission updatePermission(PermissionRequest request) {
        if (!permissionRepository.existsById(request.getName()))
            throw new AppException(ErrorCode.NOT_EXISTED);
        Permission permission = PermissionMapper.INSTANCE.toPermission(request);
        return permissionRepository.save(permission);
    }

    public Void deletePermission(String name) {
        if (!permissionRepository.existsById(name))
            throw new AppException(ErrorCode.NOT_EXISTED);
        permissionRepository.deleteById(name);
        return null;
    }
}
