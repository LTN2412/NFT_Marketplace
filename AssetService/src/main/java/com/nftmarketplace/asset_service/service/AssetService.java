package com.nftmarketplace.asset_service.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.nftmarketplace.asset_service.configuration.exception.AppException;
import com.nftmarketplace.asset_service.configuration.exception.ErrorCode;
import com.nftmarketplace.asset_service.model.Asset;
import com.nftmarketplace.asset_service.model.Author;
import com.nftmarketplace.asset_service.model.Tag;
import com.nftmarketplace.asset_service.model.dto.kafka.KafkaMessage;
import com.nftmarketplace.asset_service.model.dto.request.AssetElastic;
import com.nftmarketplace.asset_service.model.dto.request.AssetRequest;
import com.nftmarketplace.asset_service.model.dto.response.AssetCardResponse;
import com.nftmarketplace.asset_service.model.dto.response.AssetInfoResponse;
import com.nftmarketplace.asset_service.repository.AssetRepository;
import com.nftmarketplace.asset_service.repository.AuthorRepository;
import com.nftmarketplace.asset_service.repository.TagRepository;
import com.nftmarketplace.asset_service.utils.mapper.AssetMapper;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AssetService {
    AssetRepository assetRepository;
    AuthorRepository authorRepository;
    TagRepository tagRepository;
    KafkaTemplate<String, Object> kafkaTemplate;

    public Asset createAsset(AssetRequest request) {
        if (assetRepository.existsByName(request.getName()))
            throw new AppException(ErrorCode.EXISTED);
        Set<Tag> tags = new HashSet<>();
        request.getTags().forEach((tag) -> {
            Tag newTag = new Tag();
            newTag.setName(tag);
            tags.add(newTag);
            tagRepository.save(newTag);
        });
        Asset asset = AssetMapper.INSTANCE.toAsset(request);
        asset.setTags(tags);
        Asset saveAsset = assetRepository.save(asset);
        AssetElastic assetElastic = AssetMapper.INSTANCE.toAssetElastic(saveAsset);
        assetElastic.setTags(request.getTags());
        KafkaMessage<AssetElastic> kafkaMessage = KafkaMessage.<AssetElastic>builder()
                .action("CREATE")
                .data(assetElastic)
                .build();
        kafkaTemplate.send("asset", kafkaMessage);
        return saveAsset;
    }

    @Cacheable(value = "asset", key = "#id")
    public Asset getAsset(String id) {
        Asset asset = assetRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.NOT_EXISTED));
        return asset;
    }

    // // @Cacheable(value = "asset_author", key = "#id")
    public AssetInfoResponse getAssetId(String id) {
        Asset asset = assetRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.NOT_EXISTED));
        Author author = authorRepository.findById(asset.getAuthorId())
                .orElseThrow(() -> new AppException(ErrorCode.NOT_EXISTED));
        AssetInfoResponse assetInfoResponse = AssetMapper.INSTANCE.toAssetInfoResponse(asset, author);
        return assetInfoResponse;
    }

    public List<Asset> getAllAssets() {
        List<Asset> assets = assetRepository.findAll();
        return assets;
    }

    // @Cacheable(value = "asset_cards", key = "#pageSize")
    public Page<AssetCardResponse> getAssetCards(int offset, int pageSize) {
        Page<AssetCardResponse> assetCardPageable = assetRepository
                .findAssetCardPageable(PageRequest.of(offset, pageSize));
        return assetCardPageable;
    }

    public Asset updateAsset(String id, AssetRequest request) {
        if (!assetRepository.existsById(id))
            throw new AppException(ErrorCode.NOT_EXISTED);
        Asset asset = AssetMapper.INSTANCE
                .toAsset(request);
        asset.setId(id);
        Asset saveAsset = assetRepository.save(asset);
        KafkaMessage<Asset> message = KafkaMessage.<Asset>builder()
                .action("UPDATE")
                .data(saveAsset)
                .build();
        kafkaTemplate.send("asset", message);
        return saveAsset;
    }

    public Void deleteAsset(String id) {
        if (!assetRepository.existsById(id))
            throw new AppException(ErrorCode.NOT_EXISTED);
        assetRepository.deleteById(id);
        KafkaMessage<Asset> message = KafkaMessage.<Asset>builder()
                .action("DELETE")
                .data(Asset.builder()
                        .id(id)
                        .build())
                .build();
        kafkaTemplate.send("asset", message);
        return null;
    }
}
