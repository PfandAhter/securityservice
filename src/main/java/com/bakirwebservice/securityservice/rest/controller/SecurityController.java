package com.bakirwebservice.securityservice.rest.controller;

import com.bakirwebservice.securityservice.api.request.BaseRequest;
import com.bakirwebservice.securityservice.api.request.GenerateApiKeyRequest;
import com.bakirwebservice.securityservice.api.response.BaseResponse;
import com.bakirwebservice.securityservice.api.response.CheckCanAccess;
import com.bakirwebservice.securityservice.api.response.GenerateApiKeyResponse;
import com.bakirwebservice.securityservice.exceptions.AccessDeniedException;
import com.bakirwebservice.securityservice.exceptions.NotFoundException;
import com.bakirwebservice.securityservice.rest.controller.api.SecurityControllerApi;
import com.bakirwebservice.securityservice.rest.service.interfaces.IApiKeySecurityService;
import com.bakirwebservice.securityservice.rest.service.interfaces.ISecurityService;
import com.bakirwebservice.securityservice.rest.validator.interfaces.ISecurityServiceValidator;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@CrossOrigin
@RequestMapping("/security")
@RequiredArgsConstructor
@Slf4j

public class SecurityController implements SecurityControllerApi {

    private final ISecurityService securityService;

    private final ISecurityServiceValidator securityServiceValidator;

    @Override
    public ResponseEntity<CheckCanAccess> hasAccessToPath(String url, String role,HttpServletRequest request) throws AccessDeniedException, NotFoundException {
        securityServiceValidator.validateHasAccessToPath(url,role);
        log.info("SecurityService hasAccessToPath method is called");
        return ResponseEntity.ok(securityService.hasAccessToPath(url,role,request));
    }

    @Override
    public ResponseEntity<BaseResponse> logout(BaseRequest baseRequest, HttpServletRequest request) {
        return ResponseEntity.ok(securityService.logout(baseRequest,request));
    }
}