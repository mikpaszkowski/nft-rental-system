package com.rentalSystem.xrpl.nft.domain.service;

import com.rentalSystem.xrpl.nft.api.model.OfferRequestDTO;
import com.rentalSystem.xrpl.nft.api.model.OfferResponseDTO;
import com.rentalSystem.xrpl.nft.domain.model.mapper.OfferMapper;
import com.rentalSystem.xrpl.nft.domain.model.offer.OfferView;
import com.rentalSystem.xrpl.nft.domain.repository.OfferRepository;
import com.rentalSystem.xrpl.wallet.fake.domain.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.xrpl.xrpl4j.client.JsonRpcClientErrorException;
import org.xrpl.xrpl4j.model.client.accounts.NfTokenObject;
import org.xrpl.xrpl4j.model.transactions.Address;
import org.xrpl.xrpl4j.model.transactions.NfTokenCreateOffer;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class OfferService {

    private final OfferRepository offerRepository;
    private final NftDetailsService nftDetailsService;

    private final WalletService walletService;

    private final OfferMapper offerMapper;

    public OfferResponseDTO createOffer(OfferRequestDTO offerRequestDTO) throws JsonRpcClientErrorException {
        // check if the NFT already has its offer in the system
        offerRepository.findOfferViewByNftId(offerRequestDTO.getNftId())
                .ifPresent(offerView -> {
                    throw new RuntimeException("Given NFT with id: " + offerRequestDTO.getNftId() + " has already an offer: " + offerView.getId());
                });
        // check if NFT belongs to the account that submitted the request
        nftDetailsService.findAccountNfts(Address.of(offerRequestDTO.getOwnerId()))
                .stream()
                .map(NfTokenObject::nfTokenId)
                .filter(nfTokenId -> Objects.equals(offerRequestDTO.getNftId(), nfTokenId.value()))
                .findAny()
                .orElseThrow(() -> new RuntimeException("Given NFT with id: " + offerRequestDTO.getNftId() + " does not belong to account: " + offerRequestDTO.getOwnerId()));

        // save to database -> nice place to use mapper
        var savedOffer = offerRepository.save(offerMapper.mapEntity(offerRequestDTO, new OfferView()));
        return offerMapper.mapDTO(savedOffer);
    }
}
