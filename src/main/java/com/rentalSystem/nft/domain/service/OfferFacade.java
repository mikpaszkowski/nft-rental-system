package com.rentalSystem.nft.domain.service;

import com.rentalSystem.nft.api.model.OfferRequestDTO;
import com.rentalSystem.nft.api.model.OfferResponseDTO;
import com.rentalSystem.nft.domain.model.mapper.OfferMapper;
import com.rentalSystem.nft.domain.model.nft.NFTStatus;
import com.rentalSystem.nft.domain.model.nft.NFTView;
import com.rentalSystem.nft.domain.model.offer.OfferView;
import com.rentalSystem.nft.domain.repository.OfferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OfferFacade {

    private final OfferRepository offerRepository;
    private final OfferMapper offerMapper;

    @Transactional
    public OfferResponseDTO createOffer(OfferRequestDTO offerRequestDTO) {
        // check if the NFT already has its offer in the system
        offerRepository.findOne(offerRequestDTO.getTokenIndex())
                .ifPresent(offerView -> {
                    throw new RuntimeException("Given Token with id: " + offerRequestDTO.getTokenIndex() + " has already an offer: " + offerView.getId());
                });

        var nft = NFTView.builder()
                .id(offerRequestDTO.getTokenIndex())
                .ownerId(offerRequestDTO.getOwnerId())
                .issuerId(offerRequestDTO.getOwnerId())
                .nftStatus(NFTStatus.SPARE)
                .uri("")
                .build();
        var offerToBeSaved = offerMapper.mapEntity(offerRequestDTO, new OfferView());
        offerToBeSaved.setNftView(nft);
        var savedOffer = offerRepository.save(offerToBeSaved);
        return offerMapper.mapDTO(savedOffer);
    }
}
