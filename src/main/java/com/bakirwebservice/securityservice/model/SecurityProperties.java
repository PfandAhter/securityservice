package com.bakirwebservice.securityservice.model;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@ConfigurationProperties(prefix = "security.apikey")
@Component
@Getter
@Setter

public class SecurityProperties {
    private String secretKey;
    private int expirationDays = 7;
    private int maxRequestsPerMinute = 60;
    private List<String> allowedIpRanges = new ArrayList<>();
    private int maxActiveKeysPerUser = 5;
    private int rotationDays = 90;
}