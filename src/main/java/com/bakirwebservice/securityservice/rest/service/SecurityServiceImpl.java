package com.bakirwebservice.securityservice.rest.service;

import com.bakirwebservice.securityservice.api.request.BaseRequest;
import com.bakirwebservice.securityservice.api.response.BaseResponse;
import com.bakirwebservice.securityservice.api.response.CheckCanAccess;
import com.bakirwebservice.securityservice.exceptions.AccessDeniedException;
import com.bakirwebservice.securityservice.exceptions.ProcessFailedException;
import com.bakirwebservice.securityservice.model.Path;
import com.bakirwebservice.securityservice.model.Role;
import com.bakirwebservice.securityservice.repository.PathRepository;
import com.bakirwebservice.securityservice.repository.RoleRepository;
import com.bakirwebservice.securityservice.rest.service.interfaces.ISecurityService;
import com.bakirwebservice.securityservice.rest.service.jwt.JwtService;
import com.bakirwebservice.securityservice.rest.service.jwt.TokenBlackListService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.bakirwebservice.securityservice.constants.ErrorCodeConstants.ACCESS_DENIED_FOR_THE_PATH;

@Service
@RequiredArgsConstructor
@Slf4j

public class SecurityServiceImpl implements ISecurityService {

    private final RoleRepository roleRepository;

    private final PathRepository pathRepository;

    private final JwtService jwtService;

    private final TokenBlackListService tokenBlackListService;

    public CheckCanAccess hasAccessToPath(String pathString, String roleName, HttpServletRequest request) throws AccessDeniedException {
        Role role = roleRepository.findRoleByName(roleName);
        Path path = pathRepository.findPathByPath(pathString);

        if (pathString.equals("/auth/login")) {
            return new CheckCanAccess();
        }

        boolean hasRole = path.getRoles().stream()
                .anyMatch(r -> r.getName().equalsIgnoreCase(role.getName()));

        if (!hasRole) {
            throw new AccessDeniedException(ACCESS_DENIED_FOR_THE_PATH);
        }

        return new CheckCanAccess();
    }


    public BaseResponse logout(BaseRequest baseRequest, HttpServletRequest request) {
        try {
            String token = baseRequest.getToken();

            if (token == null) {
                throw new ProcessFailedException("LOGOUT_FAILED");
            }
            long ttl = jwtService.extractExpiration(token).getTime() - System.currentTimeMillis();

            tokenBlackListService.blackListToken(token, ttl);

            return new BaseResponse("Logged out successfully");
        } catch (Exception e) {
            throw new ProcessFailedException("LOGOUT_FAILED");
        }
    }

    //TODO ADD 2F VERIFICATION FOR MICROSERVICES COMMUNICATION LIKE EACH MICROSERVICE HAS A SECRET KEY AND IT WILL BE CHECKED IN THE REQUEST HEADER
}