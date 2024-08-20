// package com.nftmarketplace.user_service.model.node;

// import org.springframework.data.neo4j.core.schema.GeneratedValue;
// import org.springframework.data.neo4j.core.schema.Id;
// import org.springframework.data.neo4j.core.schema.Node;
// import org.springframework.data.neo4j.core.schema.Property;
// import org.springframework.data.neo4j.core.schema.Relationship;
// import org.springframework.data.neo4j.core.support.UUIDStringGenerator;

// import com.nftmarketplace.user_service.model.enums.NotificationStatus;

// import lombok.AccessLevel;
// import lombok.AllArgsConstructor;
// import lombok.Builder;
// import lombok.Data;
// import lombok.NoArgsConstructor;
// import lombok.experimental.FieldDefaults;

// @Node("notification")
// @Data
// @Builder
// @NoArgsConstructor
// @AllArgsConstructor
// @FieldDefaults(level = AccessLevel.PRIVATE)
// public class Notification {
// @Id
// @GeneratedValue(generatorClass = UUIDStringGenerator.class)
// String id;

// @Property
// String message;

// @Property
// NotificationStatus status;

// @Relationship(type = "SEND", direction = Relationship.Direction.INCOMING)
// User userSend;

// @Relationship(type = "RECEIVE", direction = Relationship.Direction.OUTGOING)
// User userReceive;
// }
