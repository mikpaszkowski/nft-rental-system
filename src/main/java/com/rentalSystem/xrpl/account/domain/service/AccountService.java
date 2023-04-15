package com.rentalSystem.xrpl.account.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.xrpl.xrpl4j.client.JsonRpcClientErrorException;
import org.xrpl.xrpl4j.client.XrplClient;
import org.xrpl.xrpl4j.model.client.accounts.AccountInfoRequestParams;
import org.xrpl.xrpl4j.model.client.accounts.AccountInfoResult;
import org.xrpl.xrpl4j.model.transactions.Address;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final XrplClient xrplClient;

    public AccountInfoResult getAccountInfo(Address address) throws JsonRpcClientErrorException {
        return xrplClient.accountInfo(AccountInfoRequestParams.of(address));
    }
}
