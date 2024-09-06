package com.nftmarketplace.user_service.repository;

import org.springframework.data.neo4j.repository.ReactiveNeo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import com.nftmarketplace.user_service.model.dto.response.UserComment;
import com.nftmarketplace.user_service.model.node.Comment;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CommentRepository extends ReactiveNeo4jRepository<Comment, String> {
        @Query("MATCH (u:user {id:$userId})\n" +
                        "MATCH (c:comment {id:$commentId})\n" +
                        "MERGE (u)-[r:IS_COMMENT]->(c)")
        Mono<Void> addComment(String commentId, String userId);

        @Query("MATCH (c:comment {assetId:$assetId})<-[:IS_COMMENT]-(u:user)\n" +
                        "RETURN c, u.id as userId, u.name as username, u.avatar_path as userAvatarPath\n" +
                        "ORDER BY c.updated_at DESC")
        Flux<UserComment> findAllCommentFrom1AssetId(String assetId);
}
