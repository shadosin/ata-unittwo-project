package com.kenzie.unit.two;

import com.amazonaws.services.lambda.model.InvokeResult;
import org.skyscreamer.jsonassert.JSONAssert;
import org.junit.jupiter.api.Test;
import org.json.JSONException;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MasteryTask2 {

    @Test
    void canViewEmployeeData_TASK_2() throws JSONException {

        String departmentName = "Operations";
        String viewPaycheckRole = "view-paycheck";
        String managerName = "operations-manager";

        InvokeResult invokeResult = LambdaUtility.invokeFunction("createdepartment","{\"departmentName\": \"" + departmentName + "\"}");
        System.out.println(LambdaUtility.resultToString(invokeResult));
        assertTrue(invokeResult.getStatusCode() == 200, "Can Create Department");
        invokeResult = LambdaUtility.invokeFunction("createrole","{\"roleName\": \"" + viewPaycheckRole + "\"}");
        System.out.println(LambdaUtility.resultToString(invokeResult));
        assertTrue(invokeResult.getStatusCode() == 200, "Can Create Role view-paycheck");
        invokeResult = LambdaUtility.invokeFunction("createuser","{\"userName\": \"" + managerName + "\", \"departmentName\": \"" + departmentName + "\"}");
        System.out.println(LambdaUtility.resultToString(invokeResult));
        assertTrue(invokeResult.getStatusCode() == 200, "Can Create user in department");
        invokeResult = LambdaUtility.invokeFunction("assignusertorole","{\"userName\": \"" + managerName + "\", \"roleName\": \"" + viewPaycheckRole + "\"}");
        System.out.println(LambdaUtility.resultToString(invokeResult));
        assertTrue(invokeResult.getStatusCode() == 200, "Can assign view-paycheck role to manager");


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

        String expectedResponse = "{" +
                "\"id\":\"cabaf5ae-f9f8-439d-8635-e60eb8bf1a3e\"," +
                "\"userName\":\"elenamoscow\"," +
                "\"department\":{" +
                    "\"id\":\"05ba35d9-3314-4ce5-b3b6-1dcbc00aba6a\"," +
                    "\"name\":\"Operations\"}," +
                    "\"payCheck\":\"4000\"" +
                "}";

        System.out.println(actualResponse);
        System.out.println(expectedResponse);

        JSONAssert.assertEquals(expectedResponse, actualResponse, false);
    }
}
