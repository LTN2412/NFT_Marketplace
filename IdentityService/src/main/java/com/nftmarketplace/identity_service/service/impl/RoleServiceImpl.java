package com.nftmarketplace.identity_service.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nftmarketplace.identity_service.exception.AppException;
import com.nftmarketplace.identity_service.exception.ErrorCode;
import com.nftmarketplace.identity_service.model.Role;
import com.nftmarketplace.identity_service.model.dto.request.RoleRequest;
import com.nftmarketplace.identity_service.repository.RoleRepository;
import com.nftmarketplace.identity_service.service.RoleService;
import com.nftmarketplace.identity_service.utils.RoleMapper;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleServiceImpl implements RoleService {

    RoleRepository roleRepository;

    @Override
    public Role createRole(RoleRequest request) {
        if (roleRepository.existsById(request.getName()))
            throw new AppException(ErrorCode.EXISTED);
        Role role = RoleMapper.INSTANCE.toRole(request);
        return roleRepository.save(role);
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Role updateRole(RoleRequest request) {
        Role role = roleRepository.findById(request.getName())
                .orElseThrow(() -> new AppException(ErrorCode.NOT_EXISTED));
        role.setDescription(request.getDescription());
        return roleRepository.save(role);
    }

    @Override
    public String deleteRole(String roleName) {
        if (!roleRepository.existsById(roleName))
            throw new AppException(ErrorCode.NOT_EXISTED);
        roleRepository.deleteById(roleName);
        return "Delete " + roleName + " successfully";
    }
}
