package com.marketplace.notification_service.service;

import com.marketplace.notification_service.model.Message;
import com.marketplace.notification_service.model.kafkaModel.RequestKafka;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MessageService {
    Mono<Message> createMessage(Message message);

    Flux<Message> getAllMessageRequestFrom1User(String userId);

    Flux<Message> getAllMessageReceiveFrom1User(String userId);

    Flux<Message> getAllMessagesFrom1User(String userId);

    Flux<Message> getAllMessages();

    Mono<Long> getNumberMessagesNotSeen(String userId);

    Mono<Message> updateMessage(RequestKafka message);

    Mono<String> updateIsSeen(String messageId);

    Mono<Void> checkExistUser(String userId);
}
