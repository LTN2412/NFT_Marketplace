package com.nftmarketplace.user_service.repository;

import org.springframework.data.neo4j.repository.ReactiveNeo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import com.nftmarketplace.user_service.model.node.Asset;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AssetRepository extends ReactiveNeo4jRepository<Asset, String> {
        @Query("MATCH (u:user {id:$userId})\n" +
                        "MATCH (a:asset {id:$assetId})\n" +
                        "MERGE (u)-[r:HAS_IN_CART]->(a)")
        Mono<Void> addAsset(String assetId, String userId);

        @Query("MATCH (:user {id:$userId})-[r:HAS_IN_CART]->(:asset {id:$assetId})\n" +
                        "RETURN COUNT(r)>0")
        Mono<Boolean> checkAsset(String userId, String assetId);

        @Query("MATCH (u:user {id:$userId})-[r:HAS_IN_CART]->(a:asset {id:$assetId})\n" +
                        "DETACH DELETE a")
        Mono<Void> removeAsset(String userId, String assetId);

        @Query("MATCH (:user {id:$userId})-[:HAS_IN_CART]->(a:asset)\n" +
                        "RETURN a")
        Flux<Asset> findAllAssetsFrom1User(String userId);
}
