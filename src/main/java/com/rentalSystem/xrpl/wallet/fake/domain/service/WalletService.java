package com.rentalSystem.xrpl.wallet.fake.domain.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.xrpl.xrpl4j.client.JsonRpcClientErrorException;
import org.xrpl.xrpl4j.model.client.transactions.SubmitResult;
import org.xrpl.xrpl4j.model.transactions.Transaction;

public interface WalletService<T extends Transaction> {

    SubmitResult<T> submitTransaction(T transaction) throws JsonRpcClientErrorException, JsonProcessingException;
}
