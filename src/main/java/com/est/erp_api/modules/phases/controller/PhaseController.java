package com.est.erp_api.modules.phases.controller;

import com.est.erp_api.modules.ModuleInfo;
import com.est.erp_api.modules.phases.models.PhaseInscription;
import com.est.erp_api.modules.phases.services.PhaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@ModuleInfo(id = "phases", name = "Gestion des phases", description = "Module de gestion des pre-inscriptions", roles = {"ROLE_SCHOLAR", "ROLE_ADMIN"})
@RestController
@CrossOrigin(origins = "http://localhost:8081")
@RequestMapping("/api/phases")
public class PhaseController {

    @Autowired
    PhaseService phaseService;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_SCHOLAR')")
    public Mono<String> getTitre() {
        return Mono.just("phases");
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_SCHOLAR')")
    public Flux<PhaseInscription> getAllPhases() {
        return phaseService.getPhaseInscriptions();
    }

    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SCHOLAR')")
    public Mono<PhaseInscription> createPhase(@RequestBody PhaseInscription phaseInscription) {
        System.out.println(phaseInscription);
        return phaseService.createPhaseInscription(phaseInscription);
    }

    @PutMapping("/update")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SCHOLAR')")
    public Mono<Void> updatePhase(@RequestBody PhaseInscription phaseInscription) {
        return phaseService.updatePhaseInscription(phaseInscription);
    }

    @PostMapping("/delete")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SCHOLAR')")
    public Mono<Void> deletePhase(@RequestParam String phaseId) {
        return phaseService.deletePhaseInscription(phaseId);
    }

    @GetMapping("/get/{phaseId}")
    @PreAuthorize("hasRole('ROLE_SCHOLAR')")
    public Mono<PhaseInscription> getPhaseById(@PathVariable String phaseId) {
        return phaseService.getPhaseInscriptionById(phaseId);
    }





}
