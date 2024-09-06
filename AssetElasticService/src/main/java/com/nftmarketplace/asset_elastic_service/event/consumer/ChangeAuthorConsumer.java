package com.nftmarketplace.asset_elastic_service.event.consumer;

import java.util.Collections;
import org.springframework.stereotype.Service;
import com.google.gson.Gson;
import com.nftmarketplace.asset_elastic_service.model.kafka_model.ChangeAuthorKafka;
import com.nftmarketplace.asset_elastic_service.service.AuthorService;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOptions;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ChangeAuthorConsumer {
    Gson gson = new Gson();
    @SuppressWarnings("unused")
    AuthorService authorService;

    public ChangeAuthorConsumer(ReceiverOptions<String, String> receiverOptions, AuthorService authorService) {
        this.authorService = authorService;
        KafkaReceiver.create(receiverOptions.subscription(Collections.singleton("change_author")))
                .receive()
                .flatMap(message -> {
                    log.info("here");
                    ChangeAuthorKafka authorKafka = gson.fromJson(message.value(), ChangeAuthorKafka.class);
                    return authorService.changeAuthor(authorKafka);
                })
                .subscribe();
    }
}
