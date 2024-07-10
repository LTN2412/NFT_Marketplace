package com.nftmarketplace.identity_service.controller;

import org.springframework.web.bind.annotation.RestController;

import com.nftmarketplace.identity_service.dto.APIResponse;
import com.nftmarketplace.identity_service.dto.request.PermissionRequest;
import com.nftmarketplace.identity_service.model.Permission;
import com.nftmarketplace.identity_service.service.PermissionService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/identity/permission")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionController {
    PermissionService permissionService;

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @PostMapping("")
    public APIResponse<Permission> createPermission(@RequestBody PermissionRequest request) {
        Permission permission = permissionService.createPermission(request);
        return APIResponse.<Permission>builder()
                .result(permission)
                .build();
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @GetMapping("")
    public APIResponse<Permission> getPermission(@RequestParam String name) {
        Permission permission = permissionService.getPermission(name);
        return APIResponse.<Permission>builder()
                .result(permission)
                .build();
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @GetMapping("/all")
    public APIResponse<List<Permission>> getAllPermission() {
        List<Permission> allPermission = permissionService.getAllPermission();
        return APIResponse.<List<Permission>>builder()
                .result(allPermission)
                .build();
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @PutMapping("")
    public APIResponse<Permission> updatePermission(@RequestBody PermissionRequest request) {
        Permission updatePermission = permissionService.updatePermission(request);
        return APIResponse.<Permission>builder()
                .result(updatePermission)
                .build();
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @DeleteMapping("")
    public APIResponse<Void> deletePermission(@RequestParam String name) {
        permissionService.deletePermission(name);
        return APIResponse.<Void>builder()
                .message("Delete permission completed!")
                .build();
    }
}
