package com.nftmarketplace.order_service.event;

import java.util.Collections;

import org.springframework.stereotype.Service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOptions;
import reactor.kafka.receiver.ReceiverRecord;

@Service
@Slf4j
public class EventConsumer {

    public EventConsumer(ReceiverOptions<String, String> receiverOptions) {
        KafkaReceiver.create(receiverOptions.subscription(Collections.singleton("order")))
                .receive().subscribe(this::here);
    }

    public void here(ReceiverRecord<String, String> receiverRecord) {
        log.info("HERE");
        log.info("{}", receiverRecord.value());
    }
}
