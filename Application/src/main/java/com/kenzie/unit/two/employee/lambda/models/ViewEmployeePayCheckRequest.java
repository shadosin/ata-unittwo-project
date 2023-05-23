package com.kenzie.unit.two.employee.lambda.models;

import com.kenzie.ata.ExcludeFromJacocoGeneratedReport;

@ExcludeFromJacocoGeneratedReport
public class ViewEmployeePayCheckRequest {
    private String requesterUserName;
    private String employeeUserName;

    public String getRequesterUserName() {
        return requesterUserName;
    }

    public void setRequesterUserName(String requesterUserName) {
        this.requesterUserName = requesterUserName;
    }

    public String getEmployeeUserName() {
        return employeeUserName;
    }

    public void setEmployeeUserName(String employeeUserName) {
        this.employeeUserName = employeeUserName;
    }
}
