package com.est.erp_api.models;

import lombok.Getter;
import org.springframework.data.annotation.Id;

public class Role {
    @Id
    private String role_id;

    @Getter
    private String role_name;

}
