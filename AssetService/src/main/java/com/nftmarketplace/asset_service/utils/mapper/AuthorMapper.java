package com.nftmarketplace.asset_service.utils.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.nftmarketplace.asset_service.model.Author;
import com.nftmarketplace.asset_service.model.dto.request.AuthorRequest;

@Mapper(componentModel = "spring")
public interface AuthorMapper {
    AuthorMapper INSTANCE = Mappers.getMapper(AuthorMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "assets", ignore = true)
    Author toAuthor(AuthorRequest request);

    @Mapping(source = "assets", target = "assets")
    Author toAuthor(Author author);

}
