package com.nftmarketplace.user_service.controller;

import org.springframework.web.bind.annotation.RestController;

import com.nftmarketplace.user_service.model.User;
import com.nftmarketplace.user_service.model.dto.APIResponse;
import com.nftmarketplace.user_service.model.dto.request.UserRequest;
import com.nftmarketplace.user_service.service.UserService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    UserService userService;

    @PostMapping("")
    public APIResponse<User> createUser(@RequestBody UserRequest request) {
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

    @GetMapping("/all")
    public APIResponse<List<User>> getAllUsers() {
        List<User> allUsers = userService.getAllUsers();
        return APIResponse.<List<User>>builder()
                .result(allUsers)
                .build();
    }

    @PutMapping("")
    public APIResponse<User> updateString(@RequestParam String id, @RequestBody UserRequest request) {
        User updateUser = userService.updateUser(id, request);
        return APIResponse.<User>builder()
                .result(updateUser)
                .build();
    }

    @DeleteMapping("")
    public APIResponse<Void> deleteUser(@RequestParam String id) {
        userService.deleteUser(id);
        return APIResponse.<Void>builder()
                .message("Delete completed!")
                .build();
    }

}
