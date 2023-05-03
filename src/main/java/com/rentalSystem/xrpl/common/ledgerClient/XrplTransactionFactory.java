package com.rentalSystem.xrpl.common.ledgerClient;

import com.google.common.primitives.UnsignedInteger;
import com.google.common.primitives.UnsignedLong;
import com.rentalSystem.xrpl.nft.api.model.RentRequestDTO;
import com.rentalSystem.xrpl.nft.domain.model.rental.RentalType;
import com.rentalSystem.xrpl.nft.domain.repository.OfferRepository;
import com.ripple.cryptoconditions.PreimageSha256Condition;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.xrpl.xrpl4j.client.JsonRpcClientErrorException;
import org.xrpl.xrpl4j.client.XrplClient;
import org.xrpl.xrpl4j.model.client.accounts.AccountInfoRequestParams;
import org.xrpl.xrpl4j.model.client.accounts.AccountInfoResult;
import org.xrpl.xrpl4j.model.client.common.LedgerSpecifier;
import org.xrpl.xrpl4j.model.client.ledger.LedgerRequestParams;
import org.xrpl.xrpl4j.model.client.ledger.LedgerResult;
import org.xrpl.xrpl4j.model.transactions.Address;
import org.xrpl.xrpl4j.model.transactions.NfTokenCreateOffer;
import org.xrpl.xrpl4j.model.transactions.NfTokenId;
import org.xrpl.xrpl4j.model.transactions.XrpCurrencyAmount;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class XrplTransactionFactory {

    private final XrplClient xrplClient;

    private final OfferRepository offerRepository;

    private final static XrpCurrencyAmount RENTAL_TEMP_AMOUNT = XrpCurrencyAmount.of(UnsignedLong.ONE);

    public Pair<NfTokenCreateOffer, PreimageSha256Condition> prepareNftCreateOffer(RentRequestDTO rentRequestDTO) throws JsonRpcClientErrorException {
        var offer = offerRepository.findById(rentRequestDTO.getOfferId()).orElseThrow();
        var accountInfo = getValidatedAccountInfo(Address.of(rentRequestDTO.getRenterId()));
        var fee = xrplClient.fee();
        var deadline = rentRequestDTO.getRentalType() == RentalType.COLLATERALIZED ? computeDeadline(rentRequestDTO.getRentDays()) : null;
        var rentalMemoDeterminant = RentalMemoDeterminant.from(rentRequestDTO, offer.getDailyRentalPrice(), deadline);
        return Pair.of(NfTokenCreateOffer.builder()
                .account(Address.of(rentRequestDTO.getRenterId()))
                .owner(Address.of(offer.getNftView().getOwnerId()))
                .nfTokenId(NfTokenId.of(offer.getNftView().getNfTokenID()))
                .sequence(accountInfo.accountData().sequence().plus(UnsignedInteger.ONE))
                .fee(fee.drops().baseFee())
                .amount(RENTAL_TEMP_AMOUNT)
                .memos(MemoHelper.getMemosForRental(rentalMemoDeterminant))
                .build(), rentalMemoDeterminant.condition());
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

    private UnsignedLong computeDeadline(Integer rentDays) {
        return instantToXrpTimestamp(getMinExpirationTime().plus(rentDays, ChronoUnit.DAYS));
    }

    private UnsignedLong instantToXrpTimestamp(Instant instant) {
        return UnsignedLong.valueOf(instant.getEpochSecond() - 0x386d4380);
    }

    private Instant getMinExpirationTime() {
        LedgerResult result = getValidatedLedger();
        Instant closeTime = xrpTimestampToInstant(
                result.ledger().closeTime()
                        .orElseThrow(() ->
                                new RuntimeException("Ledger close time must be present to calculate a minimum expiration time.")
                        )
        );

        Instant now = Instant.now();
        return closeTime.isBefore(now) ? now : closeTime;
    }

    private Instant xrpTimestampToInstant(UnsignedLong xrpTimeStamp) {
        return Instant.ofEpochSecond(xrpTimeStamp.plus(UnsignedLong.valueOf(0x386d4380)).longValue());
    }

    private LedgerResult getValidatedLedger() {
        try {
            LedgerRequestParams params = LedgerRequestParams.builder()
                    .ledgerSpecifier(LedgerSpecifier.VALIDATED)
                    .build();
            return xrplClient.ledger(params);
        } catch (JsonRpcClientErrorException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
