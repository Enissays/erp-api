package com.est.erp_api.modules.phases.services;

import com.est.erp_api.modules.phases.models.PhaseInscription;
import com.est.erp_api.modules.phases.repositories.PhaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Transactional
@Service
public class PhaseServiceDefault implements PhaseService {

    @Autowired
    private PhaseRepository phaseRepository;

    @Override
    public Mono<PhaseInscription> createPhaseInscription(PhaseInscription phaseInscription) {
        return phaseRepository.save(phaseInscription);
    }

    @Override
    public Mono<Void> updatePhaseInscription(PhaseInscription phaseInscription) {
        return phaseRepository.save(phaseInscription).then();
    }

    @Override
    public Mono<Void> deletePhaseInscription(String phaseId) {
        return phaseRepository.deleteById(phaseId).then();
    }

    @Override
    public Mono<PhaseInscription> getPhaseInscriptionById(String phaseId) {
        return phaseRepository.findById(phaseId);
    }

    @Override
    public Flux<PhaseInscription> getPhaseInscriptions() {
        return phaseRepository.findAll();
    }
}
