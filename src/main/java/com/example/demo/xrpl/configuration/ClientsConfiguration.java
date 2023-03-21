package com.example.demo.xrpl.configuration;

import com.example.demo.xrpl.configuration.properties.XrplConfigurationProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.HttpUrl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.xrpl.xrpl4j.client.XrplClient;
import org.xrpl.xrpl4j.client.faucet.FaucetClient;

@Configuration
@RequiredArgsConstructor
@Slf4j
class ClientsConfiguration {

    private final XrplConfigurationProperties configurationProperties;

    @Bean
    public XrplClient xrplClient() {
        var client = new XrplClient(HttpUrl.get(configurationProperties.getUrl()));
        log.info("XRP ledger network address: {}", configurationProperties.getUrl());
        return client;
    }

    @Bean
    public FaucetClient faucetClient() {
        return FaucetClient.construct(HttpUrl.get("https://faucet.devnet.rippletest.net"));
    }
}
