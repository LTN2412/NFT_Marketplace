package com.nftmarketplace.order_service.service;

import com.nftmarketplace.order_service.model.Order;
import com.nftmarketplace.order_service.model.kafka_model.AssetsOrderKafka;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface OrderService {
    Mono<Void> createAssetsOrder(AssetsOrderKafka assetsOrderKafka);

    Flux<Order> getAllOrders();
}
