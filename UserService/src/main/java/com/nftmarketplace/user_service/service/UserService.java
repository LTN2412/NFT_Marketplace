package com.nftmarketplace.user_service.service;

import org.springframework.http.codec.multipart.FilePart;

import com.nftmarketplace.user_service.model.dto.request.UserRequest;
import com.nftmarketplace.user_service.model.dto.response.UserFlat;
import com.nftmarketplace.user_service.model.kafka_model.CreateAccountKafka;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {
    Mono<UserFlat> createUser(UserRequest request);

    Mono<UserFlat> createUserKafka(CreateAccountKafka request);

    Mono<UserFlat> getUser(String userId);

    Flux<UserFlat> getAllUsers();

    Mono<UserFlat> changeToAuthor(String userId);

    Mono<UserFlat> updateUserImage(String userId, FilePart image, boolean isAvatar);

    Mono<UserFlat> updateUser(String userId, UserRequest request);

    Mono<String> deleteUser(String userId);

    Mono<Void> checkExistUsers(String... userIds);
}
