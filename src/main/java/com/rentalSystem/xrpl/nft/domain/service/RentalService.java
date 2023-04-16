package com.rentalSystem.xrpl.nft.domain.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.primitives.UnsignedInteger;
import com.rentalSystem.xrpl.nft.api.model.RentInputDTO;
import com.rentalSystem.xrpl.nft.domain.repository.OfferRepository;
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

@Service
@RequiredArgsConstructor
@Slf4j
public class RentalService {

    private final OfferRepository offerRepository;

    private final XrplClient xrplClient;

    private final WalletService walletService;

    private final static XrpCurrencyAmount RENTAL_TEMP_AMOUNT = XrpCurrencyAmount.ofDrops(1);

    public boolean rentNft(RentInputDTO rentInputDTO) throws JsonRpcClientErrorException, JsonProcessingException {
        var offer = offerRepository.findById(rentInputDTO.getOfferId());
        var accountInfo = getValidatedAccountInfo(Address.of(rentInputDTO.getRenterId()));
        var fee = xrplClient.fee();
        NfTokenCreateOffer createOffer = NfTokenCreateOffer.builder()
                .account(Address.of(rentInputDTO.getRenterId()))
                .nfTokenId(NfTokenId.of(offer.get().getNftView().getNfTokenID()))
                .sequence(accountInfo.accountData().sequence().plus(UnsignedInteger.ONE))
                .signingPublicKey(PublicKey.builder().build()) // TODO replace with fake Wallet data -> finally with XUMM
                .fee(fee.drops().baseFee())
                .amount(RENTAL_TEMP_AMOUNT)
                .build();
        var submitResult = walletService.submitTransaction(createOffer);
        log.info("NfTokenCreateOffer transaction was applied: {}", submitResult.applied());
        log.info("Explorer: https://hooks-testnet-v2-explorer.xrpl-labs.com/{}", submitResult.transactionResult().hash());
        return submitResult.applied();
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
