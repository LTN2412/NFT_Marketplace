package com.nftmarketplace.identity_service.controller;

import org.springframework.web.bind.annotation.RestController;

import com.nftmarketplace.identity_service.dto.APIResponse;
import com.nftmarketplace.identity_service.dto.request.UserCreationRequest;
import com.nftmarketplace.identity_service.model.User;
import com.nftmarketplace.identity_service.service.UserService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/identity/user")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    UserService userService;

    @PostMapping("/register")
    public APIResponse<User> createUser(@RequestBody UserCreationRequest request) {
        User user = userService.createUser(request);
        return APIResponse.<User>builder()
                .result(user)
                .build();
    }

    @GetMapping("")
    public APIResponse<User> getUser(@RequestParam String id) {
        User user = userService.getUser(id);
        return APIResponse.<User>builder()
                .result(user)
                .build();
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @GetMapping("/all")
    public APIResponse<List<User>> getAllUser() {
        List<User> users = userService.getAllUsers();
        return APIResponse.<List<User>>builder()
                .result(users)
                .build();
    }

    @PutMapping("")
    public APIResponse<User> updateUser(@RequestBody UserCreationRequest request) {
        User updateUser = userService.updateUser(request);
        return APIResponse.<User>builder()
                .result(updateUser)
                .build();
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @DeleteMapping("")
    public APIResponse<Void> deleteUser(String id) {
        userService.deleteUser(id);
        return APIResponse.<Void>builder()
                .message("Delete user completed!")
                .build();
    }
}
