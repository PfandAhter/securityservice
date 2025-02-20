package com.bakirwebservice.securityservice.api.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter

public class GenerateApiKeyResponse extends BaseResponse{

    private String apiKey;

    private LocalDateTime estimatedExceededDate;
}