package com.bakirwebservice.securityservice.security;

import com.bakirwebservice.securityservice.model.Path;
import com.bakirwebservice.securityservice.model.Role;
import com.bakirwebservice.securityservice.repository.PathRepository;
import com.bakirwebservice.securityservice.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CustomAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {

    private final PathRepository pathRepository;

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext context) {
        String requestUrl = context.getRequest().getRequestURI();
        Path path = pathRepository.findPathByPath(requestUrl);

        context.getRequest().getParameterValues("buna bi bak...");  //TODO Bakilacak. RequestParam ile gelen istekleride kontrol etmek icin duzenlenmesi lazim.

        if(path == null){
            return new AuthorizationDecision(false);
        }
        Set<Role> requiredRoles = new HashSet<>(path.getRoles());
        Set<Role> allRequiredRoles = new HashSet<>(requiredRoles);

        for (Role role: requiredRoles) {
            allRequiredRoles.addAll(getAllParentRoles(role,new HashSet<>()));
        }

        Set<String> requiredRoleNames = allRequiredRoles.stream()
                .map(Role::getName)
                .collect(Collectors.toSet());

        Authentication auth = authentication.get();

        if(auth == null || !auth.isAuthenticated()){
            return new AuthorizationDecision(false);
        }

        boolean hasRequiredRole = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(requiredRoleNames::contains);

        return new AuthorizationDecision(hasRequiredRole);
    }

    private Set<Role> getAllParentRoles (Role role, Set<Role> visited){
        if(role == null || visited.contains(role)){
            return new HashSet<>();
        }

        visited.add(role);
        Set<Role> allParentRoles = new HashSet<>(role.getParentRoles());

        for (Role parentRole: role.getParentRoles()) {
            allParentRoles.addAll(getAllParentRoles(parentRole,visited));
        }

        return allParentRoles;
    }
}