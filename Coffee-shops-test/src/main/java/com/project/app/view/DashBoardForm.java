package com.project.app.view;

import com.project.app.view.Component.SideBarForm;

import com.project.app.session.Session;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class DashBoardForm extends JFrame {
    private JPanel mainPanel;
    private JPanel sidebarPanel;
    private JPanel contentPanel;
    private CardLayout cardLayout;

    public DashBoardForm() throws SQLException {
        super("Dashboard");
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(250, 240, 230));
        createSidebarPanel();
        createContentPanel();

        mainPanel.add(new SideBarForm(), BorderLayout.NORTH);
        mainPanel.add(sidebarPanel, BorderLayout.WEST);
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        this.setContentPane(mainPanel);
        this.setSize(900, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
    }

    private JButton createSidebarButton(String buttonText) {
        JButton button = new JButton(buttonText) {
            private static final int CORNER_RADIUS = 15;

            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), CORNER_RADIUS, CORNER_RADIUS);

                super.paintComponent(g);
                g2.dispose();
            }

            @Override
            protected void paintBorder(Graphics g) {
            }
        };

        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setPreferredSize(new Dimension(100, 40));
        button.setMaximumSize(new Dimension(150, 40));
        button.setBackground(Color.decode("#C6B38E"));
        button.setForeground(new Color(0,0,0));
//        button.setForeground(new Color(250, 245, 240));
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        button.setContentAreaFilled(false);
        button.setOpaque(false);

        return button;
    }

    private void createSidebarPanel() {
        sidebarPanel = new JPanel();
        sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS));
        sidebarPanel.setBackground(new Color(245, 235, 220));
        sidebarPanel.setPreferredSize(new Dimension(170, 600));
        sidebarPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        addLogoToSidebar();
        addNavigationButtonsToSidebar();
        addLogoutButtonToSidebar();
    }

    private void addLogoToSidebar() {
        ImageIcon logoIcon = new ImageIcon(getClass().getResource("/images/logo_icon.png"));
        Image scaledImage = logoIcon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
        logoIcon = new ImageIcon(scaledImage);
        JLabel logoLabel = new JLabel(logoIcon);
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        logoLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        sidebarPanel.add(logoLabel);
        sidebarPanel.add(Box.createVerticalStrut(20));
    }

    private void addNavigationButtonsToSidebar() {
        JButton homeButton = createSidebarButton("Home");
        JButton productsButton = createSidebarButton("Products");
        JButton ordersButton = createSidebarButton("Orders");
        JButton statsButton = createSidebarButton("Statistics");
        JButton staffManagementButton = createSidebarButton("Staff");
        JButton billsButton = createSidebarButton("Bills");

        homeButton.addActionListener(e -> showContentPanel("HOME"));
        productsButton.addActionListener(e -> showContentPanel("PRODUCTS"));
        ordersButton.addActionListener(e -> showContentPanel("ORDERS"));
        statsButton.addActionListener(e -> showContentPanel("STATS"));
        staffManagementButton.addActionListener(e -> showContentPanel("STAFF"));
        ordersButton.addActionListener(e -> showContentPanel("ORDERS"));
        billsButton.addActionListener(e -> showContentPanel("BILLS"));

        sidebarPanel.add(homeButton);
        sidebarPanel.add(Box.createVerticalStrut(15));

        if (Session.getInstance().getAccount().getRole().equals("admin")) {
            sidebarPanel.add(statsButton);
            sidebarPanel.add(Box.createVerticalStrut(15));
            sidebarPanel.add(productsButton);
            sidebarPanel.add(Box.createVerticalStrut(15));
            sidebarPanel.add(staffManagementButton);
        } else {
            sidebarPanel.add(ordersButton);
        }
        sidebarPanel.add(Box.createVerticalStrut(15));
        sidebarPanel.add(billsButton);
    }

    private void addLogoutButtonToSidebar() {
        JButton logOutButton = createSidebarButton("LogOut");
        logOutButton.addActionListener(e -> handleLogout());

        sidebarPanel.add(Box.createVerticalGlue());
        sidebarPanel.add(logOutButton);
        sidebarPanel.add(Box.createVerticalStrut(20));
    }

    private void createContentPanel() throws SQLException {
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        addContentCards();
        showContentPanel("HOME");
    }

    private void addContentCards() throws SQLException {
        JPanel homePanel = new HomeView();
        JPanel productsManagementPanel = new ProductManagementView();
        JPanel orderPanel = new OrderView();
        JPanel statsPanel = new StatisticView();
        JPanel staffPanel = new StaffManagenmentView();
        JPanel billsPanel = new BillListView();


        contentPanel.add(homePanel, "HOME");
        contentPanel.add(productsManagementPanel, "PRODUCTS");
        contentPanel.add(orderPanel, "ORDERS");
        contentPanel.add(statsPanel, "STATS");
        contentPanel.add(staffPanel, "STAFF");
        contentPanel.add(billsPanel, "BILLS");
    }

    private JPanel createSimpleContentCard(String labelText) {
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(new GridBagLayout());

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Sans Serif", Font.BOLD, 24));
        panel.add(label);

        return panel;
    }

    private void showContentPanel(String cardKey) {
        cardLayout.show(contentPanel, cardKey);
    }

    private void handleLogout() {
        Session.getInstance().setAccount(null);
        new LoginForm().setVisible(true);
        this.dispose();
    }
}
