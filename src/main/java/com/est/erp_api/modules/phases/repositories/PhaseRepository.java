package com.est.erp_api.modules.phases.repositories;

import com.est.erp_api.modules.phases.models.PhaseInscription;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface PhaseRepository extends R2dbcRepository<PhaseInscription, String> {

}
