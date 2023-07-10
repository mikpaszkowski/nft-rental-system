package com.rentalSystem.xrpl.client;

import com.rentalSystem.xrpl.models.URIToken;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public interface XRPLService {

    List<URIToken> getTokensByAccNum(@NotNull String accountNumber);
}
