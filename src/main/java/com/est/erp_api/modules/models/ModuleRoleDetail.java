package com.est.erp_api.modules.models;

import com.est.erp_api.models.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ModuleRoleDetail {
    private String id;
    private String name;
    private String description;
    private List<Role> roles;


    public ModuleRoleDetail(String id, String name, String description, List<Role> roles) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.roles = roles;
    }
}
