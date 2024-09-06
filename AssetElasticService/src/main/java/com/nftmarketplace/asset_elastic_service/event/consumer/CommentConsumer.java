package com.nftmarketplace.asset_elastic_service.event.consumer;

import java.util.Collections;
import org.springframework.stereotype.Service;
import com.google.gson.Gson;
import com.nftmarketplace.asset_elastic_service.model.kafka_model.CommentKafka;
import com.nftmarketplace.asset_elastic_service.service.AssetService;
import com.nftmarketplace.asset_elastic_service.service.CommentService;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOptions;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)

public class CommentConsumer {
    Gson gson = new Gson();
    @SuppressWarnings("unused")
    CommentService commentService;
    @SuppressWarnings("unused")
    AssetService assetService;

    public CommentConsumer(ReceiverOptions<String, String> receiverOptions, CommentService commentService,
            AssetService assetService) {
        this.commentService = commentService;
        this.assetService = assetService;
        KafkaReceiver.create(receiverOptions.subscription(Collections.singleton("comment")))
                .receive()
                .flatMap(message -> {
                    CommentKafka commentKafka = gson.fromJson(message.value(), CommentKafka.class);
                    return commentService.consumeComment(commentKafka)
                            .then(assetService.addComment(commentKafka));
                })
                .subscribe();
    }
}
