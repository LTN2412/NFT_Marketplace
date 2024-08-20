package com.nftmarketplace.user_service.service.impl;

import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.nftmarketplace.user_service.exception.AppException;
import com.nftmarketplace.user_service.exception.ErrorCode;
import com.nftmarketplace.user_service.model.dto.request.UserRequest;
import com.nftmarketplace.user_service.model.dto.response.UserFlat;
import com.nftmarketplace.user_service.model.enums.FriendStatus;
import com.nftmarketplace.user_service.model.node.User;
import com.nftmarketplace.user_service.repository.UserRepository;
import com.nftmarketplace.user_service.service.CloudinaryService;
import com.nftmarketplace.user_service.service.UserService;
import com.nftmarketplace.user_service.utils.mapper.UserMapper;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {
    UserRepository userRepository;
    CloudinaryService cloudinaryService;

    @Override
    public Mono<UserFlat> createUser(UserRequest request) {
        return Mono.zip(
                userRepository.existsById(request.getId()),
                userRepository.existsByUsername(request.getUsername()),
                userRepository.existsByEmail(request.getUsername()),
                userRepository.existsByPhoneNumber(request.getUsername()))
                .flatMap(tuple -> {
                    if (tuple.getT1() || tuple.getT2() || tuple.getT3() || tuple.getT4())
                        return Mono.error(new AppException(ErrorCode.EXISTED));
                    User user = UserMapper.INSTANCE.toUser(request);
                    user.setCreatedAt(new Date());
                    user.setUpdatedAt(new Date());
                    user.setLastLogin(new Date());
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
    public Mono<String> checkFriendStatus(String userId1, String userId2) {
        return checkExistUsers(userId1, userId2).then(Mono.defer(() -> {
            return userRepository.checkFriendStatus(userId1, userId2)
                    .switchIfEmpty(Mono.just(FriendStatus.NOT_FRIEND.getStatus()));
        }));
    }

    @Override
    public Mono<String> sendFriendRequest(String userId1, String userId2) {
        return checkFriendStatus(userId1, userId2).flatMap(status -> {
            FriendStatus friendStatus = FriendStatus.valueOf(status.toUpperCase());
            return switch (friendStatus) {
                case ACCEPTED -> Mono.just("Already friend!");
                case WAITING -> Mono.just("Waiting for accept!");
                case REJECTED ->
                    userRepository.handleFriendRequest(userId1, userId2, FriendStatus.WAITING.getStatus())
                            .then(Mono.just("Resend friend request successfully!"));
                // ** Not Friend
                default -> userRepository.sendFriendRequest(userId1, userId2)
                        .then(Mono.just("Create friend request successfully!"));
            };
        });
    };

    @Override
    public Mono<String> handleFriendRequest(String userId1, String userId2, FriendStatus status) {
        return checkExistUsers(userId1, userId2).then(Mono.defer(() -> {
            return userRepository.checkFriendStatus(userId1, userId2)
                    // TODO ERROR CODE
                    .switchIfEmpty(Mono.error(new AppException(ErrorCode.TOKEN_EXPIRED)))
                    .flatMap(friendStatus -> {
                        if (!friendStatus.equals(FriendStatus.WAITING.getStatus())) {
                            // TODO ERROR CODE
                            return Mono.error(new AppException(ErrorCode.INCORRECT_USERNAME_OR_PWD));
                        }
                        return userRepository.handleFriendRequest(userId1, userId2, status.getStatus())
                                .then(Mono.just(status.getMessage()));
                    });
        }));
    }

    @Override
    public Mono<String> unFriend(String userId1, String userId2) {
        return checkExistUsers(userId1, userId2).then(Mono.defer(() -> {
            return checkFriendStatus(userId1, userId2).flatMap(status -> {
                if (!status.equals(FriendStatus.ACCEPTED.getStatus()))
                    return Mono.just("Not friend to unfriend!");
                return userRepository.unFriend(userId1, userId2).then(Mono.just("Unfriend completed!"));
            });
        }));
    }

    @Override
    public Mono<Set<String>> getAllFriends(String userId) {
        return checkExistUsers(userId).then(userRepository.findFriendIds(userId).collect(Collectors.toSet()));
    }

    @Override
    public Mono<Boolean> checkFollowerStatus(String userId1, String userId2) {
        return userRepository.checkFollowerStatus(userId1, userId2);
    }

    @Override
    public Mono<String> addFollower(String userId1, String userId2) {
        return checkExistUsers(userId1, userId2).then(Mono.defer(() -> {
            return checkFollowerStatus(userId1, userId2).flatMap(isFollow -> {
                if (isFollow)
                    return Mono.just("Already follow!");
                return userRepository.addFollower(userId1, userId2)
                        .then(Mono.just("Follow completed!"));
            });
        }));
    }

    @Override
    public Mono<String> unFollower(String userId1, String userId2) {
        return checkExistUsers(userId1, userId2).then(Mono.defer(() -> {
            return checkFollowerStatus(userId1, userId2).flatMap(isFollow -> {
                if (!isFollow)
                    return Mono.just("Not follow to unfollow!");
                return userRepository.unFollower(userId1, userId2)
                        .then(Mono.just("Unfollow completed!"));
            });
        }));
    }

    @Override
    public Mono<Set<String>> getAllFollowers(String userId) {
        return checkExistUsers(userId).then(userRepository.findFollowerIds(userId).collect(Collectors.toSet()));
    }

    // TODO Show what not exists
    @Override
    public Mono<Void> checkExistUsers(String... userIds) {
        return Flux.fromArray(userIds)
                .flatMap(userId -> userRepository.existsById(userId)
                        .flatMap(exist -> exist ? Mono.empty() : Mono.error(new AppException(ErrorCode.NOT_EXISTED))))
                .then();
    }
}
