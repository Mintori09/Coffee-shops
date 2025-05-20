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
                new Object[]{"ID", "Name", "Username", "Password", "Hire date", "Role"}, 0
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
        EmployeeDAO employeeDAO = null;
        try {
            employeeDAO = new EmployeeDAOImpl(DatabaseConnection.getConnection());
            java.util.List<Employee> employees = employeeDAO.getAllEmployees();
            for (Employee emp : employees) {
                model.addRow(new Object[]{
                        emp.getId(),
                        emp.getFullName(),
                        emp.getPhoneNumber(),
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Không thể kết nối tới cơ sở dữ liệu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addStaff() {
        DefaultTableModel model = (DefaultTableModel) staffTable.getModel();
        String name = txtName.getText();
        String username = txtUsername.getText();
        String password = new String(txtPassword.getPassword());
        String role = txtRole.getText();
        if (name.isEmpty() || username.isEmpty() || password.isEmpty() || role.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int id = model.getRowCount() + 1;
        model.addRow(new Object[]{id, name, username, role});
        clearFields();
    }

    private void updateStaff() {
        int selectedRow = staffTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn nhân viên để cập nhật!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        DefaultTableModel model = (DefaultTableModel) staffTable.getModel();
        model.setValueAt(txtName.getText(), selectedRow, 1);
        model.setValueAt(txtUsername.getText(), selectedRow, 2);
        model.setValueAt(txtRole.getText(), selectedRow, 3);
        clearFields();
    }

    private void deleteStaff() {
        int selectedRow = staffTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn nhân viên để xóa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        DefaultTableModel model = (DefaultTableModel) staffTable.getModel();
        model.removeRow(selectedRow);
        clearFields();
    }

    private void clearFields() {
        txtName.setText("");
        txtUsername.setText("");
        txtPassword.setText("");
        txtRole.setText("");
        staffTable.clearSelection();
    }

    private void fillFieldsFromTable() {
        int selectedRow = staffTable.getSelectedRow();
        if (selectedRow != -1) {
            DefaultTableModel model = (DefaultTableModel) staffTable.getModel();
            txtName.setText(model.getValueAt(selectedRow, 1).toString());
            txtUsername.setText(model.getValueAt(selectedRow, 2).toString());
            txtRole.setText(model.getValueAt(selectedRow, 3).toString());

            txtPassword.setText("");
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

