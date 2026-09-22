/*
 * The Sweet Cupcake Shop  -  CSE4006 Object Oriented Programming (WRIT1)
 *
 * File    : DatabasePanel.java
 * Package : com.mycompany.oop.ui
 * Layout  : designed in the Apache NetBeans Form Editor (DatabasePanel.form)
 */

package com.mycompany.oop.ui;

import com.mycompany.oop.db.DatabaseService;
import com.mycompany.oop.exception.ShopException;
import com.mycompany.oop.model.Account;
import com.mycompany.oop.model.Cupcake;
import com.mycompany.oop.model.LoginSession;
import com.mycompany.oop.model.User;
import com.mycompany.oop.service.ShopService;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 * The database screen, available to a Manager only.
 *
 * This is where the MySQL database is shown and managed: registering a
 * user, seeing every registered account with its current login status, reading
 * the login and logout history, and copying the product catalogue from the text
 * file into MySQL so that it can be reported on with SQL. Registering or
 * removing a member of staff here changes the staff file as well, so the two
 * stores always hold the same accounts.
 *
 * If MySQL is switched off the screen reports that, and the rest of the
 * application carries on working from its text files.
 *
 * @author The Sweet Cupcake Shop development team
 */
public class DatabasePanel extends javax.swing.JPanel implements ShopModule {

    private static final long serialVersionUID = 1L;

    private static final String[] USER_COLUMNS = {
        "ID", "Username", "Full Name", "Email", "User Level",
        "Status", "Last Login", "Registered"
    };

    private static final String[] SESSION_COLUMNS = {
        "Session", "Username", "Logged In At", "Logged Out At", "Duration"
    };

    private final transient ShopService shopService;
    private final transient User currentUser;
    private final transient DatabaseService databaseService;
    private final DefaultTableModel userModel;
    private final DefaultTableModel sessionModel;

    /**
     * Creates new form DatabasePanel.
     *
     * @param shopService     the business rules of the shop
     * @param currentUser     the Manager who is signed in
     * @param databaseService the MySQL database on WampServer
     */
    public DatabasePanel(ShopService shopService, User currentUser,
            DatabaseService databaseService) {
        this.shopService = shopService;
        this.currentUser = currentUser;
        this.databaseService = databaseService;

        initComponents();
        setBackground(AppTheme.BACKGROUND);

        userModel = readOnlyModel(USER_COLUMNS);
        sessionModel = readOnlyModel(SESSION_COLUMNS);
        userTable.setModel(userModel);
        sessionTable.setModel(sessionModel);
        AppTheme.styleTable(userTable, -1);
        AppTheme.styleTable(sessionTable, -1);
        reportScroll.setBorder(javax.swing.BorderFactory.createLineBorder(AppTheme.BORDER));
        AppTheme.primaryButton(registerBtn);
        AppTheme.secondaryButton(refreshBtn);
        AppTheme.secondaryButton(deleteBtn);
        AppTheme.secondaryButton(syncBtn);
        userScroll.setBorder(javax.swing.BorderFactory.createLineBorder(AppTheme.BORDER));
        sessionScroll.setBorder(javax.swing.BorderFactory.createLineBorder(AppTheme.BORDER));
    }

    private DefaultTableModel readOnlyModel(String[] columns) {
        return new DefaultTableModel(columns, 0) {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        titleLabel = new javax.swing.JLabel();
        subtitleLabel = new javax.swing.JLabel();
        registerBtn = new javax.swing.JButton();
        refreshBtn = new javax.swing.JButton();
        deleteBtn = new javax.swing.JButton();
        syncBtn = new javax.swing.JButton();
        usersLabel = new javax.swing.JLabel();
        userScroll = new javax.swing.JScrollPane();
        userTable = new javax.swing.JTable();
        userCountLabel = new javax.swing.JLabel();
        sessionsLabel = new javax.swing.JLabel();
        sessionScroll = new javax.swing.JScrollPane();
        sessionTable = new javax.swing.JTable();
        sessionCountLabel = new javax.swing.JLabel();
        reportLabel = new javax.swing.JLabel();
        reportScroll = new javax.swing.JScrollPane();
        reportArea = new javax.swing.JTextArea();
        cupcakeCountLabel = new javax.swing.JLabel();
        dbStatusLabel = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(1020, 634));
        setLayout(null);

        titleLabel.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        titleLabel.setText("Staff Registry and Activity");
        titleLabel.setForeground(new java.awt.Color(140, 47, 84));
        this.add(titleLabel);
        titleLabel.setBounds(24, 14, 700, 28);

        subtitleLabel.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        subtitleLabel.setText("Registered staff, who is signed in now, and the price report.");
        subtitleLabel.setForeground(new java.awt.Color(122, 112, 117));
        this.add(subtitleLabel);
        subtitleLabel.setBounds(24, 44, 940, 20);

        registerBtn.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        registerBtn.setText("Register New User");
        registerBtn.setBackground(new java.awt.Color(140, 47, 84));
        registerBtn.setForeground(new java.awt.Color(255, 255, 255));
        registerBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                registerBtnActionPerformed(evt);
            }
        });
        this.add(registerBtn);
        registerBtn.setBounds(24, 72, 180, 32);

        refreshBtn.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        refreshBtn.setText("Refresh");
        refreshBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshBtnActionPerformed(evt);
            }
        });
        this.add(refreshBtn);
        refreshBtn.setBounds(212, 72, 100, 32);

        deleteBtn.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        deleteBtn.setText("Delete Selected");
        deleteBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteBtnActionPerformed(evt);
            }
        });
        this.add(deleteBtn);
        deleteBtn.setBounds(320, 72, 150, 32);

        syncBtn.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        syncBtn.setText("Refresh the price report");
        syncBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                syncBtnActionPerformed(evt);
            }
        });
        this.add(syncBtn);
        syncBtn.setBounds(478, 72, 250, 32);

        usersLabel.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        usersLabel.setText("Registered staff and who is signed in");
        this.add(usersLabel);
        usersLabel.setBounds(24, 116, 500, 20);

        userScroll.setViewportView(userTable);
        this.add(userScroll);
        userScroll.setBounds(24, 140, 972, 170);

        userCountLabel.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        userCountLabel.setText(" ");
        userCountLabel.setForeground(new java.awt.Color(122, 112, 117));
        this.add(userCountLabel);
        userCountLabel.setBounds(24, 316, 972, 18);

        sessionsLabel.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        sessionsLabel.setText("Login and logout history");
        this.add(sessionsLabel);
        sessionsLabel.setBounds(24, 340, 500, 20);

        sessionScroll.setViewportView(sessionTable);
        this.add(sessionScroll);
        sessionScroll.setBounds(24, 364, 972, 140);

        sessionCountLabel.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        sessionCountLabel.setText(" ");
        sessionCountLabel.setForeground(new java.awt.Color(122, 112, 117));
        this.add(sessionCountLabel);
        sessionCountLabel.setBounds(24, 510, 972, 18);

        reportLabel.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        reportLabel.setText("Price report by category");
        this.add(reportLabel);
        reportLabel.setBounds(24, 534, 330, 20);

        reportArea.setFont(new java.awt.Font("Consolas", 0, 12)); // NOI18N
        reportArea.setEditable(false);
        reportScroll.setViewportView(reportArea);
        this.add(reportScroll);
        reportScroll.setBounds(360, 530, 636, 70);

        cupcakeCountLabel.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        cupcakeCountLabel.setText(" ");
        cupcakeCountLabel.setForeground(new java.awt.Color(122, 112, 117));
        this.add(cupcakeCountLabel);
        cupcakeCountLabel.setBounds(24, 558, 330, 18);

        dbStatusLabel.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        dbStatusLabel.setText(" ");
        dbStatusLabel.setForeground(new java.awt.Color(122, 112, 117));
        this.add(dbStatusLabel);
        dbStatusLabel.setBounds(24, 606, 972, 18);

    }// </editor-fold>//GEN-END:initComponents

    private void registerBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_registerBtnActionPerformed
        openRegisterDialog();
    }//GEN-LAST:event_registerBtnActionPerformed

    private void refreshBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshBtnActionPerformed
        refresh();
    }//GEN-LAST:event_refreshBtnActionPerformed

    private void deleteBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteBtnActionPerformed
        deleteSelectedUser();
    }//GEN-LAST:event_deleteBtnActionPerformed

    private void syncBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_syncBtnActionPerformed
        syncCupcakes();
    }//GEN-LAST:event_syncBtnActionPerformed


    private void openRegisterDialog() {
        if (!checkAvailable()) {
            return;
        }
        RegisterDialog dialog = new RegisterDialog(
                javax.swing.SwingUtilities.getWindowAncestor(this),
                shopService, currentUser, databaseService);
        dialog.setVisible(true);
        if (dialog.isRegistered()) {
            loadUsers();
        }
    }

    private void deleteSelectedUser() {
        if (!checkAvailable()) {
            return;
        }
        int row = userTable.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this,
                    "Please select the account you want to remove first.",
                    "Nothing selected", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String id = String.valueOf(userModel.getValueAt(row, 0));
        String username = String.valueOf(userModel.getValueAt(row, 1));

        if (username.equalsIgnoreCase(currentUser.getUsername())) {
            JOptionPane.showMessageDialog(this,
                    "You cannot delete the account you are signed in with.",
                    "Not allowed", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int answer = JOptionPane.showConfirmDialog(this,
                "Remove " + username + "?\n\n"
                + "The account is removed from the MySQL database and from the\n"
                + "staff file, so it cannot sign in again. Their login history\n"
                + "is removed as well.",
                "Confirm removal", JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (answer != JOptionPane.YES_OPTION) {
            return;
        }
        try {
            // The staff file first, through ShopService, which applies the
            // rules: a Manager only, and the shop always keeps one Manager.
            // Left in the file, the account could still sign in while MySQL is
            // off, and would be copied back into MySQL at the next start-up.
            User inFile = findInStaffFile(username);
            if (inFile != null) {
                shopService.deleteUser(currentUser, inFile.getUserId());
            }

            boolean removed = databaseService.getAccountDao().delete(Integer.parseInt(id));
            dbStatusLabel.setText(removed
                    ? username + " was removed from the database and the staff file."
                    : username + " was not found in the database.");
            loadUsers();
            loadSessions();

        } catch (ShopException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(),
                    "Not allowed", JOptionPane.WARNING_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,
                    "The staff file could not be updated, so the account was left in place.\n\n"
                    + e.getMessage(),
                    "File problem", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                    "The account could not be removed from the database.\n\n" + e.getMessage(),
                    "Database error", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "That row does not have a valid account ID.",
                    "Database error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * @param username the name shown in the registry table
     * @return the same member of staff in data/users.txt, or null when the
     *         account exists only in MySQL
     * @throws IOException   when the staff file cannot be read
     * @throws ShopException when the staff file holds a damaged record
     */
    private User findInStaffFile(String username) throws IOException, ShopException {
        for (User user : shopService.getAllUsers(currentUser)) {
            if (user.getUsername().equalsIgnoreCase(username)) {
                return user;
            }
        }
        return null;
    }

    /**
     * Reads the products from the text file and copies them into MySQL. The
     * file stays the master copy; this only refreshes the database copy.
     */
    private void syncCupcakes() {
        if (!checkAvailable()) {
            return;
        }
        try {
            List<Cupcake> fromFile = shopService.getAllCupcakes();
            int written = databaseService.syncCupcakes(fromFile, currentUser.getUsername());

            if (written < 0) {
                JOptionPane.showMessageDialog(this,
                        "The copy failed.\n\n" + databaseService.getStatus(),
                        "Database error", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                        written + " product(s) copied from the text file into the"
                        + " MySQL cupcake table.",
                        "Copied", JOptionPane.INFORMATION_MESSAGE);
                loadCupcakes();
            }

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,
                    "The cupcake file could not be read.\n\n" + e.getMessage(),
                    "File problem", JOptionPane.ERROR_MESSAGE);
        } catch (ShopException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(),
                    "Damaged record", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadUsers() {
        userModel.setRowCount(0);
        if (!databaseService.isAvailable()) {
            userCountLabel.setText("MySQL is not running. Start WampServer to see this.");
            return;
        }
        try {
            List<Account> accounts = databaseService.getAccountDao().findAll();
            for (Account account : accounts) {
                userModel.addRow(new Object[]{
                    account.getAccountId(),
                    account.getUsername(),
                    account.getFullName(),
                    account.getEmail(),
                    account.getRole().getDisplayName(),
                    account.getStatusText(),
                    account.getLastLoginAt() == null ? "never"
                            : account.getLastLoginAt().toString(),
                    account.getRegisteredAt() == null ? ""
                            : account.getRegisteredAt().toString()
                });
            }
            userCountLabel.setText(accounts.size() + " account(s) registered,  "
                    + databaseService.countLoggedIn() + " signed in right now.");

        } catch (SQLException e) {
            userCountLabel.setText("Could not read the accounts: " + e.getMessage());
        }
    }

    private void loadSessions() {
        sessionModel.setRowCount(0);
        if (!databaseService.isAvailable()) {
            sessionCountLabel.setText("MySQL is not running. Start WampServer to see this.");
            return;
        }
        try {
            List<LoginSession> sessions = databaseService.getSessionDao().findRecent(100);
            for (LoginSession session : sessions) {
                sessionModel.addRow(new Object[]{
                    session.getSessionId(),
                    session.getUsername(),
                    session.getLoginAt() == null ? "" : session.getLoginAt().toString(),
                    session.getLogoutText(),
                    session.getDurationText()
                });
            }
            int active = databaseService.getSessionDao().countActiveSessions();
            sessionCountLabel.setText(sessions.size() + " session(s) recorded,  "
                    + active + " still open (that is who is signed in now).");

        } catch (SQLException e) {
            sessionCountLabel.setText("Could not read the history: " + e.getMessage());
        }
    }

    private void loadCupcakes() {
        reportArea.setText("");
        if (!databaseService.isAvailable()) {
            cupcakeCountLabel.setText("MySQL is not running. Start WampServer to see this.");
            return;
        }
        try {
            int count = databaseService.getCupcakeDao().count();
            cupcakeCountLabel.setText(count + " product(s) in the price report.");

            StringBuilder report = new StringBuilder();
            for (String line : databaseService.getCupcakeDao().categoryPriceReport()) {
                report.append(line).append('\n');
            }
            if (report.length() == 0) {
                report.append("No products copied into MySQL yet. Press "
                        + "\"Refresh the price report\".");
            }
            reportArea.setText(report.toString());
            reportArea.setCaretPosition(0);

        } catch (SQLException e) {
            cupcakeCountLabel.setText("Could not read the cupcakes: " + e.getMessage());
        }
    }

    /**
     * @return true when MySQL is reachable, otherwise shows a message
     */
    private boolean checkAvailable() {
        if (databaseService.isAvailable()) {
            return true;
        }
        JOptionPane.showMessageDialog(this,
                "The MySQL database is not connected, so this action cannot run.\n\n"
                + databaseService.getStatus()
                + "\n\nStart WampServer, then close and reopen the program."
                + "\nThe cupcake and user records in the text files are not affected.",
                "Database not connected", JOptionPane.WARNING_MESSAGE);
        return false;
    }

    @Override
    public String getModuleTitle() {
        return "Staff Registry";
    }

    @Override
    public void refresh() {
        dbStatusLabel.setText("Database: " + databaseService.getStatus());
        loadUsers();
        loadSessions();
        loadCupcakes();
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel cupcakeCountLabel;
    private javax.swing.JLabel dbStatusLabel;
    private javax.swing.JButton deleteBtn;
    private javax.swing.JButton refreshBtn;
    private javax.swing.JButton registerBtn;
    private javax.swing.JTextArea reportArea;
    private javax.swing.JLabel reportLabel;
    private javax.swing.JScrollPane reportScroll;
    private javax.swing.JLabel sessionCountLabel;
    private javax.swing.JScrollPane sessionScroll;
    private javax.swing.JLabel sessionsLabel;
    private javax.swing.JTable sessionTable;
    private javax.swing.JLabel subtitleLabel;
    private javax.swing.JButton syncBtn;
    private javax.swing.JLabel titleLabel;
    private javax.swing.JLabel userCountLabel;
    private javax.swing.JScrollPane userScroll;
    private javax.swing.JLabel usersLabel;
    private javax.swing.JTable userTable;
    // End of variables declaration//GEN-END:variables
}
