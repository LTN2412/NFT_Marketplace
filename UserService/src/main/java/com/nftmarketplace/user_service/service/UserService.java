package com.nftmarketplace.user_service.service;

import java.util.Set;

import com.nftmarketplace.user_service.model.dto.request.UserRequest;
import com.nftmarketplace.user_service.model.dto.response.UserFlat;
import com.nftmarketplace.user_service.model.enums.FriendStatus;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {
    Mono<UserFlat> createUser(UserRequest request);

    Mono<UserFlat> getUser(String id);

    Flux<UserFlat> getAllUsers();

    Mono<UserFlat> updateUser(String id, UserRequest request);

    Mono<String> deleteUser(String id);

    Mono<String> checkFriendStatus(String userId1, String userId2);

    Mono<String> sendFriendRequest(String userId1, String userId2);

    Mono<String> handleFriendRequest(String userId1, String userId2, FriendStatus status);

    Mono<String> unFriend(String userId1, String userId2);

    Mono<Set<String>> getAllFriends(String userId);

    Mono<Boolean> checkFollowerStatus(String userId1, String userId2);

    Mono<String> addFollower(String idUsers1, String userId2);

    Mono<String> unFollower(String userId1, String userId2);

    Mono<Set<String>> getAllFollowers(String userId);

    Mono<Void> checkExistUsers(String... userIds);
}
