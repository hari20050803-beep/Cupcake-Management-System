/*
 * The Sweet Cupcake Shop  -  CSE4006 Object Oriented Programming (WRIT1)
 *
 * File    : DatabaseService.java
 * Package : com.mycompany.oop.db
 */

package com.mycompany.oop.db;

import com.mycompany.oop.exception.AuthenticationException;
import com.mycompany.oop.exception.ShopException;
import com.mycompany.oop.exception.ValidationException;
import com.mycompany.oop.model.Account;
import com.mycompany.oop.model.Cashier;
import com.mycompany.oop.model.Cupcake;
import com.mycompany.oop.model.Manager;
import com.mycompany.oop.model.Role;
import com.mycompany.oop.model.User;
import com.mycompany.oop.util.Validator;
import java.sql.SQLException;
import java.util.List;

/**
 * Coordinates the MySQL database on WampServer: staff sign-in, the staff
 * registry, the login history and the copy of the catalogue.
 *
 * This class sits between the screens and the three DAO classes. The
 * application must keep working whether MySQL is running or not, so no
 * SQLException escapes into the user interface: login() turns a database
 * problem into a ShopException with a readable message, and the other methods
 * report one by returning false or an empty result.
 *
 * It also remembers which session row belongs to the person who is signed in,
 * so that the logout can be written against the right row.
 *
 * @author The Sweet Cupcake Shop development team
 */
public class DatabaseService {

    private final Database database;
    private final AccountDao accountDao;
    private final SessionDao sessionDao;
    private final CupcakeDao cupcakeDao;

    private boolean available;
    private String status;

    private Account currentAccount;
    private int currentSessionId = -1;

    /**
     * @param database the connection provider
     */
    public DatabaseService(Database database) {
        this.database = database;
        this.accountDao = new AccountDao(database);
        this.sessionDao = new SessionDao(database);
        this.cupcakeDao = new CupcakeDao(database);
        this.status = "not checked yet";
    }

    /* ------------------------------------------------------------------
     * Start up
     * ------------------------------------------------------------------ */

    /**
     * Connects to MySQL and builds the tables if they are not there yet.
     *
     * @return true when the database is ready to use
     */
    public boolean start() {
        // The server is checked without naming the database, because on the
        // very first run the shop database does not exist yet.
        if (!database.isServerAvailable()) {
            available = false;
            status = "MySQL is not reachable: " + database.getLastError();
            return false;
        }

        // Creates the database and the three tables when they are missing.
        if (!database.initialiseSchema()) {
            available = false;
            status = "MySQL is running but the tables could not be created: "
                    + database.getLastError();
            return false;
        }

        // Finally confirm the shop database itself can be opened.
        if (!database.isAvailable()) {
            available = false;
            status = "The shop database could not be opened: "
                    + database.getLastError();
            return false;
        }

        available = true;
        status = database.describeServer();
        return true;
    }

    /**
     * @return true when the database features can be used
     */
    public boolean isAvailable() {
        return available;
    }

    /**
     * @return a short message describing the state of the database connection
     */
    public String getStatus() {
        return status;
    }

    public AccountDao getAccountDao() {
        return accountDao;
    }

    public SessionDao getSessionDao() {
        return sessionDao;
    }

    public CupcakeDao getCupcakeDao() {
        return cupcakeDao;
    }

    public Account getCurrentAccount() {
        return currentAccount;
    }

    /* ------------------------------------------------------------------
     * Login and logout tracking
     * ------------------------------------------------------------------ */

    /**
     * Records that a member of staff has signed in.
     *
     * When the person exists in the text file but not yet in the database, an
     * account row is created for them automatically, using the password they
     * have just typed. From then on that account can also be verified against
     * MySQL directly.
     *
     * @param user          the user who signed in, as loaded from the text file
     * @param plainPassword the password they typed, used only if the account has
     *                      to be created
     * @return true when the login was recorded
     */
    public boolean recordLogin(User user, String plainPassword) {
        if (!available || user == null) {
            return false;
        }
        try {
            Account account = accountDao.findByUsername(user.getUsername());

            if (account == null) {
                // The email now comes from the account itself rather than
                // being left empty, so the registry screen shows the same
                // address the Manager typed when the account was created.
                account = accountDao.register(user.getUsername(),
                        user.getUsername(), user.getEmail(), user.getRole(),
                        plainPassword);
            }
            if (account == null) {
                return false;
            }

            // Tidy up anything left open by a previous crash or forced close.
            sessionDao.closeOpenSessions(account.getAccountId());

            accountDao.markLoggedIn(account.getAccountId());
            currentSessionId = sessionDao.startSession(account.getAccountId(),
                    account.getUsername());
            currentAccount = accountDao.findById(account.getAccountId());
            return true;

        } catch (SQLException e) {
            status = "Login could not be recorded: " + e.getMessage();
            return false;
        } catch (com.mycompany.oop.exception.ValidationException e) {
            status = "Account could not be created: " + e.getMessage();
            return false;
        }
    }

    /**
     * Records that the member of staff who is signed in has logged out.
     *
     * @return true when the logout was recorded
     */
    public boolean recordLogout() {
        if (!available || currentAccount == null) {
            return false;
        }
        try {
            if (currentSessionId > 0) {
                sessionDao.endSession(currentSessionId);
            }
            accountDao.markLoggedOut(currentAccount.getAccountId());
            currentAccount = null;
            currentSessionId = -1;
            return true;

        } catch (SQLException e) {
            status = "Logout could not be recorded: " + e.getMessage();
            return false;
        }
    }

    /**
     * Checks a username and password against the MySQL account table.
     *
     * @param username login name
     * @param password password
     * @return the matching account, or null when the details are wrong or the
     *         database is unavailable
     */
    public Account authenticate(String username, String password) {
        if (!available) {
            return null;
        }
        try {
            return accountDao.authenticate(username, password);
        } catch (SQLException e) {
            status = "Database login failed: " + e.getMessage();
            return null;
        }
    }

    /**
     * Signs a member of staff in against the MySQL app_user table, at the user
     * level chosen on the login screen: a Manager by email address, a Cashier
     * by username. The session is recorded, and the account comes back as a
     * Manager or a Cashier object, so the rest of the program treats it
     * exactly like any other user.
     *
     * @param role     the user level chosen on the login screen
     * @param loginId  email address (Manager) or username (Cashier)
     * @param password the password typed
     * @return the signed-in Manager or Cashier
     * @throws ValidationException     when a box is empty or in the wrong format
     * @throws AuthenticationException when no account at that level matches
     * @throws ShopException           when MySQL cannot be reached or read
     */
    public User login(Role role, String loginId, String password) throws ShopException {
        String id = Validator.requireLoginId(role, loginId);
        String pass = Validator.requireText(password, "Password");
        if (!available) {
            throw new ShopException("MySQL is not running. Start WampServer and try again.");
        }

        Account account;
        try {
            account = accountDao.authenticate(role, id, pass);
        } catch (SQLException e) {
            status = "Database login failed: " + e.getMessage();
            throw new ShopException("The staff database could not be read: " + e.getMessage());
        }
        if (account == null) {
            // One message whichever part was wrong, so the screen never
            // reveals which emails or usernames exist.
            throw new AuthenticationException(role == Role.MANAGER
                    ? "Invalid email or password for a Manager account."
                    : "Invalid username or password for a Cashier account.");
        }

        recordLogin(account);

        // The row from the database becomes the matching subclass.
        String email = (account.getEmail() == null || account.getEmail().trim().isEmpty())
                ? account.getUsername() + "@sweetcupcake.lk"
                : account.getEmail();
        String userId = String.valueOf(account.getAccountId());
        if (account.getRole() == Role.MANAGER) {
            return new Manager(userId, account.getUsername(), email, "verified-by-database");
        }
        return new Cashier(userId, account.getUsername(), email, "verified-by-database");
    }

    /**
     * Copies into MySQL every account in the staff file that the database
     * does not have yet, keeping its password hash, so each one can sign in
     * against the database straight away. Run once at start-up.
     *
     * @param users the accounts read from the staff file
     * @return how many accounts were copied
     */
    public int importAccounts(List<User> users) {
        if (!available || users == null) {
            return 0;
        }
        int copied = 0;
        for (User user : users) {
            try {
                if (accountDao.findByUsername(user.getUsername()) == null) {
                    accountDao.importAccount(user.getUsername(), user.getEmail(),
                            user.getRole(), user.getPasswordHash());
                    copied++;
                }
            } catch (SQLException e) {
                status = "An account could not be copied into MySQL: " + e.getMessage();
            }
        }
        return copied;
    }

    /**
     * Begins a tracked session for somebody who was verified by the database
     * rather than by the text file.
     *
     * @param account the account that has just been verified
     * @return true when the login was recorded
     */
    public boolean recordLogin(Account account) {
        if (!available || account == null) {
            return false;
        }
        try {
            sessionDao.closeOpenSessions(account.getAccountId());
            accountDao.markLoggedIn(account.getAccountId());
            currentSessionId = sessionDao.startSession(account.getAccountId(),
                    account.getUsername());
            currentAccount = accountDao.findById(account.getAccountId());
            return true;
        } catch (SQLException e) {
            status = "Login could not be recorded: " + e.getMessage();
            return false;
        }
    }

    /* ------------------------------------------------------------------
     * Registration and reporting
     * ------------------------------------------------------------------ */

    /**
     * Registers a new account in the database.
     *
     * @param username login name
     * @param fullName the person's full name
     * @param email    email address, may be empty
     * @param role     Manager or Cashier
     * @param password the chosen password
     * @return the account created
     * @throws SQLException                          when the write fails
     * @throws com.mycompany.oop.exception.ValidationException when the name is taken
     */
    public Account register(String username, String fullName, String email,
            Role role, String password)
            throws SQLException, com.mycompany.oop.exception.ValidationException {
        return accountDao.register(username, fullName, email, role, password);
    }

    /**
     * Adds a member of staff who has just been created in the staff file to
     * the MySQL registry, so they can sign in against the database at once.
     * A problem here never undoes the account in the file: the account is
     * copied into MySQL the next time the program starts with it running.
     *
     * @param user     the account just created in the staff file
     * @param fullName the person's full name for the registry
     * @param password the password the Manager set, salted and hashed here
     * @return one sentence for the confirmation message
     */
    public String addToRegistry(User user, String fullName, String password) {
        if (!available) {
            return "MySQL is not running, so the account will be copied into it"
                    + " the next time the program starts with WampServer running.";
        }
        try {
            accountDao.register(user.getUsername(), fullName, user.getEmail(),
                    user.getRole(), password);
            return "Also saved in the MySQL database, where sign-in is checked.";
        } catch (SQLException | ValidationException e) {
            status = "Registry not updated: " + e.getMessage();
            return "Not saved in MySQL (" + e.getMessage() + ").";
        }
    }

    /**
     * Copies the product catalogue from the text file into MySQL.
     *
     * @param cupcakes the products read from the file
     * @param syncedBy username of the person doing the sync
     * @return the number of rows written, or -1 when the sync failed
     */
    public int syncCupcakes(List<Cupcake> cupcakes, String syncedBy) {
        if (!available) {
            return -1;
        }
        try {
            return cupcakeDao.saveAll(cupcakes, syncedBy);
        } catch (SQLException e) {
            status = "Cupcake sync failed: " + e.getMessage();
            return -1;
        }
    }

    /**
     * @return how many accounts are signed in right now, or 0 when unknown
     */
    public int countLoggedIn() {
        if (!available) {
            return 0;
        }
        try {
            return accountDao.countLoggedIn();
        } catch (SQLException e) {
            return 0;
        }
    }
}
