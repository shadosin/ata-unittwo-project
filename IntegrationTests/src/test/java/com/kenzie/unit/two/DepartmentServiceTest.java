package com.kenzie.unit.two;

import com.kenzie.unit.two.iam.lambda.models.CreateDepartmentRequest;
import com.kenzie.unit.two.iam.models.Department;
import com.kenzie.unit.two.iam.service.DepartmentService;
import com.kenzie.unit.two.iam.storage.Storage;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Field;

import java.util.List;
import java.util.Set;


import static org.mockito.Mockito.mockingDetails;
import static org.reflections.ReflectionUtils.getAllFields;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DepartmentServiceTest {

    @AfterEach
    void afterEach() {
        assert(false);
    }

    @Test
    void createNewDepartment_TASK_7() {
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
    void throwExceptionDepartmentNameAlreadyExists_TASK_7() {
        //GIVEN
        DepartmentService departmentService = App.departmentService();

        //WHEN
        String departmentName = RandomStringUtils.random(20);

        CreateDepartmentRequest createDepartmentRequest = new CreateDepartmentRequest();
        createDepartmentRequest.setDepartmentName(departmentName);

        departmentService.createDepartment(createDepartmentRequest);


        //THEN
        assertThrows(IllegalArgumentException.class,
                () -> departmentService.createDepartment(createDepartmentRequest));
    }

    @Test
    void getDepartmentByName_TASK_7(){
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
    void getAllDepartments(){
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

    @Test
    void departmentServiceTest_usesMocks_TASK_7() throws NoSuchFieldException, IllegalAccessException {
        DepartmentServiceTest departmentServiceTest = new DepartmentServiceTest();
        Set<Field> sourceFieldList = getAllFields(departmentServiceTest.getClass());

        //check if storage variable is set in this class using reflection
        if (findPropertyWithType(sourceFieldList, "storage", "Storage")) {
            Field field = getClass().getDeclaredField("storage");
            assertTrue(mockingDetails(field.get(this)).isMock(), "storage needs to be a Mock");

        }
        else{
            assertTrue(false, "Storage type variable with name <storage> needs to be declared");
        }

    }

    //helper method loops through a fieldLIst to find property name
    public static boolean findProperty(Set<Field> fieldList, String property){
        for (Field field: fieldList) {
            if(field.getName().equals(property)){
                return true;
            }
        }
        return false;
    }

    //helper method loops through a fieldLIst to find property name and type
    public static boolean findPropertyWithType(Set<Field> fieldList, String property, String type){
        for (Field field: fieldList) {
            if(field.getName().equals(property) && field.getType().getTypeName().contains(type)){
                return true;
            }
        }
        return false;
    }

}