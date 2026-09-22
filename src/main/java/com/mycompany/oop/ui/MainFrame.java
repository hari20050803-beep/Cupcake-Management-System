/*
 * The Sweet Cupcake Shop  -  CSE4006 Object Oriented Programming (WRIT1)
 *
 * File    : MainFrame.java
 * Package : com.mycompany.oop.ui
 * Layout  : designed in the Apache NetBeans Form Editor (MainFrame.form)
 */

package com.mycompany.oop.ui;

import com.mycompany.oop.db.DatabaseService;
import com.mycompany.oop.exception.ShopException;
import com.mycompany.oop.model.User;
import com.mycompany.oop.service.AuthService;
import com.mycompany.oop.service.ShopService;
import java.awt.CardLayout;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * The one window of the application.
 *
 * This frame owns no screen of its own. It holds a CardLayout and swaps whole
 * screens in and out of it: the login screen first, then the dashboard that
 * belongs to whoever signed in. Each screen remains a separate class in its own
 * file, which keeps the login code, the Manager code and the Cashier code
 * completely apart.
 *
 * Which dashboard is built is decided by the user object itself. A Manager
 * answers true to canManageUsers() and a Cashier answers false, so this class
 * never tests a role name. The field holding the dashboard is declared as the
 * Dashboard interface, so the frame does not know, and does not need to know,
 * which of the two it is holding.
 *
 * @author The Sweet Cupcake Shop development team
 */
public class MainFrame extends javax.swing.JFrame {

    private static final long serialVersionUID = 1L;

    /** Name of the login card inside the CardLayout. */
    private static final String CARD_LOGIN = "login";

    /** Name of the dashboard card inside the CardLayout. */
    private static final String CARD_DASHBOARD = "dashboard";

    private final transient AuthService authService;
    private final transient ShopService shopService;
    private final transient DatabaseService databaseService;

    /** The dashboard on screen, held as the interface, not as either class. */
    private transient Dashboard dashboard;

    /**
     * Creates new form MainFrame.
     *
     * @param authService     checks credentials against the staff file when
     *                        MySQL is not running
     * @param shopService     the business rules of the shop
     * @param databaseService the MySQL database where sign-in is checked,
     *                        may be null
     */
    public MainFrame(AuthService authService, ShopService shopService,
            DatabaseService databaseService) {

        this.authService = authService;
        this.shopService = shopService;
        this.databaseService = databaseService;

        initComponents();

        setIconImage(AppTheme.cupcakeIcon(64).getImage());
        cardPanel.setLayout(new CardLayout());

        showLogin();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cardPanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("The Sweet Cupcake Shop");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        cardPanel.setBackground(new java.awt.Color(250, 247, 248));
        cardPanel.setPreferredSize(new java.awt.Dimension(1260, 760));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(cardPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 1260, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(cardPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 760, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // Closing the window with the X button skips logOut(), which would leave
        // the MySQL session marked "still signed in". Record the logout here too.
        // recordLogout() does nothing when nobody is signed in, so this is safe
        // on the login screen. The window then closes as before (EXIT_ON_CLOSE).
        if (databaseService != null && databaseService.isAvailable()) {
            databaseService.recordLogout();
        }
    }//GEN-LAST:event_formWindowClosing


    /* ==================================================================
     * Navigation
     * ================================================================== */

    /**
     * Shows the login screen and throws away everything belonging to the
     * previous user.
     *
     * A brand new LoginPanel is built every time, and the old card is removed
     * from the layout rather than merely hidden, so the dashboard of the person
     * who has just logged out cannot be brought back.
     */
    public final void showLogin() {
        dashboard = null;

        LoginPanel login = new LoginPanel(this, authService, shopService,
                databaseService);

        cardPanel.removeAll();
        cardPanel.add(login, CARD_LOGIN);
        ((CardLayout) cardPanel.getLayout()).show(cardPanel, CARD_LOGIN);

        setTitle("The Sweet Cupcake Shop  -  Staff Login");
        cardPanel.revalidate();
        cardPanel.repaint();
        getRootPane().setDefaultButton(login.getLoginButton());
        login.focusLoginId();
    }

    /**
     * Builds and shows the dashboard belonging to the member of staff who has
     * just signed in.
     *
     * @param user the signed-in member of staff
     */
    public void openDashboard(User user) {
        JPanel panel;

        try {
            if (user.canManageUsers()) {
                ManagerDashboardPanel manager = new ManagerDashboardPanel(
                        this, shopService, databaseService, user);
                dashboard = manager;
                panel = manager;
            } else {
                CashierDashboardPanel cashier = new CashierDashboardPanel(
                        this, shopService, user);
                dashboard = cashier;
                panel = cashier;
            }

        } catch (ShopException e) {
            // Thrown if a Cashier ever reached the Manager dashboard.
            JOptionPane.showMessageDialog(this, e.getMessage(),
                    "Not allowed", JOptionPane.ERROR_MESSAGE);
            showLogin();
            return;
        }

        cardPanel.removeAll();
        cardPanel.add(panel, CARD_DASHBOARD);
        ((CardLayout) cardPanel.getLayout()).show(cardPanel, CARD_DASHBOARD);

        // Polymorphic call: the variable is a Dashboard, and the title that
        // appears is the one belonging to the dashboard actually created.
        setTitle("The Sweet Cupcake Shop  -  " + dashboard.getDashboardTitle());
        cardPanel.revalidate();
        cardPanel.repaint();
        dashboard.showHome();
    }

    /**
     * Asks for confirmation, closes the MySQL session and returns to the login
     * screen with the session cleared.
     */
    public void logOut() {
        int answer = JOptionPane.showConfirmDialog(this,
                "Log out of The Sweet Cupcake Shop?",
                "Log Out", JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (answer == JOptionPane.YES_OPTION) {
            if (databaseService != null && databaseService.isAvailable()) {
                databaseService.recordLogout();
            }
            showLogin();
        }
    }

    /**
     * @return the dashboard on screen, or null while the login screen is shown
     */
    public Dashboard getDashboard() {
        return dashboard;
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel cardPanel;
    // End of variables declaration//GEN-END:variables
}
