package com.marketplace.notification_service.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

import com.marketplace.notification_service.model.enums.MessageType;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Document("Message")
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Message {

    @MongoId
    @Id
    String id;

    @Field(name = "user_request_id")
    String userRequestId;

    @Field(name = "user_request_name")
    String userRequestName;

    @Field(name = "user_request_avatar_path")
    String userRequestAvatarPath;

    @Field(name = "user_receive_id")
    String userReceiveId;

    @Field(name = "create_at")
    Date createdAt = new Date();

    @Field(name = "updated_at")
    Date updatedAt = new Date();

    @Field(name = "message_type")
    MessageType messageType;

    @Field(name = "is_seen")
    Boolean isSeen = false;
}
