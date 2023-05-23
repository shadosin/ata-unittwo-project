package com.kenzie.unit.two;

import com.kenzie.unit.two.employee.service.EmployeeService;
import com.kenzie.unit.two.iam.service.DepartmentService;
import com.kenzie.unit.two.iam.service.RoleService;
import com.kenzie.unit.two.iam.service.UserRoleService;
import com.kenzie.unit.two.iam.service.UserService;
import com.kenzie.unit.two.iam.storage.Storage;
import com.kenzie.unit.two.warehouse.service.WareHouseService;

import com.kenzie.ata.ExcludeFromJacocoGeneratedReport;


@ExcludeFromJacocoGeneratedReport
public class App {


    public static Storage storage() {
        return new Storage();
    }

    public static DepartmentService departmentService() {
        return new DepartmentService(storage());
    }

    public static UserService userService() {
        return new UserService(storage());
    }

    public static RoleService roleService() {
        return new RoleService(storage());
    }

    public static UserRoleService userRoleService() {
        return new UserRoleService(storage());
    }

    public static WareHouseService warehouseService() {
        return new WareHouseService(userRoleService(), userService(), roleService());
    }

    public static EmployeeService employeeService() {
        return new EmployeeService(userRoleService(), userService(), roleService());
    }
}
