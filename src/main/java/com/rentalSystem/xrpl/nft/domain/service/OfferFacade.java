package com.rentalSystem.xrpl.nft.domain.service;

import com.rentalSystem.xrpl.nft.api.model.OfferRequestDTO;
import com.rentalSystem.xrpl.nft.api.model.OfferResponseDTO;
import com.rentalSystem.xrpl.nft.domain.model.mapper.OfferMapper;
import com.rentalSystem.xrpl.nft.domain.model.nft.NFTStatus;
import com.rentalSystem.xrpl.nft.domain.model.nft.NFTView;
import com.rentalSystem.xrpl.nft.domain.model.offer.OfferView;
import com.rentalSystem.xrpl.nft.domain.repository.NFTRepository;
import com.rentalSystem.xrpl.nft.domain.repository.OfferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.xrpl.xrpl4j.client.JsonRpcClientErrorException;
import org.xrpl.xrpl4j.model.client.accounts.NfTokenObject;
import org.xrpl.xrpl4j.model.transactions.Address;
import org.xrpl.xrpl4j.model.transactions.NfTokenUri;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class OfferFacade {

    private final OfferRepository offerRepository;
    private final NftDetailsService nftDetailsService;

    private final OfferMapper offerMapper;

    private final NFTRepository nftRepository;

    @Transactional
    public OfferResponseDTO createOffer(OfferRequestDTO offerRequestDTO) throws JsonRpcClientErrorException {
        //check if account exists !!!!


        // check if the NFT already has its offer in the system
        offerRepository.findOne(offerRequestDTO.getNftId())
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
        var nft = NFTView.builder()
                .id(offerRequestDTO.getNftId())
                .ownerId(offerRequestDTO.getOwnerId())
                .issuerId(offerRequestDTO.getOwnerId())
                .nftStatus(NFTStatus.SPARE)
                .uri(NfTokenUri.ofPlainText("ipfs://bafybeigdyrzt5sfp7udm7hu76uh7y26nf4dfuylqabf3oclgtqy55fbzdi").value())
                .build();
        var offerToBeSaved = offerMapper.mapEntity(offerRequestDTO, new OfferView());
        offerToBeSaved.setNftView(nft);
        var savedOffer = offerRepository.save(offerToBeSaved);
        return offerMapper.mapDTO(savedOffer);
    }
}
