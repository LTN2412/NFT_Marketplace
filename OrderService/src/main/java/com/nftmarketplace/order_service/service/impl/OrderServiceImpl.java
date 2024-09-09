package com.nftmarketplace.order_service.service.impl;

import org.springframework.stereotype.Service;

import com.nftmarketplace.order_service.model.Order;
import com.nftmarketplace.order_service.model.kafka_model.AssetsOrderKafka;
import com.nftmarketplace.order_service.repository.OrderRepository;
import com.nftmarketplace.order_service.service.OrderService;
import com.nftmarketplace.order_service.utils.mapper.OrderMapper;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class OrderServiceImpl implements OrderService {
    OrderRepository orderRepository;

    @Override
    public Mono<Void> createAssetsOrder(AssetsOrderKafka assetsOrderKafka) {
        Order order = OrderMapper.INSTANCE.toOrder(assetsOrderKafka);
        return orderRepository.save(order).then();
    }

    @Override
    public Flux<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}
