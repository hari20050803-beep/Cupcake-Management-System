/*
 * The Sweet Cupcake Shop  -  CSE4006 Object Oriented Programming (WRIT1)
 *
 * File    : Validator.java
 * Package : com.mycompany.oop.util
 */

package com.mycompany.oop.util;

import com.mycompany.oop.exception.ValidationException;
import com.mycompany.oop.model.Role;
import com.mycompany.oop.model.User;

/**
 * Checks the raw text that arrives from the Swing text fields before it is
 * handed to the model classes.
 *
 * The model classes validate their own values as well. This class exists so
 * that screen level problems (an empty box, a price that is not a number, two
 * passwords that do not match) can be reported with a friendly message before
 * an object is even created.
 *
 * @author The Sweet Cupcake Shop development team
 */
public final class Validator {

    private Validator() {
    }

    /**
     * @param value     text typed by the user
     * @param fieldName label of the field, used in the error message
     * @return the trimmed text
     * @throws ValidationException when the field was left blank
     */
    public static String requireText(String value, String fieldName)
            throws ValidationException {
        if (value == null || value.trim().isEmpty()) {
            throw new ValidationException(fieldName + " is required.");
        }
        return value.trim();
    }

    /**
     * Converts the text of the price field into a number.
     *
     * @param value text typed by the user
     * @return the price as a double
     * @throws ValidationException when the text is blank, not a number or not
     *                             a sensible price
     */
    public static double requirePrice(String value) throws ValidationException {
        String text = requireText(value, "Price");
        double price;
        try {
            price = Double.parseDouble(text);
        } catch (NumberFormatException e) {
            // The number the user typed is not valid, so the low level
            // NumberFormatException is translated into a message that makes
            // sense to a cashier.
            throw new ValidationException("Price must be a number, for example 250.00");
        }
        if (price <= 0) {
            throw new ValidationException("Price must be greater than zero.");
        }
        return price;
    }

    /**
     * Applies the password rules of the shop.
     *
     * @param password the new password
     * @param confirm  the repeated password
     * @throws ValidationException when the password is too short, contains a
     *                             space, or the two entries do not match
     */
    public static void requireValidPassword(String password, String confirm)
            throws ValidationException {
        String value = requireText(password, "Password");
        if (value.length() < User.MIN_PASSWORD_LENGTH) {
            throw new ValidationException("Password must be at least "
                    + User.MIN_PASSWORD_LENGTH + " characters long.");
        }
        if (value.indexOf(' ') >= 0) {
            throw new ValidationException("Password cannot contain spaces.");
        }
        if (!value.equals(confirm)) {
            throw new ValidationException("Password and confirmation do not match.");
        }
    }

    /**
     * Checks what was typed in the first box of the login screen for the user
     * level chosen: a Manager signs in with an email address, a Cashier with a
     * username. Both the MySQL check and the file check use this, so the two
     * can never disagree about what a valid entry looks like.
     *
     * @param role    the user level chosen on the login screen
     * @param loginId the email address or username typed
     * @return the trimmed entry
     * @throws ValidationException when no level is chosen, or the entry is
     *         empty or in the wrong format for that level
     */
    public static String requireLoginId(Role role, String loginId)
            throws ValidationException {
        if (role == null) {
            throw new ValidationException("Choose Manager or Cashier first.");
        }
        if (role == Role.MANAGER) {
            String email = requireText(loginId, "Email");
            if (!User.isValidEmail(email)) {
                throw new ValidationException(
                        "Enter a valid email, for example manager@sweetcupcake.lk");
            }
            return email;
        }
        String username = requireText(loginId, "Username");
        if (!User.isValidUsername(username)) {
            throw new ValidationException("A username has letters only, for example kasun");
        }
        return username;
    }
}
