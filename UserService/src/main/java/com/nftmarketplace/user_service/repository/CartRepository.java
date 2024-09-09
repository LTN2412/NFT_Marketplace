package com.nftmarketplace.user_service.repository;

import org.springframework.data.neo4j.repository.ReactiveNeo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import com.nftmarketplace.user_service.model.dto.response.AssetInCart;
import com.nftmarketplace.user_service.model.node.Asset;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CartRepository extends ReactiveNeo4jRepository<Asset, String> {
        @Query("MATCH (u:user {id:$userId})\n" +
                        "MATCH (a:asset {id:$assetId})\n" +
                        "MERGE (u)-[r:HAS_IN_CART {is_select:true}]->(a)")
        Mono<Void> addAsset(String assetId, String userId);

        @Query("MATCH (:user {id:$userId})-[r:HAS_IN_CART]->(:asset {id:$assetId})\n" +
                        "RETURN COUNT(r)>0")
        Mono<Boolean> checkAsset(String userId, String assetId);

        @Query("MATCH (:user {id:$userId})-[:HAS_IN_CART]->(a:asset {id:$assetId})\n" +
                        "DETACH DELETE a")
        Mono<Void> removeAsset(String userId, String assetId);

        @Query("MATCH (:user {id:$userId})-[r:HAS_IN_CART]->(a:asset)\n" +
                        "RETURN a, r.is_select as isSelect")
        Flux<AssetInCart> findAllAssetsInCartFrom1User(String userId);

        @Query("MATCH (u:user {id:$userId})-[r:HAS_IN_CART]->(a:asset {id:$assetId})\n" +
                        "SET r.is_select=$isSelect")
        Mono<Void> selectAssetInCart(String userId, String assetId, Boolean isSelect);

        @Query("MATCH (u:user {id:$userId})-[r:HAS_IN_CART {is_select:true}]->(a:asset)\n" +
                        "RETURN a, r.is_select as isSelect")
        Flux<AssetInCart> findAllAssetsIsSelectInCartFrom1User(String userId);

        @Query("MATCH (u:user {id:$userId})-[r:HAS_IN_CART {is_select:true}]->(a:asset)\n" +
                        "DETACH DELETE r")
        Mono<Void> removeAllSelectedAssetsFromCart(String userId);
}
