/*
 * The Sweet Cupcake Shop  -  CSE4006 Object Oriented Programming (WRIT1)
 *
 * File    : CupcakePanel.java
 * Package : com.mycompany.oop.ui
 * Layout  : designed in the Apache NetBeans Form Editor (CupcakePanel.form)
 */

package com.mycompany.oop.ui;

import com.mycompany.oop.exception.ShopException;
import com.mycompany.oop.model.Cupcake;
import com.mycompany.oop.model.User;
import com.mycompany.oop.service.CupcakeFileStore;
import com.mycompany.oop.service.ShopService;
import com.mycompany.oop.util.FileHelper;
import java.io.IOException;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 * Screen that lists every product in the shop, with its flavour and price.
 *
 * This screen covers the first functionality of both user levels: "View all
 * cupcake details, including flavors, and prices". The list is read from
 * data/cupcakes.txt every time the screen is opened, so it always shows what is
 * genuinely saved on disk rather than something held in memory.
 *
 * The layout was built in the NetBeans Form Editor; initComponents() is
 * generated from CupcakePanel.form and must not be edited by hand.
 *
 * @author The Sweet Cupcake Shop development team
 */
public class CupcakePanel extends javax.swing.JPanel implements ShopModule {

    private static final long serialVersionUID = 1L;

    private static final String[] COLUMNS = {
        "Cupcake ID", "Name / Flavour", "Category", "Price (Rs.)",
        "Availability", "Description"
    };

    private final transient ShopService shopService;
    private final transient User currentUser;
    private final DefaultTableModel tableModel;

    /**
     * Creates new form CupcakePanel.
     *
     * @param shopService the business rules of the shop
     * @param currentUser the member of staff who is signed in
     */
    public CupcakePanel(ShopService shopService, User currentUser) {
        this.shopService = shopService;
        this.currentUser = currentUser;

        initComponents();

        // A table model that refuses to be edited: this screen only displays.
        tableModel = new DefaultTableModel(COLUMNS, 0) {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        cupcakeTable.setModel(tableModel);
        AppTheme.styleTable(cupcakeTable, 3);
        setBackground(AppTheme.BACKGROUND);
        AppTheme.secondaryButton(reloadBtn);
        cupcakeScroll.setBorder(javax.swing.BorderFactory.createLineBorder(AppTheme.BORDER));

        cupcakeTable.getColumnModel().getColumn(0).setPreferredWidth(90);
        cupcakeTable.getColumnModel().getColumn(1).setPreferredWidth(190);
        cupcakeTable.getColumnModel().getColumn(2).setPreferredWidth(150);
        cupcakeTable.getColumnModel().getColumn(3).setPreferredWidth(100);
        cupcakeTable.getColumnModel().getColumn(4).setPreferredWidth(110);
        cupcakeTable.getColumnModel().getColumn(5).setPreferredWidth(330);

        fileLabel.setText("Records: "
                + FileHelper.absolutePathOf(CupcakeFileStore.FILE_NAME));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        titleLabel = new javax.swing.JLabel();
        subtitleLabel = new javax.swing.JLabel();
        reloadBtn = new javax.swing.JButton();
        cupcakeScroll = new javax.swing.JScrollPane();
        cupcakeTable = new javax.swing.JTable();
        countLabel = new javax.swing.JLabel();
        summaryLabel = new javax.swing.JLabel();
        fileLabel = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(1020, 634));
        setLayout(null);

        titleLabel.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        titleLabel.setText("All Cupcake Details");
        titleLabel.setForeground(new java.awt.Color(140, 47, 84));
        this.add(titleLabel);
        titleLabel.setBounds(24, 16, 500, 28);

        subtitleLabel.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        subtitleLabel.setText("Every product currently on sale.");
        subtitleLabel.setForeground(new java.awt.Color(122, 112, 117));
        this.add(subtitleLabel);
        subtitleLabel.setBounds(24, 46, 700, 20);

        reloadBtn.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        reloadBtn.setText("Reload");
        reloadBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reloadBtnActionPerformed(evt);
            }
        });
        this.add(reloadBtn);
        reloadBtn.setBounds(856, 16, 140, 34);

        cupcakeScroll.setViewportView(cupcakeTable);
        this.add(cupcakeScroll);
        cupcakeScroll.setBounds(24, 84, 972, 456);

        countLabel.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        countLabel.setText(" ");
        countLabel.setForeground(new java.awt.Color(122, 112, 117));
        this.add(countLabel);
        countLabel.setBounds(24, 550, 972, 18);

        summaryLabel.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        summaryLabel.setText(" ");
        summaryLabel.setForeground(new java.awt.Color(122, 112, 117));
        this.add(summaryLabel);
        summaryLabel.setBounds(24, 572, 972, 18);

        fileLabel.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        fileLabel.setText(" ");
        fileLabel.setForeground(new java.awt.Color(122, 112, 117));
        this.add(fileLabel);
        fileLabel.setBounds(24, 594, 972, 18);

    }// </editor-fold>//GEN-END:initComponents

    private void reloadBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reloadBtnActionPerformed
        refresh();
    }//GEN-LAST:event_reloadBtnActionPerformed


    @Override
    public String getModuleTitle() {
        return "View Cupcakes";
    }

    /**
     * Reads the products from the file and fills the table.
     *
     * File problems (IOException) and damaged records (ShopException) are both
     * caught here, so a broken data file produces a clear message instead of
     * crashing the application.
     */
    @Override
    public void refresh() {
        tableModel.setRowCount(0);
        try {
            List<Cupcake> cupcakes = shopService.getAllCupcakes();

            for (Cupcake cupcake : cupcakes) {
                tableModel.addRow(new Object[]{
                    cupcake.getCupcakeId(),
                    cupcake.getName(),
                    cupcake.getCategory().getDisplayName(),
                    String.format("%.2f", cupcake.getPrice()),
                    cupcake.getStatusText(),
                    cupcake.getDescription()
                });
            }

            countLabel.setText(cupcakes.size() + " product(s) on sale.");
            summaryLabel.setText("By category -  " + shopService.getCategorySummary());

        } catch (IOException e) {
            countLabel.setText("Could not read the cupcake file.");
            JOptionPane.showMessageDialog(this,
                    "The cupcake data file could not be read.\n\n" + e.getMessage(),
                    "File problem", JOptionPane.ERROR_MESSAGE);

        } catch (ShopException e) {
            countLabel.setText("The cupcake file contains a damaged record.");
            JOptionPane.showMessageDialog(this, e.getMessage(),
                    "Damaged record", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * @return the member of staff this screen was opened for
     */
    public User getCurrentUser() {
        return currentUser;
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel countLabel;
    private javax.swing.JScrollPane cupcakeScroll;
    private javax.swing.JTable cupcakeTable;
    private javax.swing.JLabel fileLabel;
    private javax.swing.JButton reloadBtn;
    private javax.swing.JLabel subtitleLabel;
    private javax.swing.JLabel summaryLabel;
    private javax.swing.JLabel titleLabel;
    // End of variables declaration//GEN-END:variables
}
