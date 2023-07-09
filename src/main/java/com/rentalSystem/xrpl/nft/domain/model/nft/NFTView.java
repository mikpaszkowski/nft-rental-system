package com.rentalSystem.xrpl.nft.domain.model.nft;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.rentalSystem.xrpl.nft.domain.model.offer.OfferView;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Builder
public class NFTView {

    @Id
    private String id;
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
    @JsonBackReference
    private OfferView offerView;
}
