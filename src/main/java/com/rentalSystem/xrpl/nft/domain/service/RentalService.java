package com.rentalSystem.xrpl.nft.domain.service;

import org.springframework.stereotype.Service;

@Service
class RentalService {

    public boolean proposeNFTRental(String address) {

        return true;
    }

    // possible proxy pattern for checking if renter did not corrupted the NFT
    // if that is the case, then XRPs locked in the Escrow should be transferred to the NFT issuer?
    // we need somehow prevent the renter from corrupting the amount of XRP located in Escrows


    public boolean getRentings() {

        return true;
    }

    public boolean getLendings() {
        // the way it would be handled is similar to the retrieval of lendings
        return true;
    }

}
