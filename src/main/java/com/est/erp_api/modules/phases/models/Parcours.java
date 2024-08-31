package com.est.erp_api.modules.phases.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
public class Parcours {
    @Id
    private String parcours_id;

    private String niveau;
    private String filiere;
    private String code_apogee;
    private String option;
    private boolean nouvellement_inscrit;
    private String annee_universitaire;
    private String statut_inscription;
    private String resultat_session1;
    private String resultat_session2;
    private String resultat_final;
    private String dossier_id;
}
