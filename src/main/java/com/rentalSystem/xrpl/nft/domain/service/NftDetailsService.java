package com.rentalSystem.xrpl.nft.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.xrpl.xrpl4j.client.JsonRpcClientErrorException;
import org.xrpl.xrpl4j.client.XrplClient;
import org.xrpl.xrpl4j.model.client.accounts.NfTokenObject;
import org.xrpl.xrpl4j.model.transactions.Address;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NftDetailsService {

    private final XrplClient xrplClient;
    
    public List<NfTokenObject> findAccountNfts(Address address) throws JsonRpcClientErrorException {
        return xrplClient.accountNfts(address).accountNfts();
    }
}
