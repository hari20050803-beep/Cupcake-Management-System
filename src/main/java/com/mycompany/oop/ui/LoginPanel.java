/*
 * The Sweet Cupcake Shop  -  CSE4006 Object Oriented Programming (WRIT1)
 *
 * File    : LoginPanel.java
 * Package : com.mycompany.oop.ui
 * Layout  : designed in the Apache NetBeans Form Editor (LoginPanel.form)
 */

package com.mycompany.oop.ui;

import com.mycompany.oop.db.DatabaseService;
import com.mycompany.oop.exception.AuthenticationException;
import com.mycompany.oop.exception.ShopException;
import com.mycompany.oop.model.Role;
import com.mycompany.oop.model.User;
import com.mycompany.oop.service.AuthService;
import com.mycompany.oop.service.ShopService;
import java.io.IOException;
import javax.swing.JButton;

/**
 * The login screen.
 *
 * This class does one job: collect the sign-in details, ask the service
 * classes to check them, and hand the resulting user object back to MainFrame.
 * It holds no dashboard code of its own, so the login screen and the two
 * dashboards stay completely separate.
 *
 * The member of staff first chooses their user level. A Manager then signs in
 * with an email address, and a Cashier with the username the Manager gave
 * them. The details are checked in the MySQL database on WampServer; only when
 * MySQL is not running are they checked against the staff file instead, so the
 * shop can still open.
 *
 * @author The Sweet Cupcake Shop development team
 */
public class LoginPanel extends javax.swing.JPanel {

    private static final long serialVersionUID = 1L;

    private final transient MainFrame mainFrame;
    private final transient AuthService authService;
    private final transient ShopService shopService;
    private final transient DatabaseService databaseService;

    /**
     * Creates new form LoginPanel.
     *
     * @param mainFrame       the window this screen lives in
     * @param authService     checks the details against the staff file when
     *                        MySQL is not running
     * @param shopService     the business rules of the shop
     * @param databaseService checks the details in MySQL, may be null
     */
    public LoginPanel(MainFrame mainFrame, AuthService authService,
            ShopService shopService, DatabaseService databaseService) {

        this.mainFrame = mainFrame;
        this.authService = authService;
        this.shopService = shopService;
        this.databaseService = databaseService;

        initComponents();

        // The two user levels come straight from the Role type, so the list can
        // never offer a level the rest of the program does not know about.
        roleBox.setModel(new javax.swing.DefaultComboBoxModel<>(Role.values()));
        showLoginIdLabel();

        // Things the Form Editor cannot set: the logo, which is drawn in code,
        // and the line saying where sign-in is being checked.
        logoLabel.setIcon(AppTheme.cupcakeIcon(96));
        showDatabaseStatus();
        AppTheme.primaryButton(loginButton);
        AppTheme.secondaryButton(clearButton);
        AppTheme.secondaryButton(exitButton);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bannerPanel = new javax.swing.JPanel();
        logoLabel = new javax.swing.JLabel();
        shopNameLabel = new javax.swing.JLabel();
        taglineLabel = new javax.swing.JLabel();
        storageLabel = new javax.swing.JLabel();
        dbStatusLabel = new javax.swing.JLabel();
        formPanel = new javax.swing.JPanel();
        loginTitleLabel = new javax.swing.JLabel();
        loginHintLabel = new javax.swing.JLabel();
        roleLabel = new javax.swing.JLabel();
        roleBox = new javax.swing.JComboBox<>();
        loginIdLabel = new javax.swing.JLabel();
        loginIdField = new javax.swing.JTextField();
        loginIdHint = new javax.swing.JLabel();
        passLabel = new javax.swing.JLabel();
        passwordField = new javax.swing.JPasswordField();
        showPasswordBox = new javax.swing.JCheckBox();
        loginButton = new javax.swing.JButton();
        clearButton = new javax.swing.JButton();
        exitButton = new javax.swing.JButton();
        messageLabel = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(1260, 760));
        setLayout(null);

        bannerPanel.setBackground(new java.awt.Color(140, 47, 84));
        bannerPanel.setLayout(null);
        bannerPanel.add(logoLabel);
        logoLabel.setBounds(60, 170, 96, 96);

        shopNameLabel.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        shopNameLabel.setText("The Sweet Cupcake Shop");
        shopNameLabel.setForeground(new java.awt.Color(255, 255, 255));
        bannerPanel.add(shopNameLabel);
        shopNameLabel.setBounds(60, 300, 420, 36);

        taglineLabel.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        taglineLabel.setText("Cupcake Transaction Management System");
        taglineLabel.setForeground(new java.awt.Color(240, 214, 224));
        bannerPanel.add(taglineLabel);
        taglineLabel.setBounds(60, 336, 420, 22);

        storageLabel.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        storageLabel.setText("Serving The Sweet Cupcake Shop since 2025.");
        storageLabel.setForeground(new java.awt.Color(240, 214, 224));
        bannerPanel.add(storageLabel);
        storageLabel.setBounds(60, 656, 440, 20);

        dbStatusLabel.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        dbStatusLabel.setText(" ");
        dbStatusLabel.setForeground(new java.awt.Color(240, 214, 224));
        bannerPanel.add(dbStatusLabel);
        dbStatusLabel.setBounds(60, 680, 440, 20);

        this.add(bannerPanel);
        bannerPanel.setBounds(0, 0, 520, 760);

        formPanel.setBackground(new java.awt.Color(255, 255, 255));
        formPanel.setLayout(null);
        loginTitleLabel.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        loginTitleLabel.setText("Staff Login");
        loginTitleLabel.setForeground(new java.awt.Color(140, 47, 84));
        formPanel.add(loginTitleLabel);
        loginTitleLabel.setBounds(110, 190, 460, 32);

        loginHintLabel.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        loginHintLabel.setText("Choose Manager or Cashier, then sign in.");
        loginHintLabel.setForeground(new java.awt.Color(122, 112, 117));
        formPanel.add(loginHintLabel);
        loginHintLabel.setBounds(110, 224, 460, 20);

        roleLabel.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        roleLabel.setText("Login as");
        formPanel.add(roleLabel);
        roleLabel.setBounds(110, 276, 90, 22);

        roleBox.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        roleBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                roleBoxActionPerformed(evt);
            }
        });
        formPanel.add(roleBox);
        roleBox.setBounds(210, 274, 280, 28);

        loginIdLabel.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        loginIdLabel.setText("Email");
        formPanel.add(loginIdLabel);
        loginIdLabel.setBounds(110, 320, 90, 22);

        loginIdField.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        loginIdField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginIdFieldActionPerformed(evt);
            }
        });
        formPanel.add(loginIdField);
        loginIdField.setBounds(210, 318, 280, 28);

        loginIdHint.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        loginIdHint.setText(" ");
        loginIdHint.setForeground(new java.awt.Color(122, 112, 117));
        formPanel.add(loginIdHint);
        loginIdHint.setBounds(210, 348, 300, 18);

        passLabel.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        passLabel.setText("Password");
        formPanel.add(passLabel);
        passLabel.setBounds(110, 380, 90, 22);

        passwordField.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        passwordField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                passwordFieldActionPerformed(evt);
            }
        });
        formPanel.add(passwordField);
        passwordField.setBounds(210, 378, 280, 28);

        showPasswordBox.setBackground(new java.awt.Color(255, 255, 255));
        showPasswordBox.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        showPasswordBox.setText("Show password");
        showPasswordBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showPasswordBoxActionPerformed(evt);
            }
        });
        formPanel.add(showPasswordBox);
        showPasswordBox.setBounds(208, 410, 282, 22);

        loginButton.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        loginButton.setText("Log In");
        loginButton.setBackground(new java.awt.Color(140, 47, 84));
        loginButton.setForeground(new java.awt.Color(255, 255, 255));
        loginButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginButtonActionPerformed(evt);
            }
        });
        formPanel.add(loginButton);
        loginButton.setBounds(210, 456, 110, 34);

        clearButton.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        clearButton.setText("Clear");
        clearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearButtonActionPerformed(evt);
            }
        });
        formPanel.add(clearButton);
        clearButton.setBounds(330, 456, 90, 34);

        exitButton.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        exitButton.setText("Exit");
        exitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitButtonActionPerformed(evt);
            }
        });
        formPanel.add(exitButton);
        exitButton.setBounds(430, 456, 72, 34);

        messageLabel.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        messageLabel.setText(" ");
        messageLabel.setForeground(new java.awt.Color(176, 42, 48));
        formPanel.add(messageLabel);
        messageLabel.setBounds(110, 508, 560, 24);

        this.add(formPanel);
        formPanel.setBounds(520, 0, 740, 760);

    }// </editor-fold>//GEN-END:initComponents

    private void roleBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_roleBoxActionPerformed
        // Switching level changes what the first box asks for.
        showLoginIdLabel();
        loginIdField.setText("");
        messageLabel.setText(" ");
        loginIdField.requestFocusInWindow();
    }//GEN-LAST:event_roleBoxActionPerformed

    private void loginIdFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginIdFieldActionPerformed
        // Pressing Enter in the first box moves on to the password box.
        passwordField.requestFocusInWindow();
    }//GEN-LAST:event_loginIdFieldActionPerformed

    private void passwordFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_passwordFieldActionPerformed
        // Pressing Enter in the password box signs the user in.
        attemptLogin();
    }//GEN-LAST:event_passwordFieldActionPerformed

    private void showPasswordBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showPasswordBoxActionPerformed
        if (showPasswordBox.isSelected()) {
            passwordField.setEchoChar((char) 0);        // show the password
        } else {
            passwordField.setEchoChar('•');         // hide it again
        }
    }//GEN-LAST:event_showPasswordBoxActionPerformed

    private void loginButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginButtonActionPerformed
        attemptLogin();
    }//GEN-LAST:event_loginButtonActionPerformed

    private void clearButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearButtonActionPerformed
        clearForm();
    }//GEN-LAST:event_clearButtonActionPerformed

    private void exitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitButtonActionPerformed
        System.exit(0);
    }//GEN-LAST:event_exitButtonActionPerformed

    /* ==================================================================
     * Behaviour
     * ================================================================== */

    /**
     * @return the user level chosen in the Login as box
     */
    private Role selectedRole() {
        return (Role) roleBox.getSelectedItem();
    }

    /**
     * A Manager signs in with an email address and a Cashier with a username,
     * so the first box is relabelled to match the level chosen.
     */
    private void showLoginIdLabel() {
        if (selectedRole() == Role.MANAGER) {
            loginIdLabel.setText("Email");
            loginIdHint.setText("The email address of your Manager account");
        } else {
            loginIdLabel.setText("Username");
            loginIdHint.setText("The username your Manager gave you");
        }
    }

    /**
     * Reads the form and tries to sign the member of staff in.
     */
    private void attemptLogin() {
        messageLabel.setText(" ");

        Role role = selectedRole();
        String loginId = loginIdField.getText();
        String password = new String(passwordField.getPassword());

        try {
            User user;

            if (databaseService != null && databaseService.isAvailable()) {
                // WampServer is running: the account is checked in the MySQL
                // app_user table, and the session is recorded there.
                user = databaseService.login(role, loginId, password);
            } else {
                // WampServer is not running: the same check against
                // data/users.txt, so the shop can still open.
                user = authService.login(role, loginId, password);
            }

            // The window decides which dashboard the user object deserves.
            mainFrame.openDashboard(user);

        } catch (AuthenticationException e) {
            // Wrong details: keep the email or username, clear the password.
            messageLabel.setText(e.getMessage());
            passwordField.setText("");
            passwordField.requestFocusInWindow();

        } catch (ShopException e) {
            // A box is empty or in the wrong format, or the staff records could
            // not be read. AuthenticationException is caught first above,
            // because it is itself a kind of ShopException.
            messageLabel.setText(e.getMessage());

        } catch (IOException e) {
            messageLabel.setText("Your staff record could not be read. Please tell the Manager.");
        }
    }

    /**
     * Empties the boxes and the message, ready for another attempt. The user
     * level chosen is kept.
     */
    private void clearForm() {
        loginIdField.setText("");
        passwordField.setText("");
        showPasswordBox.setSelected(false);
        passwordField.setEchoChar('•');
        messageLabel.setText(" ");
        loginIdField.requestFocusInWindow();
    }

    /**
     * Says where sign-in is being checked: MySQL when WampServer is running,
     * otherwise the staff file.
     */
    private void showDatabaseStatus() {
        if (databaseService != null && databaseService.isAvailable()) {
            dbStatusLabel.setText("Connected to MySQL. Sign-in is checked in the database.");
        } else {
            dbStatusLabel.setText("MySQL is not running: sign-in uses the staff file.");
        }
    }

    /**
     * Puts the cursor in the first box: the email or the username.
     */
    public void focusLoginId() {
        loginIdField.requestFocusInWindow();
    }

    /**
     * @return the Log In button, so the window can make it the default
     */
    public JButton getLoginButton() {
        return loginButton;
    }

    /**
     * @return the business rules object this screen was given
     */
    public ShopService getShopService() {
        return shopService;
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel bannerPanel;
    private javax.swing.JButton clearButton;
    private javax.swing.JLabel dbStatusLabel;
    private javax.swing.JButton exitButton;
    private javax.swing.JPanel formPanel;
    private javax.swing.JButton loginButton;
    private javax.swing.JLabel loginHintLabel;
    private javax.swing.JTextField loginIdField;
    private javax.swing.JLabel loginIdHint;
    private javax.swing.JLabel loginIdLabel;
    private javax.swing.JLabel loginTitleLabel;
    private javax.swing.JLabel logoLabel;
    private javax.swing.JLabel messageLabel;
    private javax.swing.JLabel passLabel;
    private javax.swing.JPasswordField passwordField;
    private javax.swing.JComboBox<com.mycompany.oop.model.Role> roleBox;
    private javax.swing.JLabel roleLabel;
    private javax.swing.JLabel shopNameLabel;
    private javax.swing.JCheckBox showPasswordBox;
    private javax.swing.JLabel storageLabel;
    private javax.swing.JLabel taglineLabel;
    // End of variables declaration//GEN-END:variables
}
