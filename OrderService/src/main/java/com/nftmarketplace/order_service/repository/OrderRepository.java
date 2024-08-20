package com.nftmarketplace.order_service.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.nftmarketplace.order_service.model.Order;

@Repository
public interface OrderRepository extends ReactiveMongoRepository<Order, String> {
};
