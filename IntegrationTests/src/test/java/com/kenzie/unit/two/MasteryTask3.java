package com.kenzie.unit.two;

import static com.kenzie.unit.two.ATATestHelpers.IntrospectionAssertions.assertDirectlyExtends;
import com.kenzie.unit.two.ATATestHelpers.ClassQuery;
import org.junit.jupiter.api.Test;

public class MasteryTask3 {

    private static final String EMPLOYEE_PACKAGE = "com.kenzie.unit.two.employee.service.models";
    private static final String USER_PACKAGE = "com.kenzie.unit.two.iam.models";
    private static final String EMPLOYEE_CLASS_NAME = "Employee";
    private static final String USER_CLASS_NAME = "User";


    @Test
    void employeeExtendsUser_TASK_3() {
        Class<?> employeeClass = ClassQuery.inExactPackage(EMPLOYEE_PACKAGE)
                .withExactSimpleName(EMPLOYEE_CLASS_NAME)
                .findClassOrFail();

        Class<?> userClass = ClassQuery.inExactPackage(USER_PACKAGE)
                .withExactSimpleName(USER_CLASS_NAME)
                .findClassOrFail();

        assertDirectlyExtends(employeeClass, userClass);
    }
}
