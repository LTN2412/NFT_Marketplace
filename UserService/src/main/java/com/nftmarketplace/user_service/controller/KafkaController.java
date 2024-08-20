package com.nftmarketplace.user_service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nftmarketplace.user_service.service.KafkaService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import reactor.core.publisher.Mono;

import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/kafka")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class KafkaController {
    KafkaService kafkaService;

    @PostMapping
    public Mono<String> sendOrder(@RequestParam String userId) {
        return kafkaService.sendOrder(userId);
    }
}
