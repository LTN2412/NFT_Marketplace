package com.nftmarketplace.asset_service.model;

import java.util.Date;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
@JsonInclude(value = Include.NON_NULL)
public class Author {
    @Id
    String id;

    @Column(unique = true)
    String name;

    @Column(name = "avatar_path")
    String avatarPath;

    @Column(name = "cover_img_path")
    String coverImgPath;

    Float volumne;

    @Column(name = "nft_solds")
    Long nftSolds;

    Long followers;

    @Column(columnDefinition = "TEXT")
    String bio;

    @Builder.Default
    @Column(updatable = false, name = "created_at")
    Date createdAt = new Date();

    @Builder.Default
    @Column(name = "updated_at")
    Date updatedAt = new Date();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "author_id")
    Set<Asset> assets;
}
