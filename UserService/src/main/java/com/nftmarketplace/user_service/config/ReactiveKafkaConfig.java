package com.nftmarketplace.user_service.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import reactor.kafka.receiver.ReceiverOptions;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderOptions;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ReactiveKafkaConfig {
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
