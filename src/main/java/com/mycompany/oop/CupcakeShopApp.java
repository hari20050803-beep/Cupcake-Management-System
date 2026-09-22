/*
 * The Sweet Cupcake Shop  -  CSE4006 Object Oriented Programming (WRIT1)
 *
 * File    : CupcakeShopApp.java
 * Package : com.mycompany.oop
 */

package com.mycompany.oop;

import com.mycompany.oop.db.Database;
import com.mycompany.oop.db.DatabaseConfig;
import com.mycompany.oop.db.DatabaseService;
import com.mycompany.oop.exception.ShopException;
import com.mycompany.oop.model.Cupcake;
import com.mycompany.oop.model.User;
import com.mycompany.oop.service.AuthService;
import com.mycompany.oop.service.CupcakeFileStore;
import com.mycompany.oop.service.DataSeeder;
import com.mycompany.oop.service.DataStore;
import com.mycompany.oop.service.ShopService;
import com.mycompany.oop.service.UserFileStore;
import com.mycompany.oop.ui.MainFrame;
import java.io.IOException;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * Starting point of The Sweet Cupcake Shop application.
 *
 * The job of this class is to create the objects the program needs, wire them
 * together and show the login window. Notice that the two stores are created as
 * concrete objects but are immediately held in DataStore variables. From that
 * point on the services only know about the interface, so the storage could be
 * changed without touching them.
 *
 * MySQL on WampServer is started here too, and the staff file's accounts are
 * copied into it so sign-in can be checked there. If the server cannot be
 * reached the application says so once and carries on: sign-in then uses the
 * staff file, and the catalogue is always kept in its text file.
 *
 * @author The Sweet Cupcake Shop development team
 */
public class CupcakeShopApp {

    /**
     * @param args not used
     */
    public static void main(String[] args) {

        applyLookAndFeel();

        // ------------------------------------------------------------------
        // File based storage - the storage the assessment requires
        // ------------------------------------------------------------------
        // The variables are declared as the interface, not the class, which is
        // the abstraction the whole system is built on.
        DataStore<User> userStore = new UserFileStore();
        DataStore<Cupcake> cupcakeStore = new CupcakeFileStore();

        final ShopService shopService = new ShopService(cupcakeStore, userStore);
        final AuthService authService = new AuthService(userStore);

        try {
            // First run only: create the starting accounts and product list.
            DataSeeder.seedUsers(userStore);
            DataSeeder.seedCupcakes(cupcakeStore);

        } catch (IOException e) {
            fatal("The data files could not be created.\n\n" + e.getMessage()
                    + "\n\nCheck that the application can write to the data folder.");
            return;

        } catch (ShopException e) {
            fatal("The starting data could not be prepared.\n\n" + e.getMessage());
            return;
        }

        // ------------------------------------------------------------------
        // MySQL on WampServer - staff sign-in and the staff registry
        // ------------------------------------------------------------------
        final DatabaseService databaseService = startDatabase();

        // Sign-in is checked in the database whenever it is running, so every
        // account the staff file has and the database lacks is copied across
        // first: the starting accounts on a new computer, and anyone added
        // while MySQL was not running.
        if (databaseService.isAvailable()) {
            try {
                int copied = databaseService.importAccounts(userStore.loadAll());
                if (copied > 0) {
                    System.out.println("Staff accounts copied into MySQL: " + copied);
                }
            } catch (IOException | ShopException e) {
                System.out.println("The staff file could not be copied into MySQL: "
                        + e.getMessage());
            }
        }

        // ------------------------------------------------------------------
        // Show the one window on the Swing event dispatch thread
        // ------------------------------------------------------------------
        // MainFrame opens on the login screen and swaps in the right dashboard
        // once somebody has signed in.
        java.awt.EventQueue.invokeLater(()
                -> new MainFrame(authService, shopService, databaseService).setVisible(true));
    }

    /**
     * Tries to connect to MySQL and prepare its tables.
     *
     * A failure here is never fatal. The method returns a service that reports
     * itself as unavailable; sign-in then uses the staff file and the database
     * screens say that MySQL is not running.
     *
     * @return the database service, ready or not
     */
    private static DatabaseService startDatabase() {
        DatabaseConfig config = new DatabaseConfig();
        Database database = new Database(config);
        DatabaseService service = new DatabaseService(database);

        boolean started = service.start();

        System.out.println("Files    : cupcakes.txt and users.txt in the data folder");
        System.out.println("Database : "
                + (started ? "connected - " + service.getStatus()
                        : "not available - " + service.getStatus()));

        if (!started) {
            System.out.println("Sign-in will use the staff file until MySQL (WampServer) is running.");
        }
        return service;
    }

    /**
     * Uses the look and feel of the operating system so the windows match the
     * rest of Windows.
     */
    private static void applyLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        } catch (ClassNotFoundException | InstantiationException
                | IllegalAccessException | UnsupportedLookAndFeelException e) {
            // The default cross platform look and feel is perfectly usable, so
            // there is nothing to do but carry on.
            System.out.println("Using the default Java look and feel.");
        }
    }

    /**
     * Shows a message and stops, used only for a problem that makes the
     * application unusable.
     *
     * @param message text to show the user
     */
    private static void fatal(String message) {
        System.err.println(message);
        JOptionPane.showMessageDialog(null, message,
                "The Sweet Cupcake Shop cannot start", JOptionPane.ERROR_MESSAGE);
    }
}
