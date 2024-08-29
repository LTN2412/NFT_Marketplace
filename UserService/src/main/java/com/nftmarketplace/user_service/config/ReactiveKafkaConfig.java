package com.nftmarketplace.user_service.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderOptions;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ReactiveKafkaConfig {
    @Bean
    NewTopic request() {
        return TopicBuilder.name("request")
                .build();
    }

    @Bean
    NewTopic notification() {
        return TopicBuilder.name("asset_change")
                .build();
    }

    @Bean
    KafkaSender<String, String> kafkaSender() {
        Map<String, Object> propsSender = new HashMap<>();
        propsSender.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        propsSender.put(ProducerConfig.ACKS_CONFIG, "all");
        propsSender.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        propsSender.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        SenderOptions<String, String> senderOptions = SenderOptions.create(propsSender);
        return KafkaSender.create(senderOptions);
    }
}
