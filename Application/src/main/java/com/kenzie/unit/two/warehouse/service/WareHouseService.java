package com.kenzie.unit.two.warehouse.service;

import com.kenzie.unit.two.iam.models.Role;
import com.kenzie.unit.two.iam.models.User;
import com.kenzie.unit.two.iam.service.RoleService;
import com.kenzie.unit.two.iam.service.UserRoleService;
import com.kenzie.unit.two.iam.service.UserService;
import com.kenzie.unit.two.iam.entities.Roles;
import com.kenzie.unit.two.warehouse.lambda.models.CanInvoiceClientRequest;
import com.kenzie.unit.two.warehouse.lambda.models.CanUserPackItemRequest;

import java.util.List;

public class WareHouseService {
    private final UserRoleService userRoleService;
    private final UserService userService;
    private final RoleService roleService;

    public WareHouseService(UserRoleService userRoleService, UserService userService, RoleService roleService) {
        this.userRoleService = userRoleService;
        this.userService = userService;
        this.roleService = roleService;
    }

    public boolean canWarehouseUserPackItem(CanUserPackItemRequest request) {
        Role packItem = roleService.getRoleByRoleName(Roles.PACK_ITEMS.toString());

        User user = userService.getUserByUserName(request.getUserName());

        return userRoleService.doesUserHaveRole(user, packItem);
    }

    public boolean canInvoiceClient(CanInvoiceClientRequest canInvoiceClientRequest) {
        User user = userService.getUserByUserName(canInvoiceClientRequest.getUserName());

        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }

        List<Role> roles = userRoleService.getUserRoles(user.getUserName()).getRoles();
        boolean matchCreateInvoiceRole = false;
        boolean matchViewClientRole = false;
        if(roles != null) {
            for (Role role : roles) {
                if (role.getRoleName().equalsIgnoreCase(Roles.CREATE_INVOICE.getRoleName())) {
                    matchCreateInvoiceRole = true;
                }
                if (role.getRoleName().equalsIgnoreCase(Roles.VIEW_CLIENT.getRoleName())) {
                    matchViewClientRole = true;
                }
            }
        }
        return matchCreateInvoiceRole && matchViewClientRole;
    }
}
