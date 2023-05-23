package com.kenzie.unit.two.iam.lambda;

import com.kenzie.unit.two.App;
import com.kenzie.unit.two.iam.lambda.models.AssignUserToRoleRequest;
import com.kenzie.unit.two.iam.models.UserRoles;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class AssignUserToRole implements RequestHandler<AssignUserToRoleRequest, UserRoles> {
    // Handler value: example.Handler
    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    public UserRoles handleRequest(AssignUserToRoleRequest event, Context context) {
        LambdaLogger logger = context.getLogger();
        logger.log("CONTEXT: " + gson.toJson(context));
        // process event
        logger.log("ENVIRONMENT VARIABLES: " + gson.toJson(System.getenv()));
        logger.log("EVENT: " + gson.toJson(event));

        UserRoles userRoles = App.userRoleService().createUserRole(event);
        return userRoles;
    }
}
