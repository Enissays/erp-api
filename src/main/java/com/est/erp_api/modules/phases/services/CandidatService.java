package com.est.erp_api.modules.phases.services;

import com.est.erp_api.modules.phases.models.PhaseCandidat;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CandidatService {
    public Mono<PhaseCandidat> addCandidat(PhaseCandidat candidat);
    public Mono<Void> deleteCandidat(String phase_id, String candidat_id);
    public Mono<Void> updateCandidat(PhaseCandidat candidat);
    public Flux<PhaseCandidat> getCandidats(String phase_id);
    public Mono<Void> importCandidats(FilePart file, String phase_id);
}
