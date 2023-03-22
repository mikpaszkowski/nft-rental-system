package com.example.demo.xrpl.nft.domain.model;

import lombok.Builder;
import org.xrpl.xrpl4j.model.client.nft.BuyOffer;
import org.xrpl.xrpl4j.model.client.nft.SellOffer;

import java.util.List;

@Builder
public record NFTokenOffers(List<SellOffer> sellOffers, List<BuyOffer> buyOffers) {
}