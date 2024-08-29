package com.nftmarketplace.user_service.controller;

import org.springframework.web.bind.annotation.RestController;

import com.nftmarketplace.user_service.model.dto.APIResponse;
import com.nftmarketplace.user_service.model.dto.request.UserRequest;
import com.nftmarketplace.user_service.model.dto.response.UserFlat;
import com.nftmarketplace.user_service.model.enums.FriendStatus;
import com.nftmarketplace.user_service.service.UserService;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Set;

import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {
        UserService userService;

        @PostMapping
        public Mono<APIResponse<UserFlat>> createUser(@Valid @ModelAttribute UserRequest request) {
                return userService.createUser(request)
                                .map(user -> APIResponse
                                                .<UserFlat>builder()
                                                .result(user)
                                                .build());
        }

        @GetMapping
        // @PreAuthorize("hasAuthority('SCOPE_USER')")
        public Mono<APIResponse<UserFlat>> getUser(@RequestParam String userId) {
                return userService.getUser(userId)
                                .map(user -> APIResponse
                                                .<UserFlat>builder()
                                                .result(user)
                                                .build());
        }

        @GetMapping(path = "/all", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
        public Flux<APIResponse<UserFlat>> findAllUsers() {
                return userService.getAllUsers()
                                .map(user -> APIResponse
                                                .<UserFlat>builder()
                                                .result(user)
                                                .build());
        }

        @PutMapping
        public Mono<APIResponse<UserFlat>> updateUser(@RequestParam String userId, @RequestBody UserRequest request) {
                return userService.updateUser(userId, request)
                                .map(user -> APIResponse
                                                .<UserFlat>builder()
                                                .result(user)
                                                .build());
        }

        @DeleteMapping
        public Mono<APIResponse<?>> deleteUser(@RequestParam String userId) {
                return userService.deleteUser(userId)
                                .map(message -> APIResponse
                                                .builder()
                                                .message(message)
                                                .build());
        }

        @PostMapping("/friendRequest")
        public Mono<APIResponse<?>> sendFriendRequest(@RequestParam String userRequestId, String userReceiveId) {
                return userService.sendFriendRequest(userRequestId, userReceiveId)
                                .map(message -> APIResponse
                                                .builder()
                                                .message(message)
                                                .build());
        }

        @PostMapping("/acceptFriend")
        public Mono<APIResponse<?>> addFriend(@RequestParam String messageId, String userRequestId,
                        String userReceiveId) {
                return userService.handleFriendRequest(messageId, userRequestId, userReceiveId, FriendStatus.ACCEPTED)
                                .map(message -> APIResponse
                                                .builder()
                                                .message(message)
                                                .build());
        }

        @PostMapping("/rejectFriend")
        public Mono<APIResponse<?>> rejectFriend(@RequestParam String messageId, String userRequestId,
                        String userReceiveId) {
                return userService.handleFriendRequest(messageId, userRequestId, userReceiveId, FriendStatus.REJECTED)
                                .map(message -> APIResponse
                                                .builder()
                                                .message(message)
                                                .build());
        }

        @GetMapping("/friends")
        public Mono<APIResponse<Set<String>>> getAllFriends(@RequestParam String userId) {
                return userService.getAllFriends(userId)
                                .map(userIds -> APIResponse
                                                .<Set<String>>builder()
                                                .result(userIds)
                                                .build());
        }

        @DeleteMapping("/unFriend")
        public Mono<APIResponse<?>> unFriend(@RequestParam String userRequestId, String userReceiveId) {
                return userService.unFriend(userRequestId, userReceiveId)
                                .map(message -> APIResponse
                                                .builder()
                                                .message(message)
                                                .build());
        }

        @PostMapping("/addFollower")
        public Mono<APIResponse<?>> addFollower(@RequestParam String userRequestId, String userReceiveId) {
                return userService.addFollower(userRequestId, userReceiveId)
                                .map(message -> APIResponse
                                                .builder()
                                                .message(message)
                                                .build());
        }

        @GetMapping("/followers")
        public Mono<APIResponse<Set<String>>> getAllFollowers(@RequestParam String userId) {
                return userService.getAllFollowers(userId)
                                .map(userIds -> APIResponse
                                                .<Set<String>>builder()
                                                .result(userIds)
                                                .build());
        }

        @DeleteMapping("/unFollower")
        public Mono<APIResponse<?>> unFollower(@RequestParam String userRequestId, String userReceiveId) {
                return userService.unFollower(userRequestId, userReceiveId)
                                .map(message -> APIResponse
                                                .builder()
                                                .message(message)
                                                .build());
        }
}
