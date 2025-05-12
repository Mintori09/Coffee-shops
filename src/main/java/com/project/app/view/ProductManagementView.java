package com.project.app.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProductManagementView extends JPanel {
    private JTable productTable;
    private JTextField nameField;
    private JComboBox<String> categoryComboBox;
    private JSpinner priceSpinner;
    private JCheckBox availabilityCheckBox;
    private JButton addButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton clearButton;

    public ProductManagementView() {
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(10, 10, 10, 10));

        // Create table
        String[] columnNames = {"ID", "Name", "Category", "Price", "Availability"};
        Object[][] data = {}; // Empty data for now
        productTable = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(productTable);

        // Create form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Name field
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1;
        nameField = new JTextField(20);
        formPanel.add(nameField, gbc);

        // Category combo box
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Category:"), gbc);
        gbc.gridx = 1;
        categoryComboBox = new JComboBox<>(new String[]{"Coffee", "Tea", "Pastry", "Other"});
        formPanel.add(categoryComboBox, gbc);

        // Price spinner
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Price:"), gbc);
        gbc.gridx = 1;
        priceSpinner = new JSpinner(new SpinnerNumberModel(0.0, 0.0, 100.0, 0.5));
        formPanel.add(priceSpinner, gbc);

        // Availability checkbox
        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(new JLabel("Available:"), gbc);
        gbc.gridx = 1;
        availabilityCheckBox = new JCheckBox();
        formPanel.add(availabilityCheckBox, gbc);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        addButton = new JButton("Add");
        updateButton = new JButton("Update");
        deleteButton = new JButton("Delete");
        clearButton = new JButton("Clear");

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);

        // Add components to main panel
        add(scrollPane, BorderLayout.CENTER);
        add(formPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);

        // Set up event listeners
        setupListeners();
    }

    private void setupListeners() {
        productTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = productTable.getSelectedRow();
                if (selectedRow >= 0) {
                    nameField.setText(productTable.getValueAt(selectedRow, 1).toString());
                    categoryComboBox.setSelectedItem(productTable.getValueAt(selectedRow, 2).toString());
                    priceSpinner.setValue(Double.parseDouble(productTable.getValueAt(selectedRow, 3).toString()));
                    availabilityCheckBox.setSelected(Boolean.parseBoolean(productTable.getValueAt(selectedRow, 4).toString()));
                }
            }
        });

        clearButton.addActionListener(e -> clearForm());
    }

    private void clearForm() {
        nameField.setText("");
        categoryComboBox.setSelectedIndex(0);
        priceSpinner.setValue(0.0);
        availabilityCheckBox.setSelected(false);
        productTable.clearSelection();
    }
}
