package com.nftmartketplace.identity_service.controller;

import org.springframework.web.bind.annotation.RestController;

import com.nftmartketplace.identity_service.dto.APIResponse;
import com.nftmartketplace.identity_service.dto.request.RoleRequest;
import com.nftmartketplace.identity_service.model.Role;
import com.nftmartketplace.identity_service.service.RoleService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleController {
    RoleService roleService;

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @PostMapping("/role")
    public APIResponse<Role> createRole(@RequestBody RoleRequest request) {
        Role role = roleService.createRole(request);
        return APIResponse.<Role>builder()
                .result(role)
                .build();
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @GetMapping("/role")
    public APIResponse<Role> getRole(@RequestParam String name) {
        Role role = roleService.getRole(name);
        return APIResponse.<Role>builder()
                .result(role)
                .build();
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @GetMapping("/roles")
    public APIResponse<List<Role>> getAllRole() {
        List<Role> allRole = roleService.getAllRoles();
        return APIResponse.<List<Role>>builder()
                .result(allRole)
                .build();
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @PutMapping("/role")
    public APIResponse<Role> updateRole(@RequestBody RoleRequest request) {
        Role updateRole = roleService.updateRole(request);
        return APIResponse.<Role>builder()
                .result(updateRole)
                .build();
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @DeleteMapping("/role")
    public APIResponse<Void> deleteRole(@RequestParam String name) {
        roleService.deleteRole(name);
        return APIResponse.<Void>builder()
                .message("Delete role completed!")
                .build();
    }
}
