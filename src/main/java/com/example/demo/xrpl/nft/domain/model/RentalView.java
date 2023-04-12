package com.example.demo.xrpl.nft.domain.model;



import java.time.LocalDateTime;

public class RentalView {

    private RentalID rentalID;

    private NFTID nftId;
    private String renterId;
    private LocalDateTime rentalExpirationDateTime;
    private Integer totalAmount;
    private RentalType rentalType;
    private Integer collateral;
    private RentalStatus rentalStatus;
}
