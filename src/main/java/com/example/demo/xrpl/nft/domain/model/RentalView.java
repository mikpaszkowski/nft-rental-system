package com.example.demo.xrpl.nft.domain.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Table(name = "rentals")
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
    @JoinColumn(name = "nft_id")
    @NotNull
    private NFTView nftView;
}
