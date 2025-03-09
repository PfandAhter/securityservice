package com.bakirwebservice.securityservice.rest.controller.api;

import com.bakirwebservice.securityservice.api.request.GenerateApiKeyRequest;
import com.bakirwebservice.securityservice.api.response.GenerateApiKeyResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface ApiKeyControllerApi {

    @PostMapping("/generateApiKey")
    ResponseEntity<GenerateApiKeyResponse> generateApikey(@RequestBody GenerateApiKeyRequest generateApiKeyRequest);

    @GetMapping("/checkApiKey")
    ResponseEntity<Boolean> checkApiKey(@RequestParam("apiKey") String apiKey);
}
