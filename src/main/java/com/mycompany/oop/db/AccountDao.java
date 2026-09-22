/*
 * The Sweet Cupcake Shop  -  CSE4006 Object Oriented Programming (WRIT1)
 *
 * File    : AccountDao.java
 * Package : com.mycompany.oop.db
 */

package com.mycompany.oop.db;

import com.mycompany.oop.exception.ValidationException;
import com.mycompany.oop.model.Account;
import com.mycompany.oop.model.Role;
import com.mycompany.oop.util.PasswordUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * All of the SQL for the app_user table.
 *
 * DAO stands for Data Access Object: a class whose only job is to move one kind
 * of record between the program and the database. Keeping every INSERT, SELECT
 * and UPDATE for accounts inside this one class means the screens never contain
 * SQL, exactly as the file based part of the system keeps all file code inside
 * the store classes.
 *
 * Every statement is a PreparedStatement with ? placeholders rather than text
 * joined together with +. The database then treats the values purely as data, so
 * a username such as  admin' OR '1'='1  cannot change the meaning of the query.
 * This is the standard defence against SQL injection.
 *
 * @author The Sweet Cupcake Shop development team
 */
public class AccountDao {

    private final Database database;

    /**
     * @param database supplies the JDBC connections
     */
    public AccountDao(Database database) {
        this.database = database;
    }

    /**
     * Registers a new account.
     *
     * The password is never stored. A random salt is generated, the salt and the
     * password are hashed together, and only the salt and the hash are saved.
     *
     * @param username      login name, must not already exist
     * @param fullName      the person's full name
     * @param email         email address, may be empty
     * @param role          Manager or Cashier
     * @param plainPassword the chosen password
     * @return the account that was created
     * @throws ValidationException when the username is already registered
     * @throws SQLException        when the database cannot be written
     */
    public Account register(String username, String fullName, String email,
            Role role, String plainPassword) throws SQLException, ValidationException {

        if (findByUsername(username) != null) {
            throw new ValidationException("The username " + username
                    + " is already registered in the database.");
        }
        // A Manager signs in by email, so two accounts may never share one.
        if (email != null && !email.trim().isEmpty() && findByEmail(email) != null) {
            throw new ValidationException("The email " + email.trim()
                    + " already belongs to another account in the database.");
        }

        String salt = PasswordUtil.newSalt();
        String hash = PasswordUtil.hashWithSalt(plainPassword, salt);

        String sql = "INSERT INTO app_user "
                + "(username, full_name, email, role, password_salt, password_hash,"
                + " is_logged_in, registered_at) "
                + "VALUES (?, ?, ?, ?, ?, ?, FALSE, NOW())";

        try (Connection conn = database.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql,
                        Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, username.trim());
            ps.setString(2, fullName.trim());
            ps.setString(3, (email == null || email.trim().isEmpty()) ? null : email.trim());
            ps.setString(4, role.name());
            ps.setString(5, salt);
            ps.setString(6, hash);
            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    return findById(keys.getInt(1));
                }
            }
        }
        return findByUsername(username);
    }

    /**
     * Checks a username and password against the database.
     *
     * The salt stored with the account is read first, the typed password is
     * hashed with that salt, and the two hashes are compared.
     *
     * @param username login name typed by the user
     * @param plainPassword password typed by the user
     * @return the matching account, or null when the details are wrong
     * @throws SQLException when the database cannot be read
     */
    public Account authenticate(String username, String plainPassword)
            throws SQLException {

        String sql = "SELECT account_id, username, full_name, email, role,"
                + " password_salt, password_hash, is_logged_in, last_login_at,"
                + " registered_at FROM app_user WHERE username = ?";

        try (Connection conn = database.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username.trim());
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }
                String salt = rs.getString("password_salt");
                String hash = rs.getString("password_hash");

                if (!PasswordUtil.matchesSalted(plainPassword, salt, hash)) {
                    return null;
                }
                return readRow(rs);
            }
        }
    }

    /**
     * Checks a sign-in at the user level chosen on the login screen: a Manager
     * is looked up by email address and a Cashier by username, and only rows at
     * that level are considered.
     *
     * An account copied in from the staff file arrives with an empty salt; the
     * first time it signs in correctly, it is given a random salt of its own.
     *
     * @param role          the user level chosen on the login screen
     * @param loginId       email address (Manager) or username (Cashier)
     * @param plainPassword the password typed
     * @return the account, or null when nothing matches
     * @throws SQLException when the database cannot be read
     */
    public Account authenticate(Role role, String loginId, String plainPassword)
            throws SQLException {

        // The column comes from a fixed choice, never from anything typed; the
        // typed value itself is always a parameter.
        String column = (role == Role.MANAGER) ? "email" : "username";
        String sql = "SELECT account_id, username, full_name, email, role,"
                + " password_salt, password_hash, is_logged_in, last_login_at,"
                + " registered_at FROM app_user WHERE " + column + " = ? AND role = ?";

        try (Connection conn = database.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, loginId == null ? "" : loginId.trim());
            ps.setString(2, role.name());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String salt = rs.getString("password_salt");
                    String hash = rs.getString("password_hash");
                    if (!PasswordUtil.matchesSalted(plainPassword, salt, hash)) {
                        continue;
                    }
                    Account account = readRow(rs);
                    if (salt == null || salt.trim().isEmpty()) {
                        String fresh = PasswordUtil.newSalt();
                        updatePassword(account.getAccountId(), fresh,
                                PasswordUtil.hashWithSalt(plainPassword, fresh));
                    }
                    return account;
                }
                return null;
            }
        }
    }

    /**
     * Copies an account in from the staff file. Its SHA-256 hash is kept as
     * it is, with an empty salt, because SHA-256 of an empty salt plus the
     * password is the same hash; the account can therefore sign in at once,
     * and is given a salt of its own the first time it does.
     *
     * @param username     the username in the staff file
     * @param email        the email address in the staff file
     * @param role         the user level in the staff file
     * @param unsaltedHash the SHA-256 password hash in the staff file
     * @throws SQLException when the database cannot be written
     */
    public void importAccount(String username, String email, Role role,
            String unsaltedHash) throws SQLException {
        String sql = "INSERT INTO app_user "
                + "(username, full_name, email, role, password_salt, password_hash,"
                + " is_logged_in, registered_at) "
                + "VALUES (?, ?, ?, ?, '', ?, FALSE, NOW())";
        try (Connection conn = database.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username.trim());
            ps.setString(2, username.trim());
            ps.setString(3, email.trim());
            ps.setString(4, role.name());
            ps.setString(5, unsaltedHash.trim());
            ps.executeUpdate();
        }
    }

    /**
     * @param accountId the account to change
     * @param salt      the new salt
     * @param hash      SHA-256 of the new salt plus the password
     * @throws SQLException when the database cannot be written
     */
    public void updatePassword(int accountId, String salt, String hash)
            throws SQLException {
        String sql = "UPDATE app_user SET password_salt = ?, password_hash = ?"
                + " WHERE account_id = ?";
        try (Connection conn = database.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, salt);
            ps.setString(2, hash);
            ps.setInt(3, accountId);
            ps.executeUpdate();
        }
    }

    /**
     * @param email the address to look for
     * @return the account with that email address, or null
     * @throws SQLException when the database cannot be read
     */
    public Account findByEmail(String email) throws SQLException {
        String sql = "SELECT account_id, username, full_name, email, role,"
                + " is_logged_in, last_login_at, registered_at"
                + " FROM app_user WHERE email = ?";
        try (Connection conn = database.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email == null ? "" : email.trim());
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? readRow(rs) : null;
            }
        }
    }

    /**
     * Marks an account as logged in and records the time.
     *
     * @param accountId the account signing in
     * @throws SQLException when the database cannot be written
     */
    public void markLoggedIn(int accountId) throws SQLException {
        String sql = "UPDATE app_user SET is_logged_in = TRUE, last_login_at = NOW()"
                + " WHERE account_id = ?";
        try (Connection conn = database.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, accountId);
            ps.executeUpdate();
        }
    }

    /**
     * Marks an account as logged out.
     *
     * @param accountId the account signing out
     * @throws SQLException when the database cannot be written
     */
    public void markLoggedOut(int accountId) throws SQLException {
        String sql = "UPDATE app_user SET is_logged_in = FALSE WHERE account_id = ?";
        try (Connection conn = database.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, accountId);
            ps.executeUpdate();
        }
    }

    /**
     * Finds an account by its login name.
     *
     * @param username login name
     * @return the account, or null when it is not registered
     * @throws SQLException when the database cannot be read
     */
    public Account findByUsername(String username) throws SQLException {
        String sql = "SELECT account_id, username, full_name, email, role,"
                + " is_logged_in, last_login_at, registered_at"
                + " FROM app_user WHERE username = ?";
        try (Connection conn = database.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username == null ? "" : username.trim());
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? readRow(rs) : null;
            }
        }
    }

    /**
     * Finds an account by its primary key.
     *
     * @param accountId primary key
     * @return the account, or null when there is no such row
     * @throws SQLException when the database cannot be read
     */
    public Account findById(int accountId) throws SQLException {
        String sql = "SELECT account_id, username, full_name, email, role,"
                + " is_logged_in, last_login_at, registered_at"
                + " FROM app_user WHERE account_id = ?";
        try (Connection conn = database.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, accountId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? readRow(rs) : null;
            }
        }
    }

    /**
     * Reads every registered account, newest first.
     *
     * @return all accounts in the database
     * @throws SQLException when the database cannot be read
     */
    public List<Account> findAll() throws SQLException {
        String sql = "SELECT account_id, username, full_name, email, role,"
                + " is_logged_in, last_login_at, registered_at"
                + " FROM app_user ORDER BY account_id";

        List<Account> accounts = new ArrayList<Account>();
        try (Connection conn = database.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                accounts.add(readRow(rs));
            }
        }
        return accounts;
    }

    /**
     * Counts how many accounts are signed in at this moment.
     *
     * @return the number of accounts whose logged in flag is set
     * @throws SQLException when the database cannot be read
     */
    public int countLoggedIn() throws SQLException {
        String sql = "SELECT COUNT(*) FROM app_user WHERE is_logged_in = TRUE";
        try (Connection conn = database.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            return rs.next() ? rs.getInt(1) : 0;
        }
    }

    /**
     * Removes an account and, through the foreign key, its session history.
     *
     * @param accountId the account to delete
     * @return true when a row was actually removed
     * @throws SQLException when the database cannot be written
     */
    public boolean delete(int accountId) throws SQLException {
        String sql = "DELETE FROM app_user WHERE account_id = ?";
        try (Connection conn = database.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, accountId);
            return ps.executeUpdate() > 0;
        }
    }

    /**
     * Turns the current row of a result set into an Account object.
     *
     * @param rs a result set positioned on a row
     * @return the account described by that row
     * @throws SQLException when a column cannot be read
     */
    private Account readRow(ResultSet rs) throws SQLException {
        Role role = Role.fromText(rs.getString("role"));
        if (role == null) {
            role = Role.CASHIER;
        }
        return new Account(
                rs.getInt("account_id"),
                rs.getString("username"),
                rs.getString("full_name"),
                rs.getString("email"),
                role,
                rs.getBoolean("is_logged_in"),
                rs.getTimestamp("last_login_at"),
                rs.getTimestamp("registered_at"));
    }
}
