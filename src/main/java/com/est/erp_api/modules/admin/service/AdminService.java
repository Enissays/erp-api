package com.est.erp_api.modules.admin.service;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public interface AdminService {
    void addRoleToUser(String userId, String roleId);
    void removeRoleFromUser(String userId, String roleId);
    void getAllRoles();
    void getAllUsers();
    void getAllRolesByUserId(String userId);
}
