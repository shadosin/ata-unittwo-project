package com.kenzie.unit.two.employee.service.models;

import java.util.Objects;

public class Employee {
    private final String id;
    private final String userName;
    private final String payCheck;
    private final String department;

    public Employee(String id, String userName, String department, String payCheck) {
        this.id = id;
        this.userName = userName;
        this.department = department;
        this.payCheck = payCheck;
    }

    public String getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getPayCheck() {
        return payCheck;
    }

    public String getDepartment() {
        return department;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(id, employee.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "employeeId='" + id + '\'' +
                ", employeeName='" + userName + '\'' +
                '}';
    }
}
