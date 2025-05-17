package com.project.app.view;

import org.jfree.data.statistics.Statistics;

import com.project.app.session.Session;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DashBoardForm {
    private JPanel mainPanel;
    private JPanel sidebarPanel;
    private JPanel contentPanel;
    private CardLayout cardLayout;

    public DashBoardForm() {
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(250, 240, 230)); // Light beige background

        createSidebarPanel();
        createContentPanel();

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

    private void createSidebarPanel() {
        sidebarPanel = new JPanel();
        sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS));
        sidebarPanel.setBackground(new Color(210, 180, 140)); // Tan color
        sidebarPanel.setPreferredSize(new Dimension(200, 600));

        JButton homeButton = createSidebarButton("Home");
        JButton productsButton = createSidebarButton("Products");
        JButton ordersButton = createSidebarButton("Orders");
        JButton statsButton = createSidebarButton("Statistics");
        JButton LogOutButton = createSidebarButton("LogOut");
        JButton staffManagementButton = createSidebarButton("Staff");

        // Add action listeners to switch content
        homeButton.addActionListener(e -> cardLayout.show(contentPanel, "HOME"));
        productsButton.addActionListener(e -> cardLayout.show(contentPanel, "PRODUCTS"));
        ordersButton.addActionListener(e -> cardLayout.show(contentPanel, "ORDERS"));
        statsButton.addActionListener(e -> cardLayout.show(contentPanel, "STATS"));
        staffManagementButton.addActionListener(e -> cardLayout.show(contentPanel, "STAFF"));
        sidebarPanel.add(Box.createVerticalStrut(20));
        sidebarPanel.add(homeButton);
        
        
        if(Session.getInstance().getAccount().getRole().equals("admin"))
        {
        	sidebarPanel.add(Box.createVerticalStrut(10));
        	sidebarPanel.add(statsButton);
        	sidebarPanel.add(Box.createVerticalStrut(10));
            sidebarPanel.add(productsButton);
            sidebarPanel.add(Box.createVerticalStrut(10));
        	sidebarPanel.add(staffManagementButton);
            
        }
        else {
        	sidebarPanel.add(Box.createVerticalStrut(10));
            sidebarPanel.add(ordersButton);
        }
        
        sidebarPanel.add(Box.createVerticalStrut(10));
        sidebarPanel.add(LogOutButton);
    }

    private void createContentPanel() {
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);

        // Create dummy panels for each section
        JPanel homePanel = createContentCard("Welcome to Home Page");
        JPanel productsPanel = new ProductManagementView();
        JPanel ordersPanel = createContentCard("Order Management Panel");
        JPanel statsPanel = new StatisticView();
        JPanel staffPanel = new StaffManagenmentView();  
        

        // Add panels to contentPanel
        contentPanel.add(homePanel, "HOME");
        contentPanel.add(productsPanel, "PRODUCTS");
        contentPanel.add(ordersPanel, "ORDERS");
        contentPanel.add(statsPanel, "STATS");
        contentPanel.add(staffPanel, "STAFF");
        // Show default card
        cardLayout.show(contentPanel, "HOME");
    }

    private JPanel createContentCard(String labelText) {
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(new GridBagLayout());

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Sans Serif", Font.BOLD, 24));
        panel.add(label);

        return panel;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public JPanel getContentPanel() {
        return contentPanel;
    }

    public JPanel getSidebarPanel() {
        return sidebarPanel;
    }
}
