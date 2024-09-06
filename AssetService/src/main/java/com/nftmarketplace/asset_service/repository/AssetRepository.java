package com.nftmarketplace.asset_service.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nftmarketplace.asset_service.model.Asset;
import com.nftmarketplace.asset_service.model.dto.response.AssetCard;

@Repository
public interface AssetRepository extends JpaRepository<Asset, String> {
    Boolean existsByName(String name);

    @Query("SELECT new com.nftmarketplace.asset_service.model.dto.response.AssetCard(as.id, as.name, as.imgPath, as.price, as.highestBid, au.id, au.name, au.avatarPath) "
            +
            "FROM Asset as JOIN Author au ON as.authorId = au.id")
    Page<AssetCard> findAssetCardsPageable(Pageable pageable);

    @EntityGraph(attributePaths = "tags")
    @Override
    List<Asset> findAll();

    @EntityGraph(attributePaths = "tags")
    @Override
    Optional<Asset> findById(String assetId);
}