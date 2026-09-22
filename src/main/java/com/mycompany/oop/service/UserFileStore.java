/*
 * The Sweet Cupcake Shop  -  CSE4006 Object Oriented Programming (WRIT1)
 *
 * File    : UserFileStore.java
 * Package : com.mycompany.oop.service
 */

package com.mycompany.oop.service;

import com.mycompany.oop.exception.ValidationException;
import com.mycompany.oop.model.Cashier;
import com.mycompany.oop.model.Manager;
import com.mycompany.oop.model.Role;
import com.mycompany.oop.model.User;

/**
 * Stores the staff accounts in data/users.txt.
 *
 * All of the file handling is inherited from TextFileStore. This class only
 * describes how one user account is written to a line and read back again.
 *
 * The parse method is the point where the stored role decides which subclass is
 * created: "MANAGER" produces a Manager object and "CASHIER" produces a Cashier
 * object. Both are returned as a User, which is why the rest of the program can
 * treat every account the same way and still get the correct behaviour.
 *
 * @author The Sweet Cupcake Shop development team
 */
public class UserFileStore extends TextFileStore<User> {

    /** Name of the text file that holds the staff accounts. */
    public static final String FILE_NAME = "users.txt";

    /** Number of fields in one user record. */
    private static final int FIELD_COUNT = 5;

    public UserFileStore() {
        super(FILE_NAME);
    }

    /**
     * Rebuilds a User object from one line of the file.
     *
     * @param fields userId, username, email, passwordHash, role
     * @return a Manager or a Cashier, depending on the stored role
     * @throws ValidationException when the record is incomplete or the role is
     *                             not recognised
     */
    @Override
    protected User parse(String[] fields) throws ValidationException {
        if (fields.length < FIELD_COUNT) {
            throw new ValidationException("expected " + FIELD_COUNT
                    + " fields but found " + fields.length);
        }

        String userId = fields[0].trim();
        String username = fields[1].trim();
        String email = fields[2].trim();
        String passwordHash = fields[3].trim();
        Role role = Role.fromText(fields[4]);

        if (role == null) {
            throw new ValidationException("unknown user role '" + fields[4].trim() + "'");
        }

        // The stored role selects the subclass to create. This is the moment a
        // saved line becomes a real object again.
        if (role == Role.MANAGER) {
            return new Manager(userId, username, email, passwordHash);
        }
        return new Cashier(userId, username, email, passwordHash);
    }

    /**
     * Turns a User object into one line of the file.
     *
     * @param user the account to save
     * @return userId|username|email|passwordHash|ROLE
     */
    @Override
    protected String format(User user) {
        return user.getUserId() + SEPARATOR
                + user.getUsername() + SEPARATOR
                + user.getEmail() + SEPARATOR
                + user.getPasswordHash() + SEPARATOR
                + user.getRole().name();
    }

    @Override
    protected String header() {
        return "userId|username|email|passwordHash(SHA-256)|role";
    }
}
