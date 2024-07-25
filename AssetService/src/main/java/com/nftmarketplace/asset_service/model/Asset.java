package com.nftmarketplace.asset_service.model;

import java.util.Date;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
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
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Column(unique = true, nullable = false)
    String name;

    @Column(nullable = false, columnDefinition = "TEXT")
    String description;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false, name = "timestamp_create")
    Date timestampCreate;

    Float price;

    @Column(name = "highest_bid")
    Float highestBid;

    @Column(name = "img_path")
    String imgPath;

    @Column(name = "author_id", nullable = false)
    String authorId;

    @PrePersist
    private void create() {
        timestampCreate = new Date();
    }

    @ManyToMany
    Set<Tag> tags;
}
