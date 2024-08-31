package com.est.erp_api.modules.phases.services;

import com.est.erp_api.modules.phases.models.PhaseCandidat;
import com.est.erp_api.modules.phases.models.PhaseInscription;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PhaseService {
    public Mono<PhaseInscription> createPhaseInscription(PhaseInscription phaseInscription);
    public Mono<Void> updatePhaseInscription(PhaseInscription phaseInscription);
    public Mono<Void> deletePhaseInscription(String phaseId);
    public Mono<PhaseInscription> getPhaseInscriptionById(String phaseId);
    public Flux<PhaseInscription> getPhaseInscriptions();

}
