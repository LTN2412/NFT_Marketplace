package com.nftmarketplace.asset_elastic_service.utils.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.nftmarketplace.asset_elastic_service.model.Author;
import com.nftmarketplace.asset_elastic_service.model.kafka_model.AuthorKafka;
import com.nftmarketplace.asset_elastic_service.model.kafka_model.ChangeAuthorKafka;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AuthorMapper {
    AuthorMapper INSTANCE = Mappers.getMapper(AuthorMapper.class);

    Author toAuthor(AuthorKafka authorKafka);

    Author toAuthor(ChangeAuthorKafka changeAuthorKafka);
}
