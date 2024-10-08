package com.nftmarketplace.user_service.controller;

import org.springframework.web.bind.annotation.RestController;

import com.nftmarketplace.user_service.model.dto.APIResponse;
import com.nftmarketplace.user_service.model.dto.request.UpdateUserRequest;
import com.nftmarketplace.user_service.model.dto.request.UserRequest;
import com.nftmarketplace.user_service.model.dto.response.UserFlat;
import com.nftmarketplace.user_service.model.enums.FriendStatus;
import com.nftmarketplace.user_service.service.FollowerService;
import com.nftmarketplace.user_service.service.FriendService;
import com.nftmarketplace.user_service.service.UserService;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Set;

import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
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
        FriendService friendService;
        FollowerService followerService;

        @PostMapping
        public Mono<APIResponse<UserFlat>> createUser(@ModelAttribute @Valid UserRequest request) {
                return userService.createUser(request)
                                .map(user -> APIResponse
                                                .<UserFlat>builder()
                                                .result(user)
                                                .build());
        }

        @PostMapping("/changeAuthor")
        public Mono<APIResponse<UserFlat>> changeToAuthor(@AuthenticationPrincipal Jwt jwt) {
                return userService.changeToAuthor(jwt.getSubject())
                                .map(user -> APIResponse
                                                .<UserFlat>builder()
                                                .result(user)
                                                .build());
        }

        @GetMapping
        public Mono<APIResponse<UserFlat>> getUser(@AuthenticationPrincipal Jwt jwt) {
                return userService.getUser(jwt.getSubject())
                                .map(user -> APIResponse
                                                .<UserFlat>builder()
                                                .result(user)
                                                .build());
        }

        @PreAuthorize("hasAuthority('ROLE_ADMIN')")
        @GetMapping(path = "/all", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
        public Flux<APIResponse<UserFlat>> findAllUsers() {
                return userService.getAllUsers()
                                .map(user -> APIResponse
                                                .<UserFlat>builder()
                                                .result(user)
                                                .build());
        }

        @PostMapping("/avatar")
        public Mono<UserFlat> updateAvatar(@RequestParam String userId, FilePart avatar) {
                return userService.updateUserImage(userId, avatar, true);
        }

        @PostMapping("/coverImg")
        public Mono<UserFlat> updateCoverImg(@RequestParam String userId, FilePart coverImg) {
                return userService.updateUserImage(userId, coverImg, false);
        }

        @PutMapping
        public Mono<APIResponse<UserFlat>> updateUser(@AuthenticationPrincipal Jwt jwt,
                        @ModelAttribute @Valid UpdateUserRequest request) {
                return userService.updateUser(jwt.getSubject(), request)
                                .map(user -> APIResponse
                                                .<UserFlat>builder()
                                                .result(user)
                                                .build());
        }

        @PreAuthorize("hasAuthority('ROLE_ADMIN')")
        @DeleteMapping
        public Mono<APIResponse<?>> deleteUser(@RequestParam String userId) {
                return userService.deleteUser(userId)
                                .map(message -> APIResponse
                                                .builder()
                                                .message(message)
                                                .build());
        }

        @PutMapping("/friendRequest")
        public Mono<APIResponse<?>> sendFriendRequest(@AuthenticationPrincipal Jwt jwt, String userReceiveId) {
                return friendService.sendFriendRequest(jwt.getSubject(), userReceiveId)
                                .map(message -> APIResponse
                                                .builder()
                                                .message(message)
                                                .build());
        }

        @PutMapping("/acceptFriend")
        public Mono<APIResponse<?>> addFriend(@AuthenticationPrincipal Jwt jwt, @RequestParam String messageId,
                        String userReceiveId) {
                return friendService
                                .handleFriendRequest(messageId, jwt.getSubject(), userReceiveId, FriendStatus.ACCEPTED)
                                .map(message -> APIResponse
                                                .builder()
                                                .message(message)
                                                .build());
        }

        @PutMapping("/rejectFriend")
        public Mono<APIResponse<?>> rejectFriend(@AuthenticationPrincipal Jwt jwt, @RequestParam String messageId,
                        String userReceiveId) {
                return friendService
                                .handleFriendRequest(messageId, jwt.getSubject(), userReceiveId, FriendStatus.REJECTED)
                                .map(message -> APIResponse
                                                .builder()
                                                .message(message)
                                                .build());
        }

        @GetMapping("/friends")
        public Mono<APIResponse<Set<String>>> getAllFriends(@AuthenticationPrincipal Jwt jwt) {
                return friendService.getAllFriends(jwt.getSubject())
                                .map(userIds -> APIResponse
                                                .<Set<String>>builder()
                                                .result(userIds)
                                                .build());
        }

        @DeleteMapping("/unFriend")
        public Mono<APIResponse<?>> unFriend(@AuthenticationPrincipal Jwt jwt, String userReceiveId) {
                return friendService.unFriend(jwt.getSubject(), userReceiveId)
                                .map(message -> APIResponse
                                                .builder()
                                                .message(message)
                                                .build());
        }

        @GetMapping("/checkFollower")
        public Mono<APIResponse<Boolean>> checkFollowerStatus(@AuthenticationPrincipal Jwt jwt,
                        @RequestParam String userId) {
                return followerService.checkFollowerStatus(jwt.getSubject(), userId)
                                .map(result -> APIResponse
                                                .<Boolean>builder()
                                                .result(result)
                                                .build());
        }

        @PutMapping("/addFollower")
        public Mono<APIResponse<?>> addFollower(@AuthenticationPrincipal Jwt jwt, @RequestParam String userReceiveId) {
                return followerService.addFollower(jwt.getSubject(), userReceiveId)
                                .map(message -> APIResponse
                                                .builder()
                                                .message(message)
                                                .build());
        }

        @GetMapping("/followers")
        public Mono<APIResponse<Set<String>>> getAllFollowers(@AuthenticationPrincipal Jwt jwt) {
                return followerService.getAllFollowers(jwt.getSubject())
                                .map(userIds -> APIResponse
                                                .<Set<String>>builder()
                                                .result(userIds)
                                                .build());
        }

        @DeleteMapping("/unFollower")
        public Mono<APIResponse<?>> unFollower(@AuthenticationPrincipal Jwt jwt, @RequestParam String userReceiveId) {
                return followerService.unFollower(jwt.getSubject(), userReceiveId)
                                .map(message -> APIResponse
                                                .builder()
                                                .message(message)
                                                .build());
        }
}
