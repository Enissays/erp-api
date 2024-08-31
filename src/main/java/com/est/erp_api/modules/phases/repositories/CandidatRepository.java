package com.est.erp_api.modules.phases.repositories;

import com.est.erp_api.modules.phases.models.PhaseCandidat;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

public interface CandidatRepository extends R2dbcRepository<PhaseCandidat, String>{

    @Query("SELECT * FROM phase_candidat WHERE phase_id = :phase_id")
    public Flux<PhaseCandidat> findByPhaseId(String phase_id);
}
