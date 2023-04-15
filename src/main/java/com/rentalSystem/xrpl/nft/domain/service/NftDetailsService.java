package com.rentalSystem.xrpl.nft.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.xrpl.xrpl4j.client.JsonRpcClientErrorException;
import org.xrpl.xrpl4j.client.XrplClient;
import org.xrpl.xrpl4j.model.client.accounts.AccountNftsResult;
import org.xrpl.xrpl4j.model.transactions.Address;

@Service
@RequiredArgsConstructor
public class NftDetailsService {

    private final XrplClient xrplClient;

    public AccountNftsResult getAll(Address address) throws JsonRpcClientErrorException {
        return xrplClient.accountNfts(address);
    }
}
