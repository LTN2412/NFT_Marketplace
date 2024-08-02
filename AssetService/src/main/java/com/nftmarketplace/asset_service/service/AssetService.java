package com.nftmarketplace.asset_service.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

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
import com.nftmarketplace.asset_service.model.dto.response.AssetsPageable;
import com.nftmarketplace.asset_service.model.dto.response.AssetInfo;
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
    CloudinaryService cloudinaryService;
    KafkaTemplate<String, Object> kafkaTemplate;

    public Asset createAsset(AssetRequest request) {
        if (assetRepository.existsByName(request.getName()))
            throw new AppException(ErrorCode.EXISTED);
        // Mapper
        Asset asset = AssetMapper.INSTANCE.toAsset(request);
        // Create Id
        String id = UUID.randomUUID().toString();
        asset.setId(id);
        // Create Tags
        Set<Tag> tags = new HashSet<>();
        request.getTags().forEach((tag) -> {
            Tag newTag = new Tag();
            newTag.setName(tag);
            tags.add(newTag);
            tagRepository.save(newTag);
        });
        asset.setTags(tags);
        // Create ImgURL
        if (request.getImgPath() != null) {
            String imgPath = cloudinaryService.uploadImg(request.getImgAsset(), id, "asset_");
            asset.setImgPath(imgPath);
        }
        // Save
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

    public AssetInfo getAssetId(String id) {
        Asset asset = assetRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.NOT_EXISTED));
        Author author = authorRepository.findById(asset.getAuthorId())
                .orElseThrow(() -> new AppException(ErrorCode.NOT_EXISTED));
        AssetInfo assetInfo = AssetMapper.INSTANCE.toAssetInfo(asset, author);
        return assetInfo;
    }

    public Set<Asset> getAllAssets() {
        List<Asset> assets = assetRepository.findAll();
        return new HashSet<Asset>(assets);
    }

    public Page<AssetsPageable> getAssetsPageable(int offset, int limit) {
        Page<AssetsPageable> assetsPageable = assetRepository
                .findAssetsPageable(PageRequest.of(offset, limit));
        return assetsPageable;
    }

    public Asset updateAsset(String id, AssetRequest request) {
        Asset oldAsset = assetRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.NOT_EXISTED));
        Asset asset = AssetMapper.INSTANCE.toAsset(request, oldAsset);
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
        Asset saveAsset = assetRepository.save(asset);
        AssetElastic assetElastic = AssetMapper.INSTANCE.toAssetElastic(saveAsset);
        KafkaMessage<AssetElastic> message = KafkaMessage.<AssetElastic>builder()
                .action("UPDATE")
                .data(assetElastic)
                .build();
        kafkaTemplate.send("asset", message);
        return saveAsset;
    }

    public Void deleteAsset(String id) {
        Asset asset = assetRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.NOT_EXISTED));
        assetRepository.deleteById(id);
        AssetElastic assetElastic = new AssetElastic();
        assetElastic.setId(id);
        assetElastic.setAuthorId(asset.getAuthorId());
        KafkaMessage<AssetElastic> message = KafkaMessage.<AssetElastic>builder()
                .action("DELETE")
                .data(assetElastic)
                .build();
        kafkaTemplate.send("asset", message);
        return null;
    }
}
