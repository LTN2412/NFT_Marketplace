package com.nftmarketplace.asset_service.utils.mapper;

import java.util.Set;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import com.nftmarketplace.asset_service.model.Asset;
import com.nftmarketplace.asset_service.model.Author;
import com.nftmarketplace.asset_service.model.dto.request.AuthorElastic;
import com.nftmarketplace.asset_service.model.dto.request.AuthorRequest;
import com.nftmarketplace.asset_service.model.dto.response.AssetFlat;
import com.nftmarketplace.asset_service.model.dto.response.AuthorFlat;

@Mapper(componentModel = "spring")
public interface AuthorMapper {
    AuthorMapper INSTANCE = Mappers.getMapper(AuthorMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "assets", ignore = true)
    Author toAuthor(AuthorRequest request);

    AuthorFlat toAuthorFlat(Author author);

    Set<AuthorFlat> toAuthorsFlat(Set<Author> authorsFlat);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "assets", ignore = true)
    Author toAuthor(AuthorRequest request, @MappingTarget Author author);

    @Mapping(target = "assetIds", ignore = true)
    AuthorElastic toAuthorElastic(Author author);

    default Set<AssetFlat> getAssets(Set<Asset> assets) {
        return AssetMapper.INSTANCE.toAssetsFlat(assets);
    }
}
