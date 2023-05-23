package com.kenzie.unit.two.iam.models;

import java.util.List;

public class UserRoles {

    private final User user;
    private final List<Role> roles;

    public UserRoles(User user, List<Role> roles) {
        this.user = user;
        this.roles = roles;
    }

    public User getUser() {
        return user;
    }

    public List<Role> getRoles() {
        return roles;
    }
}