package com.kenzie.unit.two;

import com.amazonaws.services.lambda.model.InvokeResult;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MasteryTask1 {

    @Test
    void canPackItemTest_TASK_1() {
        String departmentName = "warehouse-test";
        String userName = "warehouse-user-one";
        String packItemsRole = "pack-items";

        InvokeResult invokeResult = LambdaUtility.invokeFunction("createdepartment","{\"departmentName\": \"" + departmentName + "\"}");
        System.out.println(LambdaUtility.resultToString(invokeResult));
        assertTrue(invokeResult.getStatusCode() == 200, "Can Create Department warehouse");
        invokeResult = LambdaUtility.invokeFunction("createrole","{\"roleName\": \"" + packItemsRole + "\"}");
        System.out.println(LambdaUtility.resultToString(invokeResult));
        assertTrue(invokeResult.getStatusCode() == 200, "Can Create Role pack-items");
        invokeResult = LambdaUtility.invokeFunction("createuser","{\"userName\": \"" + userName + "\", \"departmentName\": \"" + departmentName + "\"}");
        System.out.println(LambdaUtility.resultToString(invokeResult));
        assertTrue(invokeResult.getStatusCode() == 200, "Can Create user warehouse-user-one in warehouse");
        invokeResult = LambdaUtility.invokeFunction("assignusertorole","{\"userName\": \"" + userName + "\", \"roleName\": \"" + packItemsRole + "\"}");
        System.out.println(LambdaUtility.resultToString(invokeResult));
        assertTrue(invokeResult.getStatusCode() == 200, "Can assign pack-items role to warehouse-user-one");

        invokeResult = LambdaUtility.invokeFunction("canwarehouseuserpackitem", "{\"userName\": \"" + userName + "\"}");
        System.out.println(LambdaUtility.resultToString(invokeResult));
        String ans = new String(invokeResult.getPayload().array(), StandardCharsets.UTF_8);

        boolean result = Boolean.parseBoolean(ans);

        assertTrue(result, "warehouse-user-one has permission to pack items");
    }

}
