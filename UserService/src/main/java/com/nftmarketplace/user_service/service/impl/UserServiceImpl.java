package com.nftmarketplace.user_service.service.impl;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.nftmarketplace.user_service.event.producer.EventProducer;
import com.nftmarketplace.user_service.exception.AppException;
import com.nftmarketplace.user_service.exception.ErrorCode;
import com.nftmarketplace.user_service.model.dto.request.UserRequest;
import com.nftmarketplace.user_service.model.dto.response.UserFlat;
import com.nftmarketplace.user_service.model.enums.FriendStatus;
import com.nftmarketplace.user_service.model.enums.MessageType;
import com.nftmarketplace.user_service.model.kafkaModel.CreateAccountKafka;
import com.nftmarketplace.user_service.model.kafkaModel.NotificationKafka;

import com.nftmarketplace.user_service.model.node.User;
import com.nftmarketplace.user_service.repository.UserRepository;
import com.nftmarketplace.user_service.service.CloudinaryService;
import com.nftmarketplace.user_service.service.UserService;
import com.nftmarketplace.user_service.utils.mapper.UserMapper;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserServiceImpl implements UserService {
    UserRepository userRepository;
    EventProducer eventProducer;
    CloudinaryService cloudinaryService;
    Gson gson = new Gson();

    @Override
    public Mono<UserFlat> createUser(UserRequest request) {
        return Mono.zip(
                userRepository.existsById(request.getId()),
                userRepository.existsByUsername(request.getUsername()),
                userRepository.existsByEmail(request.getEmail()),
                userRepository.existsByPhoneNumber(request.getPhoneNumber()))
                .flatMap(tuple -> {
                    if (tuple.getT1() || tuple.getT2() || tuple.getT3() || tuple.getT4())
                        return Mono.error(new AppException(ErrorCode.EXISTED));
                    User user = UserMapper.INSTANCE.toUser(request);
                    if (request.getAvatarImg() != null)
                        return cloudinaryService.createAvatarPath(Mono.just(request.getAvatarImg()), request.getId())
                                .flatMap(avatarPath -> {
                                    user.setAvatarPath(avatarPath);
                                    return userRepository.save(user)
                                            .flatMap(saveUser -> Mono.just(UserMapper.INSTANCE.toUserFlat(saveUser)));
                                });
                    return userRepository.save(user)
                            .flatMap(saveUser -> Mono.just(UserMapper.INSTANCE.toUserFlat(saveUser)));
                });
    }

    @Override
    public Mono<UserFlat> createUserKafka(CreateAccountKafka request) {
        return Mono.zip(
                userRepository.existsById(request.getId()),
                userRepository.existsByUsername(request.getUsername()),
                userRepository.existsByEmail(request.getUsername())).flatMap(tuple -> {
                    if (tuple.getT1() || tuple.getT2() || tuple.getT3())
                        return Mono.error((new AppException(ErrorCode.EXISTED)));
                    User user = UserMapper.INSTANCE.toUser(request);
                    return userRepository.save(user)
                            .flatMap(saveUser -> Mono.just(UserMapper.INSTANCE.toUserFlat(saveUser)));
                });
    }

    @Override
    public Mono<UserFlat> getUser(String userId) {
        return checkExistUsers(userId).then(Mono.defer(() -> userRepository.findByIdFlat(userId)));
    }

    @Override
    public Flux<UserFlat> getAllUsers() {
        Flux<UserFlat> allUsersFlat = userRepository.findAllFlat();
        return allUsersFlat;
    }

    @Override
    public Mono<UserFlat> updateUser(String userId, UserRequest request) {
        return userRepository.findById(userId)
                .switchIfEmpty(Mono.error(new AppException(ErrorCode.NOT_EXISTED)))
                .flatMap(user -> {
                    User userUpdate = UserMapper.INSTANCE.toUSer(request, user);
                    return userRepository.save(userUpdate)
                            .flatMap(saveUser -> Mono.just(UserMapper.INSTANCE.toUserFlat(saveUser)));
                });
    };

    @Override
    public Mono<String> deleteUser(String userId) {
        return checkExistUsers(userId)
                .then(Mono.defer(() -> userRepository.deleteById(userId).then(Mono.just("Delete completed!"))));
    }

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
                                            .userRequestName(user.getFirstName() + user.getLastName())
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
                                        .userRequestName(user.getFirstName() + user.getLastName())
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
                                        .userRequestName(user.getFirstName() + user.getLastName())
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
                                        .userRequestName(user.getFirstName() + user.getLastName())
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
                                        .userRequestName(user.getFirstName() + user.getLastName())
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
