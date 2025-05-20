package com.project.app.view;

import com.project.app.database.DatabaseConnection;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.Objects;
import java.util.Vector;

public class ProductManagementView extends JPanel {
    private JTable drinkTable;
    private DefaultTableModel tableModel;
    private JTextField drinkNameField;
    private JComboBox<String> categoryComboBox;
    private JSpinner priceSpinner;
    private JButton addButton;
    private JButton updateButton;
    private JButton deleteButton;

    public ProductManagementView() {
        initializeUI();
        loadCategories();
        loadDrinks();
        setupListeners();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(10, 10, 10, 10));
        setBackground(new Color(245, 235, 220)); // Soft beige background

        JScrollPane scrollPane = createDrinkTableScrollPane();
        JPanel formPanel = createFormPanel();
        JPanel buttonPanel = createButtonPanel();

        add(scrollPane, BorderLayout.CENTER);
        add(formPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private JScrollPane createDrinkTableScrollPane() {
        String[] columnNames = {"ID", "Tên đồ uống", "Danh mục", "Giá"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Make all cells non-editable
                return false;
            }
        };
        drinkTable = new JTable(tableModel);
        drinkTable.setFillsViewportHeight(true);
        drinkTable.setRowHeight(25);
        drinkTable.getTableHeader().setBackground(new Color(139, 69, 19)); // Coffee brown header
        drinkTable.getTableHeader().setForeground(Color.WHITE);
        drinkTable.setSelectionBackground(new Color(210, 180, 140)); // Tan selection
        drinkTable.setSelectionForeground(Color.BLACK);
        drinkTable.setFont(new Font("Arial", Font.PLAIN, 14));

        // Set alternating row colors
        drinkTable.setDefaultRenderer(Object.class, new AlternatingColorRenderer());

        JScrollPane scrollPane = new JScrollPane(drinkTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(139, 69, 19), 2)); // Coffee brown border
        return scrollPane;
    }

    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(245, 235, 220)); // Soft beige background
        formPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(139, 69, 19)), "Chi tiết đồ uống", 0, 0, new Font("Arial", Font.BOLD, 16), new Color(139, 69, 19)));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        // Category combo box
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Danh mục:"), gbc);
        gbc.gridx = 1;
        categoryComboBox = new JComboBox<>(new String[]{}); // Khởi tạo rỗng, sẽ được load từ DB
        categoryComboBox.setBackground(Color.WHITE);
        categoryComboBox.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(139, 69, 19), 1),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        formPanel.add(categoryComboBox, gbc);

        // Name field
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Tên đồ uống:"), gbc);
        gbc.gridx = 1;
        drinkNameField = new JTextField(20);
        drinkNameField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(139, 69, 19), 1),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        formPanel.add(drinkNameField, gbc);

        // Price spinner
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Giá:"), gbc);
        gbc.gridx = 1;
        priceSpinner = new JSpinner(new SpinnerNumberModel(0.0, 0.0, 500000.0, 1000.0)); // Adjusted max price for drinks
        JSpinner.NumberEditor priceEditor = new JSpinner.NumberEditor(priceSpinner, "0.00");
        priceSpinner.setEditor(priceEditor);
        priceSpinner.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(139, 69, 19), 1),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        formPanel.add(priceSpinner, gbc);

        return formPanel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(new Color(245, 235, 220)); // Soft beige background

        // Assuming icons are in the same location relative to the class file
        addButton = createStyledButton("Thêm", new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/add_icon.png"))));
        updateButton = createStyledButton("Cập nhật", new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/edit_icon.png"))));
        deleteButton = createStyledButton("Xóa", new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/delete_icon.png"))));

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);

        return buttonPanel;
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
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(160, 82, 45)); // Sienna
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(139, 69, 19)); // Coffee brown
            }
        });

        return button;
    }

    private void setupListeners() {
        setupTableSelectionListener();
        setupButtonListeners();
    }

    private void setupTableSelectionListener() {
        drinkTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = drinkTable.getSelectedRow();
                if (selectedRow >= 0) {
                    populateFormFromSelectedRow(selectedRow);
                }
            }
        });
    }

    private void populateFormFromSelectedRow(int selectedRow) {
        // Ensure order matches the table columns: ID, Tên đồ uống, Danh mục, Giá
        // The form fields are now: Danh mục, Tên đồ uống, Giá
        // So we need to map table columns to form fields correctly
        // Category is column 2 in the table model
        Object categoryValue = tableModel.getValueAt(selectedRow, 2);
        if (categoryValue != null) {
            categoryComboBox.setSelectedItem(categoryValue.toString());
        } else {
            categoryComboBox.setSelectedIndex(-1); // Không chọn mục nào nếu null
        }

        // Name is column 1 in the table model
        Object nameValue = tableModel.getValueAt(selectedRow, 1);
        drinkNameField.setText(nameValue != null ? nameValue.toString() : "");

        // Price is column 3 in the table model
        Object priceValue = tableModel.getValueAt(selectedRow, 3);
        if (priceValue instanceof Number) {
            priceSpinner.setValue(((Number) priceValue).doubleValue());
        } else if (priceValue != null) {
            try {
                priceSpinner.setValue(Double.parseDouble(priceValue.toString()));
            } catch (NumberFormatException ex) {
                ex.printStackTrace();
                priceSpinner.setValue(0.0); // Giá trị mặc định nếu parse lỗi
            }
        } else {
            priceSpinner.setValue(0.0); // Giá trị mặc định nếu null
        }
    }

    private void setupButtonListeners() {
        addButton.addActionListener(e -> addDrink());
        updateButton.addActionListener(e -> handleUpdateAction());
        deleteButton.addActionListener(e -> deleteDrink());
    }

    private void handleUpdateAction() {
        int selectedRow = drinkTable.getSelectedRow();
        if (selectedRow >= 0) {
            updateDrink();
        } else {
            showWarningMessage("Vui lòng chọn một đồ uống để cập nhật.", "Lỗi chọn");
        }
    }

    private void showWarningMessage(String message, String title) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.WARNING_MESSAGE);
    }

    private void clearForm() {
        drinkNameField.setText("");
        // Đặt lại categoryComboBox về mục đầu tiên hoặc rỗng nếu cần
        if (categoryComboBox.getItemCount() > 0) {
            categoryComboBox.setSelectedIndex(0);
        } else {
            categoryComboBox.setSelectedIndex(-1);
        }
        priceSpinner.setValue(0.0);
        drinkTable.clearSelection();
    }

    private void loadCategories() {
        String sql = "SELECT drink_category_name FROM drink_categories";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            Vector<String> categories = new Vector<>();
            while (rs.next()) {
                categories.add(rs.getString("drink_category_name"));
            }
            categoryComboBox.setModel(new DefaultComboBoxModel<>(categories));

        } catch (SQLException e) {
            handleDatabaseError("Lỗi khi tải danh mục", e);
        }
    }

    private void loadDrinks() {
        String sql = "SELECT d.drink_id, d.drink_name, dc.drink_category_name, d.price " +
                "FROM drinks d " +
                "JOIN drink_categories dc ON d.drink_category_id = dc.drink_category_id";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            tableModel.setRowCount(0); // Clear existing data
            while (rs.next()) {
                tableModel.addRow(new Object[]{
                        rs.getInt("drink_id"),
                        rs.getString("drink_name"),
                        rs.getString("drink_category_name"),
                        rs.getDouble("price")
                });
            }
        } catch (SQLException e) {
            handleDatabaseError("Lỗi khi tải danh sách đồ uống", e);
        }
    }

    private void handleDatabaseError(String message, SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this,
                message + ": " + e.getMessage(),
                "Lỗi Database",
                JOptionPane.ERROR_MESSAGE);
    }

    private void addDrink() {
        String name = drinkNameField.getText().trim();
        String category = (String) categoryComboBox.getSelectedItem();
        double price = (double) priceSpinner.getValue();

        if (!validateInput(name, category)) {
            return;
        }

        if (isDrinkNameDuplicate(name)) {
            showWarningMessage("Tên đồ uống '" + name + "' đã tồn tại.", "Lỗi nhập liệu");
            return;
        }

        insertDrink(name, category, price);
    }

    private boolean validateInput(String name, String category) {
        if (name.isEmpty()) {
            showWarningMessage("Tên đồ uống không được để trống.", "Lỗi nhập liệu");
            return false;
        }
        if (category == null || category.isEmpty()) {
            showWarningMessage("Vui lòng chọn danh mục cho đồ uống.", "Lỗi nhập liệu");
            return false;
        }
        return true;
    }

    private boolean isDrinkNameDuplicate(String name) {
        String checkSql = "SELECT COUNT(*) FROM drinks WHERE drink_name = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {

            checkStmt.setString(1, name);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                rs.close();
                return true;
            }
            rs.close();
            return false;
        } catch (SQLException e) {
            handleDatabaseError("Lỗi kiểm tra trùng tên", e);
            return true; // Assume duplicate or error occurred
        }
    }

    private void insertDrink(String name, String category, double price) {
        String insertSql = "INSERT INTO drinks (drink_name, drink_category_id, price) " +
                "SELECT ?, dc.drink_category_id, ? " +
                "FROM drink_categories dc WHERE dc.drink_category_name = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(insertSql)) {

            stmt.setString(1, name);
            stmt.setDouble(2, price);
            stmt.setString(3, category);

            stmt.executeUpdate();
            loadDrinks();
            clearForm();
            JOptionPane.showMessageDialog(this, "Thêm đồ uống thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            handleDatabaseError("Lỗi khi thêm đồ uống", e);
        }
    }

    private void updateDrink() {
        int selectedRow = drinkTable.getSelectedRow();
        if (selectedRow < 0) {
            showWarningMessage("Vui lòng chọn một đồ uống để cập nhật.", "Lỗi chọn");
            return;
        }

        int id = (int) tableModel.getValueAt(selectedRow, 0);
        String originalName = tableModel.getValueAt(selectedRow, 1).toString();
        String originalCategory = tableModel.getValueAt(selectedRow, 2).toString();
        double originalPrice = (double) tableModel.getValueAt(selectedRow, 3);

        String currentName = drinkNameField.getText().trim();
        String currentCategory = (String) categoryComboBox.getSelectedItem();
        double currentPrice = (double) priceSpinner.getValue();

        if (!hasChanges(originalName, originalCategory, originalPrice, currentName, currentCategory, currentPrice)) {
            JOptionPane.showMessageDialog(this, "Không có thay đổi nào được thực hiện.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if (!validateInput(currentName, currentCategory)) {
            return;
        }

        if (isDrinkNameDuplicateExcludingCurrent(currentName, id)) {
            showWarningMessage("Tên đồ uống '" + currentName + "' đã tồn tại cho một đồ uống khác.", "Lỗi nhập liệu");
            return;
        }

        int categoryId = getCategoryId(currentCategory);
        if (categoryId == -1) {
            showWarningMessage("Không tìm thấy ID danh mục cho '" + currentCategory + "'.", "Lỗi dữ liệu");
            return;
        }

        performUpdate(id, currentName, categoryId, currentPrice);
    }

    private boolean hasChanges(String originalName, String originalCategory, double originalPrice,
                               String currentName, String currentCategory, double currentPrice) {
        boolean nameChanged = !currentName.equals(originalName);
        boolean categoryChanged = !currentCategory.equals(originalCategory);
        boolean priceChanged = currentPrice != originalPrice;
        return nameChanged || categoryChanged || priceChanged;
    }

    private boolean isDrinkNameDuplicateExcludingCurrent(String name, int currentId) {
        String checkSql = "SELECT COUNT(*) FROM drinks WHERE drink_name = ? AND drink_id <> ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {

            checkStmt.setString(1, name);
            checkStmt.setInt(2, currentId);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                rs.close();
                return true;
            }
            rs.close();
            return false;
        } catch (SQLException e) {
            handleDatabaseError("Lỗi kiểm tra trùng tên khi cập nhật", e);
            return true; // Assume duplicate or error occurred
        }
    }

    private int getCategoryId(String categoryName) {
        String getCategoryIdSql = "SELECT drink_category_id FROM drink_categories WHERE drink_category_name = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement getCategoryIdStmt = conn.prepareStatement(getCategoryIdSql)) {

            getCategoryIdStmt.setString(1, categoryName);
            ResultSet rs = getCategoryIdStmt.executeQuery();
            if (rs.next()) {
                int categoryId = rs.getInt("drink_category_id");
                rs.close();
                return categoryId;
            }
            rs.close();
            return -1; // Category not found
        } catch (SQLException e) {
            handleDatabaseError("Lỗi khi lấy ID danh mục", e);
            return -1; // Error occurred
        }
    }

    private void performUpdate(int id, String name, int categoryId, double price) {
        String sql = "UPDATE drinks SET drink_name = ?, drink_category_id = ?, price = ? WHERE drink_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, name);
            stmt.setInt(2, categoryId);
            stmt.setDouble(3, price);
            stmt.setInt(4, id);

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                loadDrinks();
                clearForm();
                JOptionPane.showMessageDialog(this, "Cập nhật thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            } else {
                showWarningMessage("Không thể cập nhật đồ uống. Có thể đã bị xóa hoặc ID không hợp lệ.", "Lỗi Cập nhật");
            }

        } catch (SQLException e) {
            handleDatabaseError("Lỗi khi cập nhật đồ uống", e);
        }
    }

    private void deleteDrink() {
        int selectedRow = drinkTable.getSelectedRow();
        if (selectedRow < 0) {
            showWarningMessage("Vui lòng chọn một đồ uống để xóa.", "Lỗi chọn");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc chắn muốn xóa đồ uống này không?",
                "Xác nhận xóa",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            int id = (int) tableModel.getValueAt(selectedRow, 0);
            performDelete(id);
        }
    }

    private void performDelete(int id) {
        String sql = "DELETE FROM drinks WHERE drink_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
            loadDrinks();
            clearForm();
            JOptionPane.showMessageDialog(this, "Xóa đồ uống thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            handleDatabaseError("Lỗi khi xóa đồ uống", e);
        }
    }


    // Custom renderer for alternating row colors
    private static class AlternatingColorRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            if (!isSelected) {
                c.setBackground(row % 2 == 0 ? new Color(255, 248, 220) : new Color(245, 222, 179)); // Cream and Wheat
            } else {
                c.setBackground(new Color(210, 180, 140)); // Tan selection
                c.setForeground(Color.BLACK);
            }
            return c;
        }
    }

    public static void main(String[] args) {
        // Use the system look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            JFrame frame = new JFrame("Quản lý đồ uống");
            frame.setContentPane(new ProductManagementView());
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }
}
