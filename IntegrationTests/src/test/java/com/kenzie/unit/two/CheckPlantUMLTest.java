package com.kenzie.unit.two;


import com.kenzie.unit.two.ATATestHelpers.ATAFileReader;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.kenzie.test.infrastructure.assertions.PlantUmlClassDiagramAssertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class CheckPlantUMLTest {

    private static final String PUML_FILE = "iam_project_classes.puml";
    private static final String PLANTUML_PACKAGE = "com.kenzie.executingtests.plantuml";
    private static final String UML_DIAGRAM_PATH = "../";

    @Test
    void does_iam_project_puml_file_exist() {
        // GIVEN - path to directory that .puml file should exist in
        List<String> filesWithExpectedName = fileExistingInPathByName(UML_DIAGRAM_PATH, PUML_FILE);

        boolean filesWithNameInDirectory = false;

        if(filesWithExpectedName != null && filesWithExpectedName.size() > 0)  {
            filesWithNameInDirectory = true;
        }
        // THEN - file exists with expected name
        assertTrue(filesWithNameInDirectory, PUML_FILE + " not found in expected location.");
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "Storage", "Employee", "Role", "User", "Department", "UserRoles","DepartmentService", "WareHouseService", "RoleService", "UserService", "UserRoleService", "EmployeeService"})
    void plantuml_createdClassDiagram_includesExpectedClasses(String expectedClass) {
        // GIVEN - directory where path should exist
        List<String> filesWithExpectedName = fileExistingInPathByName(UML_DIAGRAM_PATH, PUML_FILE);

        int countOfFilesWithExpectedName = 0;

        if(filesWithExpectedName != null) {
            countOfFilesWithExpectedName = filesWithExpectedName.size();
        }

        if(countOfFilesWithExpectedName != 1) {
            fail(String.format("%s file should exist in this project.", PUML_FILE));
        }
        else {
            // WHEN
            String content = getFileContentFromResources(filesWithExpectedName.get(0));

            // THEN - contains each class
            assertClassDiagramContainsClass(content, expectedClass);

        }
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "Roles", "Departments"})
    void plantuml_createdClassDiagram_includesExpectedEnums(String expectedClass) {
        // GIVEN - directory where path should exist
        List<String> filesWithExpectedName = fileExistingInPathByName(UML_DIAGRAM_PATH, PUML_FILE);

        int countOfFilesWithExpectedName = 0;

        if(filesWithExpectedName != null) {
            countOfFilesWithExpectedName = filesWithExpectedName.size();
        }

        if(countOfFilesWithExpectedName != 1) {
            fail(String.format("%s file should exist in this project.", PUML_FILE));
        }
        else {
            // WHEN
            String content = getFileContentFromResources(filesWithExpectedName.get(0));

            // THEN - contains each class
            assertClassDiagramContainsEnum(content, expectedClass);

        }
    }

    @Test
    void plantuml_createdClassDiagram_includesUserCompositionRelationship() {
        // GIVEN - directory where path should exist
        List<String> filesWithExpectedName = fileExistingInPathByName(UML_DIAGRAM_PATH, PUML_FILE);

        int countOfFilesWithExpectedName = 0;

        if(filesWithExpectedName != null) {
            countOfFilesWithExpectedName = filesWithExpectedName.size();
        }

        if(countOfFilesWithExpectedName != 1) {
            fail(String.format("%s file should exist in this project.", PUML_FILE));
        }
        else {
            // WHEN
            String content = getFileContentFromResources(filesWithExpectedName.get(0));

            // THEN - diagram includes these has-a relationships
            assertClassDiagramIncludesContainsRelationship(content, "User", "Department");
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "DepartmentService", "RoleService", "UserService", "UserRoleService"})
    void plantuml_createdClassDiagram_includesStorageCompositionRelationship(String expectedClass) {
        // GIVEN - directory where path should exist
        List<String> filesWithExpectedName = fileExistingInPathByName(UML_DIAGRAM_PATH, PUML_FILE);

        int countOfFilesWithExpectedName = 0;

        if(filesWithExpectedName != null) {
            countOfFilesWithExpectedName = filesWithExpectedName.size();
        }

        if(countOfFilesWithExpectedName != 1) {
            fail(String.format("%s file should exist in this project.", PUML_FILE));
        }
        else {
            // WHEN
            String content = getFileContentFromResources(filesWithExpectedName.get(0));

            // THEN - contains each class
            assertClassDiagramIncludesContainsRelationship(content, expectedClass, "Storage");

        }
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "id", "userName", "department"})
    void plantuml_createdClassDiagram_includesUserClassProperties(String property) {
        // GIVEN - directory where path should exist
        List<String> filesWithExpectedName = fileExistingInPathByName(UML_DIAGRAM_PATH, PUML_FILE);

        int countOfFilesWithExpectedName = 0;

        if(filesWithExpectedName != null) {
            countOfFilesWithExpectedName = filesWithExpectedName.size();
        }

        if(countOfFilesWithExpectedName != 1) {
            fail(String.format("%s file should exist in this project.", PUML_FILE));
        }
        else {
            // WHEN
            String content = getFileContentFromResources(filesWithExpectedName.get(0));

            // THEN - contains each class
            assertClassDiagramTypeContainsMember (content, "User",property);
        }
    }


    @ParameterizedTest
    @ValueSource(strings = {
            "id", "userName", "payCheck", "department"})
    void plantuml_createdClassDiagram_includesEmployeeClassProperties(String property) {
        // GIVEN - directory where path should exist
        List<String> filesWithExpectedName = fileExistingInPathByName(UML_DIAGRAM_PATH, PUML_FILE);

        int countOfFilesWithExpectedName = 0;

        if(filesWithExpectedName != null) {
            countOfFilesWithExpectedName = filesWithExpectedName.size();
        }

        if(countOfFilesWithExpectedName != 1) {
            fail(String.format("%s file should exist in this project.", PUML_FILE));
        }
        else {
            // WHEN
            String content = getFileContentFromResources(filesWithExpectedName.get(0));

            // THEN - contains each class
            assertClassDiagramTypeContainsMember (content, "Employee",property);
        }
    }


    private static String getFileContentFromResources(String filename) {
        StringBuilder contentBuilder = new StringBuilder();
        try {
            ATAFileReader fileReader = new ATAFileReader(filename);
            fileReader.readLines().forEach(s -> contentBuilder.append(s).append("\n"));
        } catch (Exception e) {
            throw new IllegalArgumentException(String.format("Unable to find file: %s.", filename), e);
        }

        return contentBuilder.toString();
    }

    private static List<String> filesExistingInPathByExtension(String pathToSearch, String fileExtension) {

        Path path = Paths.get(pathToSearch);

        if (!Files.isDirectory(path)) {
            throw new IllegalArgumentException("Path must be a directory!");
        }

        List<String> result = null;

        try (Stream<Path> walk = Files.walk(path)) {
            result = walk
                    .filter(p -> !Files.isDirectory(p))
                    .map(p -> p.toString().toLowerCase())
                    .filter(f -> f.endsWith(fileExtension))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    private static List<String> fileExistingInPathByName(String pathToSearch, String fileName) {

        Path path = Paths.get(pathToSearch);

        if (!Files.isDirectory(path)) {
            throw new IllegalArgumentException("Path must be a directory!");
        }

        List<String> result = null;

        try (Stream<Path> walk = Files.walk(path)) {
            result = walk
                    .filter(p -> !Files.isDirectory(p))
                    .map(p -> p.toString().toLowerCase())
                    .filter(f -> f.contains(fileName))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
}