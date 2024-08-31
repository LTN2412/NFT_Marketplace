package com.nftmarketplace.identity_service.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nftmarketplace.identity_service.model.Role;
import com.nftmarketplace.identity_service.model.dto.APIResponse;
import com.nftmarketplace.identity_service.model.dto.request.RoleRequest;
import com.nftmarketplace.identity_service.service.RoleService;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/role")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleController {
    RoleService roleService;

    @PostMapping
    public APIResponse<Role> createRole(@ModelAttribute @Valid RoleRequest request) {
        Role role = roleService.createRole(request);
        return APIResponse.<Role>builder()
                .result(role)
                .build();
    }

    @GetMapping("/all")
    public APIResponse<List<Role>> getAllRoles() {
        List<Role> role = roleService.getAllRoles();
        return APIResponse.<List<Role>>builder()
                .result(role)
                .build();
    }

    @PutMapping
    public APIResponse<Role> updateRole(@ModelAttribute RoleRequest request) {
        Role role = roleService.updateRole(request);
        return APIResponse.<Role>builder()
                .result(role)
                .build();
    }

    @DeleteMapping
    public APIResponse<?> deleteRole(@RequestParam String roleName) {
        String message = roleService.deleteRole(roleName);
        return APIResponse.builder()
                .message(message)
                .build();
    }
}
