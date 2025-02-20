package com.bakirwebservice.securityservice.rest.validator.interfaces;

import com.bakirwebservice.securityservice.api.request.BaseRequest;
import com.bakirwebservice.securityservice.exceptions.NotFoundException;

public interface ISecurityServiceValidator {

    void validateHasAccessToPath(String url, String role) throws NotFoundException;

    void validateApiKeyGeneratorHasAccessToGenerate(BaseRequest request);

    void validateIsUserHasAccessThisApiKey(BaseRequest request);
}