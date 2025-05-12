package com.coffeeshop.views;

import javax.swing.*;
import java.awt.*;

public class MainView extends JFrame {
    private JPanel mainPanel;
    private JTabbedPane tabbedPane;
    
    public MainView() {
        setTitle("Coffee Shop Management");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        initComponents();
    }
    
    private void initComponents() {
        mainPanel = new JPanel(new BorderLayout());
        tabbedPane = new JTabbedPane();
        
        // Add tabs here
        // tabbedPane.addTab("Orders", new OrdersPanel());
        // tabbedPane.addTab("Products", new ProductsPanel());
        
        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        add(mainPanel);
    }
}
