package com.nftmarketplace.user_service.model.kafkaModel;

import com.nftmarketplace.user_service.model.enums.MessageType;

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
public class RequestKafka {
    String messageId;

    String userRequestId;

    String userRequestName;

    String userRequestAvatarPath;

    String userReceiveId;

    MessageType messageType;
}
