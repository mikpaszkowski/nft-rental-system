package com.rentalSystem.xrpl.nft.api.model.mapper;

import com.rentalSystem.xrpl.configuration.model.BaseMapperConfig;
import com.rentalSystem.xrpl.nft.api.model.NFTokenDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.xrpl.xrpl4j.model.client.accounts.NfTokenObject;

import java.util.List;

@Mapper(config = BaseMapperConfig.class)
public interface NftMapper {

    List<NFTokenDto> toDtoList(List<NfTokenObject> nfTokenObjects);

    @Mapping(target = "id", expression = "java(nfTokenObject.nfTokenId().value())")
    @Mapping(target = "uri", expression = "java(nfTokenObject.uri().map(org.xrpl.xrpl4j.model.transactions.NfTokenUri::value).orElse(null))")
    NFTokenDto toDto(NfTokenObject nfTokenObject);
}
