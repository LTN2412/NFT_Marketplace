package com.nftmarketplace.asset_elastic_search.utils.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.nftmarketplace.asset_elastic_search.model.Author;
import com.nftmarketplace.asset_elastic_search.model.dto.request.AuthorRequest;

@Mapper(componentModel = "spring")
public interface AuthorMapper {
    AuthorMapper INSTANCE = Mappers.getMapper(AuthorMapper.class);

    Author toAuthor(AuthorRequest request);
}
