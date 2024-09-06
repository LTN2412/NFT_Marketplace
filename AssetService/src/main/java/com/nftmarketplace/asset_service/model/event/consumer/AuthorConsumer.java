package com.nftmarketplace.asset_service.model.event.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.nftmarketplace.asset_service.model.kafka_model.AuthorKafka;
import com.nftmarketplace.asset_service.service.AuthorService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthorConsumer {
    AuthorService authorService;
    Gson gson = new Gson();

    @KafkaListener(topics = "change_author", groupId = "asset-service-group")
    public void consumeAuthor(String authorMessage) {
        AuthorKafka authorKafka = gson.fromJson(authorMessage, AuthorKafka.class);
        authorService.createAuthorFromKafka(authorKafka);
    }
}
