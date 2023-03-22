package com.example.demo.xrpl.account.api.dto;

import lombok.Data;

import javax.annotation.Nullable;

@Data
public class NFTokenDto {
    private String id;
    @Nullable
    private String uri;
}
