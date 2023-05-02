package com.rentalSystem.xrpl.nft.domain.model.mapper;

import com.rentalSystem.xrpl.configuration.model.BaseMapperConfig;
import com.rentalSystem.xrpl.nft.api.model.OfferRequestDTO;
import com.rentalSystem.xrpl.nft.api.model.OfferResponseDTO;
import com.rentalSystem.xrpl.nft.domain.model.nft.NFTView;
import com.rentalSystem.xrpl.nft.domain.model.offer.OfferView;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = BaseMapperConfig.class)
public abstract class OfferMapper {

    @PersistenceContext
    private EntityManager entityManager;

    @Mapping(target = "nftView", expression = "java(getNFT(offerRequestDTO.getNftId()))")
    @Mapping(target = "id", expression = "java(offerView.getId())")
    @Mapping(target = "dailyRentalPrice", expression = "java(offerView.getDailyRentalPrice())")
    @Mapping(target = "maxRentalDurationDays", expression = "java(offerView.getMaxRentalDurationDays())")
    @Mapping(target = "offerType", expression = "java(offerView.getOfferType())")
    public abstract OfferView mapEntity(OfferRequestDTO offerRequestDTO, @MappingTarget OfferView offerView);

    public abstract OfferResponseDTO mapDTO(OfferView offerView);

    protected NFTView getNFT(String nfTokenId) {
        return entityManager.getReference(NFTView.class, nfTokenId);
    }
}
