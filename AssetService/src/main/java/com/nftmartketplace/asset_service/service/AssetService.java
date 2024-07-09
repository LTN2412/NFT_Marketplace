package com.nftmartketplace.asset_service.service;

import java.util.List;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.nftmartketplace.asset_service.configuration.exception.AppException;
import com.nftmartketplace.asset_service.configuration.exception.ErrorCode;
import com.nftmartketplace.asset_service.model.Asset;
import com.nftmartketplace.asset_service.model.dto.kafka.KafkaMessage;
import com.nftmartketplace.asset_service.model.dto.request.AssetRequest;
import com.nftmartketplace.asset_service.repository.AssetRepository;
import com.nftmartketplace.asset_service.utils.mapper.AssetMapper;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AssetService {
    AssetRepository assetRepository;
    KafkaTemplate<String, Object> kafkaTemplate;

    public Asset createAsset(AssetRequest request) {
        if (assetRepository.existsByName(request.getName()))
            throw new AppException(ErrorCode.EXISTED);
        Asset asset = assetRepository.save(AssetMapper.INSTANCE.toAsset(request));
        KafkaMessage<Asset> kafkaMessage = KafkaMessage.<Asset>builder()
                .action("CREATE")
                .data(asset)
                .build();
        kafkaTemplate.send("asset", kafkaMessage);
        return asset;
    }

    @Cacheable(value = "asset", key = "#id")
    public Asset getAsset(String id) {
        Asset asset = assetRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.NOT_EXISTED));
        return asset;
    }

    public List<Asset> getAllAssets() {
        List<Asset> assets = assetRepository.findAll();
        return assets;
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
