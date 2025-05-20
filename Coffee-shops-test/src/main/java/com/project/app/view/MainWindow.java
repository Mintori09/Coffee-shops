package com.project.app.view;

import com.project.app.view.Component.SideBarForm;

import com.project.app.session.Session;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class MainWindow extends JFrame {
    private JPanel mainPanel;
    private JPanel sidebarPanel;
    private JPanel contentPanel;
    private CardLayout cardLayout;

    // Constants for dimensions
    private static final int FRAME_WIDTH = 900;
    private static final int FRAME_HEIGHT = 600;
    private static final int SIDEBAR_PREFERRED_WIDTH = 170;
    private static final int BUTTON_PREFERRED_WIDTH = 100;
    private static final int BUTTON_HEIGHT = 40;
    private static final int BUTTON_MAX_WIDTH = 150;
    private static final int LOGO_SIZE = 80;
    private static final int VERTICAL_STRUT_SMALL = 15;
    private static final int VERTICAL_STRUT_MEDIUM = 20;
    private static final int BORDER_PADDING_VERTICAL = 20;
    private static final int BORDER_PADDING_HORIZONTAL = 0;
    private static final int BUTTON_BORDER_VERTICAL = 5;
    private static final int BUTTON_BORDER_HORIZONTAL = 10;
    private static final int LOGO_BOTTOM_PADDING = 15;

    // Constants for colors
    private static final Color BG_MAIN_PANEL = new Color(250, 240, 230);
    private static final Color BG_SIDEBAR_PANEL = new Color(245, 235, 220);
    private static final Color COLOR_BUTTON_BG = Color.decode("#C6B38E");
    private static final Color COLOR_BUTTON_FG = new Color(0, 0, 0);
    private static final Color BG_CONTENT_PANEL = Color.WHITE;

    // Constants for fonts
    private static final String FONT_NAME_PRIMARY = "Segoe UI";
    private static final String FONT_NAME_SECONDARY = "Sans Serif";
    private static final int FONT_STYLE_BOLD = Font.BOLD;
    private static final int FONT_SIZE_BUTTON = 14;
    private static final int FONT_SIZE_CONTENT_LABEL = 24;

    // Constants for card keys
    private static final String CARD_KEY_HOME = "HOME";
    private static final String CARD_KEY_PRODUCTS = "PRODUCTS";
    private static final String CARD_KEY_ORDERS = "ORDERS";
    private static final String CARD_KEY_STATS = "STATS";
    private static final String CARD_KEY_STAFF = "STAFF";
    private static final String CARD_KEY_BILLS = "BILLS";

    // Constant for frame title
    private static final String FRAME_TITLE = "Dashboard";


    public MainWindow() throws SQLException {
        super(FRAME_TITLE);
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(BG_MAIN_PANEL);
        createSidebarPanel();
        createContentPanel();

        mainPanel.add(new SideBarForm(), BorderLayout.NORTH);
        mainPanel.add(sidebarPanel, BorderLayout.WEST);
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        this.setContentPane(mainPanel);
        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
    }

    private JButton createSidebarButton(String buttonText) {
        JButton button = new JButton(buttonText) {
            private static final int CORNER_RADIUS = 15; // Keep local as it's only used here

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
        button.setPreferredSize(new Dimension(BUTTON_PREFERRED_WIDTH, BUTTON_HEIGHT));
        button.setMaximumSize(new Dimension(BUTTON_MAX_WIDTH, BUTTON_HEIGHT));
        button.setBackground(COLOR_BUTTON_BG);
        button.setForeground(COLOR_BUTTON_FG);
//        button.setForeground(new Color(250, 245, 240));
        button.setFont(new Font(FONT_NAME_PRIMARY, FONT_STYLE_BOLD, FONT_SIZE_BUTTON));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(BUTTON_BORDER_VERTICAL, BUTTON_BORDER_HORIZONTAL, BUTTON_BORDER_VERTICAL, BUTTON_BORDER_HORIZONTAL));
        button.setContentAreaFilled(false);
        button.setOpaque(false);

        return button;
    }

    private void createSidebarPanel() {
        sidebarPanel = new JPanel();
        sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS));
        sidebarPanel.setBackground(BG_SIDEBAR_PANEL);
        sidebarPanel.setPreferredSize(new Dimension(SIDEBAR_PREFERRED_WIDTH, FRAME_HEIGHT));
        sidebarPanel.setBorder(BorderFactory.createEmptyBorder(BORDER_PADDING_VERTICAL, BORDER_PADDING_HORIZONTAL, BORDER_PADDING_VERTICAL, BORDER_PADDING_HORIZONTAL));

        addLogoToSidebar();
        addNavigationButtonsToSidebar();
        addLogoutButtonToSidebar();
    }

    private void addLogoToSidebar() {
        ImageIcon logoIcon = new ImageIcon(getClass().getResource("/images/icon/logo_icon.png"));
        Image scaledImage = logoIcon.getImage().getScaledInstance(LOGO_SIZE, LOGO_SIZE, Image.SCALE_SMOOTH);
        logoIcon = new ImageIcon(scaledImage);
        JLabel logoLabel = new JLabel(logoIcon);
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        logoLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, LOGO_BOTTOM_PADDING, 0));
        sidebarPanel.add(logoLabel);
        sidebarPanel.add(Box.createVerticalStrut(VERTICAL_STRUT_MEDIUM));
    }

    private void addNavigationButtonsToSidebar() {
        JButton homeButton = createSidebarButton("Home");
        JButton productsButton = createSidebarButton("Products");
        JButton ordersButton = createSidebarButton("Orders");
        JButton statsButton = createSidebarButton("Statistics");
        JButton staffManagementButton = createSidebarButton("Staff");
        JButton billsButton = createSidebarButton("Bills");

        homeButton.addActionListener(e -> showContentPanel(CARD_KEY_HOME));
        productsButton.addActionListener(e -> showContentPanel(CARD_KEY_PRODUCTS));
        ordersButton.addActionListener(e -> showContentPanel(CARD_KEY_ORDERS));
        statsButton.addActionListener(e -> showContentPanel(CARD_KEY_STATS));
        staffManagementButton.addActionListener(e -> showContentPanel(CARD_KEY_STAFF));
        billsButton.addActionListener(e -> showContentPanel(CARD_KEY_BILLS));

        sidebarPanel.add(homeButton);
        sidebarPanel.add(Box.createVerticalStrut(VERTICAL_STRUT_SMALL));

        if (Session.getInstance().getAccount().getRole().equalsIgnoreCase("admin")) {
            sidebarPanel.add(statsButton);
            sidebarPanel.add(Box.createVerticalStrut(VERTICAL_STRUT_SMALL));
            sidebarPanel.add(productsButton);
            sidebarPanel.add(Box.createVerticalStrut(VERTICAL_STRUT_SMALL));
            sidebarPanel.add(staffManagementButton);
        } else {
            sidebarPanel.add(ordersButton);
        }
        sidebarPanel.add(Box.createVerticalStrut(VERTICAL_STRUT_SMALL));
        sidebarPanel.add(billsButton);
    }

    private void addLogoutButtonToSidebar() {
        JButton logOutButton = createSidebarButton("LogOut");
        logOutButton.addActionListener(e -> handleLogout());

        sidebarPanel.add(Box.createVerticalGlue());
        sidebarPanel.add(logOutButton);
        sidebarPanel.add(Box.createVerticalStrut(VERTICAL_STRUT_MEDIUM));
    }

    private void createContentPanel() throws SQLException {
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        addContentCards();
        showContentPanel(CARD_KEY_HOME);
    }

    private void addContentCards() throws SQLException {
        JPanel homePanel = new HomeView();
        JPanel productsManagementPanel = new ProductManagementView();
        JPanel billsPanel = new BillListView(); // Create BillListView first
        JPanel orderPanel = new OrderView((BillListView) billsPanel); // Pass BillListView to OrderView
        JPanel statsPanel = new StatisticView();
        JPanel staffPanel = new StaffManagenmentView();


        contentPanel.add(homePanel, CARD_KEY_HOME);
        contentPanel.add(productsManagementPanel, CARD_KEY_PRODUCTS);
        contentPanel.add(orderPanel, CARD_KEY_ORDERS);
        contentPanel.add(statsPanel, CARD_KEY_STATS);
        contentPanel.add(staffPanel, CARD_KEY_STAFF);
        contentPanel.add(billsPanel, CARD_KEY_BILLS);
    }

    private JPanel createSimpleContentCard(String labelText) {
        JPanel panel = new JPanel();
        panel.setBackground(BG_CONTENT_PANEL);
        panel.setLayout(new GridBagLayout());

        JLabel label = new JLabel(labelText);
        label.setFont(new Font(FONT_NAME_SECONDARY, FONT_STYLE_BOLD, FONT_SIZE_CONTENT_LABEL));
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
