package com.est.erp_api.modules.admin.service;

import com.est.erp_api.models.Role;
import com.est.erp_api.models.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public interface AdminService {
    Mono<Void> addRoleToUser(String userId, String roleId);
    Mono<Void> removeRoleFromUser(String userId, String roleId);
    Flux<Role> getAllRoles();
    Flux<User> getAllUsers();
    Flux<Role> getAllRolesByUserId(String userId);

    public Mono<Void> updateUsername(String userId, String name);

    public Mono<Void> updateEmail(String userId, String email);

    public Mono<Void> updatePassword(String userId, String password);
}
