/*
 * The Sweet Cupcake Shop  -  CSE4006 Object Oriented Programming (WRIT1)
 *
 * File    : SearchCupcakePanel.java
 * Package : com.mycompany.oop.ui
 * Layout  : designed in the Apache NetBeans Form Editor (SearchCupcakePanel.form)
 */

package com.mycompany.oop.ui;

import com.mycompany.oop.exception.ShopException;
import com.mycompany.oop.model.Category;
import com.mycompany.oop.model.Cupcake;
import com.mycompany.oop.model.User;
import com.mycompany.oop.service.ShopService;
import java.io.IOException;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 * Screen used to search the products by category, by keyword, or by both.
 *
 * This screen covers the third functionality of both user levels: "Search for
 * cupcakes based on category (classic flavors, seasonal specials, custom
 * orders, gluten-free, etc.)".
 *
 * It is also where method overloading is demonstrated. ShopService offers three
 * methods that share the name searchCupcakes, and this screen calls a different
 * one depending on what the user filled in. Java chooses the correct method from
 * the arguments supplied, which is compile time polymorphism. The name of the
 * method that actually ran is shown at the bottom of the screen.
 *
 * @author The Sweet Cupcake Shop development team
 */
public class SearchCupcakePanel extends javax.swing.JPanel implements ShopModule {

    private static final long serialVersionUID = 1L;

    /** First entry of the category box, meaning "do not filter by category". */
    private static final String ALL_CATEGORIES = "All Categories";

    private static final String[] COLUMNS = {
        "Cupcake ID", "Name / Flavour", "Category", "Price (Rs.)",
        "Availability", "Description"
    };

    private final transient ShopService shopService;
    private final transient User currentUser;
    private final DefaultTableModel tableModel;

    /**
     * Creates new form SearchCupcakePanel.
     *
     * @param shopService the business rules of the shop
     * @param currentUser the member of staff who is signed in
     */
    public SearchCupcakePanel(ShopService shopService, User currentUser) {
        this.shopService = shopService;
        this.currentUser = currentUser;

        initComponents();
        setBackground(AppTheme.BACKGROUND);
        controlsPanel.setBorder(javax.swing.BorderFactory.createLineBorder(AppTheme.BORDER));
        AppTheme.primaryButton(searchBtn);
        AppTheme.secondaryButton(resetBtn);
        resultScroll.setBorder(javax.swing.BorderFactory.createLineBorder(AppTheme.BORDER));

        categoryCombo.addItem(ALL_CATEGORIES);
        for (Category category : Category.values()) {
            categoryCombo.addItem(category.getDisplayName());
        }

        tableModel = new DefaultTableModel(COLUMNS, 0) {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        resultTable.setModel(tableModel);
        AppTheme.styleTable(resultTable, 3);

        resultTable.getColumnModel().getColumn(0).setPreferredWidth(90);
        resultTable.getColumnModel().getColumn(1).setPreferredWidth(190);
        resultTable.getColumnModel().getColumn(2).setPreferredWidth(150);
        resultTable.getColumnModel().getColumn(3).setPreferredWidth(100);
        resultTable.getColumnModel().getColumn(4).setPreferredWidth(110);
        resultTable.getColumnModel().getColumn(5).setPreferredWidth(330);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        titleLabel = new javax.swing.JLabel();
        subtitleLabel = new javax.swing.JLabel();
        controlsPanel = new javax.swing.JPanel();
        catLabel = new javax.swing.JLabel();
        categoryCombo = new javax.swing.JComboBox<>();
        kwLabel = new javax.swing.JLabel();
        keywordField = new javax.swing.JTextField();
        searchBtn = new javax.swing.JButton();
        resetBtn = new javax.swing.JButton();
        resultScroll = new javax.swing.JScrollPane();
        resultTable = new javax.swing.JTable();
        resultLabel = new javax.swing.JLabel();
        methodLabel = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(1020, 634));
        setLayout(null);

        titleLabel.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        titleLabel.setText("Search Cupcakes");
        titleLabel.setForeground(new java.awt.Color(140, 47, 84));
        this.add(titleLabel);
        titleLabel.setBounds(24, 16, 500, 28);

        subtitleLabel.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        subtitleLabel.setText("Filter by category, or type a keyword, or use both together.");
        subtitleLabel.setForeground(new java.awt.Color(122, 112, 117));
        this.add(subtitleLabel);
        subtitleLabel.setBounds(24, 46, 800, 20);

        controlsPanel.setBackground(new java.awt.Color(255, 255, 255));
        controlsPanel.setLayout(null);
        catLabel.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        catLabel.setText("Category");
        controlsPanel.add(catLabel);
        catLabel.setBounds(16, 18, 70, 24);

        categoryCombo.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        controlsPanel.add(categoryCombo);
        categoryCombo.setBounds(92, 15, 210, 28);

        kwLabel.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        kwLabel.setText("Keyword");
        controlsPanel.add(kwLabel);
        kwLabel.setBounds(322, 18, 70, 24);

        keywordField.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        controlsPanel.add(keywordField);
        keywordField.setBounds(398, 15, 220, 28);

        searchBtn.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        searchBtn.setText("Search");
        searchBtn.setBackground(new java.awt.Color(140, 47, 84));
        searchBtn.setForeground(new java.awt.Color(255, 255, 255));
        searchBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchBtnActionPerformed(evt);
            }
        });
        controlsPanel.add(searchBtn);
        searchBtn.setBounds(638, 14, 110, 30);

        resetBtn.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        resetBtn.setText("Reset");
        resetBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetBtnActionPerformed(evt);
            }
        });
        controlsPanel.add(resetBtn);
        resetBtn.setBounds(758, 14, 110, 30);

        this.add(controlsPanel);
        controlsPanel.setBounds(24, 84, 972, 58);

        resultScroll.setViewportView(resultTable);
        this.add(resultScroll);
        resultScroll.setBounds(24, 156, 972, 398);

        resultLabel.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        resultLabel.setText(" ");
        resultLabel.setForeground(new java.awt.Color(122, 112, 117));
        this.add(resultLabel);
        resultLabel.setBounds(24, 564, 972, 18);

        methodLabel.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        methodLabel.setText(" ");
        methodLabel.setForeground(new java.awt.Color(122, 112, 117));
        this.add(methodLabel);
        methodLabel.setBounds(24, 586, 972, 18);

    }// </editor-fold>//GEN-END:initComponents

    private void searchBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchBtnActionPerformed
        runSearch();
    }//GEN-LAST:event_searchBtnActionPerformed

    private void resetBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetBtnActionPerformed
        categoryCombo.setSelectedIndex(0);
        keywordField.setText("");
        runSearch();
    }//GEN-LAST:event_resetBtnActionPerformed


    /**
     * Works out which of the three overloaded search methods to call, runs it,
     * and shows the results.
     */
    private void runSearch() {
        String selected = (String) categoryCombo.getSelectedItem();
        boolean allCategories = selected == null || ALL_CATEGORIES.equals(selected);
        Category category = allCategories ? null : Category.fromDisplayName(selected);

        String keyword = keywordField.getText().trim();
        boolean noKeyword = keyword.isEmpty();

        tableModel.setRowCount(0);
        try {
            List<Cupcake> results;

            if (allCategories && noKeyword) {
                results = shopService.getAllCupcakes();
                methodLabel.setText("Method used:  getAllCupcakes()");

            } else if (noKeyword) {
                // Calls searchCupcakes(Category)
                results = shopService.searchCupcakes(category);
                methodLabel.setText("Method used:  searchCupcakes(Category)");

            } else if (allCategories) {
                // Calls searchCupcakes(String)
                results = shopService.searchCupcakes(keyword);
                methodLabel.setText("Method used:  searchCupcakes(String)");

            } else {
                // Calls searchCupcakes(Category, String)
                results = shopService.searchCupcakes(category, keyword);
                methodLabel.setText("Method used:  searchCupcakes(Category, String)");
            }

            for (Cupcake cupcake : results) {
                tableModel.addRow(new Object[]{
                    cupcake.getCupcakeId(),
                    cupcake.getName(),
                    cupcake.getCategory().getDisplayName(),
                    String.format("%.2f", cupcake.getPrice()),
                    cupcake.getStatusText(),
                    cupcake.getDescription()
                });
            }

            if (results.isEmpty()) {
                resultLabel.setText("No cupcake matched this search.");
            } else {
                resultLabel.setText(results.size() + " cupcake(s) found.");
            }

        } catch (IOException e) {
            resultLabel.setText("Could not read the cupcake file.");
            JOptionPane.showMessageDialog(this,
                    "The cupcake data file could not be read.\n\n" + e.getMessage(),
                    "File problem", JOptionPane.ERROR_MESSAGE);

        } catch (ShopException e) {
            resultLabel.setText("The cupcake file contains a damaged record.");
            JOptionPane.showMessageDialog(this, e.getMessage(),
                    "Damaged record", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public String getModuleTitle() {
        return "Search Cupcakes";
    }

    @Override
    public void refresh() {
        runSearch();
    }

    /**
     * @return the member of staff this screen was opened for
     */
    public User getCurrentUser() {
        return currentUser;
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> categoryCombo;
    private javax.swing.JLabel catLabel;
    private javax.swing.JPanel controlsPanel;
    private javax.swing.JTextField keywordField;
    private javax.swing.JLabel kwLabel;
    private javax.swing.JLabel methodLabel;
    private javax.swing.JButton resetBtn;
    private javax.swing.JLabel resultLabel;
    private javax.swing.JScrollPane resultScroll;
    private javax.swing.JTable resultTable;
    private javax.swing.JButton searchBtn;
    private javax.swing.JLabel subtitleLabel;
    private javax.swing.JLabel titleLabel;
    // End of variables declaration//GEN-END:variables
}
