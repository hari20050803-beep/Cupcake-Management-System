/*
 * The Sweet Cupcake Shop  -  CSE4006 Object Oriented Programming (WRIT1)
 *
 * File    : Role.java
 * Package : com.mycompany.oop.model
 */

package com.mycompany.oop.model;

/**
 * The two user levels supported by The Sweet Cupcake Shop system.
 *
 * An enum is used instead of plain text so that an invalid role can never be
 * stored in a User object, and so that the role can be written to / read from
 * the users text file as a fixed, predictable keyword.
 *
 * @author The Sweet Cupcake Shop development team
 */
public enum Role {

    MANAGER("Manager"),
    CASHIER("Cashier");

    /** Human readable label shown on screen. */
    private final String displayName;

    /**
     * Enum constructors are always private; each constant is created once when
     * the enum class is loaded.
     *
     * @param displayName label shown in the user interface
     */
    private Role(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    /**
     * Converts a stored keyword (for example "MANAGER") back into a Role.
     *
     * @param text keyword read from the data file
     * @return the matching Role, or null when the keyword is not recognised
     */
    public static Role fromText(String text) {
        if (text == null) {
            return null;
        }
        String cleaned = text.trim().toUpperCase();
        for (Role role : Role.values()) {
            if (role.name().equals(cleaned)) {
                return role;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
