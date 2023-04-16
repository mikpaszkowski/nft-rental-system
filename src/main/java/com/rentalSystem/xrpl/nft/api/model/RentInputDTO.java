package com.rentalSystem.xrpl.nft.api.model;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RentInputDTO {

    @NotNull
    private Long offerId;
    @NotNull
    private String renterId;
    @NotNull
    private Integer rentDays;
    @Nullable
    private Integer collateralAmount;
}
