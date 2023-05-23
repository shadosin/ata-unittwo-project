package com.kenzie.unit.two.iam.models;

import java.util.Objects;
import java.util.UUID;

public class User {

    private final UUID id;
    private final String userName;
    private final Department department;

    public User(UUID id, String userName, Department department) {
        this.id = id;
        this.userName = userName;
        this.department = department;
    }

    public UUID getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public Department getDepartment() {
        return department;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", department=" + department +
                '}';
    }
}