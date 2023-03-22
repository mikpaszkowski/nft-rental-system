package com.example.demo.xrpl.account.api;

import com.example.demo.xrpl.account.domain.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.xrpl.xrpl4j.client.JsonRpcClientErrorException;
import org.xrpl.xrpl4j.model.client.accounts.AccountInfoResult;
import org.xrpl.xrpl4j.model.transactions.Address;

@RestController
@RequestMapping("account")
@RequiredArgsConstructor
class AccountInfoRestController {

    private final AccountService accountService;

    @GetMapping
    AccountInfoResult getAccountInfo(@RequestParam String address) throws JsonRpcClientErrorException {
        return accountService.getAccountInfo(Address.of(address));
    }
}
