package com.nftmarketplace.asset_service.utils.mapper;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.AfterMapping;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.nftmarketplace.asset_service.model.Asset;
import com.nftmarketplace.asset_service.model.Author;

import com.nftmarketplace.asset_service.model.dto.request.AuthorRequest;
import com.nftmarketplace.asset_service.model.dto.response.AuthorFlat;
import com.nftmarketplace.asset_service.model.kafka_model.AuthorKafka;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AuthorMapper {
    AuthorMapper INSTANCE = Mappers.getMapper(AuthorMapper.class);

    Author toAuthor(AuthorRequest request);

    Author toAuthor(AuthorKafka authorKafka);

    AuthorKafka toAuthorKafka(Author author);

    AuthorFlat toAuthorFlat(Author author);

    List<AuthorFlat> toAuthorFlatList(List<Author> authorsFlat);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Author toAuthor(AuthorRequest request, @MappingTarget Author author);

    default Set<String> getAssetIds(Set<Asset> assets) {
        return assets.stream().map(Asset::getName).collect(Collectors.toSet());
    }

    @AfterMapping
    default void updateUpdatedAt(@MappingTarget Author author) {
        author.setUpdatedAt(new Date());
    }
}
