
package com.kenzie.unit.two.iam.lambda;

import com.kenzie.unit.two.App;
import com.kenzie.unit.two.iam.lambda.models.CreateDepartmentRequest;
import com.kenzie.unit.two.iam.models.Department;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class CreateDepartment implements RequestHandler<CreateDepartmentRequest, Department> {

    // Handler value: example.Handler
    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    public Department handleRequest(CreateDepartmentRequest event, Context context) {

        LambdaLogger logger = context.getLogger();
        logger.log("CONTEXT: " + gson.toJson(context));
        // process event
        logger.log("ENVIRONMENT VARIABLES: " + gson.toJson(System.getenv()));
        logger.log("EVENT: " + gson.toJson(event));
        logger.log("Department NAME: " + event.getDepartmentName());

        Department newDepartment = App.departmentService().createDepartment(event);
        return newDepartment;
    }
}