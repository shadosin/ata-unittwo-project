package com.kenzie.unit.two.employee.service;

import com.kenzie.ata.ExcludeFromJacocoGeneratedReport;

@ExcludeFromJacocoGeneratedReport
public class EmployeeNotFoundException extends RuntimeException {
    public EmployeeNotFoundException(String message) {
        super(message);
    }
}
