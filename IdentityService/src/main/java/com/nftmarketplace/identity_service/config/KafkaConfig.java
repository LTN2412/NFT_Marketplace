package com.nftmarketplace.identity_service.config;

import org.apache.kafka.clients.admin.NewTopic;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfig {
    @Bean
    NewTopic authorTopic() {
        return new NewTopic("author", 1, (short) 1);
    }

    @Bean
    NewTopic assetTopic() {
        return new NewTopic("asset", 1, (short) 1);
    }

    @Bean
    NewTopic commentTopic() {
        return new NewTopic("comment", 1, (short) 1);
    }

    @Bean
    NewTopic changeAuthor() {
        return new NewTopic("change_author", 1, (short) 1);
    }

    @Bean
    NewTopic createAccount() {
        return new NewTopic("create_account", 1, (short) 1);
    }

    @Bean
    NewTopic createMessage() {
        return new NewTopic("notification", 1, (short) 1);
    }

    @Bean
    NewTopic createUpdateFollower() {
        return new NewTopic("update_follower", 1, (short) 1);
    }

    @Bean
    NewTopic createOrder() {
        return new NewTopic("order", 1, (short) 1);
    }
}
