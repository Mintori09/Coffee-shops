package com.project.app;

import com.project.app.view.DashBoardForm;

import javax.swing.*;

public class MainApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Coffee Shop Dashboard");
            DashBoardForm dashboard = new DashBoardForm();
            frame.setContentPane(dashboard.getMainPanel());
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(900, 600);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
