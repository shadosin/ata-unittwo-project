package com.kenzie.unit.two.employee.service;

import com.kenzie.unit.two.employee.lambda.models.ViewEmployeePayCheckRequest;
import com.kenzie.unit.two.employee.service.models.Employee;
import com.kenzie.unit.two.iam.entities.Roles;
import com.kenzie.unit.two.iam.models.Department;
import com.kenzie.unit.two.iam.models.Role;
import com.kenzie.unit.two.iam.models.User;
import com.kenzie.unit.two.iam.service.RoleService;
import com.kenzie.unit.two.iam.service.UserRoleService;
import com.kenzie.unit.two.iam.service.UserService;

import com.opencsv.CSVReaderHeaderAware;
import com.opencsv.exceptions.CsvValidationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.management.relation.RoleNotFoundException;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileOutputStream;
import java.io.InputStream;

import java.net.URL;
import java.net.URLConnection;

import java.util.Map;
import java.util.UUID;


public class EmployeeService {

    private final String employeeCSVUrl = "https://java2-kenzieacademy-donotdelete.s3.amazonaws.com/employee.csv";
    private final UserRoleService userRoleService;
    private final UserService userService;
    private final RoleService roleService;
    private final Logger log = LogManager.getLogger();
    private final ClassLoader loader = EmployeeService.class.getClassLoader();

    public EmployeeService(UserRoleService userRoleService, UserService userService, RoleService roleService) {
        this.userRoleService = userRoleService;
        this.userService = userService;
        this.roleService = roleService;
    }

    public Employee getEmployeePayCheck(ViewEmployeePayCheckRequest request) {
        Employee employee = null;

        Role viewPayCheck = roleService.getRoleByRoleName(Roles.VIEW_PAYCHECK.getRoleName());
        User user = userService.getUserByUserName(request.getRequesterUserName());


        if (!userRoleService.doesUserHaveRole(user, viewPayCheck)) {
            throw new UserOrRoleNotFoundException("User or role not found");
        } else {
            try {
                String employeeData = null;
                try {
                    employeeData = downloadEmployeeData(new URL(employeeCSVUrl), "employee.csv");
                } catch (Exception ex) {
                    throw new RuntimeException("Please message your instructor for additional help on populating the correct Employee Data if you hit this Exception.");
                }

                File file = new File(employeeData);
                CSVReaderHeaderAware reader = new CSVReaderHeaderAware(new FileReader(file));
                Map<String, String> values;
                while (((values = reader.readMap())) != null) {
                    UUID id = UUID.fromString(values.get("Id"));
                    String userName = values.get("Username");
                    String department = values.get("Department");
                    String payCheck = values.get("Paycheck");

                    if (theCorrectUser(request.getEmployeeUserName(), userName)) {
                        if (inTheSameDepartment(user.getDepartment().getName(), department)) {
                            employee = new Employee(id, userName, user.getDepartment(), payCheck);
                            log.info("Audit: User .* viewed employee .* paycheck information");
                        } else {
                            throw new UnauthorizedException("User does not belong to the employee's department");
                        }
                    }
                }
            } catch (IOException | CsvValidationException e) {
                log.error("Exception occurred", e);
            }
            if (employee == null) {
                throw new EmployeeNotFoundException("Employee cannot be found");
            }
            return employee;
        }
    }

    public boolean theCorrectUser(String requestingUsername, String userName) {
        return userName.equalsIgnoreCase(requestingUsername);
    }

    public boolean inTheSameDepartment(String requestingDepartment, String userDepartment) {
        return requestingDepartment.equals(userDepartment);
    }

    public String downloadEmployeeData(URL url, String localFilename) throws IOException {
        InputStream is = null;
        FileOutputStream fos = null;

        String tempDir = System.getProperty("java.io.tmpdir");
        String outputPath = tempDir + "/" + localFilename;

        try {
            //connect
            URLConnection urlConn = url.openConnection();

            is = urlConn.getInputStream();
            fos = new FileOutputStream(outputPath);

            byte[] buffer = new byte[4096];
            int length;

            while ((length = is.read(buffer)) > 0) {
                fos.write(buffer, 0, length);
            }
            return outputPath;
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } finally {
                if (fos != null) {
                    fos.close();
                }
            }
        }
    }

}
