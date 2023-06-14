package com.kenzie.unit.two;

import com.kenzie.unit.two.iam.lambda.models.CreateDepartmentRequest;
import com.kenzie.unit.two.iam.models.Department;
import com.kenzie.unit.two.iam.service.DepartmentService;
import com.kenzie.unit.two.iam.storage.Storage;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.lang.reflect.Field;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.reflections.ReflectionUtils.getAllFields;

class DepartmentServiceTest {
    @Mock
    private Storage storage;
    @InjectMocks
    private DepartmentService departmentService;

    @BeforeEach
    void beforeEach() {
        System.out.println(":)");
        openMocks(this);
    }

    @AfterEach
    void afterEach() {
        System.out.println(":)");
        ;
    }

    @Test
    void createNewDepartment_TASK_7() {
        // GIVEN
        String departmentName = RandomStringUtils.random(20);
        CreateDepartmentRequest createDepartmentRequest = new CreateDepartmentRequest();
        createDepartmentRequest.setDepartmentName(departmentName);
        Department expectedDepartment = new Department(UUID.randomUUID(), departmentName);

        // WHEN
        when(storage.getDepartment(departmentName)).thenReturn(expectedDepartment);
        Department department = departmentService.createDepartment(createDepartmentRequest);

        // THEN
        assertNotNull(department.getId());
        verify(storage, times(1)).getDepartmentByName(departmentName);
        verify(storage, times(1)).storeDepartment(any(Department.class));
    }

    @Test
    void throwExceptionDepartmentNameAlreadyExists_TASK_7() {
        //GIVEN
        String departmentName = RandomStringUtils.random(20);
        CreateDepartmentRequest createDepartmentRequest = new CreateDepartmentRequest();
        createDepartmentRequest.setDepartmentName(departmentName);

        when(storage.getDepartmentByName(departmentName)).thenReturn(new Department(UUID.randomUUID(), departmentName));

        // THEN
        assertThrows(IllegalArgumentException.class, () -> departmentService.createDepartment(createDepartmentRequest));
        verify(storage, times(1)).getDepartmentByName(departmentName);
    }

    @Test
    void getDepartmentByName_TASK_7() {
        //GIVEN
        String departmentName = RandomStringUtils.random(20);
        Department expectedDepartment = new Department(UUID.randomUUID(), departmentName);
        when(storage.getDepartmentByName(departmentName)).thenReturn(expectedDepartment);

        // WHEN
        Department department = departmentService.getDepartmentByName(departmentName);

        // THEN
        assertEquals(expectedDepartment, department);
        verify(storage, times(1)).getDepartmentByName(departmentName);
    }

    @Test
    void getAllDepartments() {
        //GIVEN
        Department department = new Department(UUID.randomUUID(), "Department 1");
        List<Department> expectedDepartments = new ArrayList<>();
        expectedDepartments.add(department);
        when(storage.getDepartments()).thenReturn(expectedDepartments);

        // WHEN
        List<Department> departments = departmentService.getDepartments();

        // THEN
        assertEquals(expectedDepartments, departments);
        verify(storage, times(1)).getDepartments();
    }

    @Test
    void departmentServiceTest_usesMocks_TASK_7() throws NoSuchFieldException, IllegalAccessException {
        Set<Field> sourceFieldList = getAllFields(DepartmentServiceTest.class);
        String storageFieldName = "storage";

        // WHEN
        boolean hasStorageField = findPropertyWithType(sourceFieldList, storageFieldName, "Storage");

        // THEN
        assertTrue(hasStorageField, "Storage type variable with name <storage> needs to be declared");
        assertTrue(mockingDetails(storage).isMock(), "storage needs to be a Mock");
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