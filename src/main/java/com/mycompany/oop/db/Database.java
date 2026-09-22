/*
 * The Sweet Cupcake Shop  -  CSE4006 Object Oriented Programming (WRIT1)
 *
 * File    : Database.java
 * Package : com.mycompany.oop.db
 */

package com.mycompany.oop.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Opens connections to MySQL and builds the shop database the first time the
 * application is used.
 *
 * Everything about JDBC is kept in this one class: loading the driver, opening a
 * connection and creating the tables. The rest of the database code just asks
 * this class for a Connection.
 *
 * Staff sign-in is checked in this database whenever MySQL is running. If it
 * is switched off the application must still run, so isAvailable() lets the
 * screens check first: sign-in then falls back to the staff file and the
 * database screens say that MySQL is not running.
 *
 * @author The Sweet Cupcake Shop development team
 */
public final class Database {

    private final DatabaseConfig config;
    private boolean schemaReady;
    private String lastError;

    /**
     * @param config the connection settings
     */
    public Database(DatabaseConfig config) {
        this.config = config;
    }

    public DatabaseConfig getConfig() {
        return config;
    }

    /**
     * @return the reason the last database attempt failed, or null when the last
     *         attempt worked
     */
    public String getLastError() {
        return lastError;
    }

    /**
     * Opens a connection to the shop database.
     *
     * @return an open JDBC connection, which the caller must close
     * @throws SQLException when the server cannot be reached
     */
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(config.getUrl(),
                config.getUser(), config.getPassword());
    }

    /**
     * Checks whether the MySQL server itself can be reached, without requiring
     * the shop database to exist yet.
     *
     * This is the check that must run first the very first time the application
     * is used, because at that point the database has not been created.
     *
     * @return true when the server answered
     */
    public boolean isServerAvailable() {
        try (Connection conn = DriverManager.getConnection(config.getServerUrl(),
                config.getUser(), config.getPassword())) {
            lastError = null;
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            lastError = e.getMessage();
            return false;
        }
    }

    /**
     * Checks whether the shop database can be reached right now.
     *
     * @return true when a connection was opened successfully
     */
    public boolean isAvailable() {
        try (Connection conn = getConnection()) {
            lastError = null;
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            lastError = e.getMessage();
            return false;
        }
    }

    /**
     * @return a one line description of the server, for the status bar
     */
    public String describeServer() {
        try (Connection conn = getConnection()) {
            return "MySQL " + conn.getMetaData().getDatabaseProductVersion()
                    + "  (" + config.toString() + ")";
        } catch (SQLException e) {
            lastError = e.getMessage();
            return "MySQL not connected";
        }
    }

    /**
     * Creates the database and its three tables if they do not exist yet.
     *
     * CREATE ... IF NOT EXISTS is used throughout, so this method is safe to
     * call every time the application starts: the first run builds everything
     * and later runs change nothing.
     *
     * @return true when the schema is ready to use
     */
    public boolean initialiseSchema() {
        // Step 1: connect without naming a database, and create the database.
        try (Connection conn = DriverManager.getConnection(config.getServerUrl(),
                config.getUser(), config.getPassword());
                Statement st = conn.createStatement()) {

            st.executeUpdate("CREATE DATABASE IF NOT EXISTS "
                    + config.getDatabaseName()
                    + " CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci");

        } catch (SQLException e) {
            lastError = e.getMessage();
            schemaReady = false;
            return false;
        }

        // Step 2: connect to the database itself and create the tables.
        try (Connection conn = getConnection();
                Statement st = conn.createStatement()) {

            st.executeUpdate(CREATE_APP_USER);
            st.executeUpdate(CREATE_LOGIN_SESSION);
            st.executeUpdate(CREATE_CUPCAKE);

            lastError = null;
            schemaReady = true;
            return true;

        } catch (SQLException e) {
            lastError = e.getMessage();
            schemaReady = false;
            return false;
        }
    }

    /**
     * @return true when initialiseSchema() has completed successfully
     */
    public boolean isSchemaReady() {
        return schemaReady;
    }

    /* ------------------------------------------------------------------
     * The three tables: staff accounts, login history, catalogue copy
     * ------------------------------------------------------------------ */

    /** Registered accounts, with the current logged in flag. */
    private static final String CREATE_APP_USER =
            "CREATE TABLE IF NOT EXISTS app_user ("
            + "  account_id    INT AUTO_INCREMENT PRIMARY KEY,"
            + "  username      VARCHAR(50)  NOT NULL UNIQUE,"
            + "  full_name     VARCHAR(100) NOT NULL,"
            + "  email         VARCHAR(120) NULL,"
            + "  role          VARCHAR(20)  NOT NULL,"
            + "  password_salt CHAR(32)     NOT NULL,"
            + "  password_hash CHAR(64)     NOT NULL,"
            + "  is_logged_in  BOOLEAN      NOT NULL DEFAULT FALSE,"
            + "  last_login_at DATETIME     NULL,"
            + "  registered_at DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP"
            + ") ENGINE=InnoDB";

    /** One row per login, completed with the time of the matching logout. */
    private static final String CREATE_LOGIN_SESSION =
            "CREATE TABLE IF NOT EXISTS login_session ("
            + "  session_id INT AUTO_INCREMENT PRIMARY KEY,"
            + "  account_id INT          NOT NULL,"
            + "  username   VARCHAR(50)  NOT NULL,"
            + "  login_at   DATETIME     NOT NULL,"
            + "  logout_at  DATETIME     NULL,"
            + "  CONSTRAINT fk_session_account FOREIGN KEY (account_id)"
            + "      REFERENCES app_user(account_id) ON DELETE CASCADE"
            + ") ENGINE=InnoDB";

    /** Copy of the product catalogue, kept in step with the text file. */
    private static final String CREATE_CUPCAKE =
            "CREATE TABLE IF NOT EXISTS cupcake ("
            + "  cupcake_id  VARCHAR(10)   PRIMARY KEY,"
            + "  name        VARCHAR(80)   NOT NULL,"
            + "  category    VARCHAR(40)   NOT NULL,"
            + "  price       DECIMAL(10,2) NOT NULL,"
            + "  description VARCHAR(150)  NULL,"
            + "  available   BOOLEAN       NOT NULL DEFAULT TRUE,"
            + "  synced_at   DATETIME      NOT NULL,"
            + "  synced_by   VARCHAR(50)   NOT NULL"
            + ") ENGINE=InnoDB";
}
