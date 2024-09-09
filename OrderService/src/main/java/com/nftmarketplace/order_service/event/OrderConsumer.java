package com.nftmarketplace.order_service.event;

import java.util.Collections;

import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.nftmarketplace.order_service.model.kafka_model.AssetsOrderKafka;
import com.nftmarketplace.order_service.service.OrderService;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOptions;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderConsumer {
    Gson gson = new Gson();

    @SuppressWarnings("unused")
    OrderService orderService;

    public OrderConsumer(ReceiverOptions<String, String> receiverOptions, OrderService orderService) {
        this.orderService = orderService;
        KafkaReceiver.create(receiverOptions.subscription(Collections.singleton("order")))
                .receive()
                .flatMap(message -> {
                    AssetsOrderKafka assetsOrderKafka = gson.fromJson(message.value(), AssetsOrderKafka.class);
                    return orderService.createAssetsOrder(assetsOrderKafka);
                })
                .subscribe();
    }
}
