package com.example.demo.xrpl.account.api;

import com.example.demo.xrpl.account.api.dto.NFTokenDto;
import com.example.demo.xrpl.account.domain.AccountService;
import com.example.demo.xrpl.account.domain.mapper.AccountMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.xrpl.xrpl4j.client.JsonRpcClientErrorException;
import org.xrpl.xrpl4j.model.client.accounts.AccountInfoResult;
import org.xrpl.xrpl4j.model.transactions.Address;

import java.util.List;

@RestController
@RequestMapping("xrpl/account")
@RequiredArgsConstructor
class AccountController {

    private final AccountService accountService;

    private final AccountMapper accountMapper;

    @GetMapping("/info")
    AccountInfoResult getAccountInfo(@RequestParam String address) throws JsonRpcClientErrorException {
        return accountService.getAccountInfo(Address.of(address));
    }

    @GetMapping("/nfts")
    List<NFTokenDto> getAccountNfTokens(@RequestParam String address) throws JsonRpcClientErrorException {
        var nfTokensObjects = accountService.accountNftsResult(Address.of(address)).accountNfts();
        return accountMapper.toDtoList(nfTokensObjects);
    }
}
