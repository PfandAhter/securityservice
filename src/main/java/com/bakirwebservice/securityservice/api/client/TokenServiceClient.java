package com.bakirwebservice.securityservice.api.client;

import com.bakirwebservice.securityservice.api.request.BaseRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "UserService", url = "${client.feign.token-service.path}")
public interface TokenServiceClient {

    @PostMapping("${client.feign.token-service.extractUsername}")
    String extractedUsername (@RequestBody BaseRequest baseRequest);

}
