package com.nftmarketplace.user_service.service;

import java.util.Set;

import reactor.core.publisher.Mono;

public interface FollowerService {
    Mono<Boolean> checkFollowerStatus(String userRequestId, String userReceiveId);

    Mono<String> addFollower(String idUsers1, String userReceiveId);

    Mono<String> unFollower(String userRequestId, String userReceiveId);

    Mono<Set<String>> getAllFollowers(String userId);

    Mono<Void> checkExistUsers(String... userIds);
}
