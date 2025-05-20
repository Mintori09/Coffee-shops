package com.project.app.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.time.LocalDate;
import java.sql.Connection;

import com.project.app.dao.AccountDAO;
import com.project.app.dao.EmployeeDAO;
import com.project.app.dao.impl.AccountDAOImpl;
import com.project.app.dao.impl.EmployeeDAOImpl;
import com.project.app.database.DatabaseConnection;
import com.project.app.model.Account;
import com.project.app.model.Employee;
import com.project.app.view.Component.StaffInputSidebar;

public class StaffManagenmentView extends JPanel {
    private JTable staffTable;
    private StaffInputSidebar staffInputSidebar;
    private boolean isAddingStaff;
    private int selectedEmployeeId;

    public StaffManagenmentView() {
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(245, 235, 220));

        DefaultTableModel model = new DefaultTableModel(new Object[]{"Employee ID", "Full Name", "Username", "Role", "Hire Date"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        staffTable = new JTable(model);
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
        JButton btnAdd = createButton("Add", "\u2795", new Color(139, 69, 19));
        JButton btnUpdate = createButton("Update", "\u270E", new Color(205, 133, 63));
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        add(buttonPanel, BorderLayout.SOUTH);

        staffInputSidebar = new StaffInputSidebar();
        staffInputSidebar.setVisible(false);
        add(staffInputSidebar, BorderLayout.EAST);

        btnAdd.addActionListener(e -> {
            isAddingStaff = true;
            staffInputSidebar.clearFields();
            staffInputSidebar.setVisible(true);
        });
        btnUpdate.addActionListener(e -> {
            int selectedRow = staffTable.getSelectedRow();
            if (selectedRow != -1) {
                isAddingStaff = false;
                selectedEmployeeId = (int) staffTable.getModel().getValueAt(selectedRow, 0);
                fillFieldsFromTable();
                staffInputSidebar.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn nhân viên để cập nhật!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            }
        });

        staffInputSidebar.addSaveButtonListener(e -> handleSaveStaff());
        staffInputSidebar.addCancelButtonListener(e -> handleCancelStaff());

        staffTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && staffTable.getSelectedRow() != -1) {
                fillFieldsFromTable();
            }
        });
        loadDataToTable();
    }

    private void loadDataToTable() {
        DefaultTableModel model = (DefaultTableModel) staffTable.getModel();
        model.setRowCount(0);
        // The model is already set in the constructor with overridden isCellEditable

        try {
            EmployeeDAO employeeDAO = new EmployeeDAOImpl();
            java.util.List<Object[]> staffDetails = employeeDAO.getAllStaffDetails();
            for (Object[] row : staffDetails) {
                model.addRow(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Không thể kết nối tới cơ sở dữ liệu hoặc lấy dữ liệu nhân viên!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void fillFieldsFromTable() {
        int selectedRow = staffTable.getSelectedRow();
        if (selectedRow != -1) {
            DefaultTableModel model = (DefaultTableModel) staffTable.getModel();
            int employeeId = (int) model.getValueAt(selectedRow, 0);

            try {
                EmployeeDAO employeeDAO = new EmployeeDAOImpl();
                AccountDAO accountDAO = new AccountDAOImpl();
                Employee employee = employeeDAO.findById(employeeId);
                Account account = accountDAO.findById(employee.getAccountId());

                if (employee != null && account != null) {
                    staffInputSidebar.setStaffData(employee.getFullName(), employee.getDateOfBirth(), employee.getGender(), employee.getPhoneNumber(), account.getUsername(), account.getRole());
                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Lỗi khi lấy dữ liệu nhân viên để hiển thị: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void handleSaveStaff() {
        String name = staffInputSidebar.getStaffName();
        LocalDate dateOfBirth = staffInputSidebar.getDateOfBirth();
        String gender = staffInputSidebar.getGender();
        String phoneNumber = staffInputSidebar.getPhoneNumber();
        String username = staffInputSidebar.getUsername();
        String password = staffInputSidebar.getPassword();
        String role = staffInputSidebar.getRole();

        if (name.isEmpty() || dateOfBirth == null || gender == null || phoneNumber.isEmpty() || username.isEmpty() || role == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin nhân viên!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!phoneNumber.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "Số điện thoại chỉ được chứa chữ số!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);
            AccountDAO accountDAO = new AccountDAOImpl();
            EmployeeDAO employeeDAO = new EmployeeDAOImpl();

            if (isAddingStaff) {
                // Add new staff
                if (accountDAO.findByUsername(username) != null) {
                    JOptionPane.showMessageDialog(this, "Tên đăng nhập đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    conn.rollback();
                    return;
                }

                Account account = new Account();
                account.setUsername(username);
                account.setPassword(password);
                account.setRole(role);

                int accountId = accountDAO.create(account);
                if (accountId > 0) {
                    Employee employee = new Employee();
                    employee.setFullName(name);
                    employee.setDateOfBirth(dateOfBirth);
                    employee.setGender(gender);
                    employee.setPhoneNumber(phoneNumber);
                    employee.setAccountId(accountId);

                    int employeeId = employeeDAO.create(employee);
                    if (employeeId > 0) {
                        conn.commit();
                        JOptionPane.showMessageDialog(this, "Thêm nhân viên thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                        loadDataToTable();
                        staffInputSidebar.setVisible(false);
                    } else {
                        accountDAO.deleteAccountById(accountId);
                        conn.rollback();
                        JOptionPane.showMessageDialog(this, "Thêm nhân viên thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    conn.rollback();
                    JOptionPane.showMessageDialog(this, "Tạo tài khoản thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                Employee existingEmployee = employeeDAO.findById(selectedEmployeeId);
                Account existingAccount = accountDAO.findById(existingEmployee.getAccountId());

                if (!existingAccount.getUsername().equals(username)) {
                    Account accountWithSameUsername = accountDAO.findByUsername(username);
                    if (accountWithSameUsername != null && accountWithSameUsername.getId() != existingAccount.getId()) {
                        JOptionPane.showMessageDialog(this, "Tên đăng nhập đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        conn.rollback();
                        return;
                    }
                }

                existingEmployee.setFullName(name);
                existingEmployee.setDateOfBirth(dateOfBirth);
                existingEmployee.setGender(gender);
                existingEmployee.setPhoneNumber(phoneNumber);
                boolean employeeUpdated = employeeDAO.updateEmployee(existingEmployee);

                existingAccount.setUsername(username);
                if (!password.isEmpty()) {
                    existingAccount.setPassword(password);
                }
                existingAccount.setRole(role);
                boolean accountUpdated = accountDAO.updateAccount(existingAccount);

                if (employeeUpdated && accountUpdated) {
                    conn.commit();
                    JOptionPane.showMessageDialog(this, "Cập nhật nhân viên thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    loadDataToTable();
                    staffInputSidebar.setVisible(false);
                } else {
                    conn.rollback();
                    JOptionPane.showMessageDialog(this, "Cập nhật nhân viên thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi lưu nhân viên: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    private void handleCancelStaff() {
        staffInputSidebar.setVisible(false);
        staffTable.clearSelection();
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
}
