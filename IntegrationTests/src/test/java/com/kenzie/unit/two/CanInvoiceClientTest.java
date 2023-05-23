package com.kenzie.unit.two;

import com.amazonaws.services.lambda.model.*;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CanInvoiceClientTest {

    @Test
    void canInvoiceClient() {
        String departmentName = "Finance";
        String viewClientRole = "view-client";
        String createInvoiceRole = "create-invoice";
        String userName = "invoice-user";

        InvokeResult invokeResult = LambdaUtility.invokeFunction("createdepartment","{\"departmentName\": \"" + departmentName + "\"}");
        System.out.println(LambdaUtility.resultToString(invokeResult));
        assertTrue(invokeResult.getStatusCode() == 200, "Can Create Department");
        invokeResult = LambdaUtility.invokeFunction("createrole","{\"roleName\": \"" + viewClientRole + "\"}");
        System.out.println(LambdaUtility.resultToString(invokeResult));
        assertTrue(invokeResult.getStatusCode() == 200, "Can Create Role view-client");
        invokeResult = LambdaUtility.invokeFunction("createrole","{\"roleName\": \"" + createInvoiceRole + "\"}");
        System.out.println(LambdaUtility.resultToString(invokeResult));
        assertTrue(invokeResult.getStatusCode() == 200, "Can Create Role create-invoice");
        invokeResult = LambdaUtility.invokeFunction("createuser","{\"userName\": \"" + userName + "\", \"departmentName\": \"" + departmentName + "\"}");
        System.out.println(LambdaUtility.resultToString(invokeResult));
        assertTrue(invokeResult.getStatusCode() == 200, "Can Create user invoice-user in department");
        invokeResult = LambdaUtility.invokeFunction("assignusertorole","{\"userName\": \"" + userName + "\", \"roleName\": \"" + viewClientRole + "\"}");
        System.out.println(LambdaUtility.resultToString(invokeResult));
        assertTrue(invokeResult.getStatusCode() == 200, "Can assign view-client role to invoice-user");
        invokeResult = LambdaUtility.invokeFunction("assignusertorole","{\"userName\": \"" + userName + "\", \"roleName\": \"" + createInvoiceRole + "\"}");
        System.out.println(LambdaUtility.resultToString(invokeResult));
        assertTrue(invokeResult.getStatusCode() == 200, "Can assign create-invoice role to invoice-user");

        invokeResult = LambdaUtility.invokeFunction("caninvoiceclient","{\"userName\": \"" + userName + "\"}");
        System.out.println(LambdaUtility.resultToString(invokeResult));

        String ans = new String(invokeResult.getPayload().array(), StandardCharsets.UTF_8);

        boolean result = Boolean.parseBoolean(ans);

        assertTrue(result, "The invoice-user has permission to invoice a client.");
    }
}
