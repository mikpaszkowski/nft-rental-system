package com.rentalSystem.nft.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rentalSystem.nft.api.model.OfferRequestDTO;
import com.rentalSystem.nft.api.model.OfferResponseDTO;
import com.rentalSystem.nft.api.model.RentRequestDTO;
import com.rentalSystem.nft.api.model.RentResponseDTO;
import com.rentalSystem.nft.domain.model.offer.OfferType;
import com.rentalSystem.nft.domain.model.offer.OfferView;
import com.rentalSystem.nft.domain.model.rental.RentalType;
import com.rentalSystem.nft.domain.model.rental.RentalView;
import com.rentalSystem.nft.domain.repository.OfferRepository;
import com.rentalSystem.nft.domain.repository.RentalRepository;
import com.rentalSystem.nft.domain.service.OfferFacade;
import com.rentalSystem.nft.domain.service.RentalFacade;
import com.rentalSystem.xrpl.client.XrplServiceImpl;
import com.rentalSystem.xrpl.models.URIToken;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
@RequiredArgsConstructor
class NftInfoRestController {

    private final RentalRepository rentalRepository;
    private final OfferRepository offerRepository;

    private final RentalFacade rentalFacade;
    private final OfferFacade offerFacade;

    private final XrplServiceImpl xrplServiceImpl;


    @GetMapping("/rentals")
    ResponseEntity<Page<RentalView>> findRentalsByType(@RequestParam RentalType rentalType, Pageable pageable) {
        return ResponseEntity.ok(rentalRepository.findAllByRentalType(rentalType, pageable));
    }

    @GetMapping("/offers")
    ResponseEntity<Page<OfferView>> findOffersByType(@RequestParam OfferType offerType, Pageable pageable) {
        return ResponseEntity.ok(offerRepository.findAllByOfferType(offerType, pageable));
    }

    @PostMapping("/offers")
    ResponseEntity<OfferResponseDTO> createOffer(@NotNull @Valid @RequestBody OfferRequestDTO offerRequestDTO) {
        return ResponseEntity.ok(offerFacade.createOffer(offerRequestDTO));
    }

    @PostMapping("/rentals")
    ResponseEntity<RentResponseDTO> rentNFT(@NotNull @Valid @RequestBody RentRequestDTO rentRequestDTO) throws JsonProcessingException {
        return ResponseEntity.ok(rentalFacade.rentNft(rentRequestDTO));
    }

    @GetMapping("/tokens/{accountNumber}")
    List<URIToken> findURITokens(@PathVariable("accountNumber") String accountNumber) {
        return xrplServiceImpl.getURITokens(accountNumber);
    }
}
