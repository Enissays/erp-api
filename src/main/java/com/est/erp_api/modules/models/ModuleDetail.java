package com.est.erp_api.modules.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ModuleDetail {
    private String id;
    private String name;
    private String description;
    private String[] roles;


}