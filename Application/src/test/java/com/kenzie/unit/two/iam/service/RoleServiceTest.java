package com.kenzie.unit.two.iam.service;

import com.kenzie.unit.two.App;
import com.kenzie.unit.two.iam.lambda.models.CreateRoleRequest;
import com.kenzie.unit.two.iam.models.Role;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RoleServiceTest {

    @Test
    void createNewRole() {
        //GIVEN
        RoleService roleService = App.roleService();

        //WHEN
        String roleName = RandomStringUtils.random(20);

        CreateRoleRequest createRoleRequest = new CreateRoleRequest();
        createRoleRequest.setRoleName(roleName);

        //THEN
        Role role = roleService.createRole(createRoleRequest);
        assertTrue(role.getId() != null);
    }

    @Test
    void throwExceptionRoleNameAlreadyExists() {
        //GIVEN
        RoleService roleService = App.roleService();

        //WHEN
        String roleName = RandomStringUtils.random(20);

        CreateRoleRequest createRoleRequest = new CreateRoleRequest();
        createRoleRequest.setRoleName(roleName);

        roleService.createRole(createRoleRequest);

        //THEN
        assertThrows(IllegalArgumentException.class, () -> roleService.createRole(createRoleRequest));
    }

    @Test
    void getRoleByName() {
        //GIVEN
        RoleService roleService = App.roleService();

        //WHEN
        String roleName = RandomStringUtils.random(20);

        CreateRoleRequest createRoleRequest = new CreateRoleRequest();
        createRoleRequest.setRoleName(roleName);

        Role role =  roleService.createRole(createRoleRequest);


        //THEN
        Role queriedRole = roleService.getRoleByRoleName(roleName);
        assertTrue(role.getId().equals(queriedRole.getId()));
    }

    @Test
    void getAllRoles() {
        //GIVEN
        RoleService roleService = App.roleService();
        int initialRoleSize= roleService.getRoles().size();
        //WHEN
        String roleName = RandomStringUtils.random(20);

        CreateRoleRequest createRoleRequest = new CreateRoleRequest();
        createRoleRequest.setRoleName(roleName);

        Role role =  roleService.createRole(createRoleRequest);

        //THEN
        int currentRoleSize = roleService.getRoles().size();
        assertTrue(currentRoleSize>initialRoleSize);
    }
}