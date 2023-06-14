package com.kenzie.unit.two;

import com.kenzie.unit.two.employee.service.UserOrRoleNotFoundException;
import com.kenzie.unit.two.iam.service.UserRoleService;
import com.kenzie.unit.two.iam.storage.Storage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserRoleCustomTest {
    @Test
    void missingUserRoleThrowsException_TASK_6() {
        Storage storage = mock(Storage.class);
        UserRoleService userRoleService = new UserRoleService(storage);

        // Configure the mock to return null for user and role
        when(storage.getUserByUsername(any())).thenReturn(null);
        when(storage.getRoleByRoleName(any())).thenReturn(null);


        // Assert that UserOrRoleNotFoundException is thrown
        assertThrows(UserOrRoleNotFoundException.class, () -> {
            userRoleService.doesUserHaveRole(null, null);
        }, "User or role not found.");
    }
}
