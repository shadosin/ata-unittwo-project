package com.kenzie.unit.two.employee.service;

import com.kenzie.unit.two.employee.lambda.models.ViewEmployeePayCheckRequest;
import com.kenzie.unit.two.employee.service.models.Employee;
import com.kenzie.unit.two.iam.entities.Roles;
import com.kenzie.unit.two.iam.models.Department;
import com.kenzie.unit.two.iam.models.Role;
import com.kenzie.unit.two.iam.models.User;
import com.kenzie.unit.two.iam.service.RoleService;
import com.kenzie.unit.two.iam.service.UserRoleService;
import com.kenzie.unit.two.iam.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class EmployeeServiceTest {
    @InjectMocks
    private EmployeeService service;
    @Mock
    private RoleService roleService;
    @Mock
    private UserService userService;
    @Mock
    private UserRoleService userRoleService;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }
    @AfterEach
    void afterEach(){
        System.out.println(";)");
    }

    @Test
    void getEmployeePayCheck_UserOrRoleNotFoundException_WhenUserDoesNotHaveRole() {
        User user = new User(UUID.randomUUID(), "margaret", new Department(UUID.randomUUID(), "marketing"));
        Role role = new Role(UUID.randomUUID(), "view-paycheck");

        when(userService.getUserByUserName(any())).thenReturn(user);
        when(roleService.getRoleByRoleName(any())).thenReturn(role);
        when(userRoleService.doesUserHaveRole(any(), any())).thenReturn(false);

        ViewEmployeePayCheckRequest request = new ViewEmployeePayCheckRequest();
        request.setEmployeeUserName("Nathan");
        request.setRequesterUserName("Mike");

        assertThrows(UserOrRoleNotFoundException.class, () -> service.getEmployeePayCheck(request));
    }


    @Test
    void getEmployeePayCheck_ReturnsEmployee_WhenRequestIsValid() {
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

    @Test
    void getEmployeePayCheck_ThrowsEmployeeNotFoundException_WhenEmployeeDoesNotExist() {
        User user = new User(UUID.randomUUID(), "margaret", new Department(UUID.randomUUID(), "marketing"));
        Role role = new Role(UUID.randomUUID(), "view-paycheck");

        when(userService.getUserByUserName(any())).thenReturn(user);
        when(roleService.getRoleByRoleName(any())).thenReturn(role);
        when(userRoleService.doesUserHaveRole(any(), any())).thenReturn(true);

        ViewEmployeePayCheckRequest request = new ViewEmployeePayCheckRequest();
        request.setEmployeeUserName("nonexistent");
        request.setRequesterUserName(user.getUserName());

        assertThrows(EmployeeNotFoundException.class, () -> service.getEmployeePayCheck(request));
    }

    @Test
    void theCorrectUser_ReturnsTrue_WhenUsernamesAreEqual() {
        String requestingUsername = "john_doe";
        String userName = "john_doe";

        assertTrue(service.theCorrectUser(requestingUsername, userName));
    }

    @Test
    void theCorrectUser_ReturnsFalse_WhenUsernamesAreNotEqual() {
        String requestingUsername = "john_doe";
        String userName = "jane_doe";

        assertFalse(service.theCorrectUser(requestingUsername, userName));
    }

    @Test
    void inTheSameDepartment_ReturnsTrue_WhenDepartmentsAreEqual() {
        String requestingDepartment = "marketing";
        String userDepartment = "marketing";

        assertTrue(service.inTheSameDepartment(requestingDepartment, userDepartment));
    }

    @Test
    void inTheSameDepartment_ReturnsFalse_WhenDepartmentsAreNotEqual() {
        String requestingDepartment = "marketing";
        String userDepartment = "sales";

        assertFalse(service.inTheSameDepartment(requestingDepartment, userDepartment));
    }


}