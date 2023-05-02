package com.rentalSystem.xrpl.common.ledgerClient;

import com.google.common.primitives.UnsignedInteger;
import com.google.common.primitives.UnsignedLong;
import com.rentalSystem.xrpl.common.utils.StringUtils;
import com.rentalSystem.xrpl.nft.api.model.RentRequestDTO;
import com.rentalSystem.xrpl.nft.domain.model.rental.RentalType;
import com.rentalSystem.xrpl.nft.domain.repository.OfferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.xrpl.xrpl4j.client.JsonRpcClientErrorException;
import org.xrpl.xrpl4j.client.XrplClient;
import org.xrpl.xrpl4j.model.client.accounts.AccountInfoRequestParams;
import org.xrpl.xrpl4j.model.client.accounts.AccountInfoResult;
import org.xrpl.xrpl4j.model.client.common.LedgerSpecifier;
import org.xrpl.xrpl4j.model.client.ledger.LedgerRequestParams;
import org.xrpl.xrpl4j.model.client.ledger.LedgerResult;
import org.xrpl.xrpl4j.model.transactions.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class XrplTransactionFactory {

    private final XrplClient xrplClient;

    private final OfferRepository offerRepository;

    private final static XrpCurrencyAmount RENTAL_TEMP_AMOUNT = XrpCurrencyAmount.ofDrops(1);

    public NfTokenCreateOffer prepareNftOffer(RentRequestDTO rentRequestDTO) throws JsonRpcClientErrorException {
        var offer = offerRepository.findById(rentRequestDTO.getOfferId()).orElseThrow();
        var accountInfo = getValidatedAccountInfo(Address.of(rentRequestDTO.getRenterId()));
        var fee = xrplClient.fee();
        return NfTokenCreateOffer.builder()
                .account(Address.of(rentRequestDTO.getRenterId()))
                .nfTokenId(NfTokenId.of(offer.getNftView().getNfTokenID()))
                .sequence(accountInfo.accountData().sequence().plus(UnsignedInteger.ONE))
                .fee(fee.drops().baseFee())
                .amount(RENTAL_TEMP_AMOUNT)
                .memos(getMemosForRentalRequest(rentRequestDTO))
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

    private List<ImmutableMemoWrapper> getMemosForRentalRequest(RentRequestDTO rentRequestDTO) {
        if (rentRequestDTO.getRentalType() == RentalType.COLLATERAL_FREE) {
            return List.of(
                    MemoWrapper.builder()
                            .memo(Memo.builder()
                                    .memoData(StringUtils.encodeHexString(rentRequestDTO.getRentalType().toString()))
                                    .memoFormat(StringUtils.encodeHexString("signed/type"))
                                    .memoType(StringUtils.encodeHexString("rental_type"))
                                    .build())
                            .build(),
                    MemoWrapper.builder()
                            .memo(Memo.builder()
                                    .memoData(StringUtils.encodeHexString(rentRequestDTO.getCollateralAmount().toString()))
                                    .memoFormat(StringUtils.encodeHexString("signed/total"))
                                    .memoType(StringUtils.encodeHexString("total_amount"))
                                    .build())
                            .build()
            );
        } else {
            return List.of(
                    MemoWrapper.builder()
                            .memo(Memo.builder()
                                    .memoData(StringUtils.encodeHexString(rentRequestDTO.getRentalType().toString()))
                                    .memoFormat(StringUtils.encodeHexString("signed/type"))
                                    .memoType(StringUtils.encodeHexString("rental_type"))
                                    .build())
                            .build(),
                    MemoWrapper.builder()
                            .memo(Memo.builder()
                                    .memoData(StringUtils.encodeHexString(rentRequestDTO.getCollateralAmount().toString()))
                                    .memoFormat(StringUtils.encodeHexString("signed/total"))
                                    .memoType(StringUtils.encodeHexString("total_amount"))
                                    .build())
                            .build(),
                    MemoWrapper.builder()
                            .memo(Memo.builder()
                                    .memoData(StringUtils.encodeHexString(rentRequestDTO.getCollateralAmount().toString()))
                                    .memoFormat(StringUtils.encodeHexString("signed/collateral_amount"))
                                    .memoType(StringUtils.encodeHexString("collateral_amount"))
                                    .build())
                            .build(),
                    MemoWrapper.builder()
                            .memo(Memo.builder()
                                    .memoData(StringUtils.encodeHexString(instantToXrpTimestamp(getMinExpirationTime().plus(rentRequestDTO.getRentDays(), ChronoUnit.DAYS)).toString()))
                                    .memoFormat(StringUtils.encodeHexString("signed/deadline"))
                                    .memoType(StringUtils.encodeHexString("rental_deadline"))
                                    .build())
                            .build(),
                    MemoWrapper.builder()
                            .memo(Memo.builder()
                                    .memoData(StringUtils.encodeHexString(rentRequestDTO.getRentalType().toString()))
                                    .memoFormat(StringUtils.encodeHexString("signed/condition"))
                                    .memoType(StringUtils.encodeHexString("escrow_condition"))
                                    .build())
                            .build()
            );
        }
    }

    public UnsignedLong instantToXrpTimestamp(Instant instant) {
        return UnsignedLong.valueOf(instant.getEpochSecond() - 0x386d4380);
    }

    public Instant getMinExpirationTime() {
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
