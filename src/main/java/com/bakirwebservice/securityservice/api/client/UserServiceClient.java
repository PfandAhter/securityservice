package com.bakirwebservice.securityservice.api.client;

import com.bakirwebservice.securityservice.api.request.BaseRequest;
import com.bakirwebservice.securityservice.exceptions.decoder.CustomFeignErrorDecoder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(value = "user-service", url = "${client.feign.user-service.path}", configuration = {CustomFeignErrorDecoder.class})
public interface UserServiceClient {

    @PostMapping("${client.feign.user-service.extractRole}")
    String extractUserRole(@RequestBody BaseRequest baseRequest);

    @GetMapping("${client.feign.user-service.getUserDetails}")
    UserDetails getUserDetails (@RequestParam("name") String username);
}