package com.project.app.view.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class OverviewPanel extends JPanel {

    public OverviewPanel(String totalOrders, String totalRevenue) {
        setLayout(new GridLayout(1, 4, 15, 0)); // 1 row, 4 columns, 15px horizontal gap
        setOpaque(false); // Make panel transparent to show parent background

        // Add cards with dynamic data
        add(createOverviewCard("Tổng số đơn hàng", totalOrders));
        add(createOverviewCard("Doanh thu hôm nay", totalRevenue));
        add(createOverviewCard("Số người dùng mới", "150")); // Placeholder
        add(createOverviewCard("Tỷ lệ chuyển đổi", "12%")); // Placeholder
    }

    private JPanel createOverviewCard(String title, String value) {
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                int arc = 15; // Rounded corner radius
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), arc, arc));
                g2.dispose();
            }

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(200, 100); // Preferred size for the card
            }
        };
        card.setLayout(new BorderLayout(10, 10)); // Spacing inside card
        card.setBackground(Color.WHITE); // Card background color
        card.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15)); // Padding inside card

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        titleLabel.setForeground(Color.GRAY);

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        valueLabel.setForeground(Color.BLACK);

        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);

        return card;
    }
}
