package com.nftmarketplace.asset_elastic_service.event.consumer;

import java.util.Collections;
import org.springframework.stereotype.Service;
import com.google.gson.Gson;
import com.nftmarketplace.asset_elastic_service.model.kafka_model.UpdateFollowerKafka;
import com.nftmarketplace.asset_elastic_service.service.AssetService;
import com.nftmarketplace.asset_elastic_service.service.AuthorService;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOptions;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UpdateFollowerConsumer {
    Gson gson = new Gson();
    @SuppressWarnings("unused")
    AuthorService authorService;

    public UpdateFollowerConsumer(ReceiverOptions<String, String> receiverOptions, AuthorService authorService,
            AssetService assetService) {
        this.authorService = authorService;
        KafkaReceiver.create(receiverOptions.subscription(Collections.singleton("update_follower")))
                .receive()
                .flatMap(message -> {
                    UpdateFollowerKafka updateFollowerKafka = gson.fromJson(message.value(), UpdateFollowerKafka.class);
                    return authorService.updateFollower(updateFollowerKafka);
                })
                .subscribe();
    }
}
