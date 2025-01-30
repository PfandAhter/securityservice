package com.bakirwebservice.securityservice.rest.validator;

import com.bakirwebservice.securityservice.exceptions.NotFoundException;
import com.bakirwebservice.securityservice.rest.validator.interfaces.ISecurityServiceValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j

public class SecurityServiceValidatorImpl implements ISecurityServiceValidator {

    private static final List<String> PUBLIC_URLS = List.of(
            "/auth/login", "/auth/register/user", "/auth/register/seller",
            "/auth/password/change", "/auth/password/forget",
            "/auth/password/change-with-code"
            );

    @Override
    public void validateHasAccessToPath(String url, String role) throws NotFoundException {
        try {
            log.info("validateHasAccessToPath method is called");

            if(url == null || role == null){
                throw new NotFoundException("URL OR ROLE IS NULL / NOT FOUND");
            }
            if(role.equals("ADMIN")){
                return;
            }
            if(PUBLIC_URLS.contains(url)){
                return;
            }
        } catch (Exception e) {
            log.error("Error: ",e);
            throw new NotFoundException("URL OR ROLE IS NULL / NOT FOUND");
        }
    }
}