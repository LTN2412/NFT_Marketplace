package com.nftmarketplace.user_service.event.producer;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import reactor.core.publisher.Mono;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderRecord;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EventProducer {
    KafkaSender<String, String> kafkaSender;

    public Mono<Void> send(String topic, String message) {
        return kafkaSender.send(Mono.just(SenderRecord.create(new ProducerRecord<>(topic, message), message)))
                .then();
    }
}
