package com.est.erp_api.services;

import com.est.erp_api.models.Role;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface RoleService {
    Mono<Boolean> userHasRole(String userId, String roleId);
    Mono<Void> addRoleToUser(String userId, String roleId);
    Flux<Role> getRolesByUserId(String userId);
    Mono<Role> findRoleByName(String roleName);
    Flux<Role> getAllRoles(List<String> roleIds);
}
