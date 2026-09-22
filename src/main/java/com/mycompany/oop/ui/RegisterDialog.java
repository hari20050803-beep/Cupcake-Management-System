/*
 * The Sweet Cupcake Shop  -  CSE4006 Object Oriented Programming (WRIT1)
 *
 * File    : RegisterDialog.java
 * Package : com.mycompany.oop.ui
 * Layout  : designed in the Apache NetBeans Form Editor (RegisterDialog.form)
 */

package com.mycompany.oop.ui;

import com.mycompany.oop.db.DatabaseService;
import com.mycompany.oop.exception.ShopException;
import com.mycompany.oop.model.Role;
import com.mycompany.oop.model.User;
import com.mycompany.oop.service.ShopService;
import com.mycompany.oop.util.Validator;
import java.io.IOException;
import javax.swing.JOptionPane;

/**
 * Dialog used by a Manager to create a staff account at either user level,
 * with the person's full name.
 *
 * Registration is placed inside the Manager area rather than on the login
 * screen on purpose. The scenario states that only a Manager may create user
 * accounts, so letting anybody at the login screen register themselves would
 * break that rule.
 *
 * The account is saved in the staff file through the same rules as Create
 * Cashier Account, then in the MySQL database, where sign-in is checked. The
 * password is salted and hashed before it reaches the database, so the plain
 * password is never stored.
 *
 * @author The Sweet Cupcake Shop development team
 */
public class RegisterDialog extends javax.swing.JDialog {

    private static final long serialVersionUID = 1L;

    private final transient ShopService shopService;
    private final transient User currentUser;
    private final transient DatabaseService databaseService;
    private boolean registered;

    /**
     * Creates new form RegisterDialog.
     *
     * @param owner           the window this dialog belongs to
     * @param shopService     the business rules, which save the staff file
     * @param currentUser     the Manager who is signed in
     * @param databaseService the MySQL features
     */
    public RegisterDialog(java.awt.Window owner, ShopService shopService,
            User currentUser, DatabaseService databaseService) {
        super(owner, ModalityType.APPLICATION_MODAL);
        this.shopService = shopService;
        this.currentUser = currentUser;
        this.databaseService = databaseService;

        initComponents();
        roleCombo.setModel(new javax.swing.DefaultComboBoxModel<>(Role.values()));
        getRootPane().setDefaultButton(registerBtn);
        AppTheme.primaryButton(registerBtn);
        AppTheme.secondaryButton(cancelBtn);
        setLocationRelativeTo(owner);
    }

    /**
     * @return true when an account was actually created
     */
    public boolean isRegistered() {
        return registered;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        bannerPanel = new javax.swing.JPanel();
        bannerTitle = new javax.swing.JLabel();
        bannerSub = new javax.swing.JLabel();
        userLabel = new javax.swing.JLabel();
        usernameField = new javax.swing.JTextField();
        nameLabel = new javax.swing.JLabel();
        fullNameField = new javax.swing.JTextField();
        emailLabel = new javax.swing.JLabel();
        emailField = new javax.swing.JTextField();
        roleLabel = new javax.swing.JLabel();
        roleCombo = new javax.swing.JComboBox<>();
        passLabel = new javax.swing.JLabel();
        passwordField = new javax.swing.JPasswordField();
        confirmLabel = new javax.swing.JLabel();
        confirmField = new javax.swing.JPasswordField();
        registerBtn = new javax.swing.JButton();
        cancelBtn = new javax.swing.JButton();
        messageLabel = new javax.swing.JLabel();
        hashNote = new javax.swing.JLabel();

        setTitle("Register New User");
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(520, 460));
        jPanel1.setLayout(null);
        bannerPanel.setBackground(new java.awt.Color(140, 47, 84));
        bannerPanel.setLayout(null);
        bannerTitle.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        bannerTitle.setText("Create a staff account");
        bannerTitle.setForeground(new java.awt.Color(255, 255, 255));
        bannerPanel.add(bannerTitle);
        bannerTitle.setBounds(24, 14, 470, 26);

        bannerSub.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        bannerSub.setText("Saved in the MySQL database and the staff file");
        bannerSub.setForeground(new java.awt.Color(240, 214, 224));
        bannerPanel.add(bannerSub);
        bannerSub.setBounds(24, 42, 470, 20);

        jPanel1.add(bannerPanel);
        bannerPanel.setBounds(0, 0, 520, 76);

        userLabel.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        userLabel.setText("Username");
        jPanel1.add(userLabel);
        userLabel.setBounds(30, 104, 150, 24);

        usernameField.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jPanel1.add(usernameField);
        usernameField.setBounds(190, 102, 280, 28);

        nameLabel.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        nameLabel.setText("Full name");
        jPanel1.add(nameLabel);
        nameLabel.setBounds(30, 144, 150, 24);

        fullNameField.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jPanel1.add(fullNameField);
        fullNameField.setBounds(190, 142, 280, 28);

        emailLabel.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        emailLabel.setText("Email");
        jPanel1.add(emailLabel);
        emailLabel.setBounds(30, 184, 150, 24);

        emailField.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jPanel1.add(emailField);
        emailField.setBounds(190, 182, 280, 28);

        roleLabel.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        roleLabel.setText("User level");
        jPanel1.add(roleLabel);
        roleLabel.setBounds(30, 224, 150, 24);

        roleCombo.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jPanel1.add(roleCombo);
        roleCombo.setBounds(190, 222, 280, 28);

        passLabel.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        passLabel.setText("Password");
        jPanel1.add(passLabel);
        passLabel.setBounds(30, 264, 150, 24);

        passwordField.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jPanel1.add(passwordField);
        passwordField.setBounds(190, 262, 280, 28);

        confirmLabel.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        confirmLabel.setText("Confirm password");
        jPanel1.add(confirmLabel);
        confirmLabel.setBounds(30, 304, 150, 24);

        confirmField.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jPanel1.add(confirmField);
        confirmField.setBounds(190, 302, 280, 28);

        registerBtn.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        registerBtn.setText("Register");
        registerBtn.setBackground(new java.awt.Color(140, 47, 84));
        registerBtn.setForeground(new java.awt.Color(255, 255, 255));
        registerBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                registerBtnActionPerformed(evt);
            }
        });
        jPanel1.add(registerBtn);
        registerBtn.setBounds(190, 344, 140, 34);

        cancelBtn.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        cancelBtn.setText("Cancel");
        cancelBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelBtnActionPerformed(evt);
            }
        });
        jPanel1.add(cancelBtn);
        cancelBtn.setBounds(342, 344, 128, 34);

        messageLabel.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        messageLabel.setText(" ");
        messageLabel.setForeground(new java.awt.Color(176, 42, 48));
        jPanel1.add(messageLabel);
        messageLabel.setBounds(30, 388, 460, 22);

        hashNote.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        hashNote.setText("The password is salted and hashed before it is saved.");
        hashNote.setForeground(new java.awt.Color(122, 112, 117));
        jPanel1.add(hashNote);
        hashNote.setBounds(30, 412, 460, 20);


        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 520, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 460, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void registerBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_registerBtnActionPerformed
        register();
    }//GEN-LAST:event_registerBtnActionPerformed

    private void cancelBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelBtnActionPerformed
        dispose();
    }//GEN-LAST:event_cancelBtnActionPerformed


    /**
     * Validates the form and creates the staff account: first in the staff
     * file, then in the MySQL database, where sign-in is checked.
     */
    private void register() {
        messageLabel.setText(" ");
        try {
            String username = Validator.requireText(usernameField.getText(), "Username");
            String fullName = Validator.requireText(fullNameField.getText(), "Full name");
            String email = Validator.requireText(emailField.getText(), "Email");

            String password = new String(passwordField.getPassword());
            String confirm = new String(confirmField.getPassword());
            Validator.requireValidPassword(password, confirm);

            Role role = (Role) roleCombo.getSelectedItem();

            // The service checks that the signed-in user is a Manager, that
            // the username is letters only and the email is valid, and that
            // neither is taken, before anything is written.
            User created = shopService.createStaffAccount(currentUser, role,
                    username, email, password);
            String registry = databaseService.addToRegistry(created, fullName, password);

            registered = true;
            JOptionPane.showMessageDialog(this,
                    "Staff account created:\n\n"
                    + "User ID    : " + created.getUserId() + "\n"
                    + "Username   : " + created.getUsername() + "\n"
                    + "Full name  : " + fullName + "\n"
                    + "Email      : " + created.getEmail() + "\n"
                    + "User level : " + role.getDisplayName() + "\n\n"
                    + (role == Role.MANAGER
                            ? "Signs in with Login as Manager, this email and password.\n"
                            : "Signs in with Login as Cashier, this username and password.\n")
                    + registry,
                    "Registration complete", JOptionPane.INFORMATION_MESSAGE);
            dispose();

        } catch (ShopException e) {
            // Covers an invalid or taken value and a refused requester, since
            // ValidationException and AccessDeniedException both extend it.
            messageLabel.setText(e.getMessage());

        } catch (IOException e) {
            messageLabel.setText("The staff file could not be saved.");
            JOptionPane.showMessageDialog(this,
                    "The account could not be saved.\n\n" + e.getMessage(),
                    "Save problem", JOptionPane.ERROR_MESSAGE);
        }
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel bannerPanel;
    private javax.swing.JLabel bannerSub;
    private javax.swing.JLabel bannerTitle;
    private javax.swing.JButton cancelBtn;
    private javax.swing.JPasswordField confirmField;
    private javax.swing.JLabel confirmLabel;
    private javax.swing.JTextField emailField;
    private javax.swing.JLabel emailLabel;
    private javax.swing.JTextField fullNameField;
    private javax.swing.JLabel hashNote;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel messageLabel;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JLabel passLabel;
    private javax.swing.JPasswordField passwordField;
    private javax.swing.JButton registerBtn;
    private javax.swing.JComboBox<com.mycompany.oop.model.Role> roleCombo;
    private javax.swing.JLabel roleLabel;
    private javax.swing.JLabel userLabel;
    private javax.swing.JTextField usernameField;
    // End of variables declaration//GEN-END:variables
}
