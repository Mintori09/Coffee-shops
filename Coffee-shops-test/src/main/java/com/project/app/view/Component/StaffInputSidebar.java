package com.project.app.view.Component;

import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class StaffInputSidebar extends JPanel {
    private JTextField txtName, txtUsername, txtPhoneNumber;
    private JPasswordField txtPassword;
    private JComboBox<String> cmbGender, cmbRole;
    private JDateChooser dateChooserDateOfBirth;
    private JButton btnSave, btnCancel;

    public StaffInputSidebar() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Full Name:"), gbc);
        gbc.gridx = 1;
        txtName = new JTextField(15); // Adjusted size for sidebar
        add(txtName, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Date of Birth:"), gbc);
        gbc.gridx = 1;
        dateChooserDateOfBirth = new JDateChooser();
        dateChooserDateOfBirth.setPreferredSize(new Dimension(150, 25)); // Adjusted size
        add(dateChooserDateOfBirth, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Gender:"), gbc);
        gbc.gridx = 1;
        String[] genders = {"Nam", "Nữ", "Khác"};
        cmbGender = new JComboBox<>(genders);
        cmbGender.setPreferredSize(new Dimension(150, 25)); // Adjusted size
        add(cmbGender, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        add(new JLabel("Phone Number:"), gbc);
        gbc.gridx = 1;
        txtPhoneNumber = new JTextField(15); // Adjusted size
        add(txtPhoneNumber, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        add(new JLabel("Username:"), gbc);
        gbc.gridx = 1;
        txtUsername = new JTextField(15); // Adjusted size
        add(txtUsername, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        txtPassword = new JPasswordField(15); // Adjusted size
        add(txtPassword, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        add(new JLabel("Role:"), gbc);
        gbc.gridx = 1;
        String[] roles = {"Admin", "Staff"};
        cmbRole = new JComboBox<>(roles);
        cmbRole.setPreferredSize(new Dimension(150, 25)); // Adjusted size
        add(cmbRole, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnSave = new JButton("Save");
        btnCancel = new JButton("Cancel");

        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);
        add(buttonPanel, gbc);

    }

    public String getStaffName() {
        return txtName.getText().trim();
    }

    public LocalDate getDateOfBirth() {
        Date selectedDate = dateChooserDateOfBirth.getDate();
        if (selectedDate != null) {
            return selectedDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        }
        return null;
    }

    public String getGender() {
        return (String) cmbGender.getSelectedItem();
    }

    public String getPhoneNumber() {
        return txtPhoneNumber.getText().trim();
    }

    public String getUsername() {
        return txtUsername.getText().trim();
    }

    public String getPassword() {
        return new String(txtPassword.getPassword()).trim();
    }

    public String getRole() {
        return (String) cmbRole.getSelectedItem();
    }

    public void setStaffData(String name, LocalDate dateOfBirth, String gender, String phoneNumber, String username, String role) {
        txtName.setText(name);
        if (dateOfBirth != null) {
            dateChooserDateOfBirth.setDate(Date.from(dateOfBirth.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        } else {
            dateChooserDateOfBirth.setDate(null);
        }
        cmbGender.setSelectedItem(gender);
        txtPhoneNumber.setText(phoneNumber);
        txtUsername.setText(username);
        cmbRole.setSelectedItem(role);
        txtPassword.setText("");
    }

    public void clearFields() {
        txtName.setText("");
        dateChooserDateOfBirth.setDate(null);
        cmbGender.setSelectedIndex(0);
        txtPhoneNumber.setText("");
        txtUsername.setText("");
        txtPassword.setText("");
        cmbRole.setSelectedIndex(0);
    }

    public void addSaveButtonListener(ActionListener listener) {
        btnSave.addActionListener(listener);
    }

    public void addCancelButtonListener(ActionListener listener) {
        btnCancel.addActionListener(listener);
    }
}
