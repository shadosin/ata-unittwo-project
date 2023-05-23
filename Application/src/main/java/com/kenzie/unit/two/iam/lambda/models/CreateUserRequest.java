package com.kenzie.unit.two.iam.lambda.models;

import com.kenzie.ata.ExcludeFromJacocoGeneratedReport;

@ExcludeFromJacocoGeneratedReport
public class CreateUserRequest {

    private String userName;
    private String departmentName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
}
