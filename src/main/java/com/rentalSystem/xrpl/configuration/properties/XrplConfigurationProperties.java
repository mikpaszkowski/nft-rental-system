package com.rentalSystem.xrpl.configuration.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "xrpl.addresses")
public class XrplConfigurationProperties {
    private String clientUrl;
    private String faucetUrl;
}
