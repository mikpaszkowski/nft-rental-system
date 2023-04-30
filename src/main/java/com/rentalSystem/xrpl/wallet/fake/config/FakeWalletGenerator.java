package com.rentalSystem.xrpl.wallet.fake.config;

import com.rentalSystem.xrpl.wallet.fake.domain.model.WalletView;
import com.rentalSystem.xrpl.wallet.fake.domain.repository.WalletRepository;
import feign.FeignException;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.xrpl.xrpl4j.client.faucet.FaucetClient;
import org.xrpl.xrpl4j.client.faucet.FundAccountRequest;
import org.xrpl.xrpl4j.crypto.keys.KeyPair;
import org.xrpl.xrpl4j.crypto.keys.Seed;

import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class FakeWalletGenerator {

    private final FaucetClient faucetClient;

    private final WalletRepository walletRepository;

    private static final Integer NUM_OF_TEST_WALLETS = 0;

    public FakeWalletGenerator(FaucetClient faucetClient, WalletRepository walletRepository) {
        this.faucetClient = faucetClient;
        this.walletRepository = walletRepository;
    }

    @PostConstruct
    public void prepareTestWallets() throws InterruptedException {
        var i = 0;
        while (i < NUM_OF_TEST_WALLETS) {
            wallet();
            TimeUnit.SECONDS.sleep(10);
            i++;
        }
    }

    private void wallet() {
        try {
            final KeyPair keyPair = Seed.secp256k1Seed().deriveKeyPair();
            log.info("Generated test wallet with classic-address: {}", keyPair.publicKey().deriveAddress());
            var response = faucetClient.fundAccount(FundAccountRequest.of(keyPair.publicKey().deriveAddress()));
            log.info("Test account funded: {}", response.account());
            var savedWallet = walletRepository.save(WalletView.builder()
                    .address(response.account().address().value())
                    .publicKey(keyPair.publicKey().base58Value())
                    .privateKeyHex(keyPair.privateKey().value().hexValue())
                    .build());
            log.info("Wallet referenced to address: {} saved to database", savedWallet.getAddress());
        } catch (FeignException ex) {
            log.warn("Failure in account funding with wallet creation");
        }
    }


}
