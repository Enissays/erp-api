package com.est.erp_api.modules.phases.controller;

import com.est.erp_api.modules.phases.models.Parcours;
import com.est.erp_api.modules.phases.services.ParcoursService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:8081")
@RequestMapping("/api/parcours")
public class ParcoursController {

    @Autowired
    private ParcoursService parcoursService;

    @GetMapping("/dossier")
    public Flux<Parcours> getParcoursByDossierId(@RequestParam Map<String, Object> params) {
        return parcoursService.getParcours((String) params.get("dossier_id"));
    }

    @PostMapping("/add")
    public Mono<Parcours> addParcours(@RequestBody Parcours parcours) {
        return parcoursService.addParcours(parcours);
    }

    @PostMapping("/delete")
    public Flux<Parcours> deleteParcours(@RequestParam Map<String, Object> params) {
        return parcoursService.deleteParcours((String) params.get("parcours_id")).thenMany(parcoursService.getParcours((String) params.get("dossier_id")));
    }

    @GetMapping("/filieres")
    public Mono<List<String>> findAllFilieres() {
        return parcoursService.findAllFilieres().collectList();
    }

    @PostMapping("/upload")
    public Mono<ResponseEntity<String>> uploadFile(@RequestPart("file") FilePart filePart, @RequestParam Map<String, Object> params) {
        return parcoursService.processFile(filePart, (String) params.get("filiere"), (String) params.get("annee"))
                .then(Mono.just(ResponseEntity.status(HttpStatus.OK).body("File processed successfully")));
    }

    @GetMapping("/annees")
    public Mono<List<String>> findAllAnneesUniversitaires() {
        return parcoursService.findAllAnneesUniversitaires().collectList();
    }

    @GetMapping("/all")
    public Flux<Parcours> getAllBy(@RequestParam Map<String, Object> params) {
        return parcoursService.getAllBy((String) params.get("filiere"), (String) params.get("annee"));
    }

    @GetMapping("/reintegrate")
    public Flux<Parcours> reintegrate(@RequestParam Map<String, Object> params) {
        return parcoursService.reintegrate((String) params.get("filiere"), (String) params.get("annee"));
    }

    @PostMapping("/extractAnnee")
    public Flux<String> extractAnnee(@RequestPart("file") FilePart filePart) {
        return parcoursService.extractAnnee(filePart);
    }

    @PostMapping("/updateOption")
    public Mono<Void> updateOption(@RequestBody Map<String, Object> params) {
        return parcoursService.updateOption((String) params.get("option"), (String) params.get("parcours_id"));
    }

    
}
