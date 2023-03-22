package com.example.demo.xrpl.account.domain.mapper;

import com.example.demo.xrpl.account.api.dto.NFTokenDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.xrpl.xrpl4j.model.client.accounts.NfTokenObject;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    List<NFTokenDto> toDtoList(List<NfTokenObject> nfTokenObjects);

    @Mapping(target = "id", expression = "java(nfTokenObject.nfTokenId().value())")
    @Mapping(target = "uri", expression = "java(nfTokenObject.uri().map(org.xrpl.xrpl4j.model.transactions.NfTokenUri::value).orElse(null))")
    NFTokenDto toDto(NfTokenObject nfTokenObject);
}
