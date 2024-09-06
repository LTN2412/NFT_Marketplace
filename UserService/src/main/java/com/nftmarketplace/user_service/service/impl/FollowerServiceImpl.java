package com.nftmarketplace.user_service.service.impl;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.nftmarketplace.user_service.event.producer.EventProducer;
import com.nftmarketplace.user_service.exception.AppException;
import com.nftmarketplace.user_service.exception.ErrorCode;
import com.nftmarketplace.user_service.model.enums.MessageType;
import com.nftmarketplace.user_service.model.kafka_model.NotificationKafka;
import com.nftmarketplace.user_service.repository.UserRepository;
import com.nftmarketplace.user_service.service.FollowerService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FollowerServiceImpl implements FollowerService {
    UserRepository userRepository;
    EventProducer eventProducer;
    Gson gson = new Gson();

    @Override
    public Mono<Boolean> checkFollowerStatus(String userRequestId, String userReceiveId) {
        return userRepository.checkFollowerStatus(userRequestId, userReceiveId);
    }

    @Override
    public Mono<String> addFollower(String userRequestId, String userReceiveId) {
        return checkExistUsers(userRequestId, userReceiveId).then(Mono.defer(() -> {
            return checkFollowerStatus(userRequestId, userReceiveId).flatMap(isFollow -> {
                if (isFollow)
                    return Mono.just("Already follow!");
                return userRepository.addFollower(userRequestId, userReceiveId)
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
                        .then(Mono.just("Follow completed!"));
            });
        }));
    }

    @Override
    public Mono<String> unFollower(String userRequestId, String userReceiveId) {
        return checkExistUsers(userRequestId, userReceiveId).then(Mono.defer(() -> {
            return checkFollowerStatus(userRequestId, userReceiveId).flatMap(isFollow -> {
                if (!isFollow)
                    return Mono.just("Not follow to unfollow!");
                return userRepository.unFollower(userRequestId, userReceiveId)
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
                        .then(Mono.just("Unfollow completed!"));
            });
        }));
    }

    @Override
    public Mono<Set<String>> getAllFollowers(String userId) {
        return checkExistUsers(userId).then(userRepository.findFollowerIds(userId).collect(Collectors.toSet()));
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
