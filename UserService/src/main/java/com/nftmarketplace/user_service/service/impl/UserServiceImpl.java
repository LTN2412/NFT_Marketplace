package com.nftmarketplace.user_service.service.impl;

import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.nftmarketplace.user_service.event.producer.EventProducer;
import com.nftmarketplace.user_service.exception.AppException;
import com.nftmarketplace.user_service.exception.ErrorCode;
import com.nftmarketplace.user_service.model.dto.request.UserRequest;
import com.nftmarketplace.user_service.model.dto.response.UserFlat;
import com.nftmarketplace.user_service.model.kafka_model.ChangeAuthorKafka;
import com.nftmarketplace.user_service.model.kafka_model.CreateAccountKafka;
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
    EventProducer eventProducer;
    Gson gson = new Gson();

    // @Override
    // public Mono<UserFlat> createUser(UserRequest request) {
    // return Mono.zip(
    // userRepository.existsById(request.getId()),
    // userRepository.existsByPhoneNumber(request.getPhoneNumber()))
    // .flatMap(tuple -> {
    // if (tuple.getT1() || tuple.getT2())
    // return Mono.error(new AppException(ErrorCode.EXISTED));
    // User user = UserMapper.INSTANCE.toUser(request);
    // return userRepository.save(user)
    // .flatMap(saveUser -> Mono.just(UserMapper.INSTANCE.toUserFlat(saveUser)));
    // });
    // }
    @Override
    public Mono<UserFlat> createUser(UserRequest request) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Mono<UserFlat> createUserKafka(CreateAccountKafka request) {
        return Mono.zip(
                userRepository.existsById(request.getId()),
                userRepository.existsByEmail(request.getEmail())).flatMap(tuple -> {
                    if (tuple.getT1() || tuple.getT2())
                        return Mono.error((new AppException(ErrorCode.EXISTED)));
                    User user = UserMapper.INSTANCE.toUser(request);
                    return userRepository.save(user)
                            .flatMap(saveUser -> Mono.just(UserMapper.INSTANCE.toUserFlat(saveUser)));
                });
    }

    @Override
    public Mono<UserFlat> updateUserImage(String userId, FilePart image, boolean isAvatar) {
        return checkExistUsers(userId)
                .then(Mono.defer(() -> cloudinaryService.createImgPath(Mono.just(image), userId)
                        .flatMap(imagePath -> userRepository.findById(userId)
                                .flatMap(user -> {
                                    if (isAvatar)
                                        user.setAvatarPath(imagePath);
                                    else
                                        user.setCoverImgPath(imagePath);
                                    return userRepository.save(user);
                                })
                                .map(UserMapper.INSTANCE::toUserFlat))));
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
    public Mono<UserFlat> changeToAuthor(String userId) {
        return userRepository.findById(userId)
                .switchIfEmpty(Mono.error(new AppException(ErrorCode.NOT_EXISTED)))
                .flatMap(author -> {
                    author.setIsAuthor(true);
                    return userRepository.save(author)
                            .flatMap(saveAuthor -> Mono.just(UserMapper.INSTANCE.toUserFlat(saveAuthor)))
                            .doOnSuccess(saveAuthor -> {
                                ChangeAuthorKafka authorKafka = UserMapper.INSTANCE.toChangeAuthorKafka(saveAuthor);
                                eventProducer.send("change_author", gson.toJson(authorKafka)).subscribe();
                            });
                });
    }

    @Override
    public Mono<UserFlat> updateUser(String userId, UserRequest request) {
        return userRepository.findById(userId)
                .switchIfEmpty(Mono.error(new AppException(ErrorCode.NOT_EXISTED)))
                .flatMap(user -> {
                    User userUpdate = UserMapper.INSTANCE.toUser(request, user);
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
    public Mono<Void> checkExistUsers(String... userIds) {
        return Flux.fromArray(userIds)
                .flatMap(userId -> userRepository.existsById(userId)
                        .flatMap(exist -> exist ? Mono.empty()
                                : Mono.error(new AppException(ErrorCode.NOT_EXISTED, "User " + userId + " not exist"))))
                .then();
    }
}
