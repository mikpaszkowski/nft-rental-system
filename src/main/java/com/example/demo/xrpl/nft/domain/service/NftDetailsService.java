package com.example.demo.xrpl.nft.domain.service;

import com.example.demo.xrpl.nft.domain.model.NFTokenOffers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.xrpl.xrpl4j.client.JsonRpcClientErrorException;
import org.xrpl.xrpl4j.client.XrplClient;
import org.xrpl.xrpl4j.model.client.accounts.AccountNftsResult;
import org.xrpl.xrpl4j.model.client.nft.BuyOffer;
import org.xrpl.xrpl4j.model.client.nft.NftBuyOffersRequestParams;
import org.xrpl.xrpl4j.model.client.nft.NftSellOffersRequestParams;
import org.xrpl.xrpl4j.model.client.nft.SellOffer;
import org.xrpl.xrpl4j.model.transactions.Address;
import org.xrpl.xrpl4j.model.transactions.NfTokenId;

import java.util.List;

import static java.util.Collections.emptyList;

@Service
@RequiredArgsConstructor
public class NftDetailsService {

    private final XrplClient xrplClient;

    public AccountNftsResult getAll(Address address) throws JsonRpcClientErrorException {
        return xrplClient.accountNfts(address);
    }

    public NFTokenOffers getOffers(NfTokenId nfTokenId) {
        return NFTokenOffers.builder()
                .sellOffers(getSellOffers(nfTokenId))
                .buyOffers(getButOffers(nfTokenId))
                .build();
    }

    private List<BuyOffer> getButOffers(NfTokenId nfTokenId) {
        try {
            return xrplClient.nftBuyOffers(NftBuyOffersRequestParams.builder().nfTokenId(nfTokenId).build()).offers();
        } catch (JsonRpcClientErrorException ex) {
            return emptyList();
        }
    }

    private List<SellOffer> getSellOffers(NfTokenId nfTokenId) {
        try {
            return xrplClient.nftSellOffers(NftSellOffersRequestParams.builder().nfTokenId(nfTokenId).build()).offers();
        } catch (JsonRpcClientErrorException ex) {
            return emptyList();
        }
    }
}
