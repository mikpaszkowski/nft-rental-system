package com.rentalSystem.xrpl.nft.domain.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.primitives.UnsignedInteger;
import com.rentalSystem.xrpl.nft.api.model.RentRequestDTO;
import com.rentalSystem.xrpl.nft.api.model.RentResponseDTO;
import com.rentalSystem.xrpl.nft.domain.model.rental.RentalStatus;
import com.rentalSystem.xrpl.nft.domain.model.rental.RentalView;
import com.rentalSystem.xrpl.nft.domain.repository.OfferRepository;
import com.rentalSystem.xrpl.nft.domain.repository.RentalRepository;
import com.rentalSystem.xrpl.wallet.fake.domain.service.WalletService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.xrpl.xrpl4j.client.JsonRpcClientErrorException;
import org.xrpl.xrpl4j.client.XrplClient;
import org.xrpl.xrpl4j.crypto.keys.PublicKey;
import org.xrpl.xrpl4j.model.client.accounts.AccountInfoRequestParams;
import org.xrpl.xrpl4j.model.client.accounts.AccountInfoResult;
import org.xrpl.xrpl4j.model.client.common.LedgerSpecifier;
import org.xrpl.xrpl4j.model.transactions.Address;
import org.xrpl.xrpl4j.model.transactions.NfTokenCreateOffer;
import org.xrpl.xrpl4j.model.transactions.NfTokenId;
import org.xrpl.xrpl4j.model.transactions.XrpCurrencyAmount;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class RentalService {

    private final OfferRepository offerRepository;

    private final XrplClient xrplClient;

    private final WalletService walletService;

    private final RentalRepository rentalRepository;

    private final static XrpCurrencyAmount RENTAL_TEMP_AMOUNT = XrpCurrencyAmount.ofDrops(1);

    public RentResponseDTO rentNft(RentRequestDTO rentRequestDTO) throws JsonRpcClientErrorException, JsonProcessingException {
        var offer = offerRepository.findById(rentRequestDTO.getOfferId()).orElseThrow();
        var accountInfo = getValidatedAccountInfo(Address.of(rentRequestDTO.getRenterId()));
        var fee = xrplClient.fee();
        NfTokenCreateOffer createOffer = NfTokenCreateOffer.builder()
                .account(Address.of(rentRequestDTO.getRenterId()))
                .nfTokenId(NfTokenId.of(offer.getNftView().getNfTokenID()))
                .sequence(accountInfo.accountData().sequence().plus(UnsignedInteger.ONE))
                .signingPublicKey(PublicKey.builder().build()) // TODO replace with fake Wallet data -> finally with XUMM
                .fee(fee.drops().baseFee())
                .amount(RENTAL_TEMP_AMOUNT)
                .build();
        var submitResult = walletService.submitTransaction(createOffer);
        log.info("NfTokenCreateOffer transaction was applied: {}", submitResult.applied());
        log.info("Explorer: https://hooks-testnet-v2-explorer.xrpl-labs.com/{}", submitResult.transactionResult().hash());

        var rental = RentalView.builder()
                .nftView(offer.getNftView())
                .rentalStatus(RentalStatus.IN_PROGRESS)
                .totalAmount(BigDecimal.valueOf(rentRequestDTO.getRentDays()).multiply(BigDecimal.valueOf(offer.getDailyRentalPrice())).intValue())
                .rentalType(rentRequestDTO.getRentalType())
                .renterId(rentRequestDTO.getRenterId())
                .rentalExpirationDateTime(LocalDateTime.now().plusDays(rentRequestDTO.getRentDays()))
                .collateral(rentRequestDTO.getCollateralAmount())
                .build();

        rentalRepository.save(rental);
        // at this moment, returned IN_PROGRESS, possible scenarios of different transaction application results will be
        // handle later
        return RentResponseDTO.builder()
                .renterId(rentRequestDTO.getRenterId())
                .rentalId(rental.getId())
                .rentalStatus(RentalStatus.IN_PROGRESS)
                .rentDays(rentRequestDTO.getRentDays())
                .offerId(rentRequestDTO.getOfferId())
                .collateralAmount(rentRequestDTO.getCollateralAmount())
                .rentalType(rentRequestDTO.getRentalType())
                .build();
    }

    private AccountInfoResult getValidatedAccountInfo(Address classicAddress) {
        try {
            AccountInfoRequestParams params = AccountInfoRequestParams.builder()
                    .account(classicAddress)
                    .ledgerSpecifier(LedgerSpecifier.VALIDATED)
                    .build();
            return xrplClient.accountInfo(params);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

}
