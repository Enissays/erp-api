package com.est.erp_api.repositories;

import com.est.erp_api.models.UserRole;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserRoleRepository extends R2dbcRepository<UserRole, Integer> {
    @Query("SELECT * FROM user_roles WHERE user_id = :userId")
    Flux<UserRole> findByUserId(String userId);

    @Query("SELECT * FROM user_roles WHERE role_id = :roleId")
    Flux<UserRole> findByRoleId(String roleId);

    @Query("DELETE FROM user_roles WHERE user_id = :userId AND role_id = :roleId")
    Mono<Void> removeRoleFromUser(String userId, String roleId);


    @Query("INSERT INTO user_roles (user_id, role_id) VALUES (:userId, :roleId)")
    Mono<Void> addRoleToUser(String userId, String roleId);
}
