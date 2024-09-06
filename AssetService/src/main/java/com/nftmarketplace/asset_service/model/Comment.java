package com.nftmarketplace.asset_service.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Column(name = "user_id")
    String userId;

    @Column(name = "user_nickname")
    String userNickname;

    @Column(name = "user_avatar_path")
    String userAvatarPath;

    @Column(nullable = false, columnDefinition = "TEXT")
    String userComment;

    @Column(nullable = false)
    Integer rating;

    @Builder.Default
    @Column(updatable = false, name = "created_at")
    Date createdAt = new Date();

    @Builder.Default
    @Column(name = "updated_at")
    Date updatedAt = new Date();

    @Column(name = "asset_id", nullable = false)
    String assetId;
}
