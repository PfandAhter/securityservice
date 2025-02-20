package com.bakirwebservice.securityservice.rest.service;

import com.bakirwebservice.securityservice.api.client.TokenServiceClient;
import com.bakirwebservice.securityservice.api.request.GenerateApiKeyRequest;
import com.bakirwebservice.securityservice.api.response.GenerateApiKeyResponse;
import com.bakirwebservice.securityservice.exceptions.ApiKeyGenerationFailedException;
import com.bakirwebservice.securityservice.exceptions.ExpiredApiKeyException;
import com.bakirwebservice.securityservice.exceptions.InvalidKeyException;
import com.bakirwebservice.securityservice.exceptions.RateLimiterExceededException;
import com.bakirwebservice.securityservice.model.ApiKeyEntity;
import com.bakirwebservice.securityservice.model.SecurityProperties;
import com.bakirwebservice.securityservice.repository.ApiKeyRepository;
import com.bakirwebservice.securityservice.rest.service.interfaces.IApiKeySecurityService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j

public class ApiKeySecurityServiceImpl implements IApiKeySecurityService {

    private final ApiKeyRepository apiKeyRepository;

    private final SecurityProperties properties;

    private final TokenServiceClient tokenServiceClient;

    @Value("${security.apikey.secretkey}")
    private String secretKey;

    public GenerateApiKeyResponse generateApiKey (GenerateApiKeyRequest request){
        try{
//            String payload = userId + ":" + String.join(",",permissions) + ":" + System.currentTimeMillis();

            String username = tokenServiceClient.extractedUsername(request);

            List<String> permissions = request.getPermissions();

            String payload = createPayload(username,permissions);

            String apiKey = signPayload(payload);

            saveApiKey(username, apiKey, permissions);

            GenerateApiKeyResponse response = new GenerateApiKeyResponse();

            response.setApiKey(apiKey);
            response.setEstimatedExceededDate(LocalDateTime.now().plusDays(properties.getExpirationDays()));
            response.setDescription("API Key generated successfully");

            return response;
            /*SecretKeySpec key = new SecretKeySpec(secretKey.getBytes(),"HmacSHA256");
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(key);

            byte[] hash = mac.doFinal(payload.getBytes());
            String apiKey = Base64.getEncoder().encodeToString(hash) + "." +
                    Base64.getEncoder().encodeToString(payload.getBytes());

            saveApiKey(userId, apiKey, permissions);

            return apiKey;*/
        }catch (Exception e){
            throw new ApiKeyGenerationFailedException("API Key generation failed");
            //throw new ApiKeyGenerationFailedException("API Key generation failed.",e);
        }
    }

    private String signPayload (String payload) throws NoSuchAlgorithmException, java.security.InvalidKeyException {
        SecretKeySpec keySpec = new SecretKeySpec(properties.getSecretKey().getBytes(), "HmacSHA256");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(keySpec);

        byte[] hash = mac.doFinal(payload.getBytes());

        return Base64.getEncoder().encodeToString(hash) + "." +
                Base64.getEncoder().encodeToString(payload.getBytes());
    }

    private String createPayload(String userId, List<String> permissions) throws JsonProcessingException {
        Map<String, Object> payloadMap = new HashMap<>();
        payloadMap.put("uid",userId);
        payloadMap.put("perm",permissions);
        payloadMap.put("iat",System.currentTimeMillis());
        payloadMap.put("exp",System.currentTimeMillis() +
                (properties.getExpirationDays() * 24 * 60 * 60 * 1000L));

        return new ObjectMapper().writeValueAsString(payloadMap);
    }

    public boolean validateApiKey(String apiKey){
        try {
            ApiKeyEntity apiKeyEntity = apiKeyRepository.findByApiKey(apiKey)
                    .orElseThrow(() -> new InvalidKeyException("API Key not found"));

            if (apiKeyEntity.isExpired()) {
                throw new ExpiredApiKeyException("API Key has expired");
            }

            /*if (isRotationRequired(apiKeyEntity)) {
                markForRotation(apiKeyEntity);
            }*/

            return verifySignature(apiKey);
        }catch (Exception e){
            log.error("API Key validation failed",e);
            return false;
        }
    }

    private boolean verifySignature(String apikey) throws NoSuchAlgorithmException, java.security.InvalidKeyException {
        String[] parts = apikey.split("\\.");
        if(parts.length != 2){
            return false;
        }

        String providedHash = parts[0];
        String payload = new String(Base64.getDecoder().decode(parts[1]));

        SecretKeySpec keySpec = new SecretKeySpec(properties.getSecretKey().getBytes(), "HmacSHA256");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(keySpec);

        byte[] calculatedHash = mac.doFinal(payload.getBytes());
        String calculatedHashString = Base64.getEncoder().encodeToString(calculatedHash);

        return calculatedHashString.equals(providedHash);
    }

    private void saveApiKey(String userId, String apiKey, List<String> permissions) {
        apiKeyRepository.save(ApiKeyEntity.builder()
                .userId(userId)
                .apiKey(apiKey)
                .permissions(permissions)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusDays(properties.getExpirationDays()))
                .isActive(true)
                .build());//3 days
        log.info("API Key saved for user: " + userId);
    }

    /*private void markForRotation(ApiKeyEntity keyEntity) {
        keyEntity.setRotationRequired(true);
        keyEntity.setExpiresAt(LocalDateTime.now().plusDays(7)); // 7 günlük grace period
        apiKeyRepository.save(keyEntity);

        // Kullanıcıya rotasyon bildirimi gönder
        notifyUserAboutRotation(keyEntity.getUserId());
    }*/

    /*private boolean isRotationRequired(ApiKeyEntity apiKeyEntity){
        long creationTime = apiKeyEntity.getCreatedAt().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        long rotationThreshold = properties.getRotationDays() * 24 * 60 * 60 * 1000L;
        return System.currentTimeMillis() - creationTime > rotationThreshold;
    }*/
}