package com.nftmarketplace.user_service.utils.mapper;

import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.AfterMapping;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.nftmarketplace.user_service.model.dto.request.UserRequest;
import com.nftmarketplace.user_service.model.dto.response.UserFlat;
import com.nftmarketplace.user_service.model.kafka_model.ChangeAuthorKafka;
import com.nftmarketplace.user_service.model.kafka_model.CreateAccountKafka;
import com.nftmarketplace.user_service.model.node.Asset;
import com.nftmarketplace.user_service.model.node.User;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)

public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User toUser(UserRequest request);

    User toUser(CreateAccountKafka request);

    @Mapping(target = "followers", ignore = true)
    User toUser(ChangeAuthorKafka request);

    ChangeAuthorKafka toChangeAuthorKafka(UserFlat userFlat);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User toUser(UserRequest request, @MappingTarget User user);

    @Mapping(source = "friends", target = "friendIds")
    @Mapping(source = "followers", target = "followerIds")
    @Mapping(source = "assets", target = "assetIds")
    UserFlat toUserFlat(User user);

    default Set<String> getFriendsAndFollowers(Set<User> friends) {
        return friends.stream().map(User::getId).collect(Collectors.toSet());
    }

    default Set<String> getAssets(Set<Asset> assets) {
        return assets.stream().map(Asset::getId).collect(Collectors.toSet());
    }

    @AfterMapping
    default void updateUpdatedAt(@MappingTarget User user) {
        user.setUpdatedAt(new Date());
    }
}
