package com.bakirwebservice.securityservice.rest.controller;

import com.bakirwebservice.securityservice.api.request.GenerateApiKeyRequest;
import com.bakirwebservice.securityservice.api.response.GenerateApiKeyResponse;
import com.bakirwebservice.securityservice.rest.controller.api.ApiKeyControllerApi;
import com.bakirwebservice.securityservice.rest.service.interfaces.IApiKeySecurityService;
import com.bakirwebservice.securityservice.rest.validator.interfaces.ISecurityServiceValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;


@RequiredArgsConstructor
@Controller
@CrossOrigin
@RequestMapping("/security/apikey")
@Slf4j

public class ApiKeyController implements ApiKeyControllerApi {

    private final IApiKeySecurityService apiKeySecurityService;

    private final ISecurityServiceValidator securityServiceValidator;

    @Override
    public ResponseEntity<GenerateApiKeyResponse> generateApikey(GenerateApiKeyRequest generateApiKeyRequest) {
        securityServiceValidator.validateApiKeyGeneratorHasAccessToGenerate(generateApiKeyRequest);
        log.info("SecurityService generateApiKey method is called");
        return ResponseEntity.ok(apiKeySecurityService.generateApiKey(generateApiKeyRequest));
    }

    @Override
    public ResponseEntity<Boolean> checkApiKey(String apiKey) {
//        securityServiceValidator.validateIsUserHasAccessThisApiKey();
        log.info("SecurityService checkApiKey method is called");
        return ResponseEntity.ok(apiKeySecurityService.validateApiKey(apiKey));
    }
}
