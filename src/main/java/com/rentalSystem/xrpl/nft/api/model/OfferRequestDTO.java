package com.rentalSystem.xrpl.nft.api.model;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@SuperBuilder
@Getter
public class OfferRequestDTO extends OfferBaseDTO{

    private String ownerId;
}
