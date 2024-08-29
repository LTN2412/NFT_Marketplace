package com.nftmarketplace.user_service.model.kafkaModel;

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
public class OrderKafka {
    String userId;

    String firstName;

    String lastName;

    String email;

    String phoneNumber;

    String address;

    Set<String> assetIds;
}
