package com.est.erp_api.modules.phases.controller;

import com.est.erp_api.modules.ModuleInfo;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@ModuleInfo(name = "Gestion des phases", description = "Module de gestion des pre-inscriptions", roles = {"ROLE_SCHOLAR"})
@RestController
@RequestMapping("/api/phases")
public class PhaseController {

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_SCHOLAR')")
    public Mono<String> getAllPhases() {
        return Mono.just("All phases");
    }
}
