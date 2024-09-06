package com.nftmarketplace.user_service.repository;

import org.springframework.data.neo4j.repository.ReactiveNeo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import com.nftmarketplace.user_service.model.dto.response.UserFlat;
import com.nftmarketplace.user_service.model.node.User;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends ReactiveNeo4jRepository<User, String> {
        Mono<Boolean> existsByEmail(String email);

        Mono<Boolean> existsByPhoneNumber(String phoneNumber);

        @Query("MATCH (u1:user {id:$userId})-[:IS_FRIEND {status:'ACCEPTED'}]-(u2:user) RETURN u2.id")
        Flux<String> findFriendIds(String userId);

        @Query("MATCH (u1:user {id:$userId})-[:IS_FOLLOWER]-(u2:user) RETURN u2.id")
        Flux<String> findFollowerIds(String userId);

        @Query("MATCH (u1:user {id:$userId})\n" +
                        "OPTIONAL MATCH (u1)-[:IS_FRIEND]-(u2)\n" +
                        "OPTIONAL MATCH (u1)-[:IS_FOLLOWER]-(u3)\n" +
                        "OPTIONAL MATCH (u1)-[:HAS_IN_CART]-(a:asset)\n" +
                        "RETURN u1, COLLECT(u2.id) as friendIds, COLLECT(u3.id) as followerIds, COLLECT(a.id) as assetIds")
        Mono<UserFlat> findByIdFlat(String userId);

        @Query("MATCH (u1:user)\n" +
                        "OPTIONAL MATCH (u1)-[:IS_FRIEND]-(u2)\n" +
                        "OPTIONAL MATCH (u1)-[:IS_FOLLOWER]-(u3)\n" +
                        "OPTIONAL MATCH (u1)-[:HAS_IN_CART]-(a:asset)\n" +
                        "RETURN u1, COLLECT(u2.id) as friendIds, COLLECT(u3.id) as followerIds, COLLECT(a.id) as assetIds")
        Flux<UserFlat> findAllFlat();

        @Query("MATCH (:user {id:$userId1})-[r:IS_FRIEND]-(:user {id:$userId2})\n" +
                        "RETURN r.status")
        Mono<String> checkFriendStatus(String userId1, String userId2);

        @Query("MATCH (u1:user {id:$userId1})\n" +
                        "MATCH (u2:user {id:$userId2})\n" +
                        "MERGE (u1)-[r:IS_FRIEND {status:'WAITING'}]-(u2)")
        Mono<Void> sendFriendRequest(String userId1, String userId2);

        @Query("MATCH (u1:user {id:$userId1})-[r:IS_FRIEND]->(u2:user {id:$userId2})\n" +
                        "SET r.status=$status")
        Mono<Void> handleFriendRequest(String userId1, String userId2, String status);

        @Query("MATCH (:user {id:$userId1})-[r:IS_FRIEND]-(:user {id:$userId2})\n" +
                        "DELETE r")
        Mono<Void> unFriend(String userId1, String userId2);

        @Query("MATCH (:user {id:$userId1})-[r:IS_FOLLOWER]->(:user {id:$userId2})\n" +
                        "RETURN COUNT(r)>0")
        Mono<Boolean> checkFollowerStatus(String userId1, String userId2);

        @Query("MATCH (u1:user {id:$userId1})\n" +
                        "MATCH (u2:user {id:$userId2})\n" +
                        "MERGE (u1)-[r:IS_FOLLOWER]->(u2)\n")
        Mono<Void> addFollower(String userId1, String userId2);

        @Query("MATCH (:user {id:$userId1})-[r:IS_FOLLOWER]->(:user {id:$userId2})\n" +
                        "DELETE r")
        Mono<Void> unFollower(String userId1, String userId2);
}
