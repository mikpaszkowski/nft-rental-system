package com.example.demo.xrpl.nft.api;

import com.example.demo.xrpl.nft.api.model.NFTokenDto;
import com.example.demo.xrpl.nft.api.model.mapper.NftMapper;
import com.example.demo.xrpl.nft.domain.model.*;
import com.example.demo.xrpl.nft.domain.repository.OfferRepository;
import com.example.demo.xrpl.nft.domain.repository.RentalRepository;
import com.example.demo.xrpl.nft.domain.service.NftDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.xrpl.xrpl4j.client.JsonRpcClientErrorException;
import org.xrpl.xrpl4j.model.transactions.Address;
import org.xrpl.xrpl4j.model.transactions.NfTokenId;

import java.util.List;

@RestController
@RequestMapping("nft")
@RequiredArgsConstructor
class NftInfoRestController {

    private final NftDetailsService nftDetailsService;

    private final RentalRepository rentalRepository;
    private final OfferRepository offerRepository;

    private final NftMapper accountMapper;

    @GetMapping
    ResponseEntity<List<NFTokenDto>> findAll(@RequestParam String address) throws JsonRpcClientErrorException {
        var nfTokensObjects = nftDetailsService.getAll(Address.of(address)).accountNfts();
        return ResponseEntity.ok(accountMapper.toDtoList(nfTokensObjects));
    }

    @GetMapping("/rentals")
    ResponseEntity<Page<RentalView>> findRentalsByType(@RequestParam RentalType rentalType, Pageable pageable) {
        return ResponseEntity.ok(rentalRepository.findAllByRentalType(rentalType, pageable));
    }

    @GetMapping("/offers")
    ResponseEntity<Page<OfferView>> findOffersByType(@RequestParam OfferType offerType, Pageable pageable) {
        return ResponseEntity.ok(offerRepository.findAllByOfferType(offerType, pageable));
    }
}
