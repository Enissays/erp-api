package com.est.erp_api.modules.phases.services;

import com.est.erp_api.modules.phases.models.Dossier;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface DossierService {
    public Mono<Dossier> createDossier(Dossier dossier);
    public Mono<Dossier> updateDossier(Dossier dossier);
    public Mono<Dossier> getDossier(String id);
    public Mono<Void> deleteDossier(String id);
    public Flux<Dossier> findByCandidatId(String candidatId);
    public Flux<Dossier> findAll(int limit, int offset);
    public Flux<Dossier> editFromFile(FilePart filePart);

}
