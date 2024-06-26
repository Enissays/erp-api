package com.est.erp_api.models;

import lombok.Getter;
import org.springframework.data.annotation.Id;

@Getter
public class Role {
    @Id
    private String role_id;

    private String role_name;
    private String label;
    private String description;

}
