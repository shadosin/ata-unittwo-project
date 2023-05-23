package com.kenzie.unit.two.iam.lambda;

import com.kenzie.unit.two.App;
import com.kenzie.unit.two.iam.lambda.models.CreateRoleRequest;
import com.kenzie.unit.two.iam.models.Role;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kenzie.ata.ExcludeFromJacocoGeneratedReport;


@ExcludeFromJacocoGeneratedReport
public class CreateRole implements RequestHandler<CreateRoleRequest, Role> {
    // Handler value: example.Handler
    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    public Role handleRequest(CreateRoleRequest event, Context context) {
        LambdaLogger logger = context.getLogger();
        logger.log("CONTEXT: " + gson.toJson(context));
        // process event
        logger.log("ENVIRONMENT VARIABLES: " + gson.toJson(System.getenv()));
        logger.log("EVENT: " + gson.toJson(event));
        logger.log("Role NAME: " + event.getRoleName());

        Role role = App.roleService().createRole(event);
        return role;
    }
}
