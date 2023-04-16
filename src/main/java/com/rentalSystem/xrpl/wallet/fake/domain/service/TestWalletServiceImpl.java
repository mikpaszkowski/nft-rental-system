package com.rentalSystem.xrpl.wallet.fake.domain.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rentalSystem.xrpl.wallet.fake.domain.model.WalletView;
import com.rentalSystem.xrpl.wallet.fake.domain.repository.WalletRepository;
import org.springframework.stereotype.Service;
import org.xrpl.xrpl4j.client.JsonRpcClientErrorException;
import org.xrpl.xrpl4j.client.XrplClient;
import org.xrpl.xrpl4j.codec.addresses.UnsignedByteArray;
import org.xrpl.xrpl4j.crypto.keys.PrivateKey;
import org.xrpl.xrpl4j.crypto.signing.SignatureService;
import org.xrpl.xrpl4j.crypto.signing.SingleSignedTransaction;
import org.xrpl.xrpl4j.crypto.signing.bc.BcSignatureService;
import org.xrpl.xrpl4j.model.client.transactions.SubmitResult;
import org.xrpl.xrpl4j.model.transactions.Transaction;

@Service
public class TestWalletServiceImpl<T extends Transaction> implements WalletService<T> {

    private final XrplClient xrplClient;

    protected final SignatureService<PrivateKey> signatureService;

    private final WalletRepository walletRepository;

    public TestWalletServiceImpl(XrplClient xrplClient, WalletRepository walletRepository) {
        this.xrplClient = xrplClient;
        this.walletRepository = walletRepository;
        this.signatureService = new BcSignatureService();
    }

    @Override
    public SubmitResult<T> submitTransaction(T transaction) throws JsonRpcClientErrorException, JsonProcessingException {
        var privateKeyHex = walletRepository.findById(transaction.account().value())
                .map(WalletView::getPrivateKeyHex)
                .orElseThrow();
        SingleSignedTransaction<T> signedTransaction = signatureService.sign(PrivateKey.of(UnsignedByteArray.fromHex(privateKeyHex)), transaction);
        return xrplClient.submit(signedTransaction);
    }
}
