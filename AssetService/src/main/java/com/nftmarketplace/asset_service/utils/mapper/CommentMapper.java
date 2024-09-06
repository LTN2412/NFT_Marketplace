package com.nftmarketplace.asset_service.utils.mapper;

import java.util.Date;

import org.mapstruct.AfterMapping;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.nftmarketplace.asset_service.model.Comment;
import com.nftmarketplace.asset_service.model.dto.request.CommentRequest;
import com.nftmarketplace.asset_service.model.kafka_model.CommentKafka;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CommentMapper {
    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

    Comment toComment(CommentRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Comment toComment(CommentRequest request, @MappingTarget Comment comment);

    CommentKafka toCommentKafka(Comment comment);

    @AfterMapping
    default void updateUpdatedAt(@MappingTarget Comment comment) {
        comment.setUpdatedAt(new Date());
    }
}
