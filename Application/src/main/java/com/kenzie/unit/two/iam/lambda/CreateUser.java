package com.kenzie.unit.two.iam.lambda;

import com.kenzie.unit.two.App;
import com.kenzie.unit.two.iam.lambda.models.CreateUserRequest;
import com.kenzie.unit.two.iam.models.User;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kenzie.ata.ExcludeFromJacocoGeneratedReport;


@ExcludeFromJacocoGeneratedReport
public class CreateUser implements RequestHandler<CreateUserRequest, User> {
    // Handler value: example.Handler
    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    public User handleRequest(CreateUserRequest event, Context context) {
        LambdaLogger logger = context.getLogger();
        logger.log("CONTEXT: " + gson.toJson(context));
        // process event
        logger.log("ENVIRONMENT VARIABLES: " + gson.toJson(System.getenv()));
        logger.log("EVENT: " + gson.toJson(event));
        logger.log("User NAME: " + event.getUserName());

        User user = App.userService().createUser(event);
        return user;
    }
}
