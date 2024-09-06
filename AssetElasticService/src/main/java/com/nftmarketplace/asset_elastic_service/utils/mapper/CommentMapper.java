package com.nftmarketplace.asset_elastic_service.utils.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.nftmarketplace.asset_elastic_service.model.Comment;
import com.nftmarketplace.asset_elastic_service.model.kafka_model.CommentKafka;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

    Comment toComment(CommentKafka commentKafka);
}
