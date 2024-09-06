package com.nftmarketplace.user_service.event.consumer;

import java.util.Collections;

import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.nftmarketplace.user_service.model.kafka_model.CreateAccountKafka;
import com.nftmarketplace.user_service.service.UserService;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOptions;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CreateAccountConsumer {
    Gson gson = new Gson();
    @SuppressWarnings("unused")
    UserService userService;

    public CreateAccountConsumer(ReceiverOptions<String, String> receiverOptions, UserService userService) {
        this.userService = userService;
        KafkaReceiver.create(receiverOptions.subscription(Collections.singleton("create_account")))
                .receive()
                .flatMap(message -> {
                    CreateAccountKafka account = gson.fromJson(message.value(), CreateAccountKafka.class);
                    log.info(account.toString());
                    return userService.createUserKafka(account);
                })
                .subscribe();
    }
}
