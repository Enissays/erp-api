package com.est.erp_api.modules.phases.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/phases")
public class PhaseController {

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Mono<String> getAllPhases() {
        return Mono.just("All phases");
    }
}
