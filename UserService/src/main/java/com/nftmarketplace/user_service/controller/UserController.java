package com.nftmarketplace.user_service.controller;

import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.nftmarketplace.user_service.event.EventProducer;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
        UserService userService;
        EventProducer eventProducer;

        Gson gson = new Gson();

        @PostMapping
        public Mono<APIResponse<UserFlat>> createUser(@Valid @ModelAttribute UserRequest request) {
                return userService.createUser(request)
                                .flatMap(user -> Mono.just(APIResponse
                                                .<UserFlat>builder()
                                                .result(user)
                                                .build()));
        }

        @GetMapping
        // @PreAuthorize("hasAuthority('SCOPE_USER')")
        public Mono<APIResponse<UserFlat>> getUser(@RequestParam String id) {
                return userService.getUser(id)
                                .flatMap(user -> Mono.just(APIResponse
                                                .<UserFlat>builder()
                                                .result(user)
                                                .build()))
                                .doOnSuccess(user -> eventProducer.send("order", gson.toJson(user.getResult()))
                                                .subscribe());
        }

        @GetMapping(path = "/all", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
        public Flux<APIResponse<UserFlat>> findAllUsers() {
                return userService.getAllUsers()
                                .flatMap(user -> Mono.just(APIResponse
                                                .<UserFlat>builder()
                                                .result(user)
                                                .build()));
        }

        @PutMapping
        public Mono<APIResponse<UserFlat>> updateUser(@RequestParam String id, @RequestBody UserRequest request) {
                return userService.updateUser(id, request)
                                .flatMap(user -> Mono.just(APIResponse
                                                .<UserFlat>builder()
                                                .result(user)
                                                .build()));
        }

        @DeleteMapping
        public Mono<APIResponse<?>> deleteUser(@RequestParam String id) {
                return userService.deleteUser(id)
                                .flatMap(message -> Mono.just(APIResponse
                                                .builder()
                                                .message(message)
                                                .build()));
        }

        @PostMapping("/friendRequest")
        public Mono<APIResponse<?>> sendFriendRequest(@RequestParam String userId1, String userId2) {
                return userService.sendFriendRequest(userId1, userId2)
                                .flatMap(message -> Mono.just(APIResponse
                                                .builder()
                                                .message(message)
                                                .build()));
        }

        @PostMapping("/acceptFriend")
        public Mono<APIResponse<?>> addFriend(@RequestParam String userId1, String userId2) {
                return userService.handleFriendRequest(userId1, userId2, FriendStatus.ACCEPTED)
                                .flatMap(message -> Mono.just(APIResponse
                                                .builder()
                                                .message(message)
                                                .build()));
        }

        @PostMapping("/rejectFriend")
        public Mono<APIResponse<?>> rejectFriend(@RequestParam String userId1, String userId2) {
                return userService.handleFriendRequest(userId1, userId2, FriendStatus.REJECTED)
                                .flatMap(message -> Mono.just(APIResponse
                                                .builder()
                                                .message(message)
                                                .build()));
        }

        @GetMapping("/friends")
        public Mono<APIResponse<Set<String>>> getAllFriends(@RequestParam String userId) {
                return userService.getAllFriends(userId)
                                .flatMap(userIds -> Mono.just(APIResponse
                                                .<Set<String>>builder()
                                                .result(userIds)
                                                .build()));
        }

        @DeleteMapping("/unFriend")
        public Mono<APIResponse<?>> unFriend(@RequestParam String userId1, String userId2) {
                return userService.unFriend(userId1, userId2)
                                .flatMap(message -> Mono.just(APIResponse
                                                .builder()
                                                .message(message)
                                                .build()));
        }

        @PostMapping("/addFollower")
        public Mono<APIResponse<?>> addFollower(@RequestParam String userId1, String userId2) {
                return userService.addFollower(userId1, userId2)
                                .flatMap(message -> Mono.just(APIResponse
                                                .builder()
                                                .message(message)
                                                .build()));
        }

        @GetMapping("/followers")
        public Mono<APIResponse<Set<String>>> getAllFollowers(@RequestParam String userId) {
                return userService.getAllFollowers(userId)
                                .flatMap(userIds -> Mono.just(APIResponse
                                                .<Set<String>>builder()
                                                .result(userIds)
                                                .build()));
        }

        @DeleteMapping("/unFollower")
        public Mono<APIResponse<?>> unFollower(@RequestParam String userId1, String userId2) {
                return userService.unFollower(userId1, userId2)
                                .flatMap(message -> Mono.just(APIResponse
                                                .builder()
                                                .message(message)
                                                .build()));
        }
}
