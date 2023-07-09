package com.rentalSystem.nft.domain.model.mapper;

import com.rentalSystem.configuration.model.BaseMapperConfig;
import com.rentalSystem.nft.api.model.RentRequestDTO;
import com.rentalSystem.nft.api.model.RentResponseDTO;
import com.rentalSystem.nft.domain.model.nft.NFTView;
import com.rentalSystem.nft.domain.model.offer.OfferView;
import com.rentalSystem.nft.domain.model.rental.RentalStatus;
import com.rentalSystem.nft.domain.model.rental.RentalView;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;

@Mapper(config = BaseMapperConfig.class, imports = {RentalStatus.class, LocalDateTime.class})
public abstract class RentalMapper {

    @PersistenceContext
    EntityManager entityManager;

    @Mapping(target = "id", expression = "java(rentalView.getId())")
    @Mapping(target = "renterId", expression = "java(rentRequestDTO.getRenterId())")
    @Mapping(target = "rentalExpirationDateTime", expression = "java(LocalDateTime.now().plusDays(rentRequestDTO.getRentDays()))")
    @Mapping(target = "totalAmount", expression = "java(getTotalAmount(rentRequestDTO))")
    @Mapping(target = "rentalType", expression = "java(rentRequestDTO.getRentalType())")
    @Mapping(target = "rentalStatus", expression = "java(com.rentalSystem.nft.domain.model.rental.RentalStatus.IN_PROGRESS)")
    @Mapping(target = "collateralAmount", expression = "java(rentRequestDTO.getCollateralAmount())")
    @Mapping(target = "rentDays", expression = "java(rentRequestDTO.getRentDays())")
    @Mapping(target = "nftView", expression = "java(getNFT(rentRequestDTO.getOfferId()))")
    public abstract RentalView mapEntity(RentRequestDTO rentRequestDTO, RentalView rentalView);

    public abstract RentResponseDTO mapDTO(RentalView rentalView);

    protected NFTView getNFT(Long nftOfferId) {
        var offer = entityManager.getReference(OfferView.class, nftOfferId);
        return entityManager.getReference(NFTView.class, offer.getNftView().getId());
    }

    protected Integer getTotalAmount(RentRequestDTO rentRequestDTO) {
        var offer = entityManager.getReference(OfferView.class, rentRequestDTO.getOfferId());
        return rentRequestDTO.getRentDays() * offer.getDailyRentalPrice();
    }
}
