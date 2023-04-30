package com.rentalSystem.xrpl.account.api;

import com.rentalSystem.xrpl.account.domain.service.AccountService;
import com.rentalSystem.xrpl.nft.api.model.NFTokenDto;
import com.rentalSystem.xrpl.nft.api.model.mapper.NftMapper;
import com.rentalSystem.xrpl.nft.domain.service.NftDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.xrpl.xrpl4j.client.JsonRpcClientErrorException;
import org.xrpl.xrpl4j.model.client.accounts.AccountInfoResult;
import org.xrpl.xrpl4j.model.transactions.Address;

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
}
