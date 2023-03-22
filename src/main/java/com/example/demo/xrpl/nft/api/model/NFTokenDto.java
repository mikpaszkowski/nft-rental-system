package com.example.demo.xrpl.nft.api.model;

import lombok.Data;

import javax.annotation.Nullable;

@Data
public class NFTokenDto {
    private String id;
    @Nullable
    private String uri;
}
