package com.project.app.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ProductView extends JPanel {

    private JPanel sidebarPanel;
    private JPanel productPanel;
    private JPanel cartPanel;
    private JPanel topBarPanel;

    public ProductView() {
        setLayout(new BorderLayout());
        setBackground(new Color(0xEFEBE9)); // Background color

        createSidebar();
        createProductDisplay();
        createCartPanel();
        createTopBar();

        add(sidebarPanel, BorderLayout.WEST);
        add(new JScrollPane(productPanel), BorderLayout.CENTER); // Make product area scrollable
        add(cartPanel, BorderLayout.EAST);
        add(topBarPanel, BorderLayout.NORTH);
    }

    private void createSidebar() {
        sidebarPanel = new JPanel();
        sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS));
        sidebarPanel.setBackground(new Color(0x4E342E)); // Dark coffee brown
        sidebarPanel.setBorder(new EmptyBorder(16, 16, 16, 16)); // Padding

        // Placeholder buttons
        addButtonToSidebar("Dashboard");
        addButtonToSidebar("Coffee");
        addButtonToSidebar("Tea");
        addButtonToSidebar("Dessert");
        addButtonToSidebar("Orders");

        // Add some space at the bottom
        sidebarPanel.add(Box.createVerticalGlue());
    }

    private void addButtonToSidebar(String text) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT); // Center align buttons
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(0x6D4C41)); // Accent color
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0xA1887F), 1), // Border for rounded effect (basic)
                new EmptyBorder(8, 16, 8, 16) // Padding
        ));
        button.setFocusPainted(false); // Remove focus border

        // Basic hover effect (requires more advanced techniques for true hover)
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(0x5D4037)); // Slightly darker on hover
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(0x6D4C41)); // Restore original color
            }
        });

        sidebarPanel.add(button);
        sidebarPanel.add(Box.createVerticalStrut(10)); // Spacing between buttons
    }

    private void createProductDisplay() {
        productPanel = new JPanel();
        productPanel.setLayout(new GridLayout(0, 3, 10, 10)); // 3 columns, auto rows, 10px spacing
        productPanel.setBackground(new Color(0xEFEBE9)); // Background color
        productPanel.setBorder(new EmptyBorder(16, 16, 16, 16)); // Padding

        // Mock product data
        List<Product> mockProducts = new ArrayList<>();
        mockProducts.add(new Product("Espresso", "$3.50", new Color(0xD7CCC8)));
        mockProducts.add(new Product("Latte", "$4.00", new Color(0xA1887F)));
        mockProducts.add(new Product("Cappuccino", "$4.25", new Color(0xFFF3E0)));
        mockProducts.add(new Product("Mocha", "$4.50", new Color(0xD7CCC8)));
        mockProducts.add(new Product("Americano", "$3.00", new Color(0xA1887F)));
        mockProducts.add(new Product("Flat White", "$4.00", new Color(0xFFF3E0)));

        for (Product product : mockProducts) {
            productPanel.add(createProductCard(product));
        }
    }

    private JPanel createProductCard(Product product) {
        JPanel cardPanel = new JPanel();
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
        cardPanel.setBackground(product.backgroundColor);
        cardPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1), // Basic border
                new EmptyBorder(10, 10, 10, 10) // Padding
        ));
        // For rounded corners, custom painting would be needed

        // Placeholder for circle icon (coffee cup)
        JLabel iconLabel = new JLabel("☕"); // Using an emoji as placeholder
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        iconLabel.setFont(new Font("Arial", Font.PLAIN, 48)); // Larger font for icon

        JLabel nameLabel = new JLabel(product.name);
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 16));

        JLabel priceLabel = new JLabel(product.price);
        priceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        priceLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        cardPanel.add(iconLabel);
        cardPanel.add(Box.createVerticalStrut(5)); // Spacing
        cardPanel.add(nameLabel);
        cardPanel.add(priceLabel);

        return cardPanel;
    }

    private void createCartPanel() {
        cartPanel = new JPanel();
        cartPanel.setLayout(new BorderLayout());
        cartPanel.setBackground(new Color(0xEFEBE9));
        cartPanel.setBorder(new EmptyBorder(16, 16, 16, 16));
        cartPanel.setPreferredSize(new Dimension(200, getHeight()));

        JLabel titleLabel = new JLabel("Cart Summary");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        cartPanel.add(titleLabel, BorderLayout.NORTH);

        JTextArea cartItemsArea = new JTextArea("No items in cart.");
        cartItemsArea.setEditable(false);
        cartItemsArea.setBackground(new Color(0xFFF8E1)); // Light cream
        cartItemsArea.setBorder(new EmptyBorder(8, 8, 8, 8));
        cartPanel.add(new JScrollPane(cartItemsArea), BorderLayout.CENTER);

        JButton checkoutButton = new JButton("Checkout");
        checkoutButton.setBackground(new Color(0x6D4C41)); // Accent color
        checkoutButton.setForeground(Color.WHITE);
        checkoutButton.setFont(new Font("Arial", Font.BOLD, 16));
        checkoutButton.setFocusPainted(false);
        checkoutButton.setBorder(new EmptyBorder(10, 0, 10, 0)); // Flat style padding

        cartPanel.add(checkoutButton, BorderLayout.SOUTH);
    }

    private void createTopBar() {
        topBarPanel = new JPanel();
        topBarPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        topBarPanel.setBackground(new Color(0xFFF8E1));
        topBarPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JTextField searchField = new JTextField(20);
        searchField.setFont(new Font("Arial", Font.PLAIN, 14));

        JComboBox<String> filterComboBox = new JComboBox<>(new String[]{"All", "Coffee", "Tea", "Dessert"});
        filterComboBox.setFont(new Font("Arial", Font.PLAIN, 14));

        topBarPanel.add(new JLabel("Search:"));
        topBarPanel.add(searchField);
        topBarPanel.add(new JLabel("Filter:"));
        topBarPanel.add(filterComboBox);
    }

    // Simple class to hold mock product data
    private static class Product {
        String name;
        String price;
        Color backgroundColor;

        Product(String name, String price, Color backgroundColor) {
            this.name = name;
            this.price = price;
            this.backgroundColor = backgroundColor;
        }
    }

    public static void main(String[] args) {
        // Run the GUI creation on the Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Coffee Shop Product Listing");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1000, 700); // Set a reasonable size
            frame.setLocationRelativeTo(null); // Center the frame

            ProductView productView = new ProductView();
            frame.add(productView);

            frame.setVisible(true);
        });
    }
}
//</editor-fold>
