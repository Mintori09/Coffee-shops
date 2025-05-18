package com.project.app.view;

import com.project.app.database.DatabaseConnection;
import java.sql.*;
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
    private JTable drinkTable;
    private DefaultTableModel tableModel;
    private JTextField drinkNameField;
    private JComboBox<String> categoryComboBox;
    private JSpinner priceSpinner;
    private JButton addButton;
    private JButton updateButton;
    private JButton deleteButton;

    public ProductManagementView() throws SQLException {

        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(10, 10, 10, 10));
        setBackground(new Color(245, 235, 220)); // Soft beige background

        // Create table
        String[] columnNames = {"ID", "Tên đồ uống", "Danh mục", "Giá"};
        tableModel = new DefaultTableModel(columnNames, 0);
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

        // Create form panel
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

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(new Color(245, 235, 220)); // Soft beige background

        // Assuming icons are in the same location relative to the class file
        addButton = createStyledButton("Thêm", new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/add_icon.png"))));
        updateButton = createStyledButton("Cập nhật", new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/edit_icon.png"))));
        deleteButton = createStyledButton("Xóa", new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/delete_icon.png"))));

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);

        // Add components to main panel
        add(scrollPane, BorderLayout.CENTER);
        add(formPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);

        loadCategories();
        loadDrinks();
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
        drinkTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = drinkTable.getSelectedRow();
                if (selectedRow >= 0) {
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
            }
        });

        addButton.addActionListener(e -> addDrink());
        updateButton.addActionListener(e -> {
            int selectedRow = drinkTable.getSelectedRow();
            if (selectedRow >= 0) {
                // Gọi trực tiếp updateDrink()
                updateDrink();
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một đồ uống để cập nhật.", "Lỗi chọn", JOptionPane.WARNING_MESSAGE);
            }
        });
        deleteButton.addActionListener(e -> deleteDrink());
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
        try {
            Connection conn = DatabaseConnection.getConnection();
            String sql = "SELECT food_category_name FROM drink_categories";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            Vector<String> categories = new Vector<>();
            while (rs.next()) {
                categories.add(rs.getString("food_category_name"));
            }

            categoryComboBox.setModel(new DefaultComboBoxModel<>(categories));

            rs.close();
            stmt.close();
            // It's generally better to close the connection here too,
            // or use try-with-resources as in loadDrinks().
            // Assuming DatabaseConnection handles pooling or auto-closing.
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                "Lỗi khi tải danh mục: " + e.getMessage(),
                "Lỗi Database",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadDrinks() { // Removed throws SQLException here
        String sql = "SELECT d.food_id, d.food_name, dc.food_category_name, d.price " +
                     "FROM drinks d " +
                     "JOIN drink_categories dc ON d.food_category_id = dc.food_category_id";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            tableModel.setRowCount(0); // Clear existing data
            while (rs.next()) {
                tableModel.addRow(new Object[]{
                    rs.getInt("food_id"),
                    rs.getString("food_name"),
                    rs.getString("food_category_name"),
                    rs.getDouble("price")
                });
            }
        } catch (SQLException e) { // Catch SQLException here instead of throwing
             e.printStackTrace();
             JOptionPane.showMessageDialog(this,
                 "Lỗi khi tải danh sách đồ uống: " + e.getMessage(),
                 "Lỗi Database",
                 JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addDrink() {
        String name = drinkNameField.getText().trim(); // Trim whitespace
        String category = (String) categoryComboBox.getSelectedItem();
        double price = (double) priceSpinner.getValue();

        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tên đồ uống không được để trống.", "Lỗi nhập liệu", JOptionPane.WARNING_MESSAGE);
            return;
        }
         if (category == null || category.isEmpty()) {
             JOptionPane.showMessageDialog(this, "Vui lòng chọn danh mục cho đồ uống.", "Lỗi nhập liệu", JOptionPane.WARNING_MESSAGE);
             return;
         }


        // === Bắt đầu kiểm tra trùng tên ===
        String checkSql = "SELECT COUNT(*) FROM drinks WHERE food_name = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {

            checkStmt.setString(1, name);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                // Tên đồ uống đã tồn tại
                JOptionPane.showMessageDialog(this, "Tên đồ uống '" + name + "' đã tồn tại.", "Lỗi nhập liệu", JOptionPane.WARNING_MESSAGE);
                rs.close();
                return; // Dừng quá trình thêm
            }
            rs.close(); // Đóng ResultSet sau khi kiểm tra
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi kiểm tra trùng tên: " + e.getMessage(), "Lỗi Database", JOptionPane.ERROR_MESSAGE);
            return; // Dừng nếu có lỗi khi kiểm tra
        }
        // === Kết thúc kiểm tra trùng tên ===


        // Nếu không trùng tên, tiến hành thêm đồ uống
        String insertSql = "INSERT INTO drinks (food_name, food_category_id, price) " +
                           "SELECT ?, dc.food_category_id, ? " +
                           "FROM drink_categories dc WHERE dc.food_category_name = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(insertSql)) {

            stmt.setString(1, name);
            stmt.setDouble(2, price);
            stmt.setString(3, category); // Parameter index changed

            stmt.executeUpdate();
            loadDrinks(); // Refresh table
            clearForm();

            JOptionPane.showMessageDialog(this, "Thêm đồ uống thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi thêm đồ uống: " + e.getMessage(), "Lỗi Database", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateDrink() {
        int selectedRow = drinkTable.getSelectedRow();
        if (selectedRow >= 0) {
            int id = (int) tableModel.getValueAt(selectedRow, 0);
            // Lấy giá trị gốc từ bảng
            String originalName = tableModel.getValueAt(selectedRow, 1).toString();
            String originalCategory = tableModel.getValueAt(selectedRow, 2).toString();
            double originalPrice = (double) tableModel.getValueAt(selectedRow, 3);

            // Lấy giá trị hiện tại từ form
            String currentName = drinkNameField.getText().trim();
            String currentCategory = (String) categoryComboBox.getSelectedItem();
            double currentPrice = (double) priceSpinner.getValue();

            // Kiểm tra xem có thay đổi nào được thực hiện không
            boolean nameChanged = !currentName.equals(originalName);
            boolean categoryChanged = !currentCategory.equals(originalCategory);
            // So sánh giá trị double cần cẩn thận hơn nếu có sai số nhỏ,
            // nhưng với trường hợp này, so sánh trực tiếp có thể đủ.
            boolean priceChanged = currentPrice != originalPrice;

            if (!nameChanged && !categoryChanged && !priceChanged) {
                // Nếu không có thay đổi nào, thông báo và thoát
                JOptionPane.showMessageDialog(this, "Không có thay đổi nào được thực hiện.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                return;
            }


            // Simple validation (giữ nguyên)
            if (currentName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Tên đồ uống không được để trống.", "Lỗi nhập liệu", JOptionPane.WARNING_MESSAGE);
                return;
            }
             if (currentCategory == null || currentCategory.isEmpty()) {
                 JOptionPane.showMessageDialog(this, "Vui lòng chọn danh mục cho đồ uống.", "Lỗi nhập liệu", JOptionPane.WARNING_MESSAGE);
                 return;
             }

            // === Bắt đầu kiểm tra trùng tên khi cập nhật (trừ bản ghi hiện tại) ===
            // Sử dụng tên hiện tại từ form để kiểm tra trùng
             String checkSql = "SELECT COUNT(*) FROM drinks WHERE food_name = ? AND food_id <> ?";
             try (Connection conn = DatabaseConnection.getConnection();
                   PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {

                 checkStmt.setString(1, currentName); // Sử dụng currentName
                 checkStmt.setInt(2, id); // Loại trừ ID của bản ghi đang cập nhật
                 ResultSet rs = checkStmt.executeQuery();
                 if (rs.next() && rs.getInt(1) > 0) {
                     // Tên đồ uống đã tồn tại cho bản ghi khác
                     JOptionPane.showMessageDialog(this, "Tên đồ uống '" + currentName + "' đã tồn tại cho một đồ uống khác.", "Lỗi nhập liệu", JOptionPane.WARNING_MESSAGE);
                     rs.close();
                     return; // Dừng quá trình cập nhật
                 }
                 rs.close();
             } catch (SQLException e) {
                 e.printStackTrace();
                 JOptionPane.showMessageDialog(this, "Lỗi kiểm tra trùng tên khi cập nhật: " + e.getMessage(), "Lỗi Database", JOptionPane.ERROR_MESSAGE);
                 return;
             }
            // === Kết thúc kiểm tra trùng tên khi cập nhật ===

            // === Bắt đầu lấy food_category_id từ category name ===
            // Sử dụng danh mục hiện tại từ form để lấy ID
            int categoryId = -1; // Default to -1 or handle appropriately if category not found
            String getCategoryIdSql = "SELECT food_category_id FROM drink_categories WHERE food_category_name = ?";
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement getCategoryIdStmt = conn.prepareStatement(getCategoryIdSql)) {

                getCategoryIdStmt.setString(1, currentCategory); // Sử dụng currentCategory
                ResultSet rs = getCategoryIdStmt.executeQuery();
                if (rs.next()) {
                    categoryId = rs.getInt("food_category_id");
                }
                rs.close();
            } catch (SQLException e) {
                 e.printStackTrace();
                 JOptionPane.showMessageDialog(this, "Lỗi khi lấy ID danh mục: " + e.getMessage(), "Lỗi Database", JOptionPane.ERROR_MESSAGE);
                 return; // Dừng nếu có lỗi
            }

            if (categoryId == -1) {
                 JOptionPane.showMessageDialog(this, "Không tìm thấy ID danh mục cho '" + currentCategory + "'.", "Lỗi dữ liệu", JOptionPane.WARNING_MESSAGE);
                 return; // Dừng nếu không tìm thấy categoryId
            }
            // === Kết thúc lấy food_category_id ===


            // Thực hiện UPDATE chỉ khi có thay đổi và các kiểm tra hợp lệ đã qua
            String sql = "UPDATE drinks SET food_name = ?, food_category_id = ?, price = ? WHERE food_id = ?";

            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setString(1, currentName); // Sử dụng currentName
                stmt.setInt(2, categoryId); // Sử dụng categoryId đã lấy
                stmt.setDouble(3, currentPrice); // Sử dụng currentPrice
                stmt.setInt(4, id);

                int rowsAffected = stmt.executeUpdate(); // Kiểm tra số hàng bị ảnh hưởng

                if (rowsAffected > 0) {
                     // Nếu có ít nhất một hàng bị ảnh hưởng (đã cập nhật), load lại dữ liệu và thông báo
                     loadDrinks(); // Refresh table
                     clearForm();
                     JOptionPane.showMessageDialog(this, "Cập nhật thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                } else {
                     // Trường hợp này có thể xảy ra nếu dòng bị xóa bởi người dùng khác, hoặc ID không hợp lệ
                     JOptionPane.showMessageDialog(this, "Không thể cập nhật đồ uống. Có thể đã bị xóa hoặc ID không hợp lệ.", "Lỗi Cập nhật", JOptionPane.WARNING_MESSAGE);
                }

            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Lỗi khi cập nhật đồ uống: " + e.getMessage(), "Lỗi Database", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một đồ uống để cập nhật.", "Lỗi chọn", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void deleteDrink() {
        int selectedRow = drinkTable.getSelectedRow();
        if (selectedRow >= 0) {
             int confirm = JOptionPane.showConfirmDialog(this,
                 "Bạn có chắc chắn muốn xóa đồ uống này không?",
                 "Xác nhận xóa",
                 JOptionPane.YES_NO_OPTION,
                 JOptionPane.QUESTION_MESSAGE);

            if (confirm == JOptionPane.YES_OPTION) {
                int id = (int) tableModel.getValueAt(selectedRow, 0);

                String sql = "DELETE FROM drinks WHERE food_id = ?";

                try (Connection conn = DatabaseConnection.getConnection();
                   PreparedStatement stmt = conn.prepareStatement(sql)) {

                    stmt.setInt(1, id);
                    stmt.executeUpdate();
                    loadDrinks(); // Refresh table
                    clearForm();

                    JOptionPane.showMessageDialog(this, "Xóa đồ uống thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                } catch (SQLException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Lỗi khi xóa đồ uống: " + e.getMessage(), "Lỗi Database", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
             JOptionPane.showMessageDialog(this, "Vui lòng chọn một đồ uống để xóa.", "Lỗi chọn", JOptionPane.WARNING_MESSAGE);
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
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                "Lỗi kết nối database: " + e.getMessage(),
                "Lỗi",
                JOptionPane.ERROR_MESSAGE);
        }
    }
}
