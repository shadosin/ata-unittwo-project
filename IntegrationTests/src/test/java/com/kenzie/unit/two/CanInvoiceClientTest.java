package com.kenzie.unit.two;

import com.amazonaws.services.lambda.model.*;
import com.kenzie.unit.two.iam.entities.Roles;
import com.kenzie.unit.two.iam.models.FunctionalRole;
import com.kenzie.unit.two.iam.models.Role;
import com.kenzie.unit.two.iam.models.User;
import com.kenzie.unit.two.iam.models.UserRoles;
import com.kenzie.unit.two.iam.service.RoleService;
import com.kenzie.unit.two.iam.service.UserRoleService;
import com.kenzie.unit.two.iam.service.UserService;
import com.kenzie.unit.two.warehouse.lambda.models.CanInvoiceClientRequest;
import com.kenzie.unit.two.warehouse.service.WareHouseService;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CanInvoiceClientTest {

    @Test
    void canInvoiceClient_TASK_5() {
        String departmentName = "Finance";
        String viewClientRole = "view-client";
        String createInvoiceRole = "create-invoice";
        String userName = "invoice-user";

        InvokeResult invokeResult = LambdaUtility.invokeFunction("createdepartment","{\"departmentName\": \"" + departmentName + "\"}");
        System.out.println(LambdaUtility.resultToString(invokeResult));
        assertEquals(200, (int) invokeResult.getStatusCode(), "Can Create Department");
        invokeResult = LambdaUtility.invokeFunction("createrole","{\"roleName\": \"" + viewClientRole + "\"}");
        System.out.println(LambdaUtility.resultToString(invokeResult));
        assertEquals(200, (int) invokeResult.getStatusCode(), "Can Create Role view-client");
        invokeResult = LambdaUtility.invokeFunction("createrole","{\"roleName\": \"" + createInvoiceRole + "\"}");
        System.out.println(LambdaUtility.resultToString(invokeResult));
        assertEquals(200, (int) invokeResult.getStatusCode(), "Can Create Role create-invoice");
        invokeResult = LambdaUtility.invokeFunction("createuser","{\"userName\": \"" + userName + "\", \"departmentName\": \"" + departmentName + "\"}");
        System.out.println(LambdaUtility.resultToString(invokeResult));
        assertEquals(200, (int) invokeResult.getStatusCode(), "Can Create user invoice-user in department");
        invokeResult = LambdaUtility.invokeFunction("assignusertorole","{\"userName\": \"" + userName + "\", \"roleName\": \"" + viewClientRole + "\"}");
        System.out.println(LambdaUtility.resultToString(invokeResult));
        assertEquals(200, (int) invokeResult.getStatusCode(), "Can assign view-client role to invoice-user");
        invokeResult = LambdaUtility.invokeFunction("assignusertorole","{\"userName\": \"" + userName + "\", \"roleName\": \"" + createInvoiceRole + "\"}");
        System.out.println(LambdaUtility.resultToString(invokeResult));
        assertEquals(200, (int) invokeResult.getStatusCode(), "Can assign create-invoice role to invoice-user");

        invokeResult = LambdaUtility.invokeFunction("caninvoiceclient","{\"userName\": \"" + userName + "\"}");
        System.out.println(LambdaUtility.resultToString(invokeResult));

        String ans = new String(invokeResult.getPayload().array(), StandardCharsets.UTF_8);

        boolean result = Boolean.parseBoolean(ans);

        assertTrue(result, "The invoice-user has permission to invoice a client.");
    }

    @Test
    void usesMatches_TASK_5() throws NoSuchFieldException, IllegalAccessException {
        CanInvoiceClientRequest request = mock(CanInvoiceClientRequest.class);
        when(request.getUserName()).thenReturn("claire was here");

        UserService userService = mock(UserService.class);
        User user = mock(User.class);
        when(user.getUserName()).thenReturn("claire was here");
        when(userService.getUserByUserName(any())).thenReturn(user);

        UserRoleService userRoleService = mock(UserRoleService.class);
        UserRoles userRoles = mock(UserRoles.class);
        Role role = mock(Role.class);

        when(role.getRoleName()).thenReturn(Roles.CREATE_INVOICE.getRoleName());
        when(userRoles.getRoles()).thenReturn(Arrays.asList(role));
        when(userRoleService.getUserRoles(anyString())).thenReturn(userRoles);

        WareHouseService wareHouseService = new WareHouseService(userRoleService, userService, mock(RoleService.class));

        FunctionalRole invoicingClientRoleMock = mock(FunctionalRole.class);

        Field f = wareHouseService.getClass().getDeclaredField("invoicingClientRole");
        f.setAccessible(true);
        f.set(wareHouseService, invoicingClientRoleMock);

        wareHouseService.canInvoiceClient(request);
        verify(invoicingClientRoleMock).matches(any());
    }
}
