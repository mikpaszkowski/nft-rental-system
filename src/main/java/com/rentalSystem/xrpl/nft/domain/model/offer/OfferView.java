package com.rentalSystem.xrpl.nft.domain.model.offer;


import com.rentalSystem.xrpl.nft.domain.model.nft.NFTView;
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
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nft_id", referencedColumnName = "nfTokenID", foreignKey = @ForeignKey(name = "NFT_ID_FK"))
    private NFTView nftView;
}
