package com.rentalSystem.xrpl.nft.domain.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rentalSystem.xrpl.common.ledgerClient.XrplTransactionFactory;
import com.rentalSystem.xrpl.nft.api.model.RentRequestDTO;
import com.rentalSystem.xrpl.nft.api.model.RentResponseDTO;
import com.rentalSystem.xrpl.nft.domain.model.mapper.RentalMapper;
import com.rentalSystem.xrpl.nft.domain.model.rental.RentalView;
import com.rentalSystem.xrpl.nft.domain.repository.RentalRepository;
import com.rentalSystem.xrpl.wallet.fake.domain.service.WalletService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.xrpl.xrpl4j.client.JsonRpcClientErrorException;

@Service
@RequiredArgsConstructor
@Slf4j
public class RentalFacade {

    private final WalletService walletService;

    private final RentalRepository rentalRepository;

    private final RentalMapper rentalMapper;

    //possible factory design pattern here ???
    private final XrplTransactionFactory xrplTransactionFactory;


    public RentResponseDTO rentNft(RentRequestDTO rentRequestDTO) throws JsonRpcClientErrorException, JsonProcessingException {
        var offerResult = xrplTransactionFactory.prepareNftCreateOffer(rentRequestDTO);
        var submitResult = walletService.submitTransaction(offerResult.getFirst());
        log.info("NfTokenCreateOffer transaction was applied: {}", submitResult.applied());
        log.info("Explorer: https://hooks-testnet-v2-explorer.xrpl-labs.com/{}", submitResult.transactionResult().hash());

        //saved condition to the database
        var savedRental = rentalRepository.save(rentalMapper.mapEntity(rentRequestDTO, new RentalView()));
        // at this moment, returned IN_PROGRESS, possible scenarios of different transaction application results will be
        // handle later
        return rentalMapper.mapDTO(savedRental);
    }
}
