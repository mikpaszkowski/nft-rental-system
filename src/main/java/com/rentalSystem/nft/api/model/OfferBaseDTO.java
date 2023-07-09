package com.rentalSystem.nft.api.model;

import com.rentalSystem.nft.domain.model.offer.OfferType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
abstract class OfferBaseDTO {

    private String tokenIndex;
    private Integer maxRentalDurationDays;
    private OfferType offerType;
    private Integer dailyRentalPrice;
}
