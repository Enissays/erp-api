package com.est.erp_api.controller;

import com.est.erp_api.models.User;
import com.est.erp_api.models.requests.LoginRequest;
import com.est.erp_api.models.responses.RequestResponse;
import com.est.erp_api.services.JWTService;
import com.est.erp_api.services.UserServiceDefault;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Map;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserServiceDefault userService;

    @Autowired
    private JWTService jwtService;



    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public Mono<ResponseEntity<RequestResponse<String>>> loginUser(@RequestBody LoginRequest user) {
        Mono<User> userMono = userService.validateUser(user.getEmail(), user.getPassword()).defaultIfEmpty(new User());
        return userMono.flatMap(u -> {
            if (u.getEmail() == null) {
                return Mono.just(
                        ResponseEntity.badRequest().body(new RequestResponse<>(null, "Not found"))
                );
            } else {

                System.out.println("User found: " + u);
                return Mono.just(
                        ResponseEntity.ok(new RequestResponse<>(jwtService.generate(u.getUser_id()), "Success"))
                );
            }
        });
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ResponseEntity<RequestResponse<String>>> registerUser(@RequestBody Map<String, Object> userMap) {
        String name = (String) userMap.get("name");
        String email = (String) userMap.get("email");
        String password = (String) userMap.get("password");
        return userService.registerUser(name, email, password)
                .map(u -> ResponseEntity.ok(new RequestResponse<>("User registered successfully", "")))
                .defaultIfEmpty(ResponseEntity.badRequest().body(new RequestResponse<>("User registration failed", "")));
    }
}
