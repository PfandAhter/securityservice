package com.bakirwebservice.securityservice.rest.aspect;

import com.bakirwebservice.securityservice.rest.service.interfaces.ICacheService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component
@Slf4j
@RequiredArgsConstructor

public class ServiceStartupConfiguration {

    private final ICacheService cacheService;

    @EventListener(ApplicationReadyEvent.class)
    public void getErrorCodes() {
        cacheService.getErrorCodes();
    }

}
