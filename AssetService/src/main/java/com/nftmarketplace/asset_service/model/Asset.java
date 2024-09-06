package com.nftmarketplace.asset_service.model;

import java.util.Date;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
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
public class Asset {
    @Id
    String id;

    @Column(unique = true)
    String name;

    @Column(nullable = false, columnDefinition = "TEXT")
    String description;

    Float price;

    @Column(name = "highest_bid")
    Float highestBid;

    @Column(name = "img_path")
    String imgPath;

    @Builder.Default
    @Column(updatable = false, name = "created_at")
    Date createdAt = new Date();

    @Builder.Default
    @Column(name = "updated_at")
    Date updatedAt = new Date();

    @Column(name = "author_id", nullable = false)
    String authorId;

    @ManyToMany
    Set<Tag> tags;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "asset_id")
    Set<Comment> comments;
}
