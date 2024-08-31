package com.est.erp_api.modules.phases.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;

import java.time.LocalDate;
import java.util.Date;

@Setter
@Getter
public class Dossier implements Persistable<String> {
    @Id
    private String dossier_id;


    private String code_massar;
    private String piece_identite;
    private String num_piece_identite;
    private String nom;
    private String prenom;
    private String nom_arabe;
    private String prenom_arabe;
    private String sexe;
    private LocalDate date_naissance;
    private String lieu_naissance;
    private String lieu_naissance_arabe;
    private String nationalite;
    private String pays_origine;
    private String adresse;
    private String province_residence;
    private String telephone;
    private String telephone2;
    private String email;
    private boolean handicap;
    private String type_handicap;
    private String serie_bac;
    private String annee_bac;
    private String delegation_province;
    private String academie;
    private String diplome;
    private String adresse_academique;
    private String etat_dossier;
    private boolean donnees_verifiees_etudiant;
    private String code_apogee;
    private String candidat_id;

    @Override
    public String getId() {
        return dossier_id;
    }

    @Override
    public boolean isNew() {
        return dossier_id != null;
    }
}