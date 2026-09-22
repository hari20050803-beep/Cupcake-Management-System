/*
 * The Sweet Cupcake Shop  -  CSE4006 Object Oriented Programming (WRIT1)
 *
 * File    : User.java
 * Package : com.mycompany.oop.model
 */

package com.mycompany.oop.model;

import com.mycompany.oop.exception.ValidationException;
import java.util.regex.Pattern;

/**
 * Abstraction of a person who is allowed to sign in to the Sweet Cupcake Shop
 * system.
 *
 * The class is declared abstract because the shop never employs a plain "user":
 * every account is either a Manager or a Cashier. Creating a User object
 * directly is therefore meaningless, and the compiler prevents it.
 *
 * All four attributes are private (encapsulation). They can only be changed
 * through the setters, which validate the new value first, so a User object can
 * never hold an empty username or a missing role.
 *
 * The three abstract methods describe WHAT every user level must be able to
 * answer about itself, without saying HOW. Manager and Cashier each supply their
 * own answer, which is where polymorphism comes from.
 *
 * @author The Sweet Cupcake Shop development team
 */
public abstract class User {

    /** Shortest password the system will accept. */
    public static final int MIN_PASSWORD_LENGTH = 6;

    /** Shortest and longest usernames the system will accept. */
    public static final int MIN_USERNAME_LENGTH = 3;
    public static final int MAX_USERNAME_LENGTH = 30;

    /** A username is a person's name, so it is made of letters only. */
    private static final Pattern USERNAME_PATTERN = Pattern.compile("[A-Za-z]+");

    /** name@domain.tld: the usual characters before the @, and a domain after it. */
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "[A-Za-z0-9._%+-]+@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*\\.[A-Za-z]{2,}");

    private String userId;
    private String username;
    private String email;
    private String passwordHash;
    private Role role;

    /**
     * The constructor is protected: only the subclasses (Manager, Cashier) may
     * call it, through the super(...) statement.
     *
     * @param userId       unique identifier such as U001
     * @param username     unique login name
     * @param email        work email address, set by the Manager
     * @param passwordHash SHA-256 hash of the password, never the plain text
     * @param role         the user level this account belongs to
     * @throws ValidationException when any argument is missing or invalid
     */
    protected User(String userId, String username, String email,
            String passwordHash, Role role) throws ValidationException {
        setUserId(userId);
        setUsername(username);
        setEmail(email);
        setPasswordHash(passwordHash);
        setRole(role);
    }

    /* ------------------------------------------------------------------
     * Abstract behaviour - each user level answers these differently
     * ------------------------------------------------------------------ */

    /**
     * States whether this user level is allowed to create new user accounts.
     * This single method is what enforces the difference between the two user
     * levels described in the scenario.
     *
     * @return true for a Manager, false for a Cashier
     */
    public abstract boolean canManageUsers();

    /**
     * @return one line describing what this user level may do, shown on the
     *         dashboard header
     */
    public abstract String getRoleDescription();

    /**
     * @return the menu options this user level is entitled to see
     */
    public abstract String[] getPermissions();

    /* ------------------------------------------------------------------
     * Encapsulated accessors and mutators
     * ------------------------------------------------------------------ */

    public String getUserId() {
        return userId;
    }

    public final void setUserId(String userId) throws ValidationException {
        if (userId == null || userId.trim().isEmpty()) {
            throw new ValidationException("User ID cannot be empty.");
        }
        this.userId = userId.trim();
    }

    public String getUsername() {
        return username;
    }

    /**
     * A username is the member of staff's name, such as kasun: letters only,
     * so it can never contain the pipe that separates the fields in the file.
     *
     * @param username the name a Cashier signs in with
     * @throws ValidationException when it is missing, too short or too long,
     *         or contains anything other than letters
     */
    public final void setUsername(String username) throws ValidationException {
        if (username == null || username.trim().isEmpty()) {
            throw new ValidationException("Username cannot be empty.");
        }
        String cleaned = username.trim();
        if (cleaned.length() < MIN_USERNAME_LENGTH || cleaned.length() > MAX_USERNAME_LENGTH) {
            throw new ValidationException("Username must be " + MIN_USERNAME_LENGTH
                    + " to " + MAX_USERNAME_LENGTH + " letters long.");
        }
        if (!USERNAME_PATTERN.matcher(cleaned).matches()) {
            throw new ValidationException(
                    "Username must be a name: letters only, for example kasun.");
        }
        this.username = cleaned;
    }

    /**
     * @param value text to test
     * @return true when the text is a valid username: 3 to 30 letters
     */
    public static boolean isValidUsername(String value) {
        if (value == null) {
            return false;
        }
        String v = value.trim();
        return v.length() >= MIN_USERNAME_LENGTH && v.length() <= MAX_USERNAME_LENGTH
                && USERNAME_PATTERN.matcher(v).matches();
    }

    /**
     * @param value text to test
     * @return true when the text is in email format, such as name@sweetcupcake.lk
     */
    public static boolean isValidEmail(String value) {
        return value != null && EMAIL_PATTERN.matcher(value.trim()).matches();
    }

    public String getEmail() {
        return email;
    }

    /**
     * The email is given to the member of staff by the Manager who creates the
     * account, so it is validated here in exactly the same way as every other
     * attribute: an invalid address cannot get into the object at all.
     *
     * @param email work email address
     * @throws ValidationException when the address is missing or malformed
     */
    public final void setEmail(String email) throws ValidationException {
        if (email == null || email.trim().isEmpty()) {
            throw new ValidationException("Email cannot be empty.");
        }
        String cleaned = email.trim();
        // The pattern allows no spaces and no pipe, so an address can never
        // break a line of the staff file.
        if (!isValidEmail(cleaned)) {
            throw new ValidationException(
                    "Email must look like a real address, for example name@sweetcupcake.lk");
        }
        this.email = cleaned;
    }

    /**
     * Only the hashed password is ever stored, so the plain text password does
     * not exist inside the object or inside the data file.
     *
     * @return SHA-256 hash of this user's password
     */
    public String getPasswordHash() {
        return passwordHash;
    }

    public final void setPasswordHash(String passwordHash) throws ValidationException {
        if (passwordHash == null || passwordHash.trim().isEmpty()) {
            throw new ValidationException("Password cannot be empty.");
        }
        this.passwordHash = passwordHash.trim();
    }

    public Role getRole() {
        return role;
    }

    public final void setRole(Role role) throws ValidationException {
        if (role == null) {
            throw new ValidationException("User role must be Manager or Cashier.");
        }
        this.role = role;
    }

    /**
     * Overrides the toString() method that every Java class inherits from
     * Object, so that a User can be printed or shown in a list directly.
     */
    @Override
    public String toString() {
        return userId + " - " + username + " (" + role.getDisplayName() + ")";
    }
}
