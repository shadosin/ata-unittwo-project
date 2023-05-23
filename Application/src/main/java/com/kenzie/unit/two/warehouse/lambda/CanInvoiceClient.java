package com.kenzie.unit.two.warehouse.lambda;

import com.kenzie.unit.two.App;
import com.kenzie.unit.two.warehouse.lambda.models.CanInvoiceClientRequest;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class CanInvoiceClient implements RequestHandler<CanInvoiceClientRequest, Boolean> {
    // Handler value: example.Handler
    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    public Boolean handleRequest(CanInvoiceClientRequest event, Context context) {
        LambdaLogger logger = context.getLogger();
        logger.log("CONTEXT: " + gson.toJson(context));
        // process event
        logger.log("ENVIRONMENT VARIABLES: " + gson.toJson(System.getenv()));
        logger.log("EVENT: " + gson.toJson(event));

        Boolean doesUserHaveRole = App.warehouseService().canInvoiceClient(event);
        return doesUserHaveRole;
    }
}
