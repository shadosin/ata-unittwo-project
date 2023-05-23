package com.kenzie.unit.two.iam.service;

import com.kenzie.unit.two.App;
import com.kenzie.unit.two.iam.lambda.models.AssignUserToRoleRequest;
import com.kenzie.unit.two.iam.lambda.models.CreateDepartmentRequest;
import com.kenzie.unit.two.iam.lambda.models.CreateRoleRequest;
import com.kenzie.unit.two.iam.lambda.models.CreateUserRequest;
import com.kenzie.unit.two.iam.models.Role;
import com.kenzie.unit.two.iam.models.User;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserRoleServiceTest {

    @Test
    void user_has_role() {
        //GIVEN
        UserRoleService userRoleService = App.userRoleService();
        DepartmentService departmentService = App.departmentService();
        UserService userService = App.userService();
        RoleService roleService = App.roleService();

        //WHEN
        String userName = RandomStringUtils.random(20);
        String departmentName = RandomStringUtils.random(20);
        CreateDepartmentRequest createDepartmentRequest = new CreateDepartmentRequest();
        createDepartmentRequest.setDepartmentName(departmentName);

        departmentService.createDepartment(createDepartmentRequest);

        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setUserName(userName);
        createUserRequest.setDepartmentName(departmentName);
        User user = userService.createUser(createUserRequest);


        String roleName = RandomStringUtils.random(20);
        CreateRoleRequest createRoleRequest = new CreateRoleRequest();
        createRoleRequest.setRoleName(roleName);
        Role role = roleService.createRole(createRoleRequest);

        AssignUserToRoleRequest assignUserToRoleRequest = new AssignUserToRoleRequest();
        assignUserToRoleRequest.setUserName(userName);
        assignUserToRoleRequest.setRoleName(roleName);
        userRoleService.createUserRole(assignUserToRoleRequest);

        //THEN
        boolean userHasRole = userRoleService.doesUserHaveRole(user,role);
        assertTrue(userHasRole);
    }

    @Test
    void user_does_not_have_role() {
        //GIVEN
        UserRoleService userRoleService = App.userRoleService();
        DepartmentService departmentService = App.departmentService();
        UserService userService = App.userService();
        RoleService roleService = App.roleService();

        //WHEN
        String userName = RandomStringUtils.random(20);
        String departmentName = RandomStringUtils.random(20);
        CreateDepartmentRequest createDepartmentRequest = new CreateDepartmentRequest();
        createDepartmentRequest.setDepartmentName(departmentName);

        departmentService.createDepartment(createDepartmentRequest);

        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setUserName(userName);
        createUserRequest.setDepartmentName(departmentName);
        User user = userService.createUser(createUserRequest);


        String roleName = RandomStringUtils.random(20);
        CreateRoleRequest createRoleRequest = new CreateRoleRequest();
        createRoleRequest.setRoleName(roleName);
        Role role = roleService.createRole(createRoleRequest);

        //THEN
        boolean userHasRole = userRoleService.doesUserHaveRole(user,role);
        assertFalse(userHasRole);
    }
}