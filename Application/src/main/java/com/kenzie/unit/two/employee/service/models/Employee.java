package com.kenzie.unit.two.employee.service.models;

import com.kenzie.ata.ExcludeFromJacocoGeneratedReport;
import com.kenzie.unit.two.iam.models.Department;
import com.kenzie.unit.two.iam.models.User;

import java.util.Objects;
import java.util.UUID;

@ExcludeFromJacocoGeneratedReport
public class Employee extends User {
    private final String payCheck;

    public Employee(UUID id, String userName, Department department, String payCheck) {
        super(id, userName, department);
        this.payCheck = payCheck;
    }

    public String getPayCheck() {
        return payCheck;
    }


    @Override
    public String toString() {
        return "Employee{"
                + "userName='" + getUserName() + '\'' +
                ", id='" + getId() + '\'' +
                ", department=" + getDepartment() +
                ", payCheck='" + payCheck + '\'' +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employee)) return false;
        if (!super.equals(o)) return false;
        Employee employee = (Employee) o;
        return getPayCheck().equals(employee.getPayCheck());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getPayCheck());
    }
}
