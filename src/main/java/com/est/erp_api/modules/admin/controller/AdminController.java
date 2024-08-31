package com.est.erp_api.modules.admin.controller;

import com.est.erp_api.models.Role;
import com.est.erp_api.models.User;
import com.est.erp_api.models.UserRole;
import com.est.erp_api.modules.admin.service.AdminService;
import com.est.erp_api.modules.admin.service.AdminServiceDefault;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminController {

    @Autowired
    private AdminServiceDefault adminService;

    @GetMapping("/check")
    public Mono<String> isAdmin() {
        return Mono.just("You are an admin");
    }

    @GetMapping("/users")
    public Flux<User> getAllUsers() {
        return adminService.getAllUsers();
    }

    @GetMapping("/roles/{userId}")
    public Flux<Role> getAllRolesByUserId(@PathVariable("userId") String userId) {
        return adminService.getAllRolesByUserId(userId);
    }

    @GetMapping("/roles/all")
    public Flux<Role> getAllRoles() {
        return adminService.getAllRoles();
    }

    @PostMapping("/roles/add")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Void> addRoleToUser(@RequestBody Map<String, String> roleMap) {
        String userId = roleMap.get("userId");
        String roleId = roleMap.get("roleId");
        return adminService.addRoleToUser(userId, roleId);
    }

    @PostMapping("/roles/remove")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Void> removeRoleFromUser(@RequestBody Map<String, String> roleMap) {
        String userId = roleMap.get("userId");
        String roleId = roleMap.get("roleId");
        return adminService.removeRoleFromUser(userId, roleId);
    }

    @PatchMapping("/user/{userId}/username")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Void> updateUsername(@PathVariable String userId, @RequestBody Map<String, String> request) {
        String name = request.get("username");
        return adminService.updateUsername(userId, name);
    }

    @PatchMapping("/user/{userId}/email")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Void> updateEmail(@PathVariable String userId, @RequestBody Map<String, String> request) {
        String email = request.get("email");
        return adminService.updateEmail(userId, email);
    }

    @PatchMapping("/user/{userId}/password")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Void> updatePassword(@PathVariable String userId, @RequestBody Map<String, String> request) {
        String password = request.get("password");
        return adminService.updatePassword(userId, password);
    }



}
