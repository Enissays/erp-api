package com.est.erp_api.modules.phases.repositories;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import com.est.erp_api.modules.phases.models.Parcours;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ParcoursRepository extends R2dbcRepository<Parcours, String> {

    @Query("SELECT * FROM parcours WHERE dossier_id = :dossier_id")
    public Flux<Parcours> findByDossierId(String dossier_id);


    // Return all filieres
    @Query("SELECT DISTINCT filiere FROM parcours")
    public Flux<String> findAllFilieres();

    // Find by filiere, annee universitaire and code apogee
    @Query("SELECT * FROM parcours WHERE filiere = :filiere AND annee_universitaire = :annee AND code_apogee = :code")
    public Mono<Parcours> findByFiliereAndAnneeAndCode(String filiere, String annee, String code);

    // Find by filiere and annee universitaire
    @Query("SELECT * FROM parcours WHERE filiere = :filiere AND annee_universitaire = :annee")
    public Flux<Parcours> findByFiliereAndAnnee(String filiere, String annee);

    // Return all annees universitaires
    @Query("SELECT DISTINCT annee_universitaire FROM parcours")
    public Flux<String> findAllAnneesUniversitaires();

    // Change option
    @Query("UPDATE parcours SET option = :option WHERE parcours_id = :parcours_id")
    public Mono<Void> updateOption(String option, String parcours_id);
}
