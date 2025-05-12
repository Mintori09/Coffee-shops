package com.project.app;

import javax.swing.*;
import java.awt.*;

public class MainApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame mainFrame = new JFrame("Coffee Shop Management System");
            mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            mainFrame.setSize(800, 600);
            mainFrame.setLocationRelativeTo(null);

            JTabbedPane tabbedPane = new JTabbedPane();
            
            // Will add panels for each entity here
            // tabbedPane.addTab("Employees", new NhanVienPanel());
            // tabbedPane.addTab("Menu Categories", new LoaiMonPanel());
            // tabbedPane.addTab("Invoices", new HoaDonPanel());
            // tabbedPane.addTab("Invoice Details", new ChiTietHoaDonPanel());

            mainFrame.add(tabbedPane, BorderLayout.CENTER);
            mainFrame.setVisible(true);
        });
    }
}
