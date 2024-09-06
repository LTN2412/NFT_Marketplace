package com.nftmarketplace.asset_elastic_service.event.consumer;

import java.util.Collections;
import org.springframework.stereotype.Service;
import com.google.gson.Gson;
import com.nftmarketplace.asset_elastic_service.model.kafka_model.AuthorKafka;
import com.nftmarketplace.asset_elastic_service.service.AuthorService;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOptions;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthorConsumer {
    Gson gson = new Gson();
    @SuppressWarnings("unused")
    AuthorService authorService;

    public AuthorConsumer(ReceiverOptions<String, String> receiverOptions, AuthorService authorService) {
        this.authorService = authorService;
        KafkaReceiver.create(receiverOptions.subscription(Collections.singleton("author")))
                .receive()
                .flatMap(message -> {
                    AuthorKafka authorKafka = gson.fromJson(message.value(), AuthorKafka.class);
                    return authorService.consumeAuthor(authorKafka);
                })
                .subscribe();
    }
}
