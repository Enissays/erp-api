package com.est.erp_api.modules.phases.models;

import lombok.Getter;
import org.springframework.data.annotation.Id;

@Getter
public class PhaseInscription {
    @Id
    private String phase_id;

    private String phase_name;
    private String label;
    private String description;


}
