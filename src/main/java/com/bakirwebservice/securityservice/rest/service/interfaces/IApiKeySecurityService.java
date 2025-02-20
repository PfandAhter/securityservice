package com.bakirwebservice.securityservice.rest.service.interfaces;

import com.bakirwebservice.securityservice.api.request.GenerateApiKeyRequest;
import com.bakirwebservice.securityservice.api.response.GenerateApiKeyResponse;

public interface IApiKeySecurityService {

    GenerateApiKeyResponse generateApiKey (GenerateApiKeyRequest request);

    boolean validateApiKey(String apiKey);
}
