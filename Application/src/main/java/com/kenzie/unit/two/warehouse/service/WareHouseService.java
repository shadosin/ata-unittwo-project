package com.kenzie.unit.two.warehouse.service;

import com.kenzie.unit.two.iam.entities.Roles;
import com.kenzie.unit.two.iam.models.FunctionalRole;
import com.kenzie.unit.two.iam.models.Role;
import com.kenzie.unit.two.iam.models.User;
import com.kenzie.unit.two.iam.service.RoleService;
import com.kenzie.unit.two.iam.service.UserRoleService;
import com.kenzie.unit.two.iam.service.UserService;
import com.kenzie.unit.two.warehouse.lambda.models.CanInvoiceClientRequest;
import com.kenzie.unit.two.warehouse.lambda.models.CanUserPackItemRequest;

import com.kenzie.ata.ExcludeFromJacocoGeneratedReport;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.List;

@ExcludeFromJacocoGeneratedReport
public class WareHouseService {
    private final UserRoleService userRoleService;
    private final UserService userService;
    private final RoleService roleService;

    // TODO Use this class property for Task 5, do not change this line
    private final FunctionalRole invoicingClientRole =
            new FunctionalRole(Arrays.asList(Roles.CREATE_INVOICE, Roles.VIEW_CLIENT));

    public WareHouseService(UserRoleService userRoleService, UserService userService, RoleService roleService) {
        this.userRoleService = userRoleService;
        this.userService = userService;
        this.roleService = roleService;
    }

    public boolean canWarehouseUserPackItem(CanUserPackItemRequest request) {
        Logger logger = LogManager.getLogger();
        logger.info("Role by name:" + Roles.PACK_ITEMS.toString());
        Role packItem = roleService.getRoleByRoleName(Roles.PACK_ITEMS.getRoleName());

        User user = userService.getUserByUserName(request.getUserName());

        return userRoleService.doesUserHaveRole(user, packItem);
    }

    public boolean canInvoiceClient(CanInvoiceClientRequest canInvoiceClientRequest) {
        User user = userService.getUserByUserName(canInvoiceClientRequest.getUserName());

        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }

        List<Role> roles = userRoleService.getUserRoles(user.getUserName()).getRoles();

        return invoicingClientRole.matches(roles);
    }
}
