package com.kenzie.unit.two.iam.service;

import com.kenzie.unit.two.iam.lambda.models.CreateRoleRequest;
import com.kenzie.unit.two.iam.models.Role;
import com.kenzie.unit.two.iam.storage.Storage;

import java.util.List;
import java.util.UUID;

public class RoleService {
    private final Storage storage;

    public RoleService(Storage storage) {
        this.storage = storage;
    }

    public Role createRole(CreateRoleRequest request) {
        if (storage.getRoleByRoleName(request.getRoleName()) != null) {
            throw new IllegalArgumentException("Role already exists");
        }
        Role role = new Role(UUID.randomUUID(), request.getRoleName());
        storage.storeRole(role);
        return role;
    }

    public Role getRoleByRoleName(String roleName) {
        return storage.getRoleByRoleName(roleName);
    }

    public Role getRoleByRoleId(String roleId) {
        return storage.getRole(roleId);
    }

    public List<Role> getRoles() {
        return storage.getRoles();
    }
}
