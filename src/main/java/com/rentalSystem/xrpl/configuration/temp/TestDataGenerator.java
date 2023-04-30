package com.rentalSystem.xrpl.configuration.temp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.primitives.UnsignedLong;
import com.rentalSystem.xrpl.wallet.fake.config.FakeWalletGenerator;
import com.rentalSystem.xrpl.wallet.fake.domain.repository.WalletRepository;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;
import org.xrpl.xrpl4j.client.JsonRpcClientErrorException;
import org.xrpl.xrpl4j.client.XrplClient;
import org.xrpl.xrpl4j.codec.addresses.UnsignedByteArray;
import org.xrpl.xrpl4j.crypto.keys.KeyPair;
import org.xrpl.xrpl4j.crypto.keys.PrivateKey;
import org.xrpl.xrpl4j.crypto.keys.PublicKey;
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
@DependsOn("fakeWalletGenerator")
public class TestDataGenerator {

    private final XrplClient xrplClient;

    protected final SignatureService<PrivateKey> signatureService;

    private final WalletRepository walletRepository;

//    private final FakeWalletGenerator fakeWalletGenerator;

    public TestDataGenerator(XrplClient xrplClient, WalletRepository walletRepository) {
        this.xrplClient = xrplClient;
        this.walletRepository = walletRepository;
//        this.fakeWalletGenerator = fakeWalletGenerator;
        this.signatureService = new BcSignatureService();
    }

    @PostConstruct
    public void prepareTestAccounts() throws InterruptedException {
//        fakeWalletGenerator.prepareTestWallets();
        var wallets = walletRepository.findAll();
        wallets.stream().forEach((walletView -> {
            var keyPair = KeyPair.builder()
                    .publicKey(PublicKey.fromBase58EncodedPublicKey(walletView.getPublicKey()))
                    .privateKey(PrivateKey.of(UnsignedByteArray.fromHex(walletView.getPrivateKeyHex())))
                    .build();
            try {
                mintSampleNfToken(keyPair, NfTokenUri.ofPlainText("ipfs://bafybeigdyrzt5sfp7udm7hu76uh7y26nf4dfuylqabf3oclgtqy55fbzdi"));
            } catch (JsonRpcClientErrorException | JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }));
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
