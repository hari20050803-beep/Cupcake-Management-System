/*
 * The Sweet Cupcake Shop  -  CSE4006 Object Oriented Programming (WRIT1)
 *
 * File    : Cashier.java
 * Package : com.mycompany.oop.model
 */

package com.mycompany.oop.model;

import com.mycompany.oop.exception.ValidationException;

/**
 * A Cashier who serves customers at the counter.
 *
 * A Cashier inherits all of the User attributes and behaviour but returns false
 * from canManageUsers(), which is how the system prevents a cashier from
 * creating further user accounts.
 *
 * @author The Sweet Cupcake Shop development team
 */
public class Cashier extends User {

    /**
     * @param userId       unique identifier such as U002
     * @param username     unique login name
     * @param passwordHash SHA-256 hash of the password
     * @throws ValidationException when any argument is invalid
     */
    public Cashier(String userId, String username, String email,
            String passwordHash) throws ValidationException {
        super(userId, username, email, passwordHash, Role.CASHIER);
    }

    /**
     * A Cashier may never create user accounts.
     */
    @Override
    public boolean canManageUsers() {
        return false;
    }

    @Override
    public String getRoleDescription() {
        return "Cashier - view, add and search cupcakes";
    }

    @Override
    public String[] getPermissions() {
        return new String[]{
            "View all cupcake details",
            "Add new cupcake details",
            "Search cupcakes by category"
        };
    }
}
