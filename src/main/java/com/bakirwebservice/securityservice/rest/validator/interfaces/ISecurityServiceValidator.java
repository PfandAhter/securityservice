package com.bakirwebservice.securityservice.rest.validator.interfaces;

import com.bakirwebservice.securityservice.exceptions.NotFoundException;

public interface ISecurityServiceValidator {

    void validateHasAccessToPath(String url, String role) throws NotFoundException;

}