package com.project.app.view;

import com.project.app.dao.impl.StatisticDAOImpl;
import com.project.app.dao.StatisticDAO;
import com.project.app.view.Component.OverviewPanel;
import com.project.app.view.Component.ChartsPanel;
import com.project.app.view.Component.RecentActivityPanel;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class HomeView extends JPanel {

    public HomeView() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(245, 245, 245)); // Light background
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Add padding
        setPreferredSize(new Dimension(800, 600));
        setMaximumSize(new Dimension(800, 600));
        add(new Label("Welcome"));
        setVisible(true);
    }
    private void createUIComponents() {

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(245, 245, 245)); // Light background
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Add padding

        // Add Quick Overview Section
        String totalOrders = "N/A";
        String totalRevenue = "N/A";
        StatisticDAOImpl statisticDAO = new StatisticDAOImpl();
        try {
            totalOrders = statisticDAO.getTotalOrders(StatisticDAOImpl.TimeRange.TODAY);
            totalRevenue = statisticDAO.getTotalRevenue(StatisticDAOImpl.TimeRange.TODAY);
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception, maybe show an error message on the panel
        }
        add(new OverviewPanel(totalOrders, totalRevenue));
        add(Box.createVerticalStrut(20)); // Spacing

        // Add Charts Section
        add(new ChartsPanel());
        add(Box.createVerticalStrut(20)); // Spacing

        // Add Recent Activity Section
        add(new RecentActivityPanel());

        // Ensure components take up available space
        setAlignmentX(Component.LEFT_ALIGNMENT);
    }

    public static void main(String[] args) {
        Frame frame = new Frame();
        frame.add(new HomeView());
        frame.setVisible(true);
    }
}
