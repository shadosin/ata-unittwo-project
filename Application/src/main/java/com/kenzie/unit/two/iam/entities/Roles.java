package com.kenzie.unit.two.iam.entities;

public enum Roles {
    PACK_ITEMS("pack-items"),
    VIEW_PAYCHECK("view-paycheck"),
    CREATE_INVOICE("create-invoice"),
    VIEW_CLIENT("view-client");

    private final String roleName;

    Roles(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }
}
