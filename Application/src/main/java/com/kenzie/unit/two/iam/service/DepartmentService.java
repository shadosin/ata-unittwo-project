package com.kenzie.unit.two.iam.service;

import com.kenzie.unit.two.iam.lambda.models.CreateDepartmentRequest;
import com.kenzie.unit.two.iam.models.Department;
import com.kenzie.unit.two.iam.storage.Storage;

import java.util.List;
import java.util.UUID;

public class DepartmentService {

    private final Storage storage;

    public DepartmentService(Storage storage) {
        this.storage = storage;
    }

    public Department createDepartment(CreateDepartmentRequest request) {
        if (storage.getDepartmentByName(request.getDepartmentName()) != null) {
            throw new IllegalArgumentException("Department already exists");
        }
        Department department = new Department(UUID.randomUUID(), request.getDepartmentName());
        storage.storeDepartment(department);
        return department;
    }

    public List<Department> getDepartments() {
        return storage.getDepartments();
    }


    public Department getDepartmentByName(String departmentName) {
        return storage.getDepartmentByName(departmentName);
    }

    public Department getDepartmentById(String departmentId) {
        return storage.getDepartment(departmentId);
    }
}
