package com.nftmarketplace.asset_elastic_service.event.consumer;

import java.util.Collections;
import org.springframework.stereotype.Service;
import com.google.gson.Gson;
import com.nftmarketplace.asset_elastic_service.model.kafka_model.AssetKafka;
import com.nftmarketplace.asset_elastic_service.service.AssetService;
import com.nftmarketplace.asset_elastic_service.service.AuthorService;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOptions;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AssetConsumer {
    Gson gson = new Gson();
    @SuppressWarnings("unused")
    AssetService assetService;
    @SuppressWarnings("unused")
    AuthorService authorService;

    public AssetConsumer(ReceiverOptions<String, String> receiverOptions, AssetService assetService,
            AuthorService authorService) {
        this.assetService = assetService;
        this.authorService = authorService;
        KafkaReceiver.create(receiverOptions.subscription(Collections.singleton("asset")))
                .receive()
                .flatMap(message -> {
                    AssetKafka assetKafka = gson.fromJson(message.value(), AssetKafka.class);
                    return assetService.consumeAsset(assetKafka)
                            .then(authorService.addAsset(assetKafka));
                })
                .subscribe();
    }
}
