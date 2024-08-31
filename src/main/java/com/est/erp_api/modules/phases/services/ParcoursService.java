package com.est.erp_api.modules.phases.services;

import com.est.erp_api.modules.phases.models.Parcours;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ParcoursService {
    Flux<Parcours> getParcours(String dossier_id);
    Mono<Parcours> addParcours(Parcours parcours);
    Mono<Void> deleteParcours(String parcours_id);
    Flux<String> findAllFilieres();
    Flux<String> findAllAnneesUniversitaires();
    Flux<Parcours> processFile(FilePart filePart, String id, String filiere);
    Flux<Parcours> getAllBy(String filiere, String annee);
    Flux<Parcours> reintegrate(String filiere, String annee);
    Flux<String> extractAnnee(FilePart filePart);
    Mono<Void> updateOption(String option, String parcours_id);
}
