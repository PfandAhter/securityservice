package com.bakirwebservice.securityservice.rest.validator;

import com.bakirwebservice.securityservice.api.client.TokenServiceClient;
import com.bakirwebservice.securityservice.api.client.UserServiceClient;
import com.bakirwebservice.securityservice.api.request.BaseRequest;
import com.bakirwebservice.securityservice.exceptions.AccessDeniedException;
import com.bakirwebservice.securityservice.exceptions.NotFoundException;
import com.bakirwebservice.securityservice.rest.validator.interfaces.ISecurityServiceValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j

public class SecurityServiceValidatorImpl implements ISecurityServiceValidator {

    private static final List<String> PUBLIC_URLS = List.of(
            "/auth/login", "/auth/register/user", "/auth/register/seller",
            "/auth/password/change", "/auth/password/forget",
            "/auth/password/change-with-code"
            );

    private final UserServiceClient userServiceClient;

    private final TokenServiceClient tokenServiceClient;

    @Override
    public void validateHasAccessToPath(String url, String role) throws NotFoundException {
        try {
            log.info("validateHasAccessToPath method is called");

            if(url == null || role == null){
                throw new NotFoundException("URL OR ROLE IS NULL / NOT FOUND");
            }
            if(role.equals("ADMIN")){
                return;
            }
            if(PUBLIC_URLS.contains(url)){
                return;
            }
        } catch (Exception e) {
            log.error("Error: ",e); //TODO BURAYA BAKSANA BI CATCH ICERISINDEN DE AYRICA THROW ETMEYE GEREK VAR MI BU GENERALEXCEPTION HANDLERDA HALLETMEK ICIN
            throw new NotFoundException("URL OR ROLE IS NULL / NOT FOUND");
        }
    }

    public void validateApiKeyGeneratorHasAccessToGenerate(BaseRequest request){
        try{
            String userRole = userServiceClient.extractUserRole(request);

            if(!userRole.equals("ADMIN")){
                throw new AccessDeniedException("ACCESS_DENIED");
            }
        }catch (Exception e){
            log.error("Error: ",e);
        }
    }

    public void validateIsUserHasAccessThisApiKey(BaseRequest request){
        try{
            String username = tokenServiceClient.extractedUsername(request);

            // Burada oncelikle breakpointlerle orada ne islem dondugunu gor oradan uid kisminin nasil extract edilecegine gore burada bir validate islemi yap.
        }catch (Exception e){
            log.error("Error: ",e);
        }
    }
}