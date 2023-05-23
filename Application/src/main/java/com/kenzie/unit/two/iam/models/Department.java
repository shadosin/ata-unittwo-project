package com.kenzie.unit.two.iam.models;

import com.kenzie.ata.ExcludeFromJacocoGeneratedReport;

import java.util.Objects;
import java.util.UUID;

@ExcludeFromJacocoGeneratedReport
public class Department {
    private final UUID id;
    private final String name;

    public Department(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Department that = (Department) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Department{" +
                "id='" + id + '\'' +
                '}';
    }
}
