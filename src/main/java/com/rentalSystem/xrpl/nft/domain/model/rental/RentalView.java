package com.rentalSystem.xrpl.nft.domain.model.rental;


import com.rentalSystem.xrpl.nft.domain.model.nft.NFTView;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Table(name = "rentals")
@Builder
public class RentalView {

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
    private Integer totalAmount;
    @NotNull
    private RentalType rentalType;
    @NotNull
    private RentalStatus rentalStatus;
    private Integer collateral;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nft_id", foreignKey = @ForeignKey(name = "NFT_ID_FK"))
    @NotNull
    private NFTView nftView;
    @CreationTimestamp
    @Column(name = "create_date", updatable = false)
    private LocalDateTime createDate;
}
