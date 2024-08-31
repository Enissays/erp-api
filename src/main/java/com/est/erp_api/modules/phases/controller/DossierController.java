package com.est.erp_api.modules.phases.controller;

import com.est.erp_api.modules.ModuleInfo;
import com.est.erp_api.modules.phases.models.Dossier;
import com.est.erp_api.modules.phases.services.DossierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@ModuleInfo(
        id = "dossier",
        name = "Gestion des Dossier",
        description = "Ce module est dédié à la gestion des dossiers des candidats.",
        roles = {"ROLE_ADMIN", "ROLE_SCHOLARSHIP"}
)
@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api/dossiers")
public class DossierController {

    @Autowired
    private DossierService dossierService;

    @PostMapping("/create")
    public Mono<Dossier> createDossier(@RequestBody Dossier dossier) {
        System.out.println(dossier);
        return dossierService.createDossier(dossier);
    }

    @PutMapping("/update")
    public Mono<Dossier> updateDossier(@RequestBody Dossier dossier) {
        return dossierService.updateDossier(dossier);
    }

    @GetMapping("/candidat/{id}")
    public Flux<Dossier> getDossier(@PathVariable String id) {
        return dossierService.findByCandidatId(id);
    }

    @GetMapping("/all/{limit}/{offset}")
    public Flux<Dossier> getAllDossiers(@PathVariable int limit, @PathVariable int offset) {
        return dossierService.findAll(limit, offset);
    }

    @PostMapping("/editFromFile")
    public Flux<Dossier> editFromFile(@RequestPart("file") FilePart filePart) {
        return dossierService.editFromFile(filePart);
    }

}
