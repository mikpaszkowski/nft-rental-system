package com.example.demo.xrpl.account.api;

import com.example.demo.xrpl.account.domain.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.xrpl.xrpl4j.client.JsonRpcClientErrorException;
import org.xrpl.xrpl4j.model.client.accounts.AccountInfoResult;
import org.xrpl.xrpl4j.model.transactions.Address;

@RestController
@RequestMapping("xrpl")
@RequiredArgsConstructor
class AccountController {

    private final AccountService accountService;

    @GetMapping("/account-info")
    AccountInfoResult getAccountInfo(@RequestParam String accountAddress) throws JsonRpcClientErrorException {
        return accountService.getAccountInfo(Address.of(accountAddress));
    }
}
