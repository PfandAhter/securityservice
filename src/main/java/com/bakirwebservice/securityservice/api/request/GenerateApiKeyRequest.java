package com.bakirwebservice.securityservice.api.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GenerateApiKeyRequest extends BaseRequest {

    private String userId;

    private List<String> permissions;


}