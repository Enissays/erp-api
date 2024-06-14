package com.est.erp_api.configurations;

import com.est.erp_api.models.User;
import com.est.erp_api.repositories.UserRepository;
import com.est.erp_api.services.JWTService;
import com.est.erp_api.services.UserService;
import com.sun.security.auth.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class AuthManager implements ReactiveAuthenticationManager {

    final JWTService jwtService;

    @Autowired
    UserRepository userRepository;

    public AuthManager(JWTService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        return Mono.justOrEmpty(authentication)
                .filter(auth -> auth instanceof BearerToken)
                .cast(BearerToken.class)
                .flatMap(auth -> {
                    String id = jwtService.parse(auth.getToken());
                    Mono<User> user = userRepository.findUserById(id).defaultIfEmpty(new User());
                    Mono<Authentication> authenticated = user.flatMap(u -> {
                        if (u.getEmail() == null) {
                            return Mono.error(new Exception("User not found"));
                        } else {
                            if (jwtService.validate(auth.getToken(), u)) return Mono.justOrEmpty(new UsernamePasswordAuthenticationToken(u.getEmail(), u.getPassword(), u.getAuthorities()));
                            return Mono.error(new Exception("Invalid token"));
                        }

                    });
                    return authenticated;
                });
    }
}