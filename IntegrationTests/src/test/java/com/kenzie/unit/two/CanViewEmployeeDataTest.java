package com.kenzie.unit.two;

import com.amazonaws.services.lambda.model.InvokeResult;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CanViewEmployeeDataTest {

    @Test
    void canViewEmployeeData_TASK_3() {

        String departmentName = "Operations";
        String viewPaycheckRole = "view-paycheck";
        String managerName = "operations-manager";

        InvokeResult invokeResult = LambdaUtility.invokeFunction("createdepartment","{\"departmentName\": \"" + departmentName + "\"}");
        System.out.println(LambdaUtility.resultToString(invokeResult));
        assertEquals(200, (int) invokeResult.getStatusCode(), "Can Create Department");
        invokeResult = LambdaUtility.invokeFunction("createrole","{\"roleName\": \"" + viewPaycheckRole + "\"}");
        System.out.println(LambdaUtility.resultToString(invokeResult));
        assertEquals(200, (int) invokeResult.getStatusCode(), "Can Create Role view-paycheck");
        invokeResult = LambdaUtility.invokeFunction("createuser","{\"userName\": \"" + managerName + "\", \"departmentName\": \"" + departmentName + "\"}");
        System.out.println(LambdaUtility.resultToString(invokeResult));
        assertEquals(200, (int) invokeResult.getStatusCode(), "Can Create user in department");
        invokeResult = LambdaUtility.invokeFunction("assignusertorole","{\"userName\": \"" + managerName + "\", \"roleName\": \"" + viewPaycheckRole + "\"}");
        System.out.println(LambdaUtility.resultToString(invokeResult));
        assertEquals(200, (int) invokeResult.getStatusCode(), "Can assign view-paycheck role to manager");


        String functionName = LambdaUtility.getFunctionName("viewemployeepaycheck");

        if (functionName == null) {
            throw new IllegalArgumentException("Function not yet created");
        }

        invokeResult = LambdaUtility.invokeFunction("viewemployeepaycheck","{" +
                "\"requesterUserName\": \"" + managerName + "\"," +
                "\"employeeUserName\": \"elenamoscow\"" +
                "}");
        System.out.println(LambdaUtility.resultToString(invokeResult));
        String actualResponse = new String(invokeResult.getPayload().array(), StandardCharsets.UTF_8);

        String expectedResponse =
                "(.)*" +
                        "\"userName\":\"elenamoscow\"," +
                        "\"department\"" +
                        "(.*)" +
                        "\"name\":\"Operations\"}," +
                        "\"payCheck\":\"4000\"" +
                        "}";

        System.out.println(actualResponse);
        System.out.println(expectedResponse);

        assert(actualResponse.matches(expectedResponse));
    }
}
