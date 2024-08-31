package com.est.erp_api.repositories;

import com.est.erp_api.models.Role;
import com.est.erp_api.models.User;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface RolesRepository extends R2dbcRepository<Role, String> {

    @Query("SELECT COUNT(*) FROM user_roles WHERE user_id = :userId AND role_id = :roleId")
    Mono<Boolean> userHasRole(String userId, String roleId);

    @Query("INSERT INTO user_roles (user_id, role_id) VALUES (:userId, :roleId)")
    Mono<Void> addRoleToUser(String userId, String roleId);

    @Query ("SELECT r.* FROM roles r JOIN user_roles ur ON r.role_id = ur.role_id WHERE ur.user_id = :userId")
    List<Role> getRolesByUserId(String userId);

    @Query("SELECT * FROM roles")
    Flux<Role> findAllRoles();


    @Query("SELECT * FROM roles WHERE role_id = :roleId")
    Mono<Role> findRoleById(String roleId);

    @Query("SELECT * FROM roles WHERE role_name = :roleName")
    Mono<Role> findRoleByRoleName(String roleName);

}