package com.rentalSystem.xrpl.nft.api.model;

import com.google.common.primitives.UnsignedLong;
import com.rentalSystem.xrpl.nft.domain.model.rental.RentalType;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@NoArgsConstructor
@AllArgsConstructor
abstract class RentBaseDTO {

    @NotNull
    private Long offerId;
    @NotNull
    private String renterId;
    @NotNull
    private Integer rentDays;
    @Nullable
    private UnsignedLong collateralAmount;
    @NotNull
    private RentalType rentalType;
}
