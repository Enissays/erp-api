package com.est.erp_api.modules;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ModuleDetail {
    private String name;
    private String description;
    private String[] roles;

}