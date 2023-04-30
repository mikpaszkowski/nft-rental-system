package com.rentalSystem.xrpl.nft.api.model;

import com.rentalSystem.xrpl.nft.domain.model.offer.OfferType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
abstract class OfferBaseDTO {

    private String nftId;
    private Integer maxRentalDurationDays;
    private OfferType offerType;
    private Integer dailyRentalPrice;

}
