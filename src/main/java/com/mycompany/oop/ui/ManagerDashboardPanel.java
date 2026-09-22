/*
 * The Sweet Cupcake Shop  -  CSE4006 Object Oriented Programming (WRIT1)
 *
 * File    : ManagerDashboardPanel.java
 * Package : com.mycompany.oop.ui
 * Layout  : designed in the Apache NetBeans Form Editor (ManagerDashboardPanel.form)
 */

package com.mycompany.oop.ui;

import com.mycompany.oop.db.DatabaseService;
import com.mycompany.oop.exception.AccessDeniedException;
import com.mycompany.oop.exception.ShopException;
import com.mycompany.oop.model.User;
import com.mycompany.oop.service.ShopService;
import java.awt.CardLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * The dashboard a Manager sees.
 *
 * It is a separate class from CashierDashboardPanel on purpose: the Manager
 * menu, and the two Manager-only screens behind it, exist only here, so there
 * is no code in the Cashier dashboard that could ever show them.
 *
 * Access is checked in the constructor and not only by hiding buttons. A user
 * object that answers false to canManageUsers() is refused with an
 * AccessDeniedException, so this dashboard cannot be built for a Cashier even
 * if it were asked for directly in code. The screens behind it check again:
 * RegisterCashierPanel goes through ShopService, which refuses to write
 * anything for a requester that may not manage accounts.
 *
 * The screens themselves are held in a CardLayout, each one still its own
 * class in its own file.
 *
 * @author The Sweet Cupcake Shop development team
 */
public class ManagerDashboardPanel extends javax.swing.JPanel
        implements Dashboard {

    private static final long serialVersionUID = 1L;

    private final transient MainFrame mainFrame;
    private final transient ShopService shopService;
    private final transient DatabaseService databaseService;
    private final transient User currentUser;

    private final transient List<ShopModule> modules = new ArrayList<>();
    private final transient List<JButton> menuButtons = new ArrayList<>();

    /**
     * Creates new form ManagerDashboardPanel.
     *
     * @param mainFrame       the window this dashboard lives in
     * @param shopService     the business rules of the shop
     * @param databaseService the MySQL database on WampServer, may be null
     * @param currentUser     the Manager who signed in
     * @throws AccessDeniedException if the user may not manage accounts
     */
    public ManagerDashboardPanel(MainFrame mainFrame, ShopService shopService,
            DatabaseService databaseService, User currentUser)
            throws ShopException {

        // The authorisation check, made before any Manager screen is built.
        if (currentUser == null || !currentUser.canManageUsers()) {
            throw new AccessDeniedException(
                    "Only a Manager may open the Manager dashboard.");
        }

        this.mainFrame = mainFrame;
        this.shopService = shopService;
        this.databaseService = databaseService;
        this.currentUser = currentUser;

        initComponents();

        logoLabel.setIcon(AppTheme.cupcakeIcon(46));
        signedInLabel.setText("Signed in:  " + currentUser.getUsername()
                + "  -  " + currentUser.getRole().getDisplayName());
        roleDescLabel.setText(currentUser.getRoleDescription());

        buildScreens();
        showPermissions();

        for (JButton menuButton : menuButtons) {
            AppTheme.navButton(menuButton);
        }
        AppTheme.secondaryButton(logoutBtn);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        headerPanel = new javax.swing.JPanel();
        logoLabel = new javax.swing.JLabel();
        shopNameLabel = new javax.swing.JLabel();
        taglineLabel = new javax.swing.JLabel();
        signedInLabel = new javax.swing.JLabel();
        roleDescLabel = new javax.swing.JLabel();
        menuPanel = new javax.swing.JPanel();
        menuCaption = new javax.swing.JLabel();
        menu1Btn = new javax.swing.JButton();
        menu2Btn = new javax.swing.JButton();
        menu3Btn = new javax.swing.JButton();
        menu4Btn = new javax.swing.JButton();
        menu5Btn = new javax.swing.JButton();
        menu6Btn = new javax.swing.JButton();
        permCaption = new javax.swing.JLabel();
        perm1Label = new javax.swing.JLabel();
        perm2Label = new javax.swing.JLabel();
        perm3Label = new javax.swing.JLabel();
        perm4Label = new javax.swing.JLabel();
        logoutBtn = new javax.swing.JButton();
        contentPanel = new javax.swing.JPanel();
        statusPanel = new javax.swing.JPanel();
        statusLabel = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(1260, 760));
        setLayout(null);

        headerPanel.setBackground(new java.awt.Color(140, 47, 84));
        headerPanel.setLayout(null);
        headerPanel.add(logoLabel);
        logoLabel.setBounds(20, 22, 46, 46);

        shopNameLabel.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        shopNameLabel.setText("The Sweet Cupcake Shop");
        shopNameLabel.setForeground(new java.awt.Color(255, 255, 255));
        headerPanel.add(shopNameLabel);
        shopNameLabel.setBounds(80, 18, 460, 32);

        taglineLabel.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        taglineLabel.setText("Cupcake Transaction Management System");
        taglineLabel.setForeground(new java.awt.Color(240, 214, 224));
        headerPanel.add(taglineLabel);
        taglineLabel.setBounds(82, 52, 460, 20);

        signedInLabel.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        signedInLabel.setText(" ");
        signedInLabel.setForeground(new java.awt.Color(255, 255, 255));
        signedInLabel.setHorizontalAlignment(4);
        headerPanel.add(signedInLabel);
        signedInLabel.setBounds(620, 20, 600, 24);

        roleDescLabel.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        roleDescLabel.setText(" ");
        roleDescLabel.setForeground(new java.awt.Color(240, 214, 224));
        roleDescLabel.setHorizontalAlignment(4);
        headerPanel.add(roleDescLabel);
        roleDescLabel.setBounds(620, 48, 600, 20);

        this.add(headerPanel);
        headerPanel.setBounds(0, 0, 1260, 90);

        menuPanel.setBackground(new java.awt.Color(105, 33, 62));
        menuPanel.setLayout(null);
        menuCaption.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        menuCaption.setText("MENU");
        menuCaption.setForeground(new java.awt.Color(240, 214, 224));
        menuPanel.add(menuCaption);
        menuCaption.setBounds(18, 16, 200, 18);

        menu1Btn.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        menu1Btn.setText("Dashboard");
        menu1Btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu1BtnActionPerformed(evt);
            }
        });
        menuPanel.add(menu1Btn);
        menu1Btn.setBounds(14, 42, 212, 42);

        menu2Btn.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        menu2Btn.setText("View Cupcakes");
        menu2Btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu2BtnActionPerformed(evt);
            }
        });
        menuPanel.add(menu2Btn);
        menu2Btn.setBounds(14, 92, 212, 42);

        menu3Btn.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        menu3Btn.setText("Add Cupcake");
        menu3Btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu3BtnActionPerformed(evt);
            }
        });
        menuPanel.add(menu3Btn);
        menu3Btn.setBounds(14, 142, 212, 42);

        menu4Btn.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        menu4Btn.setText("Search Cupcakes");
        menu4Btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu4BtnActionPerformed(evt);
            }
        });
        menuPanel.add(menu4Btn);
        menu4Btn.setBounds(14, 192, 212, 42);

        menu5Btn.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        menu5Btn.setText("Create Cashier Account");
        menu5Btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu5BtnActionPerformed(evt);
            }
        });
        menuPanel.add(menu5Btn);
        menu5Btn.setBounds(14, 242, 212, 42);

        menu6Btn.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        menu6Btn.setText("Staff Registry");
        menu6Btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu6BtnActionPerformed(evt);
            }
        });
        menuPanel.add(menu6Btn);
        menu6Btn.setBounds(14, 292, 212, 42);

        permCaption.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        permCaption.setText("YOUR PERMISSIONS");
        permCaption.setForeground(new java.awt.Color(240, 214, 224));
        menuPanel.add(permCaption);
        permCaption.setBounds(18, 382, 210, 18);

        perm1Label.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        perm1Label.setText(" ");
        perm1Label.setForeground(new java.awt.Color(240, 214, 224));
        menuPanel.add(perm1Label);
        perm1Label.setBounds(18, 406, 210, 18);

        perm2Label.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        perm2Label.setText(" ");
        perm2Label.setForeground(new java.awt.Color(240, 214, 224));
        menuPanel.add(perm2Label);
        perm2Label.setBounds(18, 428, 210, 18);

        perm3Label.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        perm3Label.setText(" ");
        perm3Label.setForeground(new java.awt.Color(240, 214, 224));
        menuPanel.add(perm3Label);
        perm3Label.setBounds(18, 450, 210, 18);

        perm4Label.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        perm4Label.setText(" ");
        perm4Label.setForeground(new java.awt.Color(240, 214, 224));
        menuPanel.add(perm4Label);
        perm4Label.setBounds(18, 472, 210, 18);

        logoutBtn.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        logoutBtn.setText("Logout");
        logoutBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutBtnActionPerformed(evt);
            }
        });
        menuPanel.add(logoutBtn);
        logoutBtn.setBounds(14, 560, 212, 40);

        this.add(menuPanel);
        menuPanel.setBounds(0, 90, 240, 634);

        contentPanel.setBackground(new java.awt.Color(250, 247, 248));
        this.add(contentPanel);
        contentPanel.setBounds(240, 90, 1020, 634);

        statusPanel.setBackground(new java.awt.Color(237, 237, 237));
        statusPanel.setLayout(null);
        statusLabel.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        statusLabel.setText(" ");
        statusLabel.setForeground(new java.awt.Color(122, 112, 117));
        statusPanel.add(statusLabel);
        statusLabel.setBounds(16, 8, 1220, 20);

        this.add(statusPanel);
        statusPanel.setBounds(0, 724, 1260, 36);

    }// </editor-fold>//GEN-END:initComponents

    private void menu1BtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu1BtnActionPerformed
        showScreen(0);
    }//GEN-LAST:event_menu1BtnActionPerformed

    private void menu2BtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu2BtnActionPerformed
        showScreen(1);
    }//GEN-LAST:event_menu2BtnActionPerformed

    private void menu3BtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu3BtnActionPerformed
        showScreen(2);
    }//GEN-LAST:event_menu3BtnActionPerformed

    private void menu4BtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu4BtnActionPerformed
        showScreen(3);
    }//GEN-LAST:event_menu4BtnActionPerformed

    private void menu5BtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu5BtnActionPerformed
        showScreen(4);
    }//GEN-LAST:event_menu5BtnActionPerformed

    private void menu6BtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu6BtnActionPerformed
        showScreen(5);
    }//GEN-LAST:event_menu6BtnActionPerformed

    private void logoutBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutBtnActionPerformed
        mainFrame.logOut();
    }//GEN-LAST:event_logoutBtnActionPerformed

    /* ==================================================================
     * Behaviour
     * ================================================================== */

    /**
     * Creates the six screens a Manager may use and puts each one on its own
     * card. Every screen is a separate class; this method only arranges them.
     */
    private void buildScreens() {
        contentPanel.setLayout(new CardLayout());

        modules.add(new HomePanel(shopService, currentUser));
        modules.add(new CupcakePanel(shopService, currentUser));
        modules.add(new AddCupcakePanel(shopService, currentUser));
        modules.add(new SearchCupcakePanel(shopService, currentUser));
        modules.add(new RegisterCashierPanel(shopService, currentUser, databaseService));

        if (databaseService != null) {
            modules.add(new DatabasePanel(shopService, currentUser, databaseService));
        }

        for (int i = 0; i < modules.size(); i++) {
            contentPanel.add((JPanel) modules.get(i), String.valueOf(i));
        }

        menuButtons.add(menu1Btn);
        menuButtons.add(menu2Btn);
        menuButtons.add(menu3Btn);
        menuButtons.add(menu4Btn);
        menuButtons.add(menu5Btn);
        menuButtons.add(menu6Btn);

        // A menu button with no screen behind it is taken off the menu. This
        // only happens to the MySQL button, when the server is not running.
        for (int i = 0; i < menuButtons.size(); i++) {
            JButton menuButton = menuButtons.get(i);
            if (i < modules.size()) {
                menuButton.setText(modules.get(i).getModuleTitle());
                menuButton.setVisible(true);
            } else {
                menuButton.setVisible(false);
            }
        }
    }

    /**
     * Lists what a Manager may do, asked of the user object itself.
     */
    private void showPermissions() {
        JLabel[] slots = {perm1Label, perm2Label, perm3Label, perm4Label};
        String[] permissions = currentUser.getPermissions();

        for (int i = 0; i < slots.length; i++) {
            if (i < permissions.length) {
                slots[i].setText("-  " + permissions[i]);
                slots[i].setVisible(true);
            } else {
                slots[i].setVisible(false);
            }
        }
    }

    /**
     * Brings one screen to the front and reloads it from its file.
     *
     * @param index position of the screen in the menu
     */
    private void showScreen(int index) {
        if (index < 0 || index >= modules.size()) {
            return;
        }
        ShopModule module = modules.get(index);

        ((CardLayout) contentPanel.getLayout())
                .show(contentPanel, String.valueOf(index));

        // Polymorphic call: the variable is a ShopModule, but the refresh()
        // belonging to the real screen is the one that runs.
        module.refresh();

        String database = (databaseService != null && databaseService.isAvailable())
                ? "MySQL connected" : "MySQL not connected";

        statusLabel.setText("Screen: " + module.getModuleTitle()
                + "          The Sweet Cupcake Shop"
                + "          User level: " + currentUser.getRole().getDisplayName()
                + "          " + database);
    }

    @Override
    public String getDashboardTitle() {
        return "Manager Dashboard";
    }

    @Override
    public User getSignedInUser() {
        return currentUser;
    }

    @Override
    public final void showHome() {
        showScreen(0);
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel contentPanel;
    private javax.swing.JPanel headerPanel;
    private javax.swing.JLabel logoLabel;
    private javax.swing.JButton logoutBtn;
    private javax.swing.JButton menu1Btn;
    private javax.swing.JButton menu2Btn;
    private javax.swing.JButton menu3Btn;
    private javax.swing.JButton menu4Btn;
    private javax.swing.JButton menu5Btn;
    private javax.swing.JButton menu6Btn;
    private javax.swing.JLabel menuCaption;
    private javax.swing.JPanel menuPanel;
    private javax.swing.JLabel perm1Label;
    private javax.swing.JLabel perm2Label;
    private javax.swing.JLabel perm3Label;
    private javax.swing.JLabel perm4Label;
    private javax.swing.JLabel permCaption;
    private javax.swing.JLabel roleDescLabel;
    private javax.swing.JLabel shopNameLabel;
    private javax.swing.JLabel signedInLabel;
    private javax.swing.JLabel statusLabel;
    private javax.swing.JPanel statusPanel;
    private javax.swing.JLabel taglineLabel;
    // End of variables declaration//GEN-END:variables
}
