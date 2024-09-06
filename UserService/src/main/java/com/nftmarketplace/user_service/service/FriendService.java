package com.nftmarketplace.user_service.service;

import java.util.Set;

import com.nftmarketplace.user_service.model.enums.FriendStatus;

import reactor.core.publisher.Mono;

public interface FriendService {
    Mono<String> checkFriendStatus(String userRequestId, String userReceiveId);

    Mono<String> sendFriendRequest(String userRequestId, String userReceiveId);

    Mono<String> handleFriendRequest(String messageId, String userRequestId, String userReceiveId, FriendStatus status);

    Mono<String> unFriend(String userRequestId, String userReceiveId);

    Mono<Set<String>> getAllFriends(String userId);

    Mono<Void> checkExistUsers(String... userIds);
}
