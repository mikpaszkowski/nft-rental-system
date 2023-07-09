package com.rentalSystem.nft.api.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class NFTokenDto {
    private String id;
    @NotNull
    private String uri;
}
