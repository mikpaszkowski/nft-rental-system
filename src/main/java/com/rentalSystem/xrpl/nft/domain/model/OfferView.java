package com.rentalSystem.xrpl.nft.domain.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Table(name = "offers")
public class OfferView {
    @Id
    private Long id;
    @NotNull
    private Integer dailyRentalPrice;
    @NotNull
    private Integer maxRentalDurationDays;
    @NotNull
    private OfferType offerType;
    @NotNull
    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id", foreignKey = @ForeignKey(name = "NFT_ID_FK"))
    private NFTView nftView;
}
