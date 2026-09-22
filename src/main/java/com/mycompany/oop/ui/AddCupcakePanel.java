/*
 * The Sweet Cupcake Shop  -  CSE4006 Object Oriented Programming (WRIT1)
 *
 * File    : AddCupcakePanel.java
 * Package : com.mycompany.oop.ui
 * Layout  : designed in the Apache NetBeans Form Editor (AddCupcakePanel.form)
 */

package com.mycompany.oop.ui;

import com.mycompany.oop.exception.ShopException;
import com.mycompany.oop.model.Category;
import com.mycompany.oop.model.Cupcake;
import com.mycompany.oop.model.User;
import com.mycompany.oop.service.CupcakeFileStore;
import com.mycompany.oop.service.ShopService;
import com.mycompany.oop.util.FileHelper;
import com.mycompany.oop.util.Validator;
import java.io.IOException;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 * Screen used to add, change and remove products.
 *
 * This screen covers the second functionality of both user levels, "Add new
 * cupcake details and categorize them based on flavors or special collections",
 * and adds the update and delete operations so that the catalogue can be kept
 * correct without editing the text file by hand.
 *
 * Every one of the four operations finishes by writing data/cupcakes.txt, and
 * the table is always reloaded from that file afterwards, so what is on screen
 * is always what is really saved.
 *
 * @author The Sweet Cupcake Shop development team
 */
public class AddCupcakePanel extends javax.swing.JPanel implements ShopModule {

    private static final long serialVersionUID = 1L;

    private static final String[] COLUMNS = {
        "Cupcake ID", "Name / Flavour", "Category", "Price (Rs.)",
        "Availability", "Description"
    };

    private final transient ShopService shopService;
    private final transient User currentUser;
    private final DefaultTableModel tableModel;

    /**
     * Creates new form AddCupcakePanel.
     *
     * @param shopService the business rules of the shop
     * @param currentUser the member of staff who is signed in
     */
    public AddCupcakePanel(ShopService shopService, User currentUser) {
        this.shopService = shopService;
        this.currentUser = currentUser;

        initComponents();
        setBackground(AppTheme.BACKGROUND);
        formPanel.setBorder(javax.swing.BorderFactory.createLineBorder(AppTheme.BORDER));
        cupcakeScroll.setBorder(javax.swing.BorderFactory.createLineBorder(AppTheme.BORDER));

        AppTheme.primaryButton(addBtn);
        AppTheme.secondaryButton(updateBtn);
        AppTheme.secondaryButton(deleteBtn);
        AppTheme.secondaryButton(clearBtn);

        // The category list comes straight from the enum, so a new category
        // only ever has to be added in one place.
        categoryCombo.setModel(new javax.swing.DefaultComboBoxModel<>(Category.values()));

        tableModel = new DefaultTableModel(COLUMNS, 0) {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        cupcakeTable.setModel(tableModel);
        AppTheme.styleTable(cupcakeTable, 3);
        cupcakeTable.getColumnModel().getColumn(0).setPreferredWidth(90);
        cupcakeTable.getColumnModel().getColumn(1).setPreferredWidth(190);
        cupcakeTable.getColumnModel().getColumn(2).setPreferredWidth(150);
        cupcakeTable.getColumnModel().getColumn(3).setPreferredWidth(100);
        cupcakeTable.getColumnModel().getColumn(4).setPreferredWidth(110);
        cupcakeTable.getColumnModel().getColumn(5).setPreferredWidth(320);

        // Clicking a row copies that product into the form, ready to change.
        cupcakeTable.getSelectionModel().addListSelectionListener(evt -> {
            if (!evt.getValueIsAdjusting()) {
                loadSelectedRow();
            }
        });

        fileLabel.setText("Records: "
                + FileHelper.absolutePathOf(CupcakeFileStore.FILE_NAME));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        titleLabel = new javax.swing.JLabel();
        subtitleLabel = new javax.swing.JLabel();
        formPanel = new javax.swing.JPanel();
        idLabel = new javax.swing.JLabel();
        idField = new javax.swing.JTextField();
        nameLabel = new javax.swing.JLabel();
        nameField = new javax.swing.JTextField();
        catLabel = new javax.swing.JLabel();
        categoryCombo = new javax.swing.JComboBox<>();
        priceLabel = new javax.swing.JLabel();
        priceField = new javax.swing.JTextField();
        descLabel = new javax.swing.JLabel();
        descField = new javax.swing.JTextField();
        availableCheck = new javax.swing.JCheckBox();
        addBtn = new javax.swing.JButton();
        updateBtn = new javax.swing.JButton();
        deleteBtn = new javax.swing.JButton();
        clearBtn = new javax.swing.JButton();
        messageLabel = new javax.swing.JLabel();
        hintLabel = new javax.swing.JLabel();
        cupcakeScroll = new javax.swing.JScrollPane();
        cupcakeTable = new javax.swing.JTable();
        countLabel = new javax.swing.JLabel();
        fileLabel = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(1020, 634));
        setLayout(null);

        titleLabel.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        titleLabel.setText("Add Cupcake");
        titleLabel.setForeground(new java.awt.Color(140, 47, 84));
        this.add(titleLabel);
        titleLabel.setBounds(24, 12, 600, 28);

        subtitleLabel.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        subtitleLabel.setText("Add a new product, or pick a row below to change or remove one.");
        subtitleLabel.setForeground(new java.awt.Color(122, 112, 117));
        this.add(subtitleLabel);
        subtitleLabel.setBounds(24, 42, 900, 20);

        formPanel.setBackground(new java.awt.Color(255, 255, 255));
        formPanel.setLayout(null);
        idLabel.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        idLabel.setText("Cupcake ID");
        formPanel.add(idLabel);
        idLabel.setBounds(24, 22, 120, 24);

        idField.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        formPanel.add(idField);
        idField.setBounds(150, 20, 150, 28);

        nameLabel.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        nameLabel.setText("Name / Flavour");
        formPanel.add(nameLabel);
        nameLabel.setBounds(24, 60, 120, 24);

        nameField.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        formPanel.add(nameField);
        nameField.setBounds(150, 58, 330, 28);

        catLabel.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        catLabel.setText("Category");
        formPanel.add(catLabel);
        catLabel.setBounds(24, 98, 120, 24);

        categoryCombo.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        formPanel.add(categoryCombo);
        categoryCombo.setBounds(150, 96, 250, 28);

        priceLabel.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        priceLabel.setText("Price (Rs.)");
        formPanel.add(priceLabel);
        priceLabel.setBounds(510, 22, 110, 24);

        priceField.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        formPanel.add(priceField);
        priceField.setBounds(630, 20, 150, 28);

        descLabel.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        descLabel.setText("Description");
        formPanel.add(descLabel);
        descLabel.setBounds(510, 60, 110, 24);

        descField.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        formPanel.add(descField);
        descField.setBounds(630, 58, 316, 28);

        availableCheck.setBackground(new java.awt.Color(255, 255, 255));
        availableCheck.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        availableCheck.setText("Currently available to sell");
        availableCheck.setSelected(true);
        formPanel.add(availableCheck);
        availableCheck.setBounds(628, 96, 320, 24);

        addBtn.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        addBtn.setText("Add");
        addBtn.setBackground(new java.awt.Color(140, 47, 84));
        addBtn.setForeground(new java.awt.Color(255, 255, 255));
        addBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addBtnActionPerformed(evt);
            }
        });
        formPanel.add(addBtn);
        addBtn.setBounds(150, 148, 130, 36);

        updateBtn.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        updateBtn.setText("Update");
        updateBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateBtnActionPerformed(evt);
            }
        });
        formPanel.add(updateBtn);
        updateBtn.setBounds(292, 148, 130, 36);

        deleteBtn.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        deleteBtn.setText("Delete");
        deleteBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteBtnActionPerformed(evt);
            }
        });
        formPanel.add(deleteBtn);
        deleteBtn.setBounds(434, 148, 130, 36);

        clearBtn.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        clearBtn.setText("Clear");
        clearBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearBtnActionPerformed(evt);
            }
        });
        formPanel.add(clearBtn);
        clearBtn.setBounds(576, 148, 130, 36);

        messageLabel.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        messageLabel.setText(" ");
        formPanel.add(messageLabel);
        messageLabel.setBounds(24, 198, 920, 24);

        hintLabel.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        hintLabel.setText("Click a row in the table below to load it, then press Update or Delete.");
        hintLabel.setForeground(new java.awt.Color(122, 112, 117));
        formPanel.add(hintLabel);
        hintLabel.setBounds(150, 128, 800, 18);

        this.add(formPanel);
        formPanel.setBounds(24, 78, 972, 250);

        cupcakeScroll.setViewportView(cupcakeTable);
        this.add(cupcakeScroll);
        cupcakeScroll.setBounds(24, 342, 972, 232);

        countLabel.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        countLabel.setText(" ");
        countLabel.setForeground(new java.awt.Color(122, 112, 117));
        this.add(countLabel);
        countLabel.setBounds(24, 582, 972, 18);

        fileLabel.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        fileLabel.setText(" ");
        fileLabel.setForeground(new java.awt.Color(122, 112, 117));
        this.add(fileLabel);
        fileLabel.setBounds(24, 604, 972, 18);

    }// </editor-fold>//GEN-END:initComponents

    private void addBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addBtnActionPerformed
        addCupcake();
    }//GEN-LAST:event_addBtnActionPerformed

    private void updateBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateBtnActionPerformed
        updateCupcake();
    }//GEN-LAST:event_updateBtnActionPerformed

    private void deleteBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteBtnActionPerformed
        deleteCupcake();
    }//GEN-LAST:event_deleteBtnActionPerformed

    private void clearBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearBtnActionPerformed
        clearForm();
    }//GEN-LAST:event_clearBtnActionPerformed


    /* ------------------------------------------------------------------
     * Create
     * ------------------------------------------------------------------ */

    private void addCupcake() {
        try {
            String id = Validator.requireText(idField.getText(), "Cupcake ID");
            String name = Validator.requireText(nameField.getText(), "Name / Flavour");
            double price = Validator.requirePrice(priceField.getText());
            Category category = (Category) categoryCombo.getSelectedItem();

            Cupcake saved = shopService.addCupcake(id, name, category, price,
                    descField.getText(), availableCheck.isSelected());

            success("Saved to the cupcake data file:\n\n"
                    + saved.getName() + "  (" + saved.getCupcakeId() + ")\n"
                    + saved.getCategory().getDisplayName() + "\n"
                    + saved.getFormattedPrice());
            note(saved.getCupcakeId() + " added successfully.", true);
            clearForm();

        } catch (ShopException e) {
            fail(e);
        } catch (IOException e) {
            fileProblem(e);
        }
    }

    /* ------------------------------------------------------------------
     * Update
     * ------------------------------------------------------------------ */

    private void updateCupcake() {
        try {
            String id = Validator.requireText(idField.getText(), "Cupcake ID");
            String name = Validator.requireText(nameField.getText(), "Name / Flavour");
            double price = Validator.requirePrice(priceField.getText());
            Category category = (Category) categoryCombo.getSelectedItem();

            Cupcake updated = shopService.updateCupcake(id, name, category, price,
                    descField.getText(), availableCheck.isSelected());

            success("The cupcake file has been updated:\n\n"
                    + updated.getName() + "  (" + updated.getCupcakeId() + ")\n"
                    + updated.getCategory().getDisplayName() + "\n"
                    + updated.getFormattedPrice());
            note(updated.getCupcakeId() + " updated successfully.", true);
            refresh();

        } catch (ShopException e) {
            fail(e);
        } catch (IOException e) {
            fileProblem(e);
        }
    }

    /* ------------------------------------------------------------------
     * Delete
     * ------------------------------------------------------------------ */

    private void deleteCupcake() {
        String id = idField.getText().trim();
        if (id.isEmpty()) {
            note("Choose a cupcake from the table first.", false);
            return;
        }

        int answer = JOptionPane.showConfirmDialog(this,
                "Remove " + id + " from the catalogue?\n\n"
                + "This rewrites data/cupcakes.txt and cannot be undone.",
                "Confirm removal", JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (answer != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            Cupcake removed = shopService.deleteCupcake(id);
            success(removed.getName() + " (" + removed.getCupcakeId()
                    + ") was removed from the cupcake file.");
            note(removed.getCupcakeId() + " deleted.", true);
            clearForm();
            refresh();

        } catch (ShopException e) {
            fail(e);
        } catch (IOException e) {
            fileProblem(e);
        }
    }

    /* ------------------------------------------------------------------
     * Read
     * ------------------------------------------------------------------ */

    /**
     * Copies the selected table row into the form so it can be changed.
     */
    private void loadSelectedRow() {
        int row = cupcakeTable.getSelectedRow();
        if (row < 0 || row >= tableModel.getRowCount()) {
            return;
        }
        idField.setText(String.valueOf(tableModel.getValueAt(row, 0)));
        nameField.setText(String.valueOf(tableModel.getValueAt(row, 1)));

        Category category = Category.fromDisplayName(
                String.valueOf(tableModel.getValueAt(row, 2)));
        if (category != null) {
            categoryCombo.setSelectedItem(category);
        }
        priceField.setText(String.valueOf(tableModel.getValueAt(row, 3)));
        availableCheck.setSelected(
                "Available".equals(String.valueOf(tableModel.getValueAt(row, 4))));
        descField.setText(String.valueOf(tableModel.getValueAt(row, 5)));
    }

    private void clearForm() {
        nameField.setText("");
        priceField.setText("");
        descField.setText("");
        availableCheck.setSelected(true);
        categoryCombo.setSelectedIndex(0);
        cupcakeTable.clearSelection();
        suggestId();
        nameField.requestFocusInWindow();
    }

    /**
     * Fills the ID box with the next number in the CKnnn series so that a
     * cashier cannot create a duplicate by accident.
     */
    private void suggestId() {
        try {
            idField.setText(shopService.suggestNextCupcakeId());
        } catch (IOException | ShopException e) {
            idField.setText("");
        }
    }

    /* ------------------------------------------------------------------
     * Shared message helpers
     * ------------------------------------------------------------------ */

    private void success(String message) {
        JOptionPane.showMessageDialog(this, message, "Saved",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void note(String message, boolean good) {
        messageLabel.setForeground(good ? AppTheme.SUCCESS : AppTheme.ERROR);
        messageLabel.setText(message);
    }

    private void fail(ShopException e) {
        note(e.getMessage(), false);
        JOptionPane.showMessageDialog(this, e.getMessage(),
                "Please check your entry", JOptionPane.ERROR_MESSAGE);
    }

    private void fileProblem(IOException e) {
        note("The cupcake file could not be written.", false);
        JOptionPane.showMessageDialog(this,
                "The cupcake data file could not be written.\n\n" + e.getMessage(),
                "File problem", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public String getModuleTitle() {
        return "Add Cupcake";
    }

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
            countLabel.setText(cupcakes.size() + " product(s) in the catalogue.");
            if (idField.getText().trim().isEmpty()) {
                suggestId();
            }

        } catch (IOException e) {
            countLabel.setText("Could not read the cupcake file.");
        } catch (ShopException e) {
            countLabel.setText(e.getMessage());
        }
    }

    /**
     * @return the member of staff this screen was opened for
     */
    public User getCurrentUser() {
        return currentUser;
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addBtn;
    private javax.swing.JCheckBox availableCheck;
    private javax.swing.JComboBox<com.mycompany.oop.model.Category> categoryCombo;
    private javax.swing.JLabel catLabel;
    private javax.swing.JButton clearBtn;
    private javax.swing.JLabel countLabel;
    private javax.swing.JScrollPane cupcakeScroll;
    private javax.swing.JTable cupcakeTable;
    private javax.swing.JButton deleteBtn;
    private javax.swing.JTextField descField;
    private javax.swing.JLabel descLabel;
    private javax.swing.JLabel fileLabel;
    private javax.swing.JPanel formPanel;
    private javax.swing.JLabel hintLabel;
    private javax.swing.JTextField idField;
    private javax.swing.JLabel idLabel;
    private javax.swing.JLabel messageLabel;
    private javax.swing.JTextField nameField;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JTextField priceField;
    private javax.swing.JLabel priceLabel;
    private javax.swing.JLabel subtitleLabel;
    private javax.swing.JLabel titleLabel;
    private javax.swing.JButton updateBtn;
    // End of variables declaration//GEN-END:variables
}
