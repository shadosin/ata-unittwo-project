package com.kenzie.unit.two.iam.service;

import com.kenzie.unit.two.App;
import com.kenzie.unit.two.iam.lambda.models.CreateDepartmentRequest;
import com.kenzie.unit.two.iam.lambda.models.CreateUserRequest;
import com.kenzie.unit.two.iam.models.User;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserServiceTest {

    @Test
    void createUser() {
        //GIVEN
        UserService userService = App.userService();
        DepartmentService departmentService = App.departmentService();

        //WHEN
        String userName = RandomStringUtils.random(20);
        String departmentName = RandomStringUtils.random(20);
        CreateDepartmentRequest createDepartmentRequest = new CreateDepartmentRequest();
        createDepartmentRequest.setDepartmentName(departmentName);

        departmentService.createDepartment(createDepartmentRequest);

        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setDepartmentName(departmentName);
        createUserRequest.setUserName(userName);

        //THEN

        User user = userService.createUser(createUserRequest);
        assertTrue(user.getId() != null);
    }

    @Test
    void throwExceptionDepartmentNameDoesNotExists() {
        //GIVEN
        UserService userService = App.userService();

        //WHEN
        String userName = RandomStringUtils.random(20);
        String departmentName = RandomStringUtils.random(20);

        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setDepartmentName(departmentName);
        createUserRequest.setUserName(userName);

        //THEN
        assertThrows(IllegalArgumentException.class, () -> userService.createUser(createUserRequest));
    }

    @Test
    void throwExceptionUserExists() {
        //GIVEN
        UserService userService = App.userService();
        DepartmentService departmentService = App.departmentService();

        //WHEN
        String userName = RandomStringUtils.random(20);
        String departmentName = RandomStringUtils.random(20);
        CreateDepartmentRequest createDepartmentRequest = new CreateDepartmentRequest();
        createDepartmentRequest.setDepartmentName(departmentName);
        departmentService.createDepartment(createDepartmentRequest);

        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setDepartmentName(departmentName);
        createUserRequest.setUserName(userName);
        userService.createUser(createUserRequest);

        //THEN
        assertThrows(IllegalArgumentException.class, () -> userService.createUser(createUserRequest));
    }

    @Test
    void getUserByName() {
        //GIVEN
        UserService userService = App.userService();
        DepartmentService departmentService = App.departmentService();

        //WHEN
        String userName = RandomStringUtils.random(20);
        String departmentName = RandomStringUtils.random(20);
        CreateDepartmentRequest createDepartmentRequest = new CreateDepartmentRequest();
        createDepartmentRequest.setDepartmentName(departmentName);

        departmentService.createDepartment(createDepartmentRequest);

        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setDepartmentName(departmentName);
        createUserRequest.setUserName(userName);
        User user = userService.createUser(createUserRequest);

        //THEN
        User queriedUser = userService.getUserByUserName(userName);
        assertTrue(user.getId().equals(queriedUser.getId()));
    }
}