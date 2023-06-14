package com.kenzie.unit.two;

import com.kenzie.unit.two.ATATestHelpers.ATAFileReader;
import com.kenzie.unit.two.employee.service.UserOrRoleNotFoundException;
import com.kenzie.unit.two.iam.lambda.models.AssignUserToRoleRequest;
import com.kenzie.unit.two.iam.lambda.models.CreateDepartmentRequest;
import com.kenzie.unit.two.iam.lambda.models.CreateRoleRequest;
import com.kenzie.unit.two.iam.lambda.models.CreateUserRequest;
import com.kenzie.unit.two.iam.models.Department;
import com.kenzie.unit.two.iam.models.Role;
import com.kenzie.unit.two.iam.models.User;
import com.kenzie.unit.two.iam.service.DepartmentService;
import com.kenzie.unit.two.iam.service.RoleService;
import com.kenzie.unit.two.iam.service.UserRoleService;
import com.kenzie.unit.two.iam.service.UserService;
import com.kenzie.unit.two.iam.storage.Storage;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserRoleServiceTest {

    @Test
    void userHasRole_TASK_4() {
        //GIVEN
        UserRoleService userRoleService = App.userRoleService();
        DepartmentService departmentService = App.departmentService();
        UserService userService = App.userService();
        RoleService roleService = App.roleService();

        //WHEN
        String userName = RandomStringUtils.random(20);
        String departmentName = RandomStringUtils.random(20);
        CreateDepartmentRequest createDepartmentRequest = new CreateDepartmentRequest();
        createDepartmentRequest.setDepartmentName(departmentName);

        departmentService.createDepartment(createDepartmentRequest);

        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setUserName(userName);
        createUserRequest.setDepartmentName(departmentName);
        User user = userService.createUser(createUserRequest);


        String roleName = RandomStringUtils.random(20);
        CreateRoleRequest createRoleRequest = new CreateRoleRequest();
        createRoleRequest.setRoleName(roleName);
        Role role = roleService.createRole(createRoleRequest);

        AssignUserToRoleRequest assignUserToRoleRequest = new AssignUserToRoleRequest();
        assignUserToRoleRequest.setUserName(userName);
        assignUserToRoleRequest.setRoleName(roleName);
        userRoleService.createUserRole(assignUserToRoleRequest);

        //THEN
        boolean userHasRole = userRoleService.doesUserHaveRole(user,role);
        assertTrue(userHasRole);
    }

    @Test
    void userDoesNotHaveRole_TASK_4() {
        //GIVEN
        UserRoleService userRoleService = App.userRoleService();
        DepartmentService departmentService = App.departmentService();
        UserService userService = App.userService();
        RoleService roleService = App.roleService();

        //WHEN
        String userName = RandomStringUtils.random(20);
        String departmentName = RandomStringUtils.random(20);
        CreateDepartmentRequest createDepartmentRequest = new CreateDepartmentRequest();
        createDepartmentRequest.setDepartmentName(departmentName);

        departmentService.createDepartment(createDepartmentRequest);

        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setUserName(userName);
        createUserRequest.setDepartmentName(departmentName);
        User user = userService.createUser(createUserRequest);


        String roleName = RandomStringUtils.random(20);
        CreateRoleRequest createRoleRequest = new CreateRoleRequest();
        createRoleRequest.setRoleName(roleName);
        Role role = roleService.createRole(createRoleRequest);

        //THEN
        boolean userHasRole = userRoleService.doesUserHaveRole(user,role);
        assertFalse(userHasRole);
    }

    @Test
    void doesUserHaveRole_TASK_4() {
        //GIVEN
        UserRoleService userRoleService = App.userRoleService();
        DepartmentService departmentService = App.departmentService();
        UserService userService = App.userService();
        RoleService roleService = App.roleService();

        //WHEN
        String userName = RandomStringUtils.random(20);
        String departmentName = RandomStringUtils.random(20);
        CreateDepartmentRequest createDepartmentRequest = new CreateDepartmentRequest();
        createDepartmentRequest.setDepartmentName(departmentName);

        departmentService.createDepartment(createDepartmentRequest);

        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setUserName(userName);
        createUserRequest.setDepartmentName(departmentName);
        User user = userService.createUser(createUserRequest);


        String roleName = RandomStringUtils.random(20);
        CreateRoleRequest createRoleRequest = new CreateRoleRequest();
        createRoleRequest.setRoleName(roleName);
        Role role = roleService.createRole(createRoleRequest);

        //THEN
        boolean userHasRole = userRoleService.doesUserHaveRole(user,role);
        assertFalse(userHasRole);
    }


    @Test
    void doesUserHaveRole_callsGetRolesOnce_TASK_4() {
        final String CLASS_FILE = "UserRoleService.java";
        final String CLASS_PATH = "../Application/src/main/java/com/kenzie/unit/two/iam/service";

        // GIVEN - path to directory that .java file should exist in
        List<String> filesWithExpectedName = fileExistingInPathByName(CLASS_PATH, CLASS_FILE);

        boolean filesWithNameInDirectory= false;
        if(filesWithExpectedName != null && filesWithExpectedName.size() > 0)  {
            filesWithNameInDirectory = true;
        }

        // WHEN -check file contents
        String contents = getFileContentFromResources(CLASS_PATH + "/" + CLASS_FILE);
        //checks number of times called
        int firstIndex = contents.indexOf("userRoles.getRoles()");
        int lastIndex = contents.lastIndexOf("userRoles.getRoles()");

        // THEN - file exists with expected name and calls expected method one time
        assertTrue(filesWithNameInDirectory, CLASS_FILE + " not found in expected location.");
        assertTrue(firstIndex >=0, "doesUserHaveRole method does not call userRoles.getRoles()");
        assertTrue(firstIndex==lastIndex,"doesUserHaveRole method calls userRoles.getRoles() more than once");
    }

    @Test
    void doesUserHaveRole_callsContains_TASK_4() {
        final String CLASS_FILE = "UserRoleService.java";
        final String CLASS_PATH = "../Application/src/main/java/com/kenzie/unit/two/iam/service";

        // GIVEN - path to directory that .java file should exist in
        List<String> filesWithExpectedName = fileExistingInPathByName(CLASS_PATH, CLASS_FILE);

        boolean filesWithNameInDirectory= false;
        if(filesWithExpectedName != null && filesWithExpectedName.size() > 0)  {
            filesWithNameInDirectory = true;
        }

        // WHEN -check file contents
        String contents = getFileContentFromResources(CLASS_PATH + "/" + CLASS_FILE);

        // THEN - file exists with expected name and calls expected method
        assertTrue(filesWithNameInDirectory, CLASS_FILE + " not found in expected location.");
        assertTrue(contents.contains("contains"),"doesUserHaveRole method does not call contains method");
    }



    //HELPER METHODS USED TO INSPECT FILES
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
                    .filter(f -> f.contains(fileName.toLowerCase()))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
}