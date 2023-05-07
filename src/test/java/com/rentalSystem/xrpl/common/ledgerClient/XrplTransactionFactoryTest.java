package com.rentalSystem.xrpl.common.ledgerClient;


import com.google.common.primitives.UnsignedLong;
import com.rentalSystem.xrpl.nft.api.model.RentRequestDTO;
import com.rentalSystem.xrpl.nft.domain.model.nft.NFTView;
import com.rentalSystem.xrpl.nft.domain.model.offer.OfferView;
import com.rentalSystem.xrpl.nft.domain.model.rental.RentalType;
import com.rentalSystem.xrpl.nft.domain.repository.OfferRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.xrpl.xrpl4j.client.JsonRpcClientErrorException;
import org.xrpl.xrpl4j.client.XrplClient;
import org.xrpl.xrpl4j.model.client.accounts.AccountInfoRequestParams;
import org.xrpl.xrpl4j.model.client.ledger.LedgerRequestParams;
import org.xrpl.xrpl4j.model.transactions.Address;
import org.xrpl.xrpl4j.model.transactions.NfTokenId;
import org.xrpl.xrpl4j.model.transactions.XrpCurrencyAmount;

import java.time.Instant;
import java.util.Optional;

import static com.rentalSystem.xrpl.common.ledgerClient.MemoHelper.getMemosForRental;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class XrplTransactionFactoryTest extends XrplClientHelper {

    @Mock
    private OfferRepository offerRepository;
    @Mock
    private XrplClient xrplClient;
    @InjectMocks
    private XrplTransactionFactory transactionFactory;

    @Test
    void shouldReturnExpectedNFTokenCreateOfferForCollateralFreeRental() throws JsonRpcClientErrorException {
        //given
        var requestDto = RentRequestDTO.builder()
                .offerId(1L)
                .rentalType(RentalType.COLLATERAL_FREE)
                .renterId(ADDRESS_RENTER)
                .rentDays(7)
                .build();
        var nft = NFTView.builder()
                .ownerId(ADDRESS_OWNER)
                .issuerId(ADDRESS_OWNER)
                .nfTokenID(SAMPLE_NFT_ID)
                .build();
        var offer = OfferView.builder()
                .nftView(nft)
                .dailyRentalPrice(4)
                .build();
        when(offerRepository.findById(any(Long.class))).thenReturn(Optional.of(offer));
        when(xrplClient.fee()).thenReturn(FEE_RESULT_SAMPLE);
        when(xrplClient.accountInfo(any(AccountInfoRequestParams.class))).thenReturn(ACCOUNT_INFO_SAMPLE);

        //when
        var result = transactionFactory.prepareNftCreateOffer(requestDto);
        var nfTokenCreateOffer = result.getFirst();
        var condition = result.getSecond();
        //then
        assertThat(nfTokenCreateOffer.nfTokenId()).isEqualTo(NfTokenId.of(nft.getNfTokenID()));
        assertThat(nfTokenCreateOffer.amount()).isEqualTo(XrpCurrencyAmount.of(UnsignedLong.ONE));
        assertThat(nfTokenCreateOffer.owner()).isEqualTo(Optional.of(Address.of(nft.getOwnerId())));
        assertThat(nfTokenCreateOffer.fee()).isNotNull();
        assertThat(nfTokenCreateOffer.memos()).hasSameElementsAs(
                getMemosForRental(RentalMemoDeterminant.from(requestDto, offer.getDailyRentalPrice(), UnsignedLong.valueOf(Instant.now().getEpochSecond()))));
        assertThat(condition).isNotNull();
    }

    @Test
    void shouldReturnExpectedNFTokenCreateOfferForCollateralizedRental() throws JsonRpcClientErrorException {
        //given
        var requestDto = RentRequestDTO.builder()
                .offerId(1L)
                .renterId(ADDRESS_RENTER)
                .rentDays(7)
                .rentalType(RentalType.COLLATERALIZED)
                .collateralAmount(UnsignedLong.valueOf(100))
                .build();
        var nft = NFTView.builder()
                .ownerId(ADDRESS_OWNER)
                .issuerId(ADDRESS_OWNER)
                .nfTokenID(SAMPLE_NFT_ID)
                .build();
        var offer = OfferView.builder()
                .nftView(nft)
                .dailyRentalPrice(4)
                .build();
        when(offerRepository.findById(any(Long.class))).thenReturn(Optional.of(offer));
        when(xrplClient.fee()).thenReturn(FEE_RESULT_SAMPLE);
        when(xrplClient.accountInfo(any(AccountInfoRequestParams.class))).thenReturn(ACCOUNT_INFO_SAMPLE);
        when(xrplClient.ledger(any(LedgerRequestParams.class))).thenReturn(LEDGER_RESULT_SAMPLE);
        //when
        var result = transactionFactory.prepareNftCreateOffer(requestDto);
        var nfTokenCreateOffer = result.getFirst();
        var condition = result.getSecond();
        //then
        assertThat(nfTokenCreateOffer.nfTokenId()).isEqualTo(NfTokenId.of(nft.getNfTokenID()));
        assertThat(nfTokenCreateOffer.amount()).isEqualTo(XrpCurrencyAmount.of(UnsignedLong.ONE));
        assertThat(nfTokenCreateOffer.owner()).isEqualTo(Optional.of(Address.of(nft.getOwnerId())));
        assertThat(nfTokenCreateOffer.fee()).isNotNull();
        assertThat(nfTokenCreateOffer.memos()).hasSameElementsAs(getMemosForRental(RentalMemoDeterminant.from(requestDto, offer.getDailyRentalPrice(), UnsignedLong.valueOf(Instant.now().getEpochSecond()))));
        assertThat(condition).isNotNull();
    }

}
