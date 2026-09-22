/*
 * The Sweet Cupcake Shop  -  CSE4006 Object Oriented Programming (WRIT1)
 *
 * File    : DatabaseConfig.java
 * Package : com.mycompany.oop.db
 */

package com.mycompany.oop.db;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Holds the MySQL connection settings, read from outside the source code.
 *
 * The settings live in config/database.properties, which is a plain text file
 * loaded when the application starts. Nothing about the database - not the host,
 * not the user and above all not the password - is written into any .java file,
 * so the source code can be shared or printed in the report without giving the
 * password away.
 *
 * The password may also be supplied through the environment variable
 * SCS_DB_PASSWORD, which takes priority over the file. That allows the
 * application to run with no password stored on disk at all.
 *
 * @author The Sweet Cupcake Shop development team
 */
public final class DatabaseConfig {

    /** Location of the settings file, relative to the project folder. */
    public static final String CONFIG_FILE = "config/database.properties";

    /** Environment variable that can override the password. */
    public static final String PASSWORD_ENV = "SCS_DB_PASSWORD";

    /**
     * Connection options shared by both URLs. MySQL writes every time with
     * NOW(), in the server's own time zone, and the server runs on the same PC
     * as the program (db.host=127.0.0.1), so connectionTimeZone=LOCAL reads the
     * times back unchanged. The former serverTimezone=UTC made the driver treat
     * those local times as UTC, which moved every login time shown on screen by
     * the UTC offset (five and a half hours in Sri Lanka).
     */
    private static final String OPTIONS =
            "useSSL=false&allowPublicKeyRetrieval=true&connectionTimeZone=LOCAL";

    private final String host;
    private final int port;
    private final String databaseName;
    private final String user;
    private final String password;
    private final boolean loadedFromFile;

    /**
     * Reads the settings file. When the file is missing, sensible local defaults
     * are used so the application still starts and can report the problem
     * instead of crashing.
     */
    public DatabaseConfig() {
        Properties properties = new Properties();
        boolean loaded = false;

        File file = new File(CONFIG_FILE);
        if (file.exists()) {
            try (InputStream in = new FileInputStream(file)) {
                properties.load(in);
                loaded = true;
            } catch (IOException e) {
                // Falling back to the defaults is safer than stopping: if they
                // do not reach MySQL, sign-in uses the staff file instead.
                loaded = false;
            }
        }

        this.loadedFromFile = loaded;
        this.host = properties.getProperty("db.host", "127.0.0.1").trim();
        this.databaseName = properties.getProperty("db.name", "sweet_cupcake_shop").trim();
        this.user = properties.getProperty("db.user", "root").trim();

        int parsedPort = 3306;
        try {
            parsedPort = Integer.parseInt(properties.getProperty("db.port", "3306").trim());
        } catch (NumberFormatException e) {
            parsedPort = 3306;
        }
        this.port = parsedPort;

        // The environment variable wins, so the password need not be on disk.
        String fromEnv = System.getenv(PASSWORD_ENV);
        if (fromEnv != null && !fromEnv.trim().isEmpty()) {
            this.password = fromEnv;
        } else {
            this.password = properties.getProperty("db.password", "");
        }
    }

    /**
     * @return the JDBC URL of the shop database
     */
    public String getUrl() {
        return "jdbc:mysql://" + host + ":" + port + "/" + databaseName
                + "?" + OPTIONS;
    }

    /**
     * @return a JDBC URL that does not name a database, used to create the
     *         database the first time the application runs
     */
    public String getServerUrl() {
        return "jdbc:mysql://" + host + ":" + port
                + "/?" + OPTIONS;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public String getUser() {
        return user;
    }

    /**
     * @return the password. Package private on purpose so that only the
     *         connection class in this package can read it.
     */
    String getPassword() {
        return password;
    }

    /**
     * @return true when the settings really came from config/database.properties
     */
    public boolean isLoadedFromFile() {
        return loadedFromFile;
    }

    /**
     * @return a description safe to show on screen, with no password in it
     */
    @Override
    public String toString() {
        return user + "@" + host + ":" + port + "/" + databaseName;
    }
}
