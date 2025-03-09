package com.bakirwebservice.securityservice.rest.service.interfaces;

import com.bakirwebservice.securityservice.api.request.BaseRequest;
import com.bakirwebservice.securityservice.api.response.BaseResponse;
import com.bakirwebservice.securityservice.api.response.CheckCanAccess;
import com.bakirwebservice.securityservice.exceptions.AccessDeniedException;
import jakarta.servlet.http.HttpServletRequest;

public interface ISecurityService {

    CheckCanAccess hasAccessToPath(String pathString, String roleName, HttpServletRequest request) throws AccessDeniedException;

    BaseResponse logout(BaseRequest baseRequest, HttpServletRequest request);
}
