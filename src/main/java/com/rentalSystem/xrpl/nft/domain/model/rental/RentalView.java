package com.rentalSystem.xrpl.nft.domain.model.rental;


import com.rentalSystem.xrpl.configuration.model.DateAudit;
import com.rentalSystem.xrpl.nft.domain.model.nft.NFTView;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    @NotBlank
    private String renterId;
    @NotNull
    @Future
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime rentalExpirationDateTime;
    @NotNull
    @Positive
    private Integer rentDays;
    @NotNull
    @Positive
    private Integer totalAmount;
    @NotNull
    private RentalType rentalType;
    @NotNull
    private RentalStatus rentalStatus;
    private Integer collateralAmount;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nft_id", foreignKey = @ForeignKey(name = "NFT_ID_FK"))
    @NotNull
    private NFTView nftView;
}
