package com.est.erp_api.modules.phases.models;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PhaseCandidat {
    private String phase_id;
    private String candidat_id;
    private String Code;
    private String nomfr;
    private String prenomfr;
    private String Filiere;
    private float Score;
    private String rang;


}
