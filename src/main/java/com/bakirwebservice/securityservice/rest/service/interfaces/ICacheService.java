package com.bakirwebservice.securityservice.rest.service.interfaces;

import com.bakirwebservice.securityservice.model.ErrorCodes;

import java.util.HashMap;

public interface ICacheService {

    void getErrorCodes();

    HashMap<String, ErrorCodes> getErrorCodesList();
}
