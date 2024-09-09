package com.marketplace.notification_service.service.impl;

import org.springframework.stereotype.Service;

import com.marketplace.notification_service.exception.AppException;
import com.marketplace.notification_service.exception.ErrorCode;
import com.marketplace.notification_service.model.Message;
import com.marketplace.notification_service.model.kafka_model.NotificationKafka;
import com.marketplace.notification_service.repository.MessageRepository;
import com.marketplace.notification_service.service.MessageService;
import com.marketplace.notification_service.utils.mapper.MessageMapper;

import io.netty.handler.timeout.ReadTimeoutException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MessageServiceImpl implements MessageService {

    MessageRepository messageRepository;

    @Override
    public Mono<Message> createMessage(Message message) {
        return messageRepository.save(message);
    }

    @Override
    public Flux<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    @Override
    public Flux<Message> getAllMessagesFrom1User(String userId) {
        return messageRepository.findByUserReceiveIdOrderByUpdatedAtDesc(userId);
    }

    @Override
    public Flux<Message> getAllMessageRequestFrom1User(String userId) {
        return messageRepository.findByUserRequestId(userId);
    }

    @Override
    public Flux<Message> getAllMessageReceiveFrom1User(String userId) {
        return messageRepository.findByUserReceiveId(userId);
    }

    @Override
    public Mono<Long> getNumberMessagesNotSeen(String userId) {
        return messageRepository.countMessageNotSeen(userId);
    }

    @Override
    public Mono<Message> updateMessage(NotificationKafka newMessage) {
        return messageRepository
                .findById(newMessage.getMessageId())
                .switchIfEmpty(Mono.error(new AppException(ErrorCode.NOT_EXISTED)))
                .flatMap(
                        oldMessage -> messageRepository.save(MessageMapper.INSTANCE.toMessage(newMessage, oldMessage)));
    }

    @Override
    public Mono<String> updateIsSeen(String messageId) {
        return messageRepository.findById(messageId)
                .switchIfEmpty(
                        Mono.error(new AppException(ErrorCode.NOT_EXISTED, "Message " + messageId + "not exist")))
                .doOnNext(message -> message.setIsSeen(true))
                .flatMap(message -> messageRepository.save(message).then(Mono.just("Update is seen successfully!")));
    }

    @Override
    public Mono<Void> checkExistUser(String userId) {
        return messageRepository.existsByUserRequestIdOrUserReceiveId(userId, userId)
                .flatMap(exist -> exist ? Mono.empty() : Mono.error(new ReadTimeoutException()))
                .then();
    }
}
