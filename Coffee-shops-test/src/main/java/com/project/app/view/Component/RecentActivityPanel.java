package com.project.app.view.Component;

import javax.swing.*;
import java.awt.*;

public class RecentActivityPanel extends JPanel {

    public RecentActivityPanel() {
        setLayout(new BorderLayout());
        setOpaque(false);

        JLabel titleLabel = new JLabel("Hoạt động gần đây");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        add(titleLabel, BorderLayout.NORTH);
        add(Box.createVerticalStrut(10), BorderLayout.CENTER); // Spacing

        // Placeholder for Recent Activity List/Table
        String[] columnNames = {"Thời gian", "Hoạt động", "Chi tiết"};
        Object[][] data = {
                {"10:30 AM", "Đơn hàng mới", "Đơn hàng #1001"},
                {"10:15 AM", "Người dùng đăng nhập", "user123"},
                {"09:45 AM", "Cập nhật sản phẩm", "Cà phê sữa đá"},
        };
        JTable activityTable = new JTable(data, columnNames);
        activityTable.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        activityTable.setRowHeight(25);
        activityTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        activityTable.setBackground(Color.WHITE);
        activityTable.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));

        JScrollPane scrollPane = new JScrollPane(activityTable);
        scrollPane.getViewport().setBackground(Color.WHITE); // Set viewport background to match table
        scrollPane.setBorder(BorderFactory.createEmptyBorder()); // Remove scroll pane border

        add(scrollPane, BorderLayout.SOUTH); // Add scroll pane to the south
    }
}
