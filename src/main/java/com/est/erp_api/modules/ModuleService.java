package com.est.erp_api.modules;


import com.est.erp_api.services.JWTService;
import com.est.erp_api.services.RoleService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import com.est.erp_api.models.Role;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ModuleService {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private RoleService roleService;

    public List<ModuleDetail> getAllModules() {
        Map<String, Object> beans = applicationContext.getBeansWithAnnotation(ModuleInfo.class);
        List<ModuleDetail> moduleDetails = new ArrayList<>();

        for (Object bean : beans.values()) {
            ModuleInfo moduleInfo = AnnotationUtils.findAnnotation(bean.getClass(), ModuleInfo.class);
            if (moduleInfo != null) {
                ModuleDetail detail = new ModuleDetail();
                detail.setName(moduleInfo.name());
                detail.setDescription(moduleInfo.description());
                detail.setRoles(moduleInfo.roles());
                moduleDetails.add(detail);
            }
        }
        return moduleDetails;
    }

    public List<ModuleDetail> getModulesByRole(String role) {
        List<ModuleDetail> allModules = getAllModules();
        return allModules.stream()
                .filter(module -> Arrays.asList(module.getRoles()).contains(role))
                .collect(Collectors.toList());
    }

    public Flux<ModuleDetail> getModulesByUser(String userId) {
        return roleService.getRolesByUserId(userId)
                .map(Role::getRole_name)

                .collectList()
                .flatMapMany(userRoles -> Flux.fromIterable(getAllModules())
                        .filter(module -> Arrays.stream(module.getRoles()).anyMatch(userRoles::contains)));
    }


}

