/*
 * The Sweet Cupcake Shop  -  CSE4006 Object Oriented Programming (WRIT1)
 *
 * File    : HomePanel.java
 * Package : com.mycompany.oop.ui
 * Layout  : designed in the Apache NetBeans Form Editor (HomePanel.form)
 */

package com.mycompany.oop.ui;

import com.mycompany.oop.exception.ShopException;
import com.mycompany.oop.model.User;
import com.mycompany.oop.service.ShopService;
import java.io.IOException;
import javax.swing.JLabel;

/**
 * The Dashboard / Home screen, the first thing shown after signing in.
 *
 * It reports who is signed in, what that user level is allowed to do, and a
 * short summary of the catalogue read from the text file. The list of
 * permissions is not written here: it is asked of the user object, and because
 * getPermissions() is overridden by Manager and Cashier, the same line of code
 * produces four entries for one and three for the other.
 *
 * @author The Sweet Cupcake Shop development team
 */
public class HomePanel extends javax.swing.JPanel implements ShopModule {

    private static final long serialVersionUID = 1L;

    private final transient ShopService shopService;
    private final transient User currentUser;

    /**
     * Creates new form HomePanel.
     *
     * @param shopService the business rules of the shop
     * @param currentUser the member of staff who is signed in
     */
    public HomePanel(ShopService shopService, User currentUser) {
        this.shopService = shopService;
        this.currentUser = currentUser;

        initComponents();
        setBackground(AppTheme.BACKGROUND);
        infoPanel.setBorder(javax.swing.BorderFactory.createLineBorder(AppTheme.BORDER));
        statsPanel.setBorder(javax.swing.BorderFactory.createLineBorder(AppTheme.BORDER));
        AppTheme.secondaryButton(refreshBtn);

        welcomeLabel.setText("Welcome, " + currentUser.getUsername() + ".");

        // getRoleDescription() is overridden by both subclasses, so this one
        // line says something different for a Manager and for a Cashier.
        roleLabel.setText(currentUser.getRoleDescription());

        showPermissions();
        refresh();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        titleLabel = new javax.swing.JLabel();
        hintLabel = new javax.swing.JLabel();
        infoPanel = new javax.swing.JPanel();
        welcomeLabel = new javax.swing.JLabel();
        roleLabel = new javax.swing.JLabel();
        permCaption = new javax.swing.JLabel();
        perm1Label = new javax.swing.JLabel();
        perm2Label = new javax.swing.JLabel();
        perm3Label = new javax.swing.JLabel();
        perm4Label = new javax.swing.JLabel();
        statsPanel = new javax.swing.JPanel();
        statsCaption = new javax.swing.JLabel();
        totalLabel = new javax.swing.JLabel();
        categoryLabel = new javax.swing.JLabel();
        accountLabel = new javax.swing.JLabel();
        fileLabel = new javax.swing.JLabel();
        storageNote = new javax.swing.JLabel();
        storageNote2 = new javax.swing.JLabel();
        refreshBtn = new javax.swing.JButton();

        setPreferredSize(new java.awt.Dimension(1020, 634));
        setLayout(null);

        titleLabel.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        titleLabel.setText("Dashboard");
        titleLabel.setForeground(new java.awt.Color(140, 47, 84));
        this.add(titleLabel);
        titleLabel.setBounds(30, 24, 700, 30);

        hintLabel.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        hintLabel.setText("Use the menu on the left to move between screens.");
        hintLabel.setForeground(new java.awt.Color(122, 112, 117));
        this.add(hintLabel);
        hintLabel.setBounds(30, 58, 900, 20);

        infoPanel.setBackground(new java.awt.Color(255, 255, 255));
        infoPanel.setLayout(null);
        welcomeLabel.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        welcomeLabel.setText("Welcome");
        infoPanel.add(welcomeLabel);
        welcomeLabel.setBounds(24, 18, 920, 28);

        roleLabel.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        roleLabel.setText(" ");
        roleLabel.setForeground(new java.awt.Color(122, 112, 117));
        infoPanel.add(roleLabel);
        roleLabel.setBounds(24, 50, 920, 20);

        permCaption.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        permCaption.setText("What this account may do");
        infoPanel.add(permCaption);
        permCaption.setBounds(24, 86, 400, 20);

        perm1Label.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        perm1Label.setText(" ");
        infoPanel.add(perm1Label);
        perm1Label.setBounds(24, 112, 920, 18);

        perm2Label.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        perm2Label.setText(" ");
        infoPanel.add(perm2Label);
        perm2Label.setBounds(24, 134, 920, 18);

        perm3Label.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        perm3Label.setText(" ");
        infoPanel.add(perm3Label);
        perm3Label.setBounds(24, 156, 920, 18);

        perm4Label.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        perm4Label.setText(" ");
        infoPanel.add(perm4Label);
        perm4Label.setBounds(24, 178, 920, 18);

        this.add(infoPanel);
        infoPanel.setBounds(24, 94, 972, 212);

        statsPanel.setBackground(new java.awt.Color(255, 255, 255));
        statsPanel.setLayout(null);
        statsCaption.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        statsCaption.setText("The catalogue at a glance");
        statsPanel.add(statsCaption);
        statsCaption.setBounds(24, 18, 500, 20);

        totalLabel.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        totalLabel.setText(" ");
        statsPanel.add(totalLabel);
        totalLabel.setBounds(24, 48, 920, 20);

        categoryLabel.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        categoryLabel.setText(" ");
        statsPanel.add(categoryLabel);
        categoryLabel.setBounds(24, 74, 920, 20);

        accountLabel.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        accountLabel.setText(" ");
        statsPanel.add(accountLabel);
        accountLabel.setBounds(24, 100, 920, 20);

        fileLabel.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        fileLabel.setText(" ");
        fileLabel.setForeground(new java.awt.Color(122, 112, 117));
        statsPanel.add(fileLabel);
        fileLabel.setBounds(24, 130, 920, 18);

        this.add(statsPanel);
        statsPanel.setBounds(24, 324, 972, 178);

        storageNote.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        storageNote.setText("Every product and every staff account is saved as soon as it is entered.");
        storageNote.setForeground(new java.awt.Color(122, 112, 117));
        this.add(storageNote);
        storageNote.setBounds(30, 520, 960, 20);

        storageNote2.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        storageNote2.setText("Nothing is held only on screen, so it is all still here the next time you sign in.");
        storageNote2.setForeground(new java.awt.Color(122, 112, 117));
        this.add(storageNote2);
        storageNote2.setBounds(30, 542, 960, 20);

        refreshBtn.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        refreshBtn.setText("Refresh");
        refreshBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshBtnActionPerformed(evt);
            }
        });
        this.add(refreshBtn);
        refreshBtn.setBounds(856, 20, 140, 32);

    }// </editor-fold>//GEN-END:initComponents

    private void refreshBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshBtnActionPerformed
        refresh();
    }//GEN-LAST:event_refreshBtnActionPerformed

    /* ==================================================================
     * Behaviour
     * ================================================================== */

    /**
     * Lists what this account may do, taken from the user object itself.
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

    @Override
    public String getModuleTitle() {
        return "Dashboard";
    }

    /**
     * Reads the catalogue and the account list from their files and reports
     * what is in them.
     */
    @Override
    public final void refresh() {
        try {
            int products = shopService.getAllCupcakes().size();
            totalLabel.setText(products + " product(s) in the catalogue.");
            categoryLabel.setText("By category -  " + shopService.getCategorySummary());

            if (currentUser.canManageUsers()) {
                int accounts = shopService.getAllUsers(currentUser).size();
                accountLabel.setText(accounts + " staff account(s) in the user file.");
                accountLabel.setVisible(true);
            } else {
                accountLabel.setVisible(false);
            }

            fileLabel.setText("Records: "
                    + com.mycompany.oop.util.FileHelper.absolutePathOf("cupcakes.txt"));

        } catch (IOException e) {
            totalLabel.setText("The cupcake file could not be read.");
            categoryLabel.setText(e.getMessage());

        } catch (ShopException e) {
            totalLabel.setText("The cupcake file contains a damaged record.");
            categoryLabel.setText(e.getMessage());
        }
    }

    /**
     * @return the member of staff this screen was opened for
     */
    public User getCurrentUser() {
        return currentUser;
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel accountLabel;
    private javax.swing.JLabel categoryLabel;
    private javax.swing.JLabel fileLabel;
    private javax.swing.JLabel hintLabel;
    private javax.swing.JPanel infoPanel;
    private javax.swing.JLabel perm1Label;
    private javax.swing.JLabel perm2Label;
    private javax.swing.JLabel perm3Label;
    private javax.swing.JLabel perm4Label;
    private javax.swing.JLabel permCaption;
    private javax.swing.JButton refreshBtn;
    private javax.swing.JLabel roleLabel;
    private javax.swing.JLabel statsCaption;
    private javax.swing.JPanel statsPanel;
    private javax.swing.JLabel storageNote;
    private javax.swing.JLabel storageNote2;
    private javax.swing.JLabel titleLabel;
    private javax.swing.JLabel totalLabel;
    private javax.swing.JLabel welcomeLabel;
    // End of variables declaration//GEN-END:variables
}
