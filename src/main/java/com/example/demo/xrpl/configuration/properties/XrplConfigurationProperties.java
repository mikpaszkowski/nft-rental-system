package com.example.demo.xrpl.configuration.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "xrpl")
public class XrplConfigurationProperties {
    private String url;
}
