package com.example.demo.xrpl.nft.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Generated;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "nfts")
public class NFTView {

    @Id
    @Generated
    private Long nfTokenID;
    @NotNull
    @NotBlank
    private String issuerId;
    @NotNull
    @NotBlank
    private String ownerId;
    @NotNull
    @NotBlank
    private String uri;
    @NotNull
    private NFTStatus nftStatus;
}
