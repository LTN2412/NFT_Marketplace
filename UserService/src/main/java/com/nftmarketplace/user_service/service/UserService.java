package com.nftmarketplace.user_service.service;

import java.util.Set;

import com.nftmarketplace.user_service.model.dto.request.UserRequest;
import com.nftmarketplace.user_service.model.dto.response.UserFlat;
import com.nftmarketplace.user_service.model.enums.FriendStatus;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {
    Mono<UserFlat> createUser(UserRequest request);

    Mono<UserFlat> getUser(String userId);

    Flux<UserFlat> getAllUsers();

    Mono<UserFlat> updateUser(String userId, UserRequest request);

    Mono<String> deleteUser(String userId);

    Mono<String> checkFriendStatus(String userRequestId, String userReceiveId);

    Mono<String> sendFriendRequest(String userRequestId, String userReceiveId);

    Mono<String> handleFriendRequest(String messageId, String userRequestId, String userReceiveId, FriendStatus status);

    Mono<String> unFriend(String userRequestId, String userReceiveId);

    Mono<Set<String>> getAllFriends(String userId);

    Mono<Boolean> checkFollowerStatus(String userRequestId, String userReceiveId);

    Mono<String> addFollower(String idUsers1, String userReceiveId);

    Mono<String> unFollower(String userRequestId, String userReceiveId);

    Mono<Set<String>> getAllFollowers(String userId);

    Mono<Void> checkExistUsers(String... userIds);
}
