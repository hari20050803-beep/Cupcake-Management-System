/*
 * The Sweet Cupcake Shop  -  CSE4006 Object Oriented Programming (WRIT1)
 *
 * File    : AuthService.java
 * Package : com.mycompany.oop.service
 */

package com.mycompany.oop.service;

import com.mycompany.oop.exception.AuthenticationException;
import com.mycompany.oop.exception.ShopException;
import com.mycompany.oop.exception.ValidationException;
import com.mycompany.oop.model.Role;
import com.mycompany.oop.model.User;
import com.mycompany.oop.util.PasswordUtil;
import com.mycompany.oop.util.Validator;
import java.io.IOException;
import java.util.List;

/**
 * Checks the username and password typed at the login screen.
 *
 * The service is given a DataStore of users rather than a UserFileStore. It
 * therefore depends only on the abstraction, and would keep working unchanged if
 * the accounts were later moved out of a text file.
 *
 * @author The Sweet Cupcake Shop development team
 */
public class AuthService {

    private final DataStore<User> userStore;

    /**
     * @param userStore any storage that can supply the staff accounts
     */
    public AuthService(DataStore<User> userStore) {
        this.userStore = userStore;
    }

    /**
     * Attempts to sign a member of staff in.
     *
     * The accounts are read from the file, the typed password is hashed, and the
     * hash is compared with the stored hash. The object that is returned is a
     * Manager or a Cashier, but it is handed back as a User so that the
     * dashboard can work with either one.
     *
     * @param username the name typed at the login screen
     * @param password the password typed at the login screen
     * @return the signed-in user
     * @throws ValidationException     when a field was left empty
     * @throws AuthenticationException when the credentials are wrong
     * @throws IOException             when the accounts file cannot be read
     * @throws ShopException           when the accounts file holds a damaged
     *                                 record
     */
    public User login(String username, String password)
            throws IOException, ShopException {

        String name = Validator.requireText(username, "Username");
        String pass = Validator.requireText(password, "Password");

        List<User> users = userStore.loadAll();

        for (User user : users) {
            if (user.getUsername().equalsIgnoreCase(name)
                    && PasswordUtil.matches(pass, user.getPasswordHash())) {
                return user;
            }
        }

        // One single message for both a wrong username and a wrong password, so
        // that the screen never reveals which usernames exist.
        throw new AuthenticationException("Invalid username or password.");
    }

    /**
     * Signs a member of staff in from the staff file at the user level chosen
     * on the login screen. The login screen uses this only when MySQL is not
     * running, so the shop can still work; it applies exactly the same rules
     * as the database check: a Manager by email, a Cashier by username, and
     * the account must be at the chosen level.
     *
     * @param role     the user level chosen on the login screen
     * @param loginId  email address (Manager) or username (Cashier)
     * @param password the password typed
     * @return the signed-in user
     * @throws ValidationException     when a box is empty or in the wrong format
     * @throws AuthenticationException when no account at that level matches
     * @throws IOException             when the accounts file cannot be read
     * @throws ShopException           when the accounts file holds a damaged
     *                                 record
     */
    public User login(Role role, String loginId, String password)
            throws IOException, ShopException {

        String id = Validator.requireLoginId(role, loginId);
        String pass = Validator.requireText(password, "Password");

        for (User user : userStore.loadAll()) {
            String key = (role == Role.MANAGER) ? user.getEmail() : user.getUsername();
            if (user.getRole() == role && key.equalsIgnoreCase(id)
                    && PasswordUtil.matches(pass, user.getPasswordHash())) {
                return user;
            }
        }
        throw new AuthenticationException(role == Role.MANAGER
                ? "Invalid email or password for a Manager account."
                : "Invalid username or password for a Cashier account.");
    }
}
