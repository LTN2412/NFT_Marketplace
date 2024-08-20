// package com.nftmarketplace.user_service.repository;

// import org.springframework.data.neo4j.repository.ReactiveNeo4jRepository;
// import org.springframework.data.neo4j.repository.query.Query;

// import com.nftmarketplace.user_service.model.node.Notification;

// import reactor.core.publisher.Mono;

// public interface NotifitcationRepository extends
// ReactiveNeo4jRepository<Notification, String> {
// @Query("MATCH (u1:user {id:$userId1})\n" +
// "MATCH (u2:user {id:$userId2})\n" +
// "MATCH (n:notification {id:$notificationId})\n" +
// "MERGE (u1)-[:SEND]->(n)\n" +
// "MERGE (n)<-[r:RECEIVE]-(u2)\n" +
// "RETURN COUNT(r)")
// Mono<Integer> sendFriendRequest(String userId1, String userId2, String
// notificationId);
// }
