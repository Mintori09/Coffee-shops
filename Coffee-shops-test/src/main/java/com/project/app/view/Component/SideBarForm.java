package com.project.app.view.Component;

import com.project.app.session.Session;

import javax.swing.*;
import java.awt.*;

public class SideBarForm extends JPanel {
    public SideBarForm() {
        setLayout(new BorderLayout());
        setBackground(new Color(210, 180, 140));
        String username = "Guest";
        if (Session.getInstance().getAccount() != null) {
            username = Session.getInstance().getAccount().getUsername();
        }
        JLabel usernameLabel = new JLabel("Coffee Shop");
        usernameLabel.setFont(new Font("Inter", Font.BOLD, 12));
        usernameLabel.setForeground(Color.BLACK);
        usernameLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 30));
        usernameLabel.setHorizontalAlignment(SwingConstants.LEFT);

        JLabel userLabel = new JLabel("User: " + username);
        userLabel.setFont(new Font("Inter", Font.BOLD, 12));
        userLabel.setForeground(Color.BLACK);
        userLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 30));
        userLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.X_AXIS));
        headerPanel.setBackground(new Color(210, 180, 140));

        headerPanel.add(usernameLabel);
        headerPanel.add(Box.createHorizontalGlue()); // khoảng cách linh hoạt đẩy label bên phải
        headerPanel.add(userLabel);

        add(headerPanel, BorderLayout.NORTH);
    }
}
