package com.nftmarketplace.order_service.controller;

import org.springframework.web.bind.annotation.RestController;

import com.nftmarketplace.order_service.model.Order;
import com.nftmarketplace.order_service.model.dto.APIResponse;
import com.nftmarketplace.order_service.service.OrderService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import reactor.core.publisher.Mono;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderController {
    OrderService orderService;

    @GetMapping("/all")
    public Mono<APIResponse<Set<Order>>> getAllOrders() {
        return orderService.getAllOrders()
                .collect(Collectors.toSet())
                .map(orders -> APIResponse.<Set<Order>>builder()
                        .result(orders)
                        .build());
    }
}