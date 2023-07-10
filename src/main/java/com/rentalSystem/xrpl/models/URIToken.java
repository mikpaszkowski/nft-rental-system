package com.rentalSystem.xrpl.models;

import lombok.Data;

@Data
public class URIToken {

    private String index;
    private String uri;
    private String owner;
    private String issuer;
    private String destination;
    private Integer amount;
    private String digest;
}
