package com.est.erp_api.modules.admin.service;

import com.est.erp_api.models.Role;
import com.est.erp_api.models.User;
import com.est.erp_api.repositories.RolesRepository;
import com.est.erp_api.repositories.UserRepository;
import com.est.erp_api.repositories.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Transactional
@Configurable
public class AdminServiceDefault implements AdminService{

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private UserRepository userRepository;

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    @Override
    public Mono<Void> addRoleToUser(String userId, String roleId) {
        return userRoleRepository.addRoleToUser(userId, roleId);
    }

    @Override
    public Mono<Void> removeRoleFromUser(String userId, String roleId) {
        return userRoleRepository.removeRoleFromUser(userId, roleId);
    }

    @Override
    public Flux<Role> getAllRoles() {
        return rolesRepository.findAllRoles();
    }

    @Override
    public Flux<User> getAllUsers() {
        return userRepository.findAllUsers();
    }

    @Override
    public Flux<Role> getAllRolesByUserId(String userId) {
        return userRoleRepository.findByUserId(userId)
                .flatMap(userRole -> rolesRepository.findRoleById(userRole.getRole_id()));
    }

    public Mono<Void> updateUsername(String userId, String name) {
        return userRepository.updateUsername(userId, name);
    }

    public Mono<Void> updateEmail(String userId, String email) {
        return userRepository.updateEmail(userId, email);
    }

    public Mono<Void> updatePassword(String userId, String password) {
        return userRepository.updatePassword(userId, passwordEncoder.encode(password));
    }
}
