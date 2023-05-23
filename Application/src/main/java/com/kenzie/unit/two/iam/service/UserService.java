package com.kenzie.unit.two.iam.service;

import com.kenzie.unit.two.iam.lambda.models.CreateUserRequest;
import com.kenzie.unit.two.iam.models.Department;
import com.kenzie.unit.two.iam.models.User;
import com.kenzie.unit.two.iam.storage.Storage;

import java.util.List;
import java.util.UUID;


public class UserService {
    private final Storage storage;

    public UserService(Storage storage) {
        this.storage = storage;
    }

    public User createUser(CreateUserRequest request) {
        Department department = storage.getDepartmentByName(request.getDepartmentName());

        if(department == null){
            throw new IllegalArgumentException("Department does not exist");
        }
        if (storage.getUserByUsername(request.getUserName()) != null) {
            throw new IllegalArgumentException("User already exists");
        }
        User user = new User(UUID.randomUUID(), request.getUserName(), department);
        storage.storeUser(user);
        return user;
    }

    public User getUserByUserName(String  userName) {
        return storage.getUserByUsername(userName);
    }

    public User getUserByUserId(String  userId) {
        return storage.getUser(userId);
    }

    public List<User> getUsers() {
        return storage.getUsers();
    }
}
