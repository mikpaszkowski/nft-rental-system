package com.rentalSystem.xrpl.wallet.fake.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Deprecated
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Entity
@Table(name = "wallets")
public class WalletView {

    @Id
    private String address;
    @NotNull
    private String privateKeyHex;
    @NotNull
    private String publicKey;
}
