package com.rentalSystem.xrpl.client;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class XRPLClient {
    private final WebClient webClient;


}
