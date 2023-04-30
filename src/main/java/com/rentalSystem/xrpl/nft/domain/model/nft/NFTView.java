package com.rentalSystem.xrpl.nft.domain.model.nft;

import com.rentalSystem.xrpl.nft.domain.model.offer.OfferView;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "nfts")
@Builder
public class NFTView {

    @Id
    private String nfTokenID;
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
    @OneToOne(mappedBy = "nftView")
    private OfferView offerView;
}
