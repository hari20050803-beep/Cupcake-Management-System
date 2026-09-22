/*
 * The Sweet Cupcake Shop  -  CSE4006 Object Oriented Programming (WRIT1)
 *
 * File    : RegisterCashierPanel.java
 * Package : com.mycompany.oop.ui
 * Layout  : designed in the Apache NetBeans Form Editor (RegisterCashierPanel.form)
 */

package com.mycompany.oop.ui;

import com.mycompany.oop.db.DatabaseService;
import com.mycompany.oop.exception.ShopException;
import com.mycompany.oop.model.Cashier;
import com.mycompany.oop.model.User;
import com.mycompany.oop.service.ShopService;
import com.mycompany.oop.service.UserFileStore;
import com.mycompany.oop.util.FileHelper;
import com.mycompany.oop.util.Validator;
import java.io.IOException;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 * Screen used by a Manager to create a new staff account for a cashier.
 *
 * This is the one functionality that belongs to the Manager user level only.
 * The Manager chooses the username, gives the new cashier a work email address
 * and sets their first password; the cashier can then sign in straight away.
 *
 * Access is checked twice on purpose. The dashboard only builds this screen for
 * a user that answers true to canManageUsers(), and ShopService checks the same
 * thing again before it writes anything. If the second check ever fails an
 * AccessDeniedException is thrown and nothing is saved, so the rule holds even
 * if the screen were reached some other way.
 *
 * @author The Sweet Cupcake Shop development team
 */
public class RegisterCashierPanel extends javax.swing.JPanel implements ShopModule {

    private static final long serialVersionUID = 1L;

    private static final String[] COLUMNS =
            {"User ID", "Username", "Email", "User Level"};

    private final transient ShopService shopService;
    private final transient User currentUser;
    private final transient DatabaseService databaseService;
    private final DefaultTableModel tableModel;

    /**
     * Creates new form RegisterCashierPanel.
     *
     * @param shopService     the business rules of the shop
     * @param currentUser     the Manager who is signed in
     * @param databaseService MySQL, where sign-in is checked; may be null
     */
    public RegisterCashierPanel(ShopService shopService, User currentUser,
            DatabaseService databaseService) {
        this.shopService = shopService;
        this.currentUser = currentUser;
        this.databaseService = databaseService;

        initComponents();
        setBackground(AppTheme.BACKGROUND);
        formPanel.setBorder(javax.swing.BorderFactory.createLineBorder(AppTheme.BORDER));
        AppTheme.primaryButton(createBtn);
        AppTheme.secondaryButton(clearBtn);
        userScroll.setBorder(javax.swing.BorderFactory.createLineBorder(AppTheme.BORDER));

        tableModel = new DefaultTableModel(COLUMNS, 0) {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        userTable.setModel(tableModel);
        AppTheme.styleTable(userTable, -1);
        userTable.getColumnModel().getColumn(0).setPreferredWidth(90);
        userTable.getColumnModel().getColumn(1).setPreferredWidth(200);
        userTable.getColumnModel().getColumn(2).setPreferredWidth(320);
        userTable.getColumnModel().getColumn(3).setPreferredWidth(150);

        fileLabel.setText("Staff records: "
                + FileHelper.absolutePathOf(UserFileStore.FILE_NAME));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        titleLabel = new javax.swing.JLabel();
        subtitleLabel = new javax.swing.JLabel();
        formPanel = new javax.swing.JPanel();
        userLabel = new javax.swing.JLabel();
        usernameField = new javax.swing.JTextField();
        userHint = new javax.swing.JLabel();
        emailLabel = new javax.swing.JLabel();
        emailField = new javax.swing.JTextField();
        emailHint = new javax.swing.JLabel();
        passLabel = new javax.swing.JLabel();
        passwordField = new javax.swing.JPasswordField();
        passHint = new javax.swing.JLabel();
        confirmLabel = new javax.swing.JLabel();
        confirmField = new javax.swing.JPasswordField();
        confirmHint = new javax.swing.JLabel();
        createBtn = new javax.swing.JButton();
        clearBtn = new javax.swing.JButton();
        messageLabel = new javax.swing.JLabel();
        accountsLabel = new javax.swing.JLabel();
        userScroll = new javax.swing.JScrollPane();
        userTable = new javax.swing.JTable();
        countLabel = new javax.swing.JLabel();
        fileLabel = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(1020, 634));
        setLayout(null);

        titleLabel.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        titleLabel.setText("Create Cashier Account");
        titleLabel.setForeground(new java.awt.Color(140, 47, 84));
        this.add(titleLabel);
        titleLabel.setBounds(24, 16, 600, 28);

        subtitleLabel.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        subtitleLabel.setText("Only a Manager can create staff accounts. The Manager sets the email address and the first password.");
        subtitleLabel.setForeground(new java.awt.Color(122, 112, 117));
        this.add(subtitleLabel);
        subtitleLabel.setBounds(24, 46, 900, 20);

        formPanel.setBackground(new java.awt.Color(255, 255, 255));
        formPanel.setLayout(null);
        userLabel.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        userLabel.setText("Username");
        formPanel.add(userLabel);
        userLabel.setBounds(30, 26, 150, 24);

        usernameField.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        formPanel.add(usernameField);
        usernameField.setBounds(190, 24, 230, 28);

        userHint.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        userHint.setText("Letters only, 3 to 30 characters");
        userHint.setForeground(new java.awt.Color(122, 112, 117));
        formPanel.add(userHint);
        userHint.setBounds(434, 26, 500, 22);

        emailLabel.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        emailLabel.setText("Email");
        formPanel.add(emailLabel);
        emailLabel.setBounds(30, 68, 150, 24);

        emailField.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        formPanel.add(emailField);
        emailField.setBounds(190, 66, 230, 28);

        emailHint.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        emailHint.setText("Work address, for example name@sweetcupcake.lk");
        emailHint.setForeground(new java.awt.Color(122, 112, 117));
        formPanel.add(emailHint);
        emailHint.setBounds(434, 68, 500, 22);

        passLabel.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        passLabel.setText("Password");
        formPanel.add(passLabel);
        passLabel.setBounds(30, 110, 150, 24);

        passwordField.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        formPanel.add(passwordField);
        passwordField.setBounds(190, 108, 230, 28);

        passHint.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        passHint.setText("At least 6 characters, no spaces");
        passHint.setForeground(new java.awt.Color(122, 112, 117));
        formPanel.add(passHint);
        passHint.setBounds(434, 110, 500, 22);

        confirmLabel.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        confirmLabel.setText("Confirm Password");
        formPanel.add(confirmLabel);
        confirmLabel.setBounds(30, 152, 150, 24);

        confirmField.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        formPanel.add(confirmField);
        confirmField.setBounds(190, 150, 230, 28);

        confirmHint.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        confirmHint.setText("Type the same password again");
        confirmHint.setForeground(new java.awt.Color(122, 112, 117));
        formPanel.add(confirmHint);
        confirmHint.setBounds(434, 152, 500, 22);

        createBtn.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        createBtn.setText("Create Account");
        createBtn.setBackground(new java.awt.Color(140, 47, 84));
        createBtn.setForeground(new java.awt.Color(255, 255, 255));
        createBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createBtnActionPerformed(evt);
            }
        });
        formPanel.add(createBtn);
        createBtn.setBounds(190, 192, 160, 34);

        clearBtn.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        clearBtn.setText("Clear");
        clearBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearBtnActionPerformed(evt);
            }
        });
        formPanel.add(clearBtn);
        clearBtn.setBounds(362, 192, 110, 34);

        this.add(formPanel);
        formPanel.setBounds(24, 84, 972, 240);

        messageLabel.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        messageLabel.setText(" ");
        messageLabel.setForeground(new java.awt.Color(176, 42, 48));
        this.add(messageLabel);
        messageLabel.setBounds(24, 336, 972, 24);

        accountsLabel.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        accountsLabel.setText("Existing staff accounts");
        this.add(accountsLabel);
        accountsLabel.setBounds(24, 368, 400, 22);

        userTable.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        userScroll.setViewportView(userTable);
        this.add(userScroll);
        userScroll.setBounds(24, 396, 972, 170);

        countLabel.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        countLabel.setText(" ");
        countLabel.setForeground(new java.awt.Color(122, 112, 117));
        this.add(countLabel);
        countLabel.setBounds(24, 576, 972, 18);

        fileLabel.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        fileLabel.setText(" ");
        fileLabel.setForeground(new java.awt.Color(122, 112, 117));
        this.add(fileLabel);
        fileLabel.setBounds(24, 598, 972, 18);

    }// </editor-fold>//GEN-END:initComponents

    private void createBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createBtnActionPerformed
        createAccount();
    }//GEN-LAST:event_createBtnActionPerformed

    private void clearBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearBtnActionPerformed
        clearForm();
    }//GEN-LAST:event_clearBtnActionPerformed

    /* ==================================================================
     * Behaviour
     * ================================================================== */

    /**
     * Validates the form and asks the service to create the cashier account.
     */
    private void createAccount() {
        try {
            String username = Validator.requireText(usernameField.getText(),
                    "Username");
            String email = Validator.requireText(emailField.getText(), "Email");

            String password = new String(passwordField.getPassword());
            String confirm = new String(confirmField.getPassword());
            Validator.requireValidPassword(password, confirm);

            // currentUser is passed in so the service can check the user level
            // before it writes anything.
            Cashier created = shopService.createCashierAccount(
                    currentUser, username, email, password);

            // Sign-in is checked in MySQL, so the new cashier goes there too.
            String registry = (databaseService == null) ? ""
                    : "\n" + databaseService.addToRegistry(created, created.getUsername(), password);

            JOptionPane.showMessageDialog(this,
                    "Cashier account created:\n\n"
                    + "User ID   : " + created.getUserId() + "\n"
                    + "Username  : " + created.getUsername() + "\n"
                    + "Email     : " + created.getEmail() + "\n"
                    + "User level: " + created.getRole().getDisplayName() + "\n\n"
                    + created.getUsername() + " signs in with Login as Cashier,\n"
                    + "this username and this password." + registry,
                    "Account created", JOptionPane.INFORMATION_MESSAGE);

            messageLabel.setForeground(AppTheme.SUCCESS);
            messageLabel.setText("Account " + created.getUsername() + " created.");
            clearForm();
            loadAccounts();

        } catch (ShopException e) {
            // Catches validation problems, a duplicate username or email, and
            // any access denied error, because all three extend ShopException.
            messageLabel.setForeground(AppTheme.ERROR);
            messageLabel.setText(e.getMessage());
            JOptionPane.showMessageDialog(this, e.getMessage(),
                    "Please check your entry", JOptionPane.ERROR_MESSAGE);

        } catch (IOException e) {
            messageLabel.setForeground(AppTheme.ERROR);
            messageLabel.setText("The staff records could not be saved.");
            JOptionPane.showMessageDialog(this,
                    "The staff records could not be saved.\n\n" + e.getMessage(),
                    "Save problem", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearForm() {
        usernameField.setText("");
        emailField.setText("");
        passwordField.setText("");
        confirmField.setText("");
        usernameField.requestFocusInWindow();
    }

    /**
     * Reads the staff accounts and shows them. No password is ever displayed.
     */
    private void loadAccounts() {
        tableModel.setRowCount(0);
        try {
            List<User> users = shopService.getAllUsers(currentUser);
            for (User user : users) {
                tableModel.addRow(new Object[]{
                    user.getUserId(),
                    user.getUsername(),
                    user.getEmail(),
                    user.getRole().getDisplayName()
                });
            }
            countLabel.setText(users.size() + " staff account(s), "
                    + countManagers(users) + " of them Manager.");

        } catch (IOException e) {
            countLabel.setText("The staff records could not be read.");
        } catch (ShopException e) {
            countLabel.setText(e.getMessage());
        }
    }

    /**
     * @param users every saved account
     * @return how many of them may manage accounts
     */
    private int countManagers(List<User> users) {
        int managers = 0;
        for (User user : users) {
            // Asked of the object, not of the role name.
            if (user.canManageUsers()) {
                managers++;
            }
        }
        return managers;
    }

    @Override
    public String getModuleTitle() {
        return "Create Cashier Account";
    }

    @Override
    public void refresh() {
        messageLabel.setText(" ");
        loadAccounts();
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel accountsLabel;
    private javax.swing.JButton clearBtn;
    private javax.swing.JPasswordField confirmField;
    private javax.swing.JLabel confirmHint;
    private javax.swing.JLabel confirmLabel;
    private javax.swing.JLabel countLabel;
    private javax.swing.JButton createBtn;
    private javax.swing.JTextField emailField;
    private javax.swing.JLabel emailHint;
    private javax.swing.JLabel emailLabel;
    private javax.swing.JLabel fileLabel;
    private javax.swing.JPanel formPanel;
    private javax.swing.JLabel messageLabel;
    private javax.swing.JLabel passHint;
    private javax.swing.JLabel passLabel;
    private javax.swing.JPasswordField passwordField;
    private javax.swing.JLabel subtitleLabel;
    private javax.swing.JLabel titleLabel;
    private javax.swing.JLabel userHint;
    private javax.swing.JLabel userLabel;
    private javax.swing.JTextField usernameField;
    private javax.swing.JScrollPane userScroll;
    private javax.swing.JTable userTable;
    // End of variables declaration//GEN-END:variables
}
