package com.bakirwebservice.securityservice.infra;

import com.bakirwebservice.securityservice.model.CommonHeader;
import jakarta.servlet.http.HttpServletRequest;

public interface HeaderService {
    CommonHeader getHeader(HttpServletRequest request);
}
