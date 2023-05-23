package com.kenzie.unit.two.employee.service;

import com.kenzie.unit.two.employee.lambda.models.ViewEmployeePayCheckRequest;
import com.kenzie.unit.two.employee.service.models.Employee;
import com.kenzie.unit.two.iam.models.Department;
import com.kenzie.unit.two.iam.models.Role;
import com.kenzie.unit.two.iam.models.User;
import com.kenzie.unit.two.iam.service.RoleService;
import com.kenzie.unit.two.iam.service.UserRoleService;
import com.kenzie.unit.two.iam.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


public class EmployeeServiceTest {

    EmployeeService service;
    RoleService roleService;
    UserService userService;
    UserRoleService userRoleService;
    Logger logger;

    @BeforeEach
    void beforeEach() {
        MockedStatic<LogManager> logManagerMock = mockStatic(LogManager.class);
        this.logger = mock(Logger.class);
        logManagerMock.when(LogManager::getLogger).thenReturn(logger);

        this.userRoleService = mock(UserRoleService.class);
        this.userService = mock(UserService.class);
        this.roleService = mock(RoleService.class);
        this.service = new EmployeeService(userRoleService, userService, roleService);
    }

    @Test
    void missingUserRoleThrowsException_TASK_2() {

        when(this.userService.getUserByUserName(any())).thenReturn(null);
        when(this.roleService.getRoleByRoleName(any())).thenReturn(null);

        ViewEmployeePayCheckRequest request = new ViewEmployeePayCheckRequest();
        request.setEmployeeUserName("does not exist");
        request.setRequesterUserName("not a person");

        Exception exception = assertThrows(UserOrRoleNotFoundException.class,
                () -> this.service.getEmployeePayCheck(request));
    }


    @Test
    void employeePaycheckLogsInfo_TASK_2() {

        User validUser = new User(UUID.randomUUID(), "margaret", new Department(UUID.randomUUID(), "marketing"));
        Role validRole = new Role(UUID.randomUUID(), "view-paycheck");

        when(this.userService.getUserByUserName(any())).thenReturn(validUser);
        when(this.roleService.getRoleByRoleName(any())).thenReturn(validRole);
        when(this.userRoleService.doesUserHaveRole(any(), any())).thenReturn(true);

        EmployeeService spy = Mockito.spy(this.service);
        doReturn(true).when(spy).theCorrectUser(any(),any());
        doReturn(true).when(spy).inTheSameDepartment(any(),any());

        ViewEmployeePayCheckRequest request = new ViewEmployeePayCheckRequest();
        request.setEmployeeUserName("Nathan");
        request.setRequesterUserName("Mike");

        spy.getEmployeePayCheck(request);

        verify(this.logger, atLeast(1)).info(matches("Audit: User .* viewed employee .* paycheck information"));
    }

    @Test
    void getEmployeePaycheckTest_TASK_2() {
        Department department = new Department(UUID.randomUUID(), "Marketing");
        User validUser = new User(UUID.randomUUID(), "margaretparis", department);
        Role validRole = new Role(UUID.randomUUID(), "view-paycheck");

        when(this.userService.getUserByUserName(any())).thenReturn(validUser);
        when(this.roleService.getRoleByRoleName(any())).thenReturn(validRole);
        when(this.userRoleService.doesUserHaveRole(any(), any())).thenReturn(true);

        EmployeeService spy = Mockito.spy(this.service);

        ViewEmployeePayCheckRequest request = new ViewEmployeePayCheckRequest();
        request.setEmployeeUserName("margaretparis");
        request.setRequesterUserName("margaretparis");

        Employee employee = spy.getEmployeePayCheck(request);

        assertEquals(employee.getUserName(), "margaretparis");
        assertEquals(employee.getDepartment(), department);
    }
}
