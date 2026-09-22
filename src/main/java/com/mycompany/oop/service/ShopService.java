/*
 * The Sweet Cupcake Shop  -  CSE4006 Object Oriented Programming (WRIT1)
 *
 * File    : ShopService.java
 * Package : com.mycompany.oop.service
 */

package com.mycompany.oop.service;

import com.mycompany.oop.exception.AccessDeniedException;
import com.mycompany.oop.exception.ShopException;
import com.mycompany.oop.exception.ValidationException;
import com.mycompany.oop.model.Cashier;
import com.mycompany.oop.model.Category;
import com.mycompany.oop.model.Cupcake;
import com.mycompany.oop.model.Manager;
import com.mycompany.oop.model.Role;
import com.mycompany.oop.model.User;
import com.mycompany.oop.util.PasswordUtil;
import com.mycompany.oop.util.Validator;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * All of the business rules of The Sweet Cupcake Shop in one place.
 *
 * The Swing screens never touch a file and never create a Cupcake themselves;
 * they call this service, which decides what is allowed, builds the objects and
 * asks the stores to save them. Because the rules live here and not in the
 * screens, exactly the same rule applies however the action is started.
 *
 * Both stores are held as DataStore fields, so this class is written against
 * the interface and not against the text file implementation.
 *
 * @author The Sweet Cupcake Shop development team
 */
public class ShopService {

    private final DataStore<Cupcake> cupcakeStore;
    private final DataStore<User> userStore;

    /**
     * @param cupcakeStore storage for the products
     * @param userStore    storage for the staff accounts
     */
    public ShopService(DataStore<Cupcake> cupcakeStore, DataStore<User> userStore) {
        this.cupcakeStore = cupcakeStore;
        this.userStore = userStore;
    }

    /* ==================================================================
     * Viewing products
     * ================================================================== */

    /**
     * Reads every product from the data file.
     *
     * @return all products currently saved
     * @throws IOException   when the file cannot be read
     * @throws ShopException when the file holds a damaged record
     */
    public List<Cupcake> getAllCupcakes() throws IOException, ShopException {
        return cupcakeStore.loadAll();
    }

    /* ==================================================================
     * Searching products - three overloaded methods
     * ================================================================== */

    /**
     * Searches by category only.
     *
     * This is the first of three methods that share the name searchCupcakes.
     * Java decides which one to run from the arguments supplied, which is
     * compile time polymorphism (method overloading).
     *
     * @param category the category to filter by, or null for every category
     * @return the matching products
     * @throws IOException   when the file cannot be read
     * @throws ShopException when the file holds a damaged record
     */
    public List<Cupcake> searchCupcakes(Category category)
            throws IOException, ShopException {
        return searchCupcakes(category, null);
    }

    /**
     * Searches by keyword only.
     *
     * @param keyword text to look for in the id, name, category or description
     * @return the matching products
     * @throws IOException   when the file cannot be read
     * @throws ShopException when the file holds a damaged record
     */
    public List<Cupcake> searchCupcakes(String keyword)
            throws IOException, ShopException {
        return searchCupcakes(null, keyword);
    }

    /**
     * Searches by category and keyword together. The other two versions both
     * call this one, so the filtering rule is written only once.
     *
     * @param category the category to filter by, or null for every category
     * @param keyword  text to look for, or null to ignore the keyword
     * @return the matching products
     * @throws IOException   when the file cannot be read
     * @throws ShopException when the file holds a damaged record
     */
    public List<Cupcake> searchCupcakes(Category category, String keyword)
            throws IOException, ShopException {

        List<Cupcake> results = new ArrayList<Cupcake>();

        for (Cupcake cupcake : cupcakeStore.loadAll()) {
            boolean categoryMatches = (category == null)
                    || cupcake.getCategory() == category;

            // matchesKeyword() belongs to the Cupcake object itself, so the
            // product decides whether it matches.
            boolean keywordMatches = cupcake.matchesKeyword(keyword);

            if (categoryMatches && keywordMatches) {
                results.add(cupcake);
            }
        }
        return results;
    }

    /* ==================================================================
     * Adding a product
     * ================================================================== */

    /**
     * Adds a new product and saves it to the data file straight away.
     *
     * @param cupcakeId   unique identifier
     * @param name        flavour or product name
     * @param category    the category it belongs to
     * @param price       selling price
     * @param description short description, may be empty
     * @param available   true when the item is in stock
     * @return the product that was created and saved
     * @throws IOException   when the file cannot be written
     * @throws ShopException when a value is invalid or the id is already used
     */
    public Cupcake addCupcake(String cupcakeId, String name, Category category,
            double price, String description, boolean available)
            throws IOException, ShopException {

        String id = (cupcakeId == null) ? "" : cupcakeId.trim().toUpperCase();

        // No two products may share an identifier.
        for (Cupcake existing : cupcakeStore.loadAll()) {
            if (existing.getCupcakeId().equalsIgnoreCase(id)) {
                throw new ValidationException("Cupcake ID " + id
                        + " is already used by " + existing.getName() + ".");
            }
        }

        // The Cupcake constructor validates every value, so an invalid product
        // is never created and never reaches the file.
        Cupcake cupcake = new Cupcake(id, name, category, price, description, available);
        cupcakeStore.add(cupcake);
        return cupcake;
    }

    /* ==================================================================
     * Updating and removing a product
     * ================================================================== */

    /**
     * Changes the details of a product that is already saved.
     *
     * The whole list is read, the matching product is replaced, and the list is
     * written back with saveAll(). Writing the complete file is the simplest
     * correct way to change one line of a text file, because a line that gets
     * longer or shorter would otherwise overwrite the line after it.
     *
     * @param cupcakeId   identifier of the product to change
     * @param name        new flavour or product name
     * @param category    new category
     * @param price       new selling price
     * @param description new description
     * @param available   whether the item is in stock
     * @return the updated product
     * @throws IOException   when the file cannot be written
     * @throws ShopException when a value is invalid or the id does not exist
     */
    public Cupcake updateCupcake(String cupcakeId, String name, Category category,
            double price, String description, boolean available)
            throws IOException, ShopException {

        String id = (cupcakeId == null) ? "" : cupcakeId.trim().toUpperCase();
        List<Cupcake> all = cupcakeStore.loadAll();

        Cupcake updated = null;
        for (int i = 0; i < all.size(); i++) {
            if (all.get(i).getCupcakeId().equalsIgnoreCase(id)) {
                // The constructor validates every value, so an invalid change
                // is rejected before anything is written.
                updated = new Cupcake(id, name, category, price, description, available);
                all.set(i, updated);
                break;
            }
        }

        if (updated == null) {
            throw new ValidationException("No cupcake with the ID " + id + " was found.");
        }

        cupcakeStore.saveAll(all);
        return updated;
    }

    /**
     * Removes a product from the catalogue.
     *
     * @param cupcakeId identifier of the product to remove
     * @return the product that was removed
     * @throws IOException   when the file cannot be written
     * @throws ShopException when the id does not exist
     */
    public Cupcake deleteCupcake(String cupcakeId) throws IOException, ShopException {
        String id = (cupcakeId == null) ? "" : cupcakeId.trim().toUpperCase();
        List<Cupcake> all = cupcakeStore.loadAll();

        Cupcake removed = null;
        for (int i = 0; i < all.size(); i++) {
            if (all.get(i).getCupcakeId().equalsIgnoreCase(id)) {
                removed = all.remove(i);
                break;
            }
        }

        if (removed == null) {
            throw new ValidationException("No cupcake with the ID " + id + " was found.");
        }

        cupcakeStore.saveAll(all);
        return removed;
    }

    /**
     * Finds one product by its identifier.
     *
     * @param cupcakeId the identifier to look for
     * @return the product, or null when there is no such product
     * @throws IOException   when the file cannot be read
     * @throws ShopException when the file holds a damaged record
     */
    public Cupcake findCupcake(String cupcakeId) throws IOException, ShopException {
        if (cupcakeId == null) {
            return null;
        }
        for (Cupcake cupcake : cupcakeStore.loadAll()) {
            if (cupcake.getCupcakeId().equalsIgnoreCase(cupcakeId.trim())) {
                return cupcake;
            }
        }
        return null;
    }

    /**
     * Suggests the next free product identifier, for example CK009 when CK008
     * is the highest one saved. The Add screen fills this in automatically so a
     * cashier cannot create a duplicate by accident.
     *
     * @return the next identifier in the CKnnn series
     * @throws IOException   when the file cannot be read
     * @throws ShopException when the file holds a damaged record
     */
    public String suggestNextCupcakeId() throws IOException, ShopException {
        int highest = 0;
        for (Cupcake cupcake : cupcakeStore.loadAll()) {
            String id = cupcake.getCupcakeId();
            if (id.length() > 2 && id.startsWith("CK")) {
                try {
                    int number = Integer.parseInt(id.substring(2));
                    if (number > highest) {
                        highest = number;
                    }
                } catch (NumberFormatException e) {
                    // An identifier that does not follow the CKnnn pattern is
                    // simply ignored when working out the next number.
                }
            }
        }
        return String.format("CK%03d", highest + 1);
    }

    /* ==================================================================
     * Managing staff accounts - Manager only
     * ================================================================== */

    /**
     * Creates a new cashier account.
     *
     * The first thing this method does is ask the signed-in user whether their
     * user level allows account creation. A Manager answers true and a Cashier
     * answers false, so a cashier is refused here even if the button were
     * somehow reached. This is the security check that enforces the two user
     * levels of the scenario.
     *
     * @param requester     the user who is signed in
     * @param username      login name for the new cashier
     * @param email         work email address the Manager is giving them
     * @param plainPassword password for the new cashier, hashed before saving
     * @return the new Cashier account
     * @throws AccessDeniedException when the requester is not a Manager
     * @throws IOException           when the file cannot be written
     * @throws ShopException         when a value is invalid or the username is
     *                               taken
     */
    public Cashier createCashierAccount(User requester, String username,
            String email, String plainPassword) throws IOException, ShopException {

        return (Cashier) createStaffAccount(requester, Role.CASHIER, username,
                email, plainPassword);
    }

    /**
     * Creates a new staff account, at either user level, in the staff file.
     *
     * As with a cashier account, the signed-in user is asked first whether
     * their level may create accounts, so only a Manager gets any further.
     * The password rule is checked here as well as on the screens, so no
     * caller can get round it, and the username and email formats are
     * enforced by the User setters when the object is built.
     *
     * @param requester     the user who is signed in
     * @param role          the user level of the new account
     * @param username      login name: letters only
     * @param email         work email address
     * @param plainPassword password, hashed before saving
     * @return the new Manager or Cashier, held as a User
     * @throws AccessDeniedException when the requester is not a Manager
     * @throws IOException           when the file cannot be written
     * @throws ShopException         when a value is invalid or already taken
     */
    public User createStaffAccount(User requester, Role role, String username,
            String email, String plainPassword) throws IOException, ShopException {

        if (requester == null || !requester.canManageUsers()) {
            throw new AccessDeniedException(
                    "Only a Manager is allowed to create user accounts.");
        }
        if (role == null) {
            throw new ValidationException("Choose a user level: Manager or Cashier.");
        }
        Validator.requireValidPassword(plainPassword, plainPassword);

        String name = (username == null) ? "" : username.trim();
        String address = (email == null) ? "" : email.trim();

        // The username and the email must both stay unique: two people could
        // otherwise log in with the same name, or be contacted at the same
        // address without it being clear which account is meant.
        for (User existing : userStore.loadAll()) {
            if (existing.getUsername().equalsIgnoreCase(name)) {
                throw new ValidationException("The username " + name
                        + " is already taken. Please choose another one.");
            }
            if (existing.getEmail().equalsIgnoreCase(address)) {
                throw new ValidationException("The email " + address
                        + " already belongs to " + existing.getUsername() + ".");
            }
        }

        String userId = suggestNextUserId();
        String hash = PasswordUtil.hash(plainPassword);

        // The variable is a User; the object is whichever subclass the chosen
        // level calls for.
        User account = (role == Role.MANAGER)
                ? new Manager(userId, name, address, hash)
                : new Cashier(userId, name, address, hash);
        userStore.add(account);
        return account;
    }

    /**
     * Reads every staff account from the data file.
     *
     * @param requester the user who is signed in
     * @return all saved accounts
     * @throws AccessDeniedException when the requester is not a Manager
     * @throws IOException           when the file cannot be read
     * @throws ShopException         when the file holds a damaged record
     */
    public List<User> getAllUsers(User requester) throws IOException, ShopException {
        if (requester == null || !requester.canManageUsers()) {
            throw new AccessDeniedException(
                    "Only a Manager is allowed to view the user accounts.");
        }
        return userStore.loadAll();
    }

    /**
     * Removes a staff account.
     *
     * A Manager may not delete their own account, and the shop must always keep
     * at least one Manager, otherwise nobody could ever create accounts again.
     *
     * @param requester the user who is signed in
     * @param userId    identifier of the account to remove
     * @return the account that was removed
     * @throws AccessDeniedException when the requester is not a Manager
     * @throws IOException           when the file cannot be written
     * @throws ShopException         when the account cannot be removed
     */
    public User deleteUser(User requester, String userId)
            throws IOException, ShopException {

        if (requester == null || !requester.canManageUsers()) {
            throw new AccessDeniedException(
                    "Only a Manager is allowed to remove user accounts.");
        }

        List<User> all = userStore.loadAll();
        User target = null;
        for (User user : all) {
            if (user.getUserId().equalsIgnoreCase(userId)) {
                target = user;
            }
        }

        if (target == null) {
            throw new ValidationException("No account with the ID " + userId + " was found.");
        }
        if (target.getUsername().equalsIgnoreCase(requester.getUsername())) {
            throw new ValidationException("You cannot remove the account you are signed in with.");
        }

        // Count the managers that would be left behind.
        int managersLeft = 0;
        for (User user : all) {
            if (user.canManageUsers() && !user.getUserId().equalsIgnoreCase(userId)) {
                managersLeft++;
            }
        }
        if (managersLeft == 0) {
            throw new ValidationException(
                    "The shop must keep at least one Manager account.");
        }

        all.remove(target);
        userStore.saveAll(all);
        return target;
    }

    /**
     * @return the next identifier in the Unnn series
     * @throws IOException   when the file cannot be read
     * @throws ShopException when the file holds a damaged record
     */
    public String suggestNextUserId() throws IOException, ShopException {
        int highest = 0;
        for (User user : userStore.loadAll()) {
            String id = user.getUserId();
            if (id.length() > 1 && id.startsWith("U")) {
                try {
                    int number = Integer.parseInt(id.substring(1));
                    if (number > highest) {
                        highest = number;
                    }
                } catch (NumberFormatException e) {
                    // Ignore identifiers that do not follow the Unnn pattern.
                }
            }
        }
        return String.format("U%03d", highest + 1);
    }

    /* ==================================================================
     * Small summary used by the dashboard
     * ================================================================== */

    /**
     * Counts how many products are saved in each category.
     *
     * @return one line of text per category that currently has products
     * @throws IOException   when the file cannot be read
     * @throws ShopException when the file holds a damaged record
     */
    public String getCategorySummary() throws IOException, ShopException {
        List<Cupcake> all = cupcakeStore.loadAll();
        StringBuilder builder = new StringBuilder();

        for (Category category : Category.values()) {
            int count = 0;
            for (Cupcake cupcake : all) {
                if (cupcake.getCategory() == category) {
                    count++;
                }
            }
            if (count > 0) {
                if (builder.length() > 0) {
                    builder.append("    ");
                }
                builder.append(category.getDisplayName()).append(": ").append(count);
            }
        }
        return builder.toString();
    }
}
