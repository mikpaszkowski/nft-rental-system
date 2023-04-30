package com.rentalSystem.xrpl.account.api;

import com.rentalSystem.xrpl.account.domain.service.AccountService;
import com.rentalSystem.xrpl.nft.api.model.NFTokenDto;
import com.rentalSystem.xrpl.nft.api.model.OfferResponseDTO;
import com.rentalSystem.xrpl.nft.api.model.mapper.NftMapper;
import com.rentalSystem.xrpl.nft.domain.service.NftDetailsService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.xrpl.xrpl4j.client.JsonRpcClientErrorException;
import org.xrpl.xrpl4j.model.client.accounts.AccountInfoResult;
import org.xrpl.xrpl4j.model.client.nft.NftBuyOffersResult;
import org.xrpl.xrpl4j.model.transactions.Address;
import org.xrpl.xrpl4j.model.transactions.NfTokenId;

import java.util.List;

@RestController
@RequestMapping("account")
@RequiredArgsConstructor
class AccountInfoRestController {

    private final AccountService accountService;
    private final NftDetailsService nftDetailsService;
    private final NftMapper nftMapper;

    @GetMapping
    AccountInfoResult findAccountInfo(@RequestParam String address) throws JsonRpcClientErrorException {
        return accountService.getAccountInfo(Address.of(address));
    }

    @GetMapping("/nft")
    ResponseEntity<List<NFTokenDto>> findAll(@RequestParam String address) throws JsonRpcClientErrorException {
        var nfTokenObjects = nftDetailsService.findAccountNfts(Address.of(address));
        return ResponseEntity.ok(nftMapper.toDtoList(nfTokenObjects));
    }

    @GetMapping("/buy-offers")
    ResponseEntity<NftBuyOffersResult> createOffer(@NotNull @Valid @RequestParam String nftTokenId) throws JsonRpcClientErrorException {
        return ResponseEntity.ok(nftDetailsService.findNftBuyOffers(NfTokenId.of(nftTokenId)));
    }
}
