package com.bakirwebservice.securityservice.rest.service;

import com.bakirwebservice.securityservice.api.response.CheckCanAccess;
import com.bakirwebservice.securityservice.exceptions.AccessDeniedException;
import com.bakirwebservice.securityservice.model.Path;
import com.bakirwebservice.securityservice.model.Role;
import com.bakirwebservice.securityservice.repository.PathRepository;
import com.bakirwebservice.securityservice.repository.RoleRepository;
import com.bakirwebservice.securityservice.rest.service.interfaces.ISecurityService;
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

    public CheckCanAccess hasAccessToPath(String pathString, String roleName, HttpServletRequest request) throws AccessDeniedException {
        Role role = roleRepository.findRoleByName(roleName);
        Path path = pathRepository.findPathByPath(pathString);

        if(pathString.equals("/auth/login")){
            return new CheckCanAccess();
        }

        boolean hasRole = path.getRoles().stream()
                .anyMatch(r -> r.getName().equalsIgnoreCase(role.getName()));

        if(!hasRole){
            throw new AccessDeniedException(ACCESS_DENIED_FOR_THE_PATH);
        }

        return new CheckCanAccess();
    }

    //TODO ADD 2F VERIFICATION FOR MICROSERVICES COMMUNICATION LIKE EACH MICROSERVICE HAS A SECRET KEY AND IT WILL BE CHECKED IN THE REQUEST HEADER
}