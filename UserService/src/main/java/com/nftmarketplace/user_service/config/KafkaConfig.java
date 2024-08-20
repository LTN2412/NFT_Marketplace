package com.nftmarketplace.user_service.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {
    @Bean
    NewTopic order() {
        return TopicBuilder.name("order")
                .partitions(3)
                .replicas(1)
                .build();
    }
}
