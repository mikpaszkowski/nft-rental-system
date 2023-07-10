package com.rentalSystem.xrpl.client;

import com.rentalSystem.xrpl.models.URIToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class XrplServiceImpl {

    private final WebClient webClient;

    public List<URIToken> getURITokens(String accountNumber) {
        var response = webClient
                .get()
                .uri(uriBuilder -> uriBuilder.path("/uri-tokens/{accountNumber}").build(accountNumber))
                .retrieve()
                .toEntityList(URIToken.class)
                .block();
        assert response != null;
        return response.getBody();
    }
}

