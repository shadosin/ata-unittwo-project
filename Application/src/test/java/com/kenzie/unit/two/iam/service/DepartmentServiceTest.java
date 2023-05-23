package com.kenzie.unit.two.iam.service;

import com.kenzie.unit.two.App;
import com.kenzie.unit.two.iam.lambda.models.CreateDepartmentRequest;
import com.kenzie.unit.two.iam.models.Department;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DepartmentServiceTest {

    @Test
    void create_new_department() {
        //GIVEN
        DepartmentService departmentService = App.departmentService();

        //WHEN
        String departmentName = RandomStringUtils.random(20);
        CreateDepartmentRequest createDepartmentRequest = new CreateDepartmentRequest();
        createDepartmentRequest.setDepartmentName(departmentName);

        //THEN
        Department department = departmentService.createDepartment(createDepartmentRequest);
        assertTrue(department.getId() != null);
    }

    @Test
    void throwException_department_name_already_exists() {
        //GIVEN
        DepartmentService departmentService = App.departmentService();

        //WHEN
        String departmentName = RandomStringUtils.random(20);

        CreateDepartmentRequest createDepartmentRequest = new CreateDepartmentRequest();
        createDepartmentRequest.setDepartmentName(departmentName);

        departmentService.createDepartment(createDepartmentRequest);


        //THEN
        assertThrows(IllegalArgumentException.class, () -> departmentService.createDepartment(createDepartmentRequest));
    }

    @Test
    void get_department_by_name(){
        //GIVEN
        DepartmentService departmentService = App.departmentService();

        //WHEN
        String departmentName = RandomStringUtils.random(20);

        CreateDepartmentRequest createDepartmentRequest = new CreateDepartmentRequest();
        createDepartmentRequest.setDepartmentName(departmentName);

        Department department = departmentService.createDepartment(createDepartmentRequest);


        //THEN
        Department queriedDepartment = departmentService.getDepartmentByName(departmentName);
        assertTrue(department.getId().equals(queriedDepartment.getId()));
    }

    @Test
    void get_all_departments(){
        //GIVEN
        DepartmentService departmentService = App.departmentService();

        //WHEN
        String departmentName = RandomStringUtils.random(20);

        CreateDepartmentRequest createDepartmentRequest = new CreateDepartmentRequest();
        createDepartmentRequest.setDepartmentName(departmentName);

        Department department = departmentService.createDepartment(createDepartmentRequest);


        //THEN
        List<Department> departments = departmentService.getDepartments();
        assertTrue(departments.contains(department));
    }
}