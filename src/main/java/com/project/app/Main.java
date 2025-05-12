package com.project.app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Main {
    public static void main(String[] args) {
        // Create the main frame
        JFrame frame = new JFrame("Project App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout());

        // Create welcome label
        JLabel welcomeLabel = new JLabel("Hello and welcome!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        
        // Create counter button
        JButton counterButton = new JButton("Show Numbers");
        counterButton.addActionListener(e -> {
            StringBuilder numbers = new StringBuilder("<html>");
            for (int i = 1; i <= 5; i++) {
                numbers.append("i = ").append(i).append("<br>");
            }
            numbers.append("</html>");
            welcomeLabel.setText(numbers.toString());
        });

        // Add components to frame
        frame.add(welcomeLabel, BorderLayout.CENTER);
        frame.add(counterButton, BorderLayout.SOUTH);

        // Center and show frame
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
