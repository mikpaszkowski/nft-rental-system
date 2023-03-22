package com.example.demo.xrpl.nft.domain.service;

import org.springframework.stereotype.Service;

@Service
class RentalService {

    public boolean proposeNFTRental() {

        // find account of specified address

        // create escrow
        // condition -> when user sends back the NFT to the previous owner,
        // unblock the collateral and send back to renter), set cancel after and finish after,
        // FinishAfter time must be before the CancelAfter time.

        // only when escrow creation was successful -> create an NFT offer with 0 amount by default
        // change NFT rental status to PENDING
        return true;
    }

    // possible proxy pattern for checking if renter did not corrupted the NFT
    // if that is the case, then XRPs locked in the Escrow should be transferred to the NFT issuer?
    // we need somehow prevent the renter from corrupting the amount of XRP located in Escrows
    //
    public boolean acceptNFTRentalProposal() {
        // accept the NFT offer
        // NFT is transferred to the rental account
        // NFT is visible on the previous owner account as renting asset
        // renter has the NFT on his account
        // NEED TO SPECIFY DESTINATION ACCOUNT
     return true;
    }

    public boolean rejectNFTRentalProposal() {
        // can be rejected by owner or renter
        return true;
    }

    public boolean getRentings() {
        // get from db the list of NFTs which the current user is renting
        // on the other hand we could store only ids of NFT and other necessary info
        //and based on some algorithm which would differentiate personal NFTs and rented ones
        return true;
    }

    public boolean getLendings() {
        // the way it would be handled is similar to the retrieval of lendings
        return true;
    }

}
