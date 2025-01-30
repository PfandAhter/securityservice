package com.bakirwebservice.securityservice.rest.controller.api;

import com.bakirwebservice.securityservice.api.request.BaseRequest;
import com.bakirwebservice.securityservice.api.response.BaseResponse;
import com.bakirwebservice.securityservice.api.response.CheckCanAccess;
import com.bakirwebservice.securityservice.exceptions.AccessDeniedException;
import com.bakirwebservice.securityservice.exceptions.NotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface SecurityControllerApi {

    @GetMapping("/hasAccessToPath")
    ResponseEntity<CheckCanAccess> hasAccessToPath(@RequestParam("url") String url,@RequestParam("role") String role, HttpServletRequest request) throws AccessDeniedException, NotFoundException;

}
