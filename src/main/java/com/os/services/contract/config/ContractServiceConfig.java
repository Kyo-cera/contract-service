
package com.os.services.contract.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties("contract-service")
public class ContractServiceConfig
{
    private String rootCredential;
    private String serverIP;
}
