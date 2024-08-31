package com.marketplace.notification_service.utils.mapper;

import java.util.Date;

import org.mapstruct.AfterMapping;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import com.marketplace.notification_service.model.Message;
import com.marketplace.notification_service.model.kafkaModel.NotificationKafka;

@Mapper(componentModel = "spring")
public interface MessageMapper {
    MessageMapper INSTANCE = Mappers.getMapper(MessageMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "isSeen", ignore = true)
    Message toMessage(NotificationKafka request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "isSeen", ignore = true)
    Message toMessage(NotificationKafka newMessage, @MappingTarget Message oldMessage);

    @AfterMapping
    default void updateUpdatedAt(@MappingTarget Message message) {
        message.setUpdatedAt(new Date());
    }
}
