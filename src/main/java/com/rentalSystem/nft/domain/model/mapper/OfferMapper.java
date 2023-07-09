package com.rentalSystem.nft.domain.model.mapper;

import com.rentalSystem.configuration.model.BaseMapperConfig;
import com.rentalSystem.nft.api.model.OfferRequestDTO;
import com.rentalSystem.nft.api.model.OfferResponseDTO;
import com.rentalSystem.nft.domain.model.nft.NFTView;
import com.rentalSystem.nft.domain.model.offer.OfferView;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = BaseMapperConfig.class)
public abstract class OfferMapper {

    @PersistenceContext
    private EntityManager entityManager;

    @Mapping(target = "nftView", expression = "java(getNFT(offerRequestDTO.getTokenIndex()))")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dailyRentalPrice", expression = "java(offerRequestDTO.getDailyRentalPrice())")
    @Mapping(target = "maxRentalDurationDays", expression = "java(offerRequestDTO.getMaxRentalDurationDays())")
    @Mapping(target = "offerType", expression = "java(offerRequestDTO.getOfferType())")
    public abstract OfferView mapEntity(OfferRequestDTO offerRequestDTO, @MappingTarget OfferView offerView);

    @Mapping(target = "tokenIndex", source = "nftView.id")
    public abstract OfferResponseDTO mapDTO(OfferView offerView);

    protected NFTView getNFT(String nfTokenId) {
        return entityManager.getReference(NFTView.class, nfTokenId);
    }
}
