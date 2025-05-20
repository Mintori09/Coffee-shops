package com.project.app.view.Component;

import javax.swing.*;
import java.awt.*;

public class ChartsPanel extends JPanel {

    public ChartsPanel() {
        setLayout(new GridLayout(1, 2, 15, 0)); // 1 row, 2 columns, 15px horizontal gap
        setOpaque(false);

        // Placeholder for Revenue Chart
        JPanel revenueChartPanel = new JPanel(new BorderLayout());
        revenueChartPanel.setBackground(Color.WHITE);
        revenueChartPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(10, 10, 10, 10), // Outer padding
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1) // Inner border
        ));
        JLabel revenueChartLabel = new JLabel("Biểu đồ doanh thu theo tháng (Placeholder)");
        revenueChartLabel.setHorizontalAlignment(SwingConstants.CENTER);
        revenueChartPanel.add(revenueChartLabel, BorderLayout.CENTER);

        // Placeholder for Orders by Status Chart
        JPanel ordersChartPanel = new JPanel(new BorderLayout());
        ordersChartPanel.setBackground(Color.WHITE);
        ordersChartPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(10, 10, 10, 10), // Outer padding
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1) // Inner border
        ));
        JLabel ordersChartLabel = new JLabel("Biểu đồ đơn hàng theo trạng thái (Placeholder)");
        ordersChartLabel.setHorizontalAlignment(SwingConstants.CENTER);
        ordersChartPanel.add(ordersChartLabel, BorderLayout.CENTER);

        add(revenueChartPanel);
        add(ordersChartPanel);
    }
}
