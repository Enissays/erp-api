package com.est.erp_api.services;

import com.est.erp_api.models.Role;
import com.est.erp_api.repositories.RolesRepository;
import com.est.erp_api.repositories.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@Transactional
@Configurable
public class RoleServiceDefault implements RoleService {

    @Autowired
    private RolesRepository roleRepository;

    @Autowired
    private UserRoleRepository user2RoleRepository;

    @Override
    public Mono<Boolean> userHasRole(String userId, String roleId) {
        return null;
    }

    @Override
    public Mono<Void> addRoleToUser(String userId, String roleId) {
        return null;
    }

    public Flux<Role> getRolesByUserId(String userId) {
        return user2RoleRepository.findByUserId(userId)
                .flatMap(user2Role -> {
                            return roleRepository.findRoleById(user2Role.getRole_id());
                        }
                );
    }

    public Mono<Role> findRoleByName(String roleName) {
        return roleRepository.findRoleByRoleName(roleName);
    }

    public Flux<Role> getAllRoles(List<String> roleIds) {
        return Flux.fromIterable(roleIds)
                .flatMap(roleRepository::findRoleByRoleName);
    }
}