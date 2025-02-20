package com.bakirwebservice.securityservice.api.client;

import com.bakirwebservice.securityservice.api.request.BaseRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(value = "UserService", url = "${client.feign.user-service.path}")
public interface UserServiceClient {

    @PostMapping("${client.feign.user-service.extractRole}")
    String extractUserRole(@RequestBody BaseRequest baseRequest);
}