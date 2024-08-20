package com.nftmarketplace.user_service.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import com.nftmarketplace.user_service.model.kafkaModel.OrderKafka;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class KafkaService {
    KafkaTemplate<String, OrderKafka> kafkaTemplate;
    UserService userService;

    public Mono<String> sendOrder(String userId) {
        return userService.getUser(userId).flatMap(user -> {
            OrderKafka orderKafka = OrderKafka.builder()
                    .userId(userId)
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .email(user.getEmail())
                    .phoneNumber(user.getPhoneNumber())
                    .address(user.getAddress())
                    .assetIds(user.getAssetIds())
                    .build();
            Message<OrderKafka> message = MessageBuilder
                    .withPayload(orderKafka)
                    .setHeader(KafkaHeaders.TOPIC, "order")
                    .build();
            kafkaTemplate.send(message);
            return Mono.just("Order Kafka message sent successfully");
        });
    }
}
