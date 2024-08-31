package com.nftmarketplace.identity_service.utils;

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

import com.nftmarketplace.identity_service.model.Account;
import com.nftmarketplace.identity_service.model.Role;
import com.nftmarketplace.identity_service.model.dto.request.AccountRequest;
import com.nftmarketplace.identity_service.model.kafka_model.CreateAccountKafka;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Account toAccount(AccountRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "username", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Account toAccount(AccountRequest request, @MappingTarget Account account);

    CreateAccountKafka toAccount(Account account);

    default Set<String> getRoles(Set<Role> roles) {
        return roles.stream().map(Role::getName).collect(Collectors.toSet());
    }

    @AfterMapping
    default void updateUpdatedAt(@MappingTarget Account account) {
        account.setUpdatedAt(new Date());
    }
}
