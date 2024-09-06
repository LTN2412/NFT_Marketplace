package com.nftmarketplace.asset_elastic_service.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import reactor.kafka.receiver.ReceiverOptions;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ReactiveKafkaConfig {
    @Bean
    ReceiverOptions<String, String> receiverOptions() {
        Map<String, Object> propsReceiver = new HashMap<>();
        propsReceiver.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        propsReceiver.put(ConsumerConfig.GROUP_ID_CONFIG, "group-account");
        propsReceiver.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        propsReceiver.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        return ReceiverOptions.create(propsReceiver);
    }
}
