package com.bakirwebservice.securityservice.rest.service;

import com.bakirwebservice.securityservice.model.ErrorCodes;
import com.bakirwebservice.securityservice.repository.ErrorCodeRepository;
import com.bakirwebservice.securityservice.rest.service.interfaces.ICacheService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j

public class CacheServiceImpl implements ICacheService {

    private static final HashMap<String, ErrorCodes> errorCodeList = new HashMap<>();

    private final ErrorCodeRepository errorCodeRepository;


    @Override
    public void getErrorCodes() {
        try {
            List<ErrorCodes> errorCodes = errorCodeRepository.findAll();
            errorCodes.forEach(errorCode -> errorCodeList.put(errorCode.getId(), errorCode));

        } catch (Exception e) {
            log.error("Error in getting error codes from database");
        }
    }

    @Override
    public HashMap<String,ErrorCodes> getErrorCodesList() {
        return errorCodeList;
    }

}
