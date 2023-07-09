package com.rentalSystem.nft.api.model;

import com.rentalSystem.nft.domain.model.rental.RentalStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDateTime;

@Jacksonized
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class RentResponseDTO extends RentBaseDTO{

    private Long id;
    private RentalStatus rentalStatus;

    private LocalDateTime rentalExpirationDateTime;
}
