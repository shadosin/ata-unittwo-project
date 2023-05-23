package com.kenzie.unit.two.employee.lambda;

import com.kenzie.unit.two.App;
import com.kenzie.unit.two.employee.lambda.models.ViewEmployeePayCheckRequest;
import com.kenzie.unit.two.employee.service.models.Employee;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ViewEmployeePayCheck implements RequestHandler<ViewEmployeePayCheckRequest, Employee> {
    // Handler value: example.Handler
    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    public Employee handleRequest(ViewEmployeePayCheckRequest event, Context context) {
        LambdaLogger logger = context.getLogger();
        logger.log("CONTEXT: " + gson.toJson(context));
        // process event
        logger.log("ENVIRONMENT VARIABLES: " + gson.toJson(System.getenv()));
        logger.log("EVENT: " + gson.toJson(event));

        Employee employee = App.employeeService().getEmployeePayCheck(event);
        return employee;
    }
}
