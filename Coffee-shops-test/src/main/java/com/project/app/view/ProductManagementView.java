package com.project.app.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;
import java.util.Vector;

public class ProductManagementView extends JPanel {
    private JTable productTable;
    private DefaultTableModel tableModel;
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
        setBackground(new Color(245, 235, 220)); // Soft beige background

        // Create table
        String[] columnNames = {"ID", "Name", "Category", "Price", "Availability"};
        tableModel = new DefaultTableModel(columnNames, 0);
        productTable = new JTable(tableModel);
        productTable.setFillsViewportHeight(true);
        productTable.setRowHeight(25);
        productTable.getTableHeader().setBackground(new Color(139, 69, 19)); // Coffee brown header
        productTable.getTableHeader().setForeground(Color.WHITE);
        productTable.setSelectionBackground(new Color(210, 180, 140)); // Tan selection
        productTable.setSelectionForeground(Color.BLACK);
        productTable.setFont(new Font("Arial", Font.PLAIN, 14));

        // Set alternating row colors
        productTable.setDefaultRenderer(Object.class, new AlternatingColorRenderer());


        JScrollPane scrollPane = new JScrollPane(productTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(139, 69, 19), 2)); // Coffee brown border

        // Create form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(245, 235, 220)); // Soft beige background
        formPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(139, 69, 19)), "Product Details", 0, 0, new Font("Arial", Font.BOLD, 16), new Color(139, 69, 19)));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
// Name field
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1;
        nameField = new JTextField(20);
        nameField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(139, 69, 19), 1),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        formPanel.add(nameField, gbc);

        // Category combo box
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Category:"), gbc);
        gbc.gridx = 1;
        categoryComboBox = new JComboBox<>(new String[]{"Coffee", "Tea", "Pastry", "Other"});
        categoryComboBox.setBackground(Color.WHITE);
        categoryComboBox.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(139, 69, 19), 1),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        formPanel.add(categoryComboBox, gbc);

        // Price spinner
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Price:"), gbc);
        gbc.gridx = 1;
        priceSpinner = new JSpinner(new SpinnerNumberModel(0.0, 0.0, 1000.0, 0.5));
        JSpinner.NumberEditor priceEditor = new JSpinner.NumberEditor(priceSpinner, "0.00");
        priceSpinner.setEditor(priceEditor);
        priceSpinner.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(139, 69, 19), 1),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        formPanel.add(priceSpinner, gbc);

        // Availability checkbox
        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(new JLabel("Available:"), gbc);
        gbc.gridx = 1;
        availabilityCheckBox = new JCheckBox();
        availabilityCheckBox.setBackground(new Color(245, 235, 220)); // Soft beige background
        formPanel.add(availabilityCheckBox, gbc);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(new Color(245, 235, 220)); // Soft beige background

        addButton = createStyledButton("Add", new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/add_icon.png"))));
        updateButton = createStyledButton("Update", new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/edit_icon.png"))));
        deleteButton = createStyledButton("Delete", new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/delete_icon.png"))));
        clearButton = createStyledButton("Clear", new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/clear_icon.png"))));

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

    private JButton createStyledButton(String text, ImageIcon icon) {
        JButton button = new JButton(text, icon);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(139, 69, 19)); // Coffee brown
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(101, 46, 9), 2), // Darker brown border
                BorderFactory.createEmptyBorder(8, 15, 8, 15)));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setOpaque(true);
        button.setBorderPainted(true);

        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(160, 82, 45)); // Sienna
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(139, 69, 19)); // Coffee brown
            }
        });

        return button;
    }


    private void setupListeners() {
        productTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = productTable.getSelectedRow();
                if (selectedRow >= 0) {
                    nameField.setText(tableModel.getValueAt(selectedRow, 1).toString());
                    categoryComboBox.setSelectedItem(tableModel.getValueAt(selectedRow, 2).toString());
                    priceSpinner.setValue(Double.parseDouble(tableModel.getValueAt(selectedRow, 3).toString()));
                    availabilityCheckBox.setSelected(Boolean.parseBoolean(tableModel.getValueAt(selectedRow, 4).toString()));
                }
            }
        });

        clearButton.addActionListener(e -> clearForm());
        addButton.addActionListener(e -> addProduct());
        updateButton.addActionListener(e -> {
            int selectedRow = productTable.getSelectedRow();
            if (selectedRow >= 0) {
                // Retrieve product information from the selected row
                Object id = tableModel.getValueAt(selectedRow, 0);
                Object name = tableModel.getValueAt(selectedRow, 1);
                Object category = tableModel.getValueAt(selectedRow, 2);
                Object price = tableModel.getValueAt(selectedRow, 3);
                Object availability = tableModel.getValueAt(selectedRow, 4);

                // Format the information for the popup
                String productInfo = String.format(
                        "Product Details:\n" +
                                "ID: %s\n" +
                                "Name: %s\n" +
                                "Category: %s\n" +
                                "Price: %.2f\n" +
                                "Available: %s",
                        id, name, category, (Double) price, availability
                );

                // Show the popup
                JOptionPane.showMessageDialog(this, productInfo, "Product Information", JOptionPane.INFORMATION_MESSAGE);

                // Proceed with the update logic
                updateProduct();
            } else {
                JOptionPane.showMessageDialog(this, "Please select a product to update.", "Selection Error", JOptionPane.WARNING_MESSAGE);
            }
        });
        deleteButton.addActionListener(e -> deleteProduct());
    }

    private void addProduct() {
        String name = nameField.getText();
        String category = (String) categoryComboBox.getSelectedItem();
        double price = (double) priceSpinner.getValue();
        boolean available = availabilityCheckBox.isSelected();

        // Simple validation
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Product name cannot be empty.", "Input Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Generate a simple ID (for demonstration purposes)
        int id = tableModel.getRowCount() + 1;

        // Add data to the table model
        tableModel.addRow(new Object[]{id, name, category, price, available});

        // Clear the form
        clearForm();
    }

    private void updateProduct() {
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow >= 0) {
            String name = nameField.getText();
            String category = (String) categoryComboBox.getSelectedItem();
            double price = (double) priceSpinner.getValue();
            boolean available = availabilityCheckBox.isSelected();

            // Simple validation
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Product name cannot be empty.", "Input Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Update data in the table model
            tableModel.setValueAt(name, selectedRow, 1);
            tableModel.setValueAt(category, selectedRow, 2);
            tableModel.setValueAt(price, selectedRow, 3);
            tableModel.setValueAt(available, selectedRow, 4);

            // Clear the form
            clearForm();
        } else {
            JOptionPane.showMessageDialog(this, "Please select a product to update.", "Selection Error", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void deleteProduct() {
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow >= 0) {
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this product?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                // Remove data from the table model
                tableModel.removeRow(selectedRow);

                // Clear the form
                clearForm();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a product to delete.", "Selection Error", JOptionPane.WARNING_MESSAGE);
        }
    }


    private void clearForm() {
        nameField.setText("");
        categoryComboBox.setSelectedIndex(0);
        priceSpinner.setValue(0.0);
        availabilityCheckBox.setSelected(false);
        productTable.clearSelection();
    }

    // Custom renderer for alternating row colors
    private static class AlternatingColorRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            if (!isSelected) {
                c.setBackground(row % 2 == 0 ? new Color(255, 248, 220) : new Color(245, 222, 179)); // Cream and Wheat
            }
            return c;
        }
    }

    public static void main(String[] args) {
        // Use the system look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                 UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        JFrame frame = new JFrame("Product Management");
        frame.setContentPane(new ProductManagementView());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null); // Center the frame
        frame.setVisible(true);
    }
}
