package com.est.erp_api.configurations;

import com.est.erp_api.services.JWTService;
import com.est.erp_api.services.RoleServiceDefault;

import com.est.erp_api.services.UserServiceDefault;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class AuthManager implements ReactiveAuthenticationManager {

    final JWTService jwtService;

    @Autowired
    UserServiceDefault userServiceDefault;

    @Autowired
    RoleServiceDefault roleServiceDefault;

    public AuthManager(JWTService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        return Mono.justOrEmpty(authentication)
                .filter(auth -> auth instanceof BearerToken)
                .cast(BearerToken.class)
                .flatMap(auth -> {
                    String userId = jwtService.parse(auth.getToken());
                    return userServiceDefault.getUserById(userId)
                            .flatMap(user -> {

                                if (jwtService.validate(auth.getToken(), user)) {
                                    return user.getAuth(roleServiceDefault)
                                            .collectList()
                                            .map(auths -> {
                                                return new UsernamePasswordAuthenticationToken(user.getUsername(), null, auths);
                                            });

                                } else {
                                    return Mono.error(new Exception("Invalid token"));
                                }
                            });
                });
    }
}