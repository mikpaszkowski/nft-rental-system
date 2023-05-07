package com.rentalSystem.xrpl.common.ledgerClient;

import com.rentalSystem.xrpl.common.utils.StringUtils;
import com.rentalSystem.xrpl.nft.domain.model.rental.RentalType;
import jakarta.validation.constraints.NotNull;
import org.xrpl.xrpl4j.model.transactions.ImmutableMemoWrapper;
import org.xrpl.xrpl4j.model.transactions.Memo;
import org.xrpl.xrpl4j.model.transactions.MemoWrapper;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class MemoHelper {

    public static List<ImmutableMemoWrapper> getMemosForRental(@NotNull RentalMemoDeterminant rentalMemoDeterminant) {
        var basicMemos = getBasicMemosForRental(rentalMemoDeterminant);
        if(rentalMemoDeterminant.rentalType() == RentalType.COLLATERAL_FREE) {
            return basicMemos;
        }
        var additionalMemos = getAdditionalMemosForCollateralRental(rentalMemoDeterminant);
        return Stream.concat(basicMemos.stream(), additionalMemos.stream())
                .collect(Collectors.toList());
    }

    private static List<ImmutableMemoWrapper> getBasicMemosForRental(RentalMemoDeterminant rentalMemoDeterminant) {
        return List.of(
                MemoWrapper.builder()
                        .memo(Memo.builder()
                                .memoData(StringUtils.encodeHexString(rentalMemoDeterminant.rentalType().name()))
                                .memoFormat(StringUtils.encodeHexString("signed/type"))
                                .memoType(StringUtils.encodeHexString("rental_type"))
                                .build())
                        .build(),
                MemoWrapper.builder()
                        .memo(Memo.builder()
                                .memoData(StringUtils.encodeHexString(rentalMemoDeterminant.totalAmountDrops().toString()))
                                .memoFormat(StringUtils.encodeHexString("signed/total"))
                                .memoType(StringUtils.encodeHexString("total_amount"))
                                .build())
                        .build()
        );
    }

    private static List<ImmutableMemoWrapper> getAdditionalMemosForCollateralRental(RentalMemoDeterminant rentalMemoDeterminant) {
        return List.of(
                MemoWrapper.builder()
                        .memo(Memo.builder()
                                .memoData(StringUtils.encodeHexString(rentalMemoDeterminant.collateralAmountDrops().toString()))
                                .memoFormat(StringUtils.encodeHexString("signed/collateral_amount"))
                                .memoType(StringUtils.encodeHexString("collateral_amount"))
                                .build())
                        .build(),
                MemoWrapper.builder()
                        .memo(Memo.builder()
                                .memoData(StringUtils.encodeHexString(rentalMemoDeterminant.deadlineTimestamp().toString()))
                                .memoFormat(StringUtils.encodeHexString("signed/deadline"))
                                .memoType(StringUtils.encodeHexString("rental_deadline"))
                                .build())
                        .build(),
                MemoWrapper.builder()
                        .memo(Memo.builder()
                                .memoData(StringUtils.encodeHexString(rentalMemoDeterminant.condition().toString()))
                                .memoFormat(StringUtils.encodeHexString("signed/condition"))
                                .memoType(StringUtils.encodeHexString("escrow_condition"))
                                .build())
                        .build()
        );
    }
}
