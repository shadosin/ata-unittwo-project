package com.kenzie.unit.two;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.lambda.AWSLambda;
import com.amazonaws.services.lambda.AWSLambdaClientBuilder;
import com.amazonaws.services.lambda.model.*;

import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.List;

public class LambdaUtility {
    public static String resultToString(InvokeResult result) {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (result.getStatusCode() != null)
            sb.append("StatusCode: ").append(result.getStatusCode()).append(",");
        if (result.getFunctionError() != null)
            sb.append("FunctionError: ").append(result.getFunctionError()).append(",");
        if (result.getLogResult() != null)
            sb.append("LogResult: ").append(result.getLogResult()).append(",");
        if (result.getPayload() != null)
            sb.append("Payload: ").append(new String(result.getPayload().array(), StandardCharsets.UTF_8)).append(",");
        if (result.getExecutedVersion() != null)
            sb.append("ExecutedVersion: ").append(result.getExecutedVersion());
        sb.append("}");
        return sb.toString();
    }

    public static InvokeResult invokeFunction(String rawFunctionName, String payload) {
        String functionName = LambdaUtility.getFunctionName(rawFunctionName);

        if (functionName == null) {
            throw new IllegalArgumentException("Function not yet created");
        }

        InvokeRequest invokeRequest = new InvokeRequest()
                .withFunctionName(functionName)
                .withPayload(payload);
        InvokeResult invokeResult = null;


        AWSLambda awsLambda = AWSLambdaClientBuilder.standard()
                .withCredentials(DefaultAWSCredentialsProviderChain.getInstance())
                .build();

        invokeResult = awsLambda.invoke(invokeRequest);
        return invokeResult;
    }

    public static String getFunctionName(String functionName) {
        ListFunctionsResult functionResult = null;

        String region = System.getenv("AWS_REGION");
        if (region == null) {
            region = "us-east-1";
        }

        String stackName = System.getenv("STACK_NAME");

        try {
            AWSLambda awsLambda = AWSLambdaClientBuilder.standard()
                    .withCredentials(DefaultAWSCredentialsProviderChain.getInstance())
                    .withRegion(region).build();

            functionResult = awsLambda.listFunctions();

            List<FunctionConfiguration> list = functionResult.getFunctions();

            for (Iterator iter = list.iterator(); iter.hasNext(); ) {
                FunctionConfiguration config = (FunctionConfiguration) iter.next();
                if (config.getFunctionName().contains(functionName) && (stackName == null || config.getFunctionName().startsWith(stackName.substring(0, 12)))) {
                    return config.getFunctionName();
                }
            }

        } catch (ServiceException e) {
            System.out.println(e);
        }
        return null;
    }
}
