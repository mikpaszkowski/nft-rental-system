package com.rentalSystem.xrpl.common.ledgerClient;

import com.google.common.primitives.UnsignedInteger;
import com.google.common.primitives.UnsignedLong;
import org.xrpl.xrpl4j.model.client.accounts.AccountInfoResult;
import org.xrpl.xrpl4j.model.client.common.LedgerIndex;
import org.xrpl.xrpl4j.model.client.fees.FeeDrops;
import org.xrpl.xrpl4j.model.client.fees.FeeLevels;
import org.xrpl.xrpl4j.model.client.fees.FeeResult;
import org.xrpl.xrpl4j.model.client.ledger.LedgerResult;
import org.xrpl.xrpl4j.model.flags.AccountRootFlags;
import org.xrpl.xrpl4j.model.ledger.AccountRootObject;
import org.xrpl.xrpl4j.model.ledger.LedgerHeader;
import org.xrpl.xrpl4j.model.transactions.Address;
import org.xrpl.xrpl4j.model.transactions.Hash256;
import org.xrpl.xrpl4j.model.transactions.XrpCurrencyAmount;

import java.time.Instant;

public abstract class XrplClientHelper {

    public static final String ADDRESS_OWNER = "r3hNcEuWdbF959TKQ3DnKacsMr16tjVNZR";
    public static final String ADDRESS_RENTER = "rMhtcfL37WeXaSv1KK4DSMWG3Xgk61vnNH";
    public static String SAMPLE_NFT_ID = "000A00004D657CD71B08AD536141E70D0D860FDA3F1096442DCBAB9C00000002";
    private static final XrpCurrencyAmount ONE_XRP = XrpCurrencyAmount.of(UnsignedLong.ONE);

    private static final Hash256 SAMPLE_HASH_256 = Hash256.of("ba7816bf8f01cfea414140de5dae2223b00361a396177a9cb410ff61f20015ad");

    public static FeeResult FEE_RESULT_SAMPLE = FeeResult.builder().drops(FeeDrops.builder()
                    .baseFee(ONE_XRP)
                    .medianFee(ONE_XRP)
                    .minimumFee(ONE_XRP)
                    .openLedgerFee(ONE_XRP).build())
            .currentLedgerSize(UnsignedInteger.ONE)
            .currentQueueSize(UnsignedInteger.ONE)
            .expectedLedgerSize(UnsignedInteger.ONE)
            .ledgerCurrentIndex(LedgerIndex.of(UnsignedInteger.ONE))
            .levels(FeeLevels.builder()
                    .medianLevel(ONE_XRP)
                    .minimumLevel(ONE_XRP)
                    .openLedgerLevel(ONE_XRP)
                    .referenceLevel(ONE_XRP)
                    .build())
            .build();

    public static AccountInfoResult ACCOUNT_INFO_SAMPLE = AccountInfoResult.builder()
            .accountData(AccountRootObject.builder()
                    .sequence(UnsignedInteger.valueOf(45353))
                    .account(Address.of(ADDRESS_RENTER))
                    .balance(ONE_XRP)
                    .flags(AccountRootFlags.DEFAULT_RIPPLE)
                    .ownerCount(UnsignedInteger.ONE)
                    .previousTransactionId(SAMPLE_HASH_256)
                    .previousTransactionLedgerSequence(UnsignedInteger.ONE)
                    .index(SAMPLE_HASH_256)
                    .build())
            .build();

    public static LedgerResult LEDGER_RESULT_SAMPLE = LedgerResult.builder()
            .ledger(LedgerHeader.builder()
                    .closeTime(UnsignedLong.valueOf(Instant.now().getEpochSecond()))
                    .ledgerIndex(LedgerIndex.of(UnsignedInteger.ONE))
                    .parentHash(SAMPLE_HASH_256)
                    .build())
            .build();

}
