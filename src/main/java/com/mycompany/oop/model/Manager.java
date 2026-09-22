/*
 * The Sweet Cupcake Shop  -  CSE4006 Object Oriented Programming (WRIT1)
 *
 * File    : Manager.java
 * Package : com.mycompany.oop.model
 */

package com.mycompany.oop.model;

import com.mycompany.oop.exception.ValidationException;

/**
 * A Manager of the shop.
 *
 * A Manager inherits every attribute and method of User and adds no new data;
 * what changes is the behaviour. By returning true from canManageUsers() a
 * Manager is granted the extra functionality described in the scenario, namely
 * creating new user accounts for cashiers.
 *
 * @author The Sweet Cupcake Shop development team
 */
public class Manager extends User {

    /**
     * @param userId       unique identifier such as U001
     * @param username     unique login name
     * @param passwordHash SHA-256 hash of the password
     * @throws ValidationException when any argument is invalid
     */
    public Manager(String userId, String username, String email,
            String passwordHash) throws ValidationException {
        // The role is fixed by the subclass, so a Manager object can never
        // accidentally be created holding the Cashier role.
        super(userId, username, email, passwordHash, Role.MANAGER);
    }

    /**
     * A Manager is the only user level allowed to create accounts.
     */
    @Override
    public boolean canManageUsers() {
        return true;
    }

    @Override
    public String getRoleDescription() {
        return "Manager - full access, including creating cashier accounts";
    }

    @Override
    public String[] getPermissions() {
        return new String[]{
            "View all cupcake details",
            "Add new cupcake details",
            "Search cupcakes by category",
            "Create new cashier accounts"
        };
    }
}
