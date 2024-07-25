package com.nftmarketplace.asset_service.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nftmarketplace.asset_service.model.Asset;
import com.nftmarketplace.asset_service.model.dto.response.AssetCardResponse;

@Repository
public interface AssetRepository extends JpaRepository<Asset, String> {
    Boolean existsByName(String name);

    @Query("select new com.nftmarketplace.asset_service.model.dto.response.AssetCardResponse(asset.id,asset.name,asset.imgPath,asset.price,asset.highestBid,author.id,author.name,author.avatarPath) from Asset asset join Author author on asset.authorId=author.id")
    Page<AssetCardResponse> findAssetCardPageable(PageRequest pageRequest);

    @EntityGraph(attributePaths = { "tags" })
    List<Asset> findAll();
}

// asset.id as id, asset.name as name, asset.imgPath as imgPath, asset.price as
// price, asset.highestBid as highestBid, asset.authorId as authorId,
// author.name as authorName, author.avatarPath as authorAvatarPath