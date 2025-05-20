package com.project.app.view;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import com.project.app.dao.EmployeeDAO;
import com.project.app.dao.impl.EmployeeDAOImpl;
import com.project.app.database.*;
import com.project.app.model.Employee;

public class StaffManagenmentView extends JPanel {
    private static final long serialVersionUID = 1L;
    private JTextField txtName, txtUsername, txtRole;
    private JPasswordField txtPassword;
    private JTable staffTable;
    private JButton btnAdd, btnUpdate, btnDelete, btnClear;

    public StaffManagenmentView() {
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(245, 235, 220));
        JPanel infoPanel = new JPanel();
        infoPanel.setBackground(new Color(245, 235, 220));
        infoPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(160, 82, 45)),
                "Staff Details",
                TitledBorder.LEFT, TitledBorder.TOP,
                new Font("Sans Serif", Font.BOLD, 18),
                new Color(160, 82, 45)
        ));

        infoPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        infoPanel.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1;
        txtName = new JTextField(18);
        infoPanel.add(txtName, gbc);

        gbc.gridx = 2;
        infoPanel.add(new JLabel("Username:"), gbc);
        gbc.gridx = 3;
        txtUsername = new JTextField(15);
        infoPanel.add(txtUsername, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        infoPanel.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        txtPassword = new JPasswordField(18);
        infoPanel.add(txtPassword, gbc);

        gbc.gridx = 2;
        infoPanel.add(new JLabel("Role:"), gbc);
        gbc.gridx = 3;
        txtRole = new JTextField(15);
        infoPanel.add(txtRole, gbc);
        add(infoPanel, BorderLayout.NORTH);

        staffTable = new JTable(new DefaultTableModel(
                new Object[]{"Employee ID", "Full Name", "Username", "Role", "Hire Date"}, 0
        ));
        staffTable.getTableHeader().setFont(new Font("Sans Serif", Font.BOLD, 14));
        staffTable.getTableHeader().setBackground(new Color(160, 82, 45));
        staffTable.getTableHeader().setForeground(Color.WHITE);
        staffTable.setRowHeight(28);
        staffTable.setFont(new Font("Sans Serif", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(staffTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(160, 82, 45)));
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(245, 235, 220));
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        btnAdd = createButton("Add", "\u2795", new Color(139, 69, 19));
        btnUpdate = createButton("Update", "\u270E", new Color(205, 133, 63));
        btnDelete = createButton("Delete", "\u1F5D1", new Color(205, 92, 92));
        btnClear = createButton("Clear", "\u274C", new Color(160, 82, 45));
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnClear);
        add(buttonPanel, BorderLayout.SOUTH);
        btnAdd.addActionListener(e -> addStaff());
        btnUpdate.addActionListener(e -> updateStaff());
        btnDelete.addActionListener(e -> deleteStaff());
        btnClear.addActionListener(e -> clearFields());
        staffTable.getSelectionModel().addListSelectionListener(e -> fillFieldsFromTable());
        loadDataToTable();
    }

    private void loadDataToTable() {
        DefaultTableModel model = (DefaultTableModel) staffTable.getModel();
        model.setRowCount(0);
        // Assuming a DAO method exists to get combined Employee and Account data
        // For demonstration, let's assume a method like getAllStaffDetails() exists
        // and returns a list of objects with getEmployeeId(), getFullName(), getUsername(), getRole(), getHireDate()
        EmployeeDAO employeeDAO = null;
        try {
            employeeDAO = new EmployeeDAOImpl();
            java.util.List<Object[]> staffDetails = employeeDAO.getAllStaffDetails();
            for (Object[] row : staffDetails) {
                model.addRow(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Không thể kết nối tới cơ sở dữ liệu hoặc lấy dữ liệu nhân viên!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addStaff() {
        // This method needs to be updated to handle adding both Employee and Account data
        // This will likely involve calls to both EmployeeDAO and AccountDAO
        JOptionPane.showMessageDialog(this, "Chức năng thêm nhân viên cần được triển khai để xử lý cả thông tin nhân viên và tài khoản.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        clearFields();
    }

    private void updateStaff() {
        // This method needs to be updated to handle updating both Employee and Account data
        // This will likely involve calls to both EmployeeDAO and AccountDAO
        int selectedRow = staffTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn nhân viên để cập nhật!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        JOptionPane.showMessageDialog(this, "Chức năng cập nhật nhân viên cần được triển khai để xử lý cả thông tin nhân viên và tài khoản.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        clearFields();
    }

    private void deleteStaff() {
        // This method needs to be updated to handle deleting both Employee and Account data
        // This will likely involve calls to both EmployeeDAO and AccountDAO
        int selectedRow = staffTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn nhân viên để xóa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        JOptionPane.showMessageDialog(this, "Chức năng xóa nhân viên cần được triển khai để xử lý cả thông tin nhân viên và tài khoản.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        clearFields();
    }

    private void clearFields() {
        txtName.setText("");
        txtUsername.setText("");
        txtPassword.setText(""); // Password field is still cleared, but not used for display
        txtRole.setText("");
        staffTable.clearSelection();
    }

    private void fillFieldsFromTable() {
        int selectedRow = staffTable.getSelectedRow();
        if (selectedRow != -1) {
            DefaultTableModel model = (DefaultTableModel) staffTable.getModel();
            txtName.setText(model.getValueAt(selectedRow, 1).toString()); // Full Name
            txtUsername.setText(model.getValueAt(selectedRow, 2).toString()); // Username
            txtRole.setText(model.getValueAt(selectedRow, 3).toString()); // Role

            txtPassword.setText(""); // Password is not displayed in the table
        }
    }

    private JButton createButton(String text, String icon, Color bgColor) {
        JButton btn = new JButton(icon + " " + text);
        btn.setFont(new Font("Sans Serif", Font.BOLD, 15));
        btn.setBackground(bgColor);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(120, 40));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    public static void main(String[] args) {
        new StaffManagenmentView().setVisible(true);
    }
}
