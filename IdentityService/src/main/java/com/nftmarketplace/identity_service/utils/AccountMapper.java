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
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.nftmarketplace.identity_service.model.Account;
import com.nftmarketplace.identity_service.model.Role;
import com.nftmarketplace.identity_service.model.dto.request.AccountRequest;
import com.nftmarketplace.identity_service.model.kafka_model.CreateAccountKafka;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AccountMapper {
    AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);

    @Mapping(target = "roles", ignore = true)
    Account toAccount(AccountRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "roles", ignore = true)
    Account toAccount(AccountRequest request, @MappingTarget Account account);

    CreateAccountKafka toCreateAccounKafka(Account account);

    default Set<String> getRoles(Set<Role> roles) {
        return roles.stream().map(Role::getName).collect(Collectors.toSet());
    }

    @AfterMapping
    default void updateUpdatedAt(@MappingTarget Account account) {
        account.setUpdatedAt(new Date());
    }
}
