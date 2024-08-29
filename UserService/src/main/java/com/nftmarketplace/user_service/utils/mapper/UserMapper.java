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
import org.mapstruct.factory.Mappers;

import com.nftmarketplace.user_service.model.dto.request.UserRequest;
import com.nftmarketplace.user_service.model.dto.response.UserFlat;
import com.nftmarketplace.user_service.model.node.Asset;
import com.nftmarketplace.user_service.model.node.User;

@Mapper(componentModel = "spring")

public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "avatarPath", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "lastLogin", ignore = true)
    @Mapping(target = "friends", ignore = true)
    @Mapping(target = "followers", ignore = true)
    @Mapping(target = "assets", ignore = true)
    User toUser(UserRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "avatarPath", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "lastLogin", ignore = true)
    @Mapping(target = "friends", ignore = true)
    @Mapping(target = "followers", ignore = true)
    @Mapping(target = "assets", ignore = true)
    User toUSer(UserRequest request, @MappingTarget User user);

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
