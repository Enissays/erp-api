package com.est.erp_api.modules.phases.controller;

import com.est.erp_api.modules.phases.models.PhaseCandidat;
import com.est.erp_api.modules.phases.services.CandidatService;
import com.est.erp_api.modules.phases.services.CandidatServiceDefault;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@CrossOrigin(origins = "http://localhost:8081")
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RestController
@RequestMapping("/api/candidats")
public class CandidatController {

    @Autowired
    private CandidatService candidatService;

    @GetMapping("/all/{phase_id}")
    public Flux<PhaseCandidat> getCandidats(@PathVariable String phase_id) {
        return candidatService.getCandidats(phase_id);
    }

    @PostMapping("/add")
    public Mono<PhaseCandidat> addCandidat(@RequestBody PhaseCandidat candidat) {
        return candidatService.addCandidat(candidat);
    }


    @PostMapping(path="/upload", consumes = { "text/csv", MediaType.MULTIPART_FORM_DATA_VALUE })
    public Mono<Void> importCandidats(@RequestParam String phase_id, @RequestPart("file") FilePart file) {
        return candidatService.importCandidats(file, phase_id);
    }


}
