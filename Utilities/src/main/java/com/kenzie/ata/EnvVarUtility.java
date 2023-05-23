package com.kenzie.ata;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EnvVarUtility {

    /*
     * Returns input string with environment variable references expanded, e.g. $SOME_VAR or ${SOME_VAR}
     *
     * Gratefully adapted from Tim Lewis's answer on
     * https://stackoverflow.com/questions/2263929/regarding-application-properties-file-and-environment-variable
     */
    public static String resolveEnvVars(Map<String,String> envVars, String input) {
        if (null == input) {
            return null;
        }
        // match ${ENV_VAR_NAME} or $ENV_VAR_NAME
        Pattern p = Pattern.compile("\\$\\{(\\w+)\\}|\\$(\\w+)");
        Matcher m = p.matcher(input); // get a matcher object
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            String envVarName = null == m.group(1) ? m.group(2) : m.group(1);
            String envVarValue = envVars.get(envVarName);
            if (envVarValue == null) {
                throw new RuntimeException("Environment variable " + envVarName +
                        " expected but not found or is null. Please set variable to a non-null value.");
            }
            //how the shell works natively
            //m.appendReplacement(sb, null == envVarValue ? "" : envVarValue);
            m.appendReplacement(sb,envVarValue);
        }
        m.appendTail(sb);
        return sb.toString();
    }

    /*
     * Reads a line expecting an environment variable and resolves it if found.
     * If no environment variable is found, the line is skipped
     */
    public static Map<String,String> computeEnvVar(Map<String,String> envVars, String line) {
        String[] parts = line.trim().split("\\s+");

        Map<String,String> newVars = new HashMap<>(envVars);

        if (parts.length > 1) {
            String[] var = parts[1].split("=");

            if (var.length > 1) {
                String varName = var[0];
                String initVarValue = var[1];

                String varValue = resolveEnvVars(envVars,initVarValue);
                newVars.put(varName,varValue);
            }
        }

        return newVars;
    }

    /*
     * Finds the file from the relative path provided (relative to the project root)
     * and returns a copy of the map of environment variables with the ones found in the file added.
     *
     * Throws an IllegalArgumentException if the given file is not found.
     */
    public static Map<String,String> getEnvVariablesFromFile(Map<String, String> envVars, List<String> pathFromProjectRoot, String filename) {
        Path rootDir = Paths.get(".").normalize().toAbsolutePath().getParent();

        Path path = rootDir;
        for (String pathPart : pathFromProjectRoot) {
            path = path.resolve(pathPart);
        }
        path = path.normalize().resolve(filename);


        BufferedReader reader;

        Map<String,String> newEnvVars = new HashMap<>(envVars);

        try {
            reader = new BufferedReader(new FileReader(path.toFile()));
            String line = reader.readLine();

            while (line != null) {

                if (line.startsWith("export ")) {
                    newEnvVars = computeEnvVar(newEnvVars, line);
                }

                line = reader.readLine();
            }

            reader.close();
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("File " + path.toString() + " not found.", e);
        } catch (IOException e) {
            e.printStackTrace();
        }


        return newEnvVars;
    }

    /*
     * Parses the setupEnvironment.sh file for all environment variables set within. Resolves using
     * the environment variables set in System.getenv() and any variables in the file in found order.
     * Since the file is at the root of the project, the path from the root is empty
     */
    public static String getEnvVarFromEnvFile(String envVarName) {
        Map<String,String> envVars = System.getenv();
        List<String> pathToFile = new ArrayList<>();
        String filename = "setupEnvironment.sh";

        envVars = getEnvVariablesFromFile(envVars,pathToFile,filename);

        return envVars.get(envVarName);
    }
}
