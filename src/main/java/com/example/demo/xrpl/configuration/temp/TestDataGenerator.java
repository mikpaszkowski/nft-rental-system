package com.example.demo.xrpl.configuration.temp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.primitives.UnsignedLong;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.xrpl.xrpl4j.client.JsonRpcClientErrorException;
import org.xrpl.xrpl4j.client.XrplClient;
import org.xrpl.xrpl4j.client.faucet.FaucetClient;
import org.xrpl.xrpl4j.client.faucet.FundAccountRequest;
import org.xrpl.xrpl4j.crypto.keys.KeyPair;
import org.xrpl.xrpl4j.crypto.keys.PrivateKey;
import org.xrpl.xrpl4j.crypto.keys.Seed;
import org.xrpl.xrpl4j.crypto.signing.SignatureService;
import org.xrpl.xrpl4j.crypto.signing.SingleSignedTransaction;
import org.xrpl.xrpl4j.crypto.signing.bc.BcSignatureService;
import org.xrpl.xrpl4j.model.client.accounts.AccountInfoRequestParams;
import org.xrpl.xrpl4j.model.client.accounts.AccountInfoResult;
import org.xrpl.xrpl4j.model.client.transactions.SubmitResult;
import org.xrpl.xrpl4j.model.transactions.NfTokenMint;
import org.xrpl.xrpl4j.model.transactions.NfTokenUri;
import org.xrpl.xrpl4j.model.transactions.XrpCurrencyAmount;

@Slf4j
@Component
public class TestDataGenerator {

    private final FaucetClient faucetClient;
    private final XrplClient xrplClient;

    protected final SignatureService<PrivateKey> signatureService;

    public TestDataGenerator(FaucetClient faucetClient, XrplClient xrplClient) {
        this.faucetClient = faucetClient;
        this.xrplClient = xrplClient;
        this.signatureService = new BcSignatureService();
    }

    @PostConstruct
    public void prepareTestAccounts() throws JsonRpcClientErrorException, JsonProcessingException {
        final KeyPair keyPair = Seed.secp256k1Seed().deriveKeyPair();
        log.info("Generated test wallet with classic-address: {}", keyPair.publicKey().deriveAddress());
        var response = faucetClient.fundAccount(FundAccountRequest.of(keyPair.publicKey().deriveAddress()));
        log.info("Test account funded: {}", response.account());

        mintSampleNfToken(keyPair, NfTokenUri.ofPlainText("ipfs://bafybeigdyrzt5sfp7udm7hu76uh7y26nf4dfuylqabf3oclgtqy55fbzdi"));
        mintSampleNfToken(keyPair, NfTokenUri.ofPlainText("ipfs://kfoqeigdyrzt5sfp7udm7hu76uh7y26nf4dfuylqabf3oclgtqy55fbzdi"));
    }

    private void mintSampleNfToken(KeyPair keyPair, NfTokenUri uri) throws JsonRpcClientErrorException, JsonProcessingException {
        AccountInfoResult accountInfoResult = xrplClient.accountInfo(AccountInfoRequestParams.of(keyPair.publicKey().deriveAddress()));
        NfTokenMint nfTokenMint = NfTokenMint.builder()
                .account(keyPair.publicKey().deriveAddress())
                .tokenTaxon(UnsignedLong.ONE)
                .fee(XrpCurrencyAmount.ofDrops(50))
                .signingPublicKey(keyPair.publicKey())
                .sequence(accountInfoResult.accountData().sequence())
                .uri(uri)
                .build();

        SingleSignedTransaction<NfTokenMint> signedTransaction = signatureService.sign(keyPair.privateKey(), nfTokenMint);
        SubmitResult<NfTokenMint> submitResult = xrplClient.submit(signedTransaction);
        log.info("Account {} minted NFToken of taxon: {}", keyPair.publicKey().deriveAddress(), submitResult.transactionResult().transaction().uri().orElseThrow());

    }
}
