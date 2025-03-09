package com.bakirwebservice.securityservice.infra.impl;

import com.bakirwebservice.securityservice.infra.HeaderService;
import com.bakirwebservice.securityservice.model.CommonHeader;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Service
public class HeaderServiceImpl implements HeaderService {

    @Override
    public CommonHeader getHeader(HttpServletRequest request) {
        CommonHeader commonHeader = new CommonHeader();
        commonHeader.setToken(request.getHeader(AUTHORIZATION));
        return commonHeader;
    }
}