package com.marketplace.notification_service.repository;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.marketplace.notification_service.model.Message;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MessageRepository extends ReactiveMongoRepository<Message, String> {
    Mono<Boolean> existsByUserRequestIdOrUserReceiveId(String userRequestId, String userReceiveId);

    Flux<Message> findByUserRequestId(String userReceiveId);

    Flux<Message> findByUserReceiveId(String userReceiveId);

    Flux<Message> findByUserReceiveIdOrderByUpdatedAtDesc(String userReceiveId);

    Mono<Message> findByUserRequestIdAndUserReceiveIdAndMessageType(String userRequestId, String userReceiveId,
            String messageType);

    @Query(value = "{'user_receive_id': ?0, 'is_seen': false}", count = true)
    Mono<Long> countMessageNotSeen(String userReceiveId);
}
