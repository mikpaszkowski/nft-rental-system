package com.rentalSystem.xrpl.common.ledgerClient;

import com.google.common.primitives.UnsignedLong;
import com.rentalSystem.xrpl.common.utils.OptionalUtils;
import com.rentalSystem.xrpl.nft.api.model.RentRequestDTO;
import com.rentalSystem.xrpl.nft.domain.model.rental.RentalType;
import com.ripple.cryptoconditions.PreimageSha256Fulfillment;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import org.xrpl.xrpl4j.model.transactions.XrpCurrencyAmount;

import java.util.UUID;

record RentalMemoDeterminant(@NonNull RentalType rentalType,
                             @NonNull UnsignedLong totalAmountDrops,
                             @Nullable UnsignedLong collateralAmountDrops,
                             @Nullable UnsignedLong deadlineTimestamp,
                             @Nullable String condition) {
    static RentalMemoDeterminant from(@NotNull RentRequestDTO rentRequestDTO, @NotNull Integer dailyRentalPriceInXRP, @Nullable UnsignedLong deadlineTimestamp) {
        return new RentalMemoDeterminant(rentRequestDTO.getRentalType(),
                UnsignedLong.valueOf(XrpCurrencyAmount.ONE_XRP_IN_DROPS).times(UnsignedLong.valueOf(dailyRentalPriceInXRP)).times(UnsignedLong.valueOf(rentRequestDTO.getRentDays())),
                UnsignedLong.valueOf(XrpCurrencyAmount.ONE_XRP_IN_DROPS).times(OptionalUtils.mapOr(rentRequestDTO, RentRequestDTO::getCollateralAmount, UnsignedLong.ZERO)),
                deadlineTimestamp,
                rentRequestDTO.getRentalType() == RentalType.COLLATERALIZED ? PreimageSha256Fulfillment.from(UUID.randomUUID().toString().getBytes()).getDerivedCondition().getFingerprintBase64Url() : "");
    }
}
