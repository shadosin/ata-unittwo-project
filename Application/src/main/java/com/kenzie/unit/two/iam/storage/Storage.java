package com.kenzie.unit.two.iam.storage;

import com.kenzie.unit.two.iam.models.Department;
import com.kenzie.unit.two.iam.models.Role;
import com.kenzie.unit.two.iam.models.User;
import com.kenzie.unit.two.iam.models.UserRoles;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.kenzie.ata.ExcludeFromJacocoGeneratedReport;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ExcludeFromJacocoGeneratedReport
public class Storage {

    public static final String DEPARTMENTS_DATA_STORE = "departments";
    public static final String USERS_DATA_STORE = "users";
    public static final String ROLES_DATA_STORE = "roles";
    public static final String USER_ROLES_DATA_STORE = "userroles";

    static final int DEFAULT_BUFFER_SIZE = 8192;

    private final AmazonS3 s3client;
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private String bucketName = "";

    public Storage() {

        s3client = AmazonS3ClientBuilder
                .standard()
                .withCredentials(DefaultAWSCredentialsProviderChain.getInstance())
                .build();
        // Get bucket name when deployed
        bucketName = System.getenv("BucketName");
        if (bucketName == null) {
            // Get it from the local environment
            bucketName = System.getenv("UNIT_TWO_APP_STORAGE_BUCKET");
            if (bucketName == null) {
                throw new RuntimeException("UNIT_TWO_APP_STORAGE_BUCKET is not defined.  Make sure the " +
                        "setupEnvironment.sh script is configured and that it is included in your system path.");
            }
        }
        bucketName = bucketName.toLowerCase();

        createBucket();
    }

    private void createBucket() {

        if (s3client.doesBucketExistV2(bucketName)) {
            return;
        }
        s3client.createBucket(bucketName);
    }

    public List<Department> getDepartments() {
        List<Department> departments = new ArrayList<>();
        if (s3client.doesObjectExist(bucketName, DEPARTMENTS_DATA_STORE)) {
            String content = getContentFromS3Bucket(DEPARTMENTS_DATA_STORE);
            if (!content.equalsIgnoreCase("")) {
                departments = gson.fromJson(content, new TypeToken<ArrayList<Department>>() {
                }.getType());

            }
        }
        return departments;
    }

    public void storeDepartment(Department department) {
        List<Department> departments = getDepartments();
        departments.add(department);
        s3client.putObject(bucketName, DEPARTMENTS_DATA_STORE, gson.toJson(departments));
    }

    public Department getDepartment(String departmentId) {
        Department department = null;
        List<Department> departments = getDepartments();
        for (Department listDepartment : departments) {
            if (listDepartment.getId().toString().equals(departmentId)) {
                department = listDepartment;
                break;
            }
        }
        return department;
    }

    public Department getDepartmentByName(String departmentName) {
        Department department = null;
        List<Department> departments = getDepartments();
        for (Department listDepartment : departments) {
            if (listDepartment.getName().equalsIgnoreCase(departmentName)) {
                department = listDepartment;
                break;
            }
        }
        return department;
    }

    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        if (s3client.doesObjectExist(bucketName, USERS_DATA_STORE)) {
            String content = getContentFromS3Bucket(USERS_DATA_STORE);
            if (!content.equalsIgnoreCase("")) {
                users = gson.fromJson(content, new TypeToken<ArrayList<User>>() {
                }.getType());

            }
        }
        return users;
    }

    public void storeUser(User user) {
        List<User> users = getUsers();
        users.add(user);
        s3client.putObject(bucketName, USERS_DATA_STORE, gson.toJson(users));
    }

    public User getUser(String userId) {
        User user = null;
        List<User> users = getUsers();
        for (User listUser : users) {
            if (listUser.getId().toString().equals(userId)) {
                user = listUser;
                break;
            }
        }
        return user;
    }

    public User getUserByUsername(String userName) {
        User user = null;
        List<User> users = getUsers();
        for (User listUser : users) {
            if (listUser.getUserName().equalsIgnoreCase(userName)) {
                user = listUser;
                break;
            }
        }
        return user;
    }

    public List<Role> getRoles() {
        List<Role> roles = new ArrayList<>();
        if (s3client.doesObjectExist(bucketName, ROLES_DATA_STORE)) {
            String content = getContentFromS3Bucket(ROLES_DATA_STORE);
            if (!content.equalsIgnoreCase("")) {
                roles = gson.fromJson(content, new TypeToken<ArrayList<Role>>() {
                }.getType());

            }
        }
        return roles;
    }

    public void storeRole(Role role) {
        List<Role> roles = getRoles();
        roles.add(role);
        s3client.putObject(bucketName, ROLES_DATA_STORE, gson.toJson(roles));
    }


    public Role getRole(String roleId) {
        Role role = null;
        List<Role> roles = getRoles();
        for (Role listRole : roles) {
            if (listRole.getId().toString().equals(roleId)) {
                role = listRole;
                break;
            }
        }
        return role;
    }

    public Role getRoleByRoleName(String roleName) {
        Role role = null;
        List<Role> roles = getRoles();
        for (Role listRole : roles) {
            if (listRole.getRoleName().equalsIgnoreCase(roleName)) {
                role = listRole;
                break;
            }
        }
        return role;
    }

    public UserRoles storeUserRole(User user, Role role) {
        Map<String, List<Role>> mapUserRoles = getUserRolesMap();
        List<Role> roles = mapUserRoles.get(user.getId().toString());
        if (roles == null) {
            roles = new ArrayList<>();
        }

        roles.add(role);
        mapUserRoles.put(user.getId().toString(), roles);
        s3client.putObject(bucketName, USER_ROLES_DATA_STORE, gson.toJson(mapUserRoles));
        return new UserRoles(user, roles);
    }

    public UserRoles getUserRoles(User user) {
        Map<String, List<Role>> mapUserRoles = getUserRolesMap();
        return new UserRoles(user, mapUserRoles.get(user.getId().toString()));
    }

    private Map<String, List<Role>> getUserRolesMap() {
        Map<String, List<Role>> userRoles = new HashMap<>();
        if (s3client.doesObjectExist(bucketName, USER_ROLES_DATA_STORE)) {
            String content = getContentFromS3Bucket(USER_ROLES_DATA_STORE);
            if (!content.equalsIgnoreCase("")) {
                userRoles = gson.fromJson(content, new TypeToken<Map<String, List<Role>>>() {
                }.getType());

            }
        }
        return userRoles;
    }

    public boolean doesUserHaveRole(User user, Role role) {
        UserRoles userRoles = getUserRoles(user);
        return userRoles != null && userRoles.getRoles() != null && userRoles.getRoles().contains(role);
    }

    private String getContentFromS3Bucket(String usersDataStore) {
        S3Object s3object = s3client.getObject(bucketName, usersDataStore);
        S3ObjectInputStream inputStream = s3object.getObjectContent();
        return convertInputStreamToString(inputStream);
    }

    private String convertInputStreamToString(InputStream is) {
        try {
            ByteArrayOutputStream result = new ByteArrayOutputStream();
            byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
            int length;
            while ((length = is.read(buffer)) != -1) {
                result.write(buffer, 0, length);
            }
            return result.toString(StandardCharsets.UTF_8.name());
        } catch (IOException ex) {
            throw new RuntimeException("Could not convert input stream to string");
        }
    }
}
