package com.nftmarketplace.asset_service.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.nftmarketplace.asset_service.exception.AppException;
import com.nftmarketplace.asset_service.exception.ErrorCode;
import com.nftmarketplace.asset_service.model.Asset;
import com.nftmarketplace.asset_service.model.Tag;
import com.nftmarketplace.asset_service.model.dto.request.AssetRequest;
import com.nftmarketplace.asset_service.model.dto.response.AssetCard;
import com.nftmarketplace.asset_service.model.dto.response.AssetFlat;
import com.nftmarketplace.asset_service.model.dto.response.AuthorFlat;
import com.nftmarketplace.asset_service.model.enums.Action;
import com.nftmarketplace.asset_service.model.kafka_model.AssetKafka;
import com.nftmarketplace.asset_service.repository.AssetRepository;
import com.nftmarketplace.asset_service.repository.TagRepository;
import com.nftmarketplace.asset_service.service.AssetService;
import com.nftmarketplace.asset_service.service.AuthorService;
import com.nftmarketplace.asset_service.service.CloudinaryService;
import com.nftmarketplace.asset_service.utils.mapper.AssetMapper;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AssetServiceImpl implements AssetService {
    AssetRepository assetRepository;
    TagRepository tagRepository;
    AuthorService authorService;
    CloudinaryService cloudinaryService;
    KafkaTemplate<String, String> kafkaTemplate;
    Gson gson = new Gson();

    public Asset createAsset(AssetRequest request) {
        // * Check and map asset
        if (assetRepository.existsByName(request.getName()))
            throw new AppException(ErrorCode.EXISTED);
        Asset asset = AssetMapper.INSTANCE.toAsset(request);
        // * Create id
        String assetId = UUID.randomUUID().toString();
        asset.setId(assetId);
        // * Create tags
        Set<Tag> tags = new HashSet<>();
        request.getTags().forEach((tag) -> {
            Tag newTag = new Tag();
            newTag.setName(tag);
            tags.add(newTag);
            tagRepository.save(newTag);
        });
        asset.setTags(tags);
        // * Create imgURL
        if (request.getImgAsset() != null) {
            String imgPath = cloudinaryService.uploadImg(request.getImgAsset(), assetId, "asset_");
            asset.setImgPath(imgPath);
        }
        // * Save
        Asset saveAsset = assetRepository.save(asset);
        // * Send Kafka
        AssetKafka assetKafka = AssetMapper.INSTANCE.toAssetKafka(saveAsset);
        AuthorFlat authorFlat = authorService.getAuthorFlat(request.getAuthorId());
        assetKafka.setAuthorName(authorFlat.getName());
        assetKafka.setAuthorAvatarPath(authorFlat.getAvatarPath());
        assetKafka.setAction(Action.CREATE);
        kafkaTemplate.send("asset", gson.toJson(assetKafka));
        return saveAsset;
    }

    public AssetFlat getAssetFlat(String assetId) {
        Asset asset = assetRepository.findById(assetId).orElseThrow(() -> new AppException(ErrorCode.NOT_EXISTED));
        AssetFlat assetFlat = AssetMapper.INSTANCE.toAssetFlat(asset);
        return assetFlat;
    }

    public List<Asset> getAllAssets() {
        return assetRepository.findAll();
    }

    public Page<AssetCard> getAssetCardsPageable(Integer offset, Integer limit) {
        Page<AssetCard> assetCardsPageable = assetRepository.findAssetCardsPageable(PageRequest.of(offset, limit));
        return assetCardsPageable;
    }

    public Asset updateAsset(String assetId, AssetRequest request) {
        // * Get old asset
        Asset oldAsset = assetRepository.findById(assetId).orElseThrow(() -> new AppException(ErrorCode.NOT_EXISTED));
        Asset asset = AssetMapper.INSTANCE.toAsset(request, oldAsset);
        // * Update tags if exits
        if (request.getTags() != null) {
            Set<Tag> tags = new HashSet<>();
            request.getTags().forEach((tag) -> {
                Tag newTag = new Tag();
                newTag.setName(tag);
                tags.add(newTag);
                tagRepository.save(newTag);
            });
            asset.setTags(tags);
        }
        // * Update imgPath if exits
        if (request.getImgAsset() != null) {
            String imgPath = cloudinaryService.uploadImg(request.getImgAsset(), assetId, "asset_");
            asset.setImgPath(imgPath);
        }
        // * Save
        Asset saveAsset = assetRepository.save(asset);
        // * Send Kafka
        AssetKafka assetKafka = AssetMapper.INSTANCE.toAssetKafka(saveAsset);
        assetKafka.setAction(Action.UPDATE);
        kafkaTemplate.send("asset", gson.toJson(assetKafka));
        return saveAsset;
    }

    public Void deleteAsset(String assetId) {
        // * Check asset is exits
        Asset asset = assetRepository.findById(assetId).orElseThrow(() -> new AppException(ErrorCode.NOT_EXISTED));
        // * Delete asset
        assetRepository.deleteById(assetId);
        // * Send Kafka
        AssetKafka assetKafka = new AssetKafka();
        assetKafka.setId(assetId);
        assetKafka.setAuthorId(asset.getAuthorId());
        assetKafka.setAction(Action.DELETE);
        kafkaTemplate.send("asset", gson.toJson(assetKafka));
        return null;
    }
}
