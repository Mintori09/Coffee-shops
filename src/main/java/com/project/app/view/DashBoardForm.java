package com.project.app.view;

import javax.swing.*;
import java.awt.*;

public class DashBoardForm {
    private JPanel mainPanel;
    private JPanel sidebarPanel;
    private JPanel contentPanel;

    public DashBoardForm() {
        // Main panel setup
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(250, 240, 230)); // Light beige background

        // Sidebar panel setup
        sidebarPanel = new JPanel();
        sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS));
        sidebarPanel.setBackground(new Color(210, 180, 140)); // Tan color
        sidebarPanel.setPreferredSize(new Dimension(200, 600));

        // Create buttons
        JButton homeButton = createSidebarButton("Home");
        JButton productsButton = createSidebarButton("Products");
        JButton ordersButton = createSidebarButton("Orders");
        JButton statsButton = createSidebarButton("Statistics");

        // Add buttons to sidebar
        sidebarPanel.add(Box.createVerticalStrut(20));
        sidebarPanel.add(homeButton);
        sidebarPanel.add(Box.createVerticalStrut(10));
        sidebarPanel.add(productsButton);
        sidebarPanel.add(Box.createVerticalStrut(10));
        sidebarPanel.add(ordersButton);
        sidebarPanel.add(Box.createVerticalStrut(10));
        sidebarPanel.add(statsButton);

        // Content panel setup
        contentPanel = new JPanel();
        contentPanel.setBackground(new Color(255, 255, 255)); // White background

        // Add components to main panel
        mainPanel.add(sidebarPanel, BorderLayout.WEST);
        mainPanel.add(contentPanel, BorderLayout.CENTER);
    }

    private JButton createSidebarButton(String text) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setPreferredSize(new Dimension(150, 40));
        button.setMaximumSize(new Dimension(150, 40));
        button.setBackground(new Color(200, 150, 100)); // Coffee brown
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Sans Serif", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        return button;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
