package com.nftmarketplace.identity_service.model.kafka_model;

import java.util.Set;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateAccountKafka {
    String id;

    String email;

    String username;

    Set<String> roles;
}
