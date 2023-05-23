package com.kenzie.unit.two.iam.lambda.models;

public class AssignUserToRoleRequest {
    private String roleName;

    private String userName;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
