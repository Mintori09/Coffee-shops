package com.project.app.view;

import javax.swing.*;
import java.awt.*;

public class HomeView extends JPanel {

    public HomeView() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        ImageIcon logoIcon = new ImageIcon(getClass().getResource("/images/icon/logo_icon.png"));
        JLabel logoLabel = new JLabel(logoIcon);
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel titleLabel = new JLabel("Ứng dụng quản lý quán cà phê");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextArea descriptionArea = new JTextArea("Ứng dụng này giúp quản lý hiệu quả các hoạt động của quán cà phê, bao gồm quản lý nhân viên, sản phẩm, hóa đơn và thống kê doanh thu.");
        descriptionArea.setFont(new Font("Arial", Font.PLAIN, 14));
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setLineWrap(true);
        descriptionArea.setEditable(false);
        descriptionArea.setBackground(getBackground());
        descriptionArea.setAlignmentX(Component.CENTER_ALIGNMENT);
        descriptionArea.setMaximumSize(new Dimension(400, 100)); // Limit the width

        add(Box.createVerticalGlue());
        add(logoLabel);
        add(Box.createRigidArea(new Dimension(0, 10))); // Add some space
        add(titleLabel);
        add(Box.createRigidArea(new Dimension(0, 10))); // Add some space
        add(descriptionArea);
        add(Box.createVerticalGlue());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Home View Test");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new HomeView());
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
