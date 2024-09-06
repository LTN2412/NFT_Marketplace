package com.nftmarketplace.user_service.service.impl;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.nftmarketplace.user_service.event.producer.EventProducer;
import com.nftmarketplace.user_service.exception.AppException;
import com.nftmarketplace.user_service.exception.ErrorCode;
import com.nftmarketplace.user_service.model.enums.FriendStatus;
import com.nftmarketplace.user_service.model.enums.MessageType;
import com.nftmarketplace.user_service.model.kafka_model.NotificationKafka;
import com.nftmarketplace.user_service.repository.UserRepository;
import com.nftmarketplace.user_service.service.FriendService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FriendServiceImpl implements FriendService {
    UserRepository userRepository;
    EventProducer eventProducer;
    Gson gson = new Gson();

    @Override
    public Mono<String> checkFriendStatus(String userRequestId, String userReceiveId) {
        return checkExistUsers(userRequestId, userReceiveId).then(Mono.defer(() -> {
            return userRepository.checkFriendStatus(userRequestId, userReceiveId)
                    .switchIfEmpty(Mono.just(FriendStatus.NOT_FRIEND.name()));
        }));
    }

    @Override
    public Mono<String> sendFriendRequest(String userRequestId, String userReceiveId) {
        return checkFriendStatus(userRequestId, userReceiveId).flatMap(status -> {
            FriendStatus friendStatus = FriendStatus.valueOf(status.toUpperCase());
            return switch (friendStatus) {
                case ACCEPTED -> Mono.just("Already friend!");
                case WAITING -> Mono.just("Waiting for accept!");
                case REJECTED ->
                    userRepository.handleFriendRequest(userRequestId, userReceiveId, FriendStatus.WAITING.name())
                            .doOnSuccess(_ -> {
                                userRepository.findById(userReceiveId).flatMap(user -> {
                                    NotificationKafka request = NotificationKafka.builder()
                                            .userRequestId(userRequestId)
                                            .userRequestName(user.getName())
                                            .userRequestAvatarPath(user.getAvatarPath())
                                            .userReceiveId(userReceiveId)
                                            .messageType(MessageType.ADD_FRIEND)
                                            .build();
                                    eventProducer.send("request", gson.toJson(request)).subscribe();
                                    return Mono.empty();
                                }).subscribe();
                            })
                            .then(Mono.just("Resend friend request successfully!"));
                // ** Not Friend
                default -> userRepository.sendFriendRequest(userRequestId, userReceiveId)
                        .doOnSuccess(_ -> {
                            userRepository.findById(userReceiveId).flatMap(user -> {
                                NotificationKafka request = NotificationKafka.builder()
                                        .userRequestId(userRequestId)
                                        .userRequestName(user.getName())
                                        .userRequestAvatarPath(user.getAvatarPath())
                                        .userReceiveId(userReceiveId)
                                        .messageType(MessageType.ADD_FRIEND)
                                        .build();
                                eventProducer.send("request", gson.toJson(request)).subscribe();
                                return Mono.empty();
                            }).subscribe();
                        })
                        .then(Mono.just("Create friend request successfully!"));
            };
        });
    };

    @Override
    public Mono<String> handleFriendRequest(String messageId, String userRequestId, String userReceiveId,
            FriendStatus status) {
        return checkExistUsers(userRequestId, userReceiveId).then(Mono.defer(() -> {
            return checkFriendStatus(userRequestId, userReceiveId)
                    .flatMap(friendStatus -> {
                        if (!friendStatus.equals(FriendStatus.WAITING.name())) {
                            return Mono.error(new AppException(ErrorCode.NOT_REQUEST_FRIEND));
                        }
                        return userRepository.handleFriendRequest(userRequestId, userReceiveId, status.name())
                                .doOnSuccess(_ -> {
                                    userRepository.findById(userReceiveId).flatMap(user -> {
                                        NotificationKafka request = NotificationKafka.builder()
                                                .messageId(messageId)
                                                .messageType(status.name() == FriendStatus.ACCEPTED.name()
                                                        ? MessageType.ACCEPT_FRIEND
                                                        : MessageType.REJECT_FRIEND)
                                                .build();
                                        eventProducer.send("request", gson.toJson(request)).subscribe();
                                        return Mono.empty();
                                    }).subscribe();
                                })
                                .then(Mono.just(status.getMessage()));
                    });
        }));
    }

    @Override
    public Mono<String> unFriend(String userRequestId, String userReceiveId) {
        return checkExistUsers(userRequestId, userReceiveId).then(Mono.defer(() -> {
            return checkFriendStatus(userRequestId, userReceiveId).flatMap(status -> {
                if (!status.equals(FriendStatus.ACCEPTED.name()))
                    return Mono.just("Not friend to unfriend!");
                return userRepository.unFriend(userRequestId, userReceiveId)
                        .doOnSuccess(_ -> {
                            userRepository.findById(userReceiveId).flatMap(user -> {
                                NotificationKafka request = NotificationKafka.builder()
                                        .userRequestId(userRequestId)
                                        .userRequestName(user.getName())
                                        .userRequestAvatarPath(user.getAvatarPath())
                                        .userReceiveId(userReceiveId)
                                        .messageType(MessageType.ADD_FRIEND)
                                        .build();
                                eventProducer.send("request", gson.toJson(request)).subscribe();
                                return Mono.empty();
                            }).subscribe();
                        })
                        .then(Mono.just("Unfriend completed!"));
            });
        }));
    }

    @Override
    public Mono<Set<String>> getAllFriends(String userId) {
        return checkExistUsers(userId).then(userRepository.findFriendIds(userId).collect(Collectors.toSet()));
    }

    @Override
    public Mono<Void> checkExistUsers(String... userIds) {
        return Flux.fromArray(userIds)
                .flatMap(userId -> userRepository.existsById(userId)
                        .flatMap(exist -> exist ? Mono.empty()
                                : Mono.error(new AppException(ErrorCode.NOT_EXISTED, "User " + userId + " not exist"))))
                .then();
    }
}
