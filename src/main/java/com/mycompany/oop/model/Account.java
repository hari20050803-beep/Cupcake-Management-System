/*
 * The Sweet Cupcake Shop  -  CSE4006 Object Oriented Programming (WRIT1)
 *
 * File    : Account.java
 * Package : com.mycompany.oop.model
 */

package com.mycompany.oop.model;

import java.sql.Timestamp;

/**
 * A registered account as it is stored in the MySQL app_user table.
 *
 * This class exists beside User rather than replacing it. User is what the
 * screens and services work with; Account carries the details that only the
 * database keeps - the person's full name, whether they are logged in at this
 * moment, when they last logged in and when they were registered.
 *
 * Like every other model class its fields are private and reached through
 * getters, so a row read from the database cannot be altered by accident.
 *
 * @author The Sweet Cupcake Shop development team
 */
public class Account {

    private int accountId;
    private String username;
    private String fullName;
    private String email;
    private Role role;
    private boolean loggedIn;
    private Timestamp lastLoginAt;
    private Timestamp registeredAt;

    /**
     * @param accountId    primary key from the database
     * @param username     login name
     * @param fullName     the person's full name
     * @param email        email address, may be null
     * @param role         Manager or Cashier
     * @param loggedIn     true when the account is signed in right now
     * @param lastLoginAt  time of the most recent login, may be null
     * @param registeredAt time the account was registered
     */
    public Account(int accountId, String username, String fullName, String email,
            Role role, boolean loggedIn, Timestamp lastLoginAt,
            Timestamp registeredAt) {

        this.accountId = accountId;
        this.username = username;
        this.fullName = fullName;
        this.email = email;
        this.role = role;
        this.loggedIn = loggedIn;
        this.lastLoginAt = lastLoginAt;
        this.registeredAt = registeredAt;
    }

    public int getAccountId() {
        return accountId;
    }

    public String getUsername() {
        return username;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return (email == null) ? "" : email;
    }

    public Role getRole() {
        return role;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public Timestamp getLastLoginAt() {
        return lastLoginAt;
    }

    public Timestamp getRegisteredAt() {
        return registeredAt;
    }

    /**
     * @return "Logged in" or "Logged out", ready to show in a table
     */
    public String getStatusText() {
        return loggedIn ? "Logged in" : "Logged out";
    }

    @Override
    public String toString() {
        return username + " (" + role.getDisplayName() + ") - " + getStatusText();
    }
}
