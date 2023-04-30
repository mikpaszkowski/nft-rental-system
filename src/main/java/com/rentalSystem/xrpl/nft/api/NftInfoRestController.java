package com.rentalSystem.xrpl.nft.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rentalSystem.xrpl.nft.api.model.OfferRequestDTO;
import com.rentalSystem.xrpl.nft.api.model.OfferResponseDTO;
import com.rentalSystem.xrpl.nft.api.model.RentRequestDTO;
import com.rentalSystem.xrpl.nft.api.model.RentResponseDTO;
import com.rentalSystem.xrpl.nft.domain.model.offer.OfferType;
import com.rentalSystem.xrpl.nft.domain.model.offer.OfferView;
import com.rentalSystem.xrpl.nft.domain.model.rental.RentalType;
import com.rentalSystem.xrpl.nft.domain.model.rental.RentalView;
import com.rentalSystem.xrpl.nft.domain.repository.OfferRepository;
import com.rentalSystem.xrpl.nft.domain.repository.RentalRepository;
import com.rentalSystem.xrpl.nft.domain.service.RentalService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.xrpl.xrpl4j.client.JsonRpcClientErrorException;

@RestController
@RequestMapping
@RequiredArgsConstructor
class NftInfoRestController {

    private final RentalRepository rentalRepository;
    private final OfferRepository offerRepository;

    private final RentalService rentalService;


    @GetMapping("/rentals")
    ResponseEntity<Page<RentalView>> findRentalsByType(@RequestParam RentalType rentalType, Pageable pageable) {
        return ResponseEntity.ok(rentalRepository.findAllByRentalType(rentalType, pageable));
    }

    @GetMapping("/offers")
    ResponseEntity<Page<OfferView>> findOffersByType(@RequestParam OfferType offerType, Pageable pageable) {
        return ResponseEntity.ok(offerRepository.findAllByOfferType(offerType, pageable));
    }

    @PostMapping("/rentals")
    ResponseEntity<RentResponseDTO> rentNFT(@NotNull @Valid @RequestBody RentRequestDTO inputDTO) {
        try {
            return ResponseEntity.ok(rentalService.rentNft(inputDTO));
        } catch (JsonRpcClientErrorException | JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

//    @PostMapping("/offers")
//    ResponseEntity<OfferResponseDTO> createOffer(@NotNull @Valid @RequestBody OfferRequestDTO offerRequestDTO) {
//        try {
//
//        }
//    }
}
