package com.est.erp_api.modules.phases.repositories;

import com.est.erp_api.modules.phases.models.Dossier;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface DossierRepository extends R2dbcRepository<Dossier, String> {

    @Query("SELECT * FROM dossier WHERE candidat_id = :candidat_id")
    public Flux<Dossier> findByCandidatId(String candidat_id);

    @Query("SELECT * FROM dossier LIMIT :limit OFFSET :offset")
    public Flux<Dossier> findAll(int limit, int offset);

    @Query("SELECT * FROM dossier WHERE code_massar = :code_massar")
    public Mono<Dossier> findByCodeMassar(String code_massar);
}
