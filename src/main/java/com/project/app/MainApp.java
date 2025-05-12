package com.project.app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Create and show login window
            JFrame loginFrame = new JFrame("Login");
            loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            loginFrame.setSize(300, 200);
            loginFrame.setLocationRelativeTo(null);
            
            JPanel panel = new JPanel(new GridLayout(3, 2));
            JLabel userLabel = new JLabel("Username:");
            JTextField userText = new JTextField();
            JLabel passLabel = new JLabel("Password:");
            JPasswordField passText = new JPasswordField();
            JButton loginButton = new JButton("Login");
            
            panel.add(userLabel);
            panel.add(userText);
            panel.add(passLabel);
            panel.add(passText);
            panel.add(new JLabel());
            panel.add(loginButton);
            
            loginButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // TODO: Add database authentication
                    // For now, just show dashboard on any login
                    loginFrame.dispose();
                    showDashboard();
                }
            });
            
            loginFrame.add(panel);
            loginFrame.setVisible(true);
        });
    }
    
    private static void showDashboard() {
        JFrame dashboard = new JFrame("Dashboard");
        dashboard.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        dashboard.setSize(600, 400);
        dashboard.setLocationRelativeTo(null);
        
        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
        
        JButton employeesBtn = new JButton("Employees");
        JButton invoicesBtn = new JButton("Invoices");
        JButton ordersBtn = new JButton("Orders");
        JButton categoriesBtn = new JButton("Categories");
        
        panel.add(employeesBtn);
        panel.add(invoicesBtn);
        panel.add(ordersBtn);
        panel.add(categoriesBtn);
        
        dashboard.add(panel, BorderLayout.CENTER);
        dashboard.setVisible(true);
    }
}
