package com.rentalSystem.xrpl.nft.domain.model.rental;


import com.google.common.primitives.UnsignedLong;
import com.rentalSystem.xrpl.configuration.model.DateAudit;
import com.rentalSystem.xrpl.nft.domain.model.nft.NFTView;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "rentals")
@Builder
public class RentalView extends DateAudit {

    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    @NotBlank
    private String renterId;
    @NotNull
    @Future
    private LocalDateTime rentalExpirationDateTime;
    @NotNull
    @Positive
    private Integer rentDays;
    @NotNull
    @Positive
    private UnsignedLong totalAmount;
    @NotNull
    private RentalType rentalType;
    @NotNull
    private RentalStatus rentalStatus;
    private UnsignedLong collateralAmount;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nft_id", foreignKey = @ForeignKey(name = "NFT_ID_FK"))
    @NotNull
    private NFTView nftView;
}
