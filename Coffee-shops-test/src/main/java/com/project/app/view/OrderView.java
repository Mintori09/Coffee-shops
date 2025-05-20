package com.project.app.view;

import com.project.app.dao.BillDAO;
import com.project.app.dao.BillDetailDAO;
import com.project.app.dao.DrinkDAO;
import com.project.app.dao.impl.BillDAOImpl;
import com.project.app.dao.impl.BillDetailDAOImpl;
import com.project.app.dao.impl.DrinkDAOImpl;
import com.project.app.model.Bill;
import com.project.app.model.BillDetail;
import com.project.app.model.Drink;
import com.project.app.model.Employee;
import com.project.app.session.Session;
import com.project.app.dao.EmployeeDAO;
import com.project.app.dao.impl.EmployeeDAOImpl;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderView extends JPanel {

    private JPanel productPanel;
    private JPanel cartPanel;
    private JButton checkoutButton;
    private JTable cartTable;
    private DefaultTableModel cartTableModel;
    private JLabel totalLabel;

    private final DrinkDAO drinkDAO;
    private final BillDAO billDAO;
    private final BillDetailDAO billDetailDAO;
    private final EmployeeDAO employeeDAO;
    private List<CartItem> cartItems; // List to hold items in the cart

    // Colors (Pastel Brown Palette)
    private static final Color COLOR_PASTEL_LIGHT = new Color(245, 235, 220); // Similar to EFEBE9 / BG_PRIMARY
    private static final Color COLOR_PASTEL_MEDIUM = new Color(215, 204, 200); // Similar to D7CCC8
    private static final Color COLOR_PASTEL_DARK = new Color(161, 136, 127); // Similar to A1887F
    private static final Color COLOR_TEXT_DARK = new Color(50, 40, 30); // Dark brown for text
    private static final Color COLOR_ACCENT = new Color(180, 120, 90); // A slightly warmer brown accent
    private static final Color COLOR_BUTTON_TEXT = Color.WHITE; // White text for contrast

    // Fonts (Modern, easy to read)
    private static final Font FONT_PRIMARY = new Font("Roboto", Font.PLAIN, 14);
    private static final Font FONT_BOLD = new Font("Segoe UI", Font.BOLD, 14);
    private static final Font FONT_LARGE_BOLD = new Font("Segoe UI", Font.BOLD, 18);


    // Borders
    private final Border BORDER_LINE = BorderFactory.createLineBorder(COLOR_PASTEL_DARK, 1);
    private final Border BORDER_PADDING_10 = BorderFactory.createEmptyBorder(10, 10, 10, 10);
    private final Border BORDER_PADDING_5 = BorderFactory.createEmptyBorder(5, 5, 5, 5);


    public OrderView() {
        drinkDAO = new DrinkDAOImpl();
        billDAO = new BillDAOImpl();
        billDetailDAO = new BillDetailDAOImpl();
        employeeDAO = new EmployeeDAOImpl();
        cartItems = new ArrayList<>(); // Initialize cart items list

        setLayout(new BorderLayout());
        setBackground(COLOR_PASTEL_LIGHT);

        // Product Panel
        productPanel = new JPanel();
        productPanel.setBorder(BorderFactory.createTitledBorder(BORDER_LINE, "Products",
                javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, FONT_BOLD, COLOR_TEXT_DARK));
        productPanel.setBackground(COLOR_PASTEL_LIGHT);
        productPanel.setLayout(new GridLayout(0, 3, 15, 15)); // Increased gaps
        productPanel.setBorder(BorderFactory.createCompoundBorder(productPanel.getBorder(), BORDER_PADDING_10)); // Add padding

        loadProducts();

        // Cart Panel
        cartPanel = new JPanel();
        cartPanel.setBorder(BorderFactory.createTitledBorder(BORDER_LINE, "Cart",
                javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, FONT_BOLD, COLOR_TEXT_DARK));
        cartPanel.setBackground(COLOR_PASTEL_LIGHT);
        cartPanel.setLayout(new BorderLayout());
        cartPanel.setPreferredSize(new Dimension(350, getHeight())); // Slightly wider cart panel
        cartPanel.setBorder(BorderFactory.createCompoundBorder(cartPanel.getBorder(), BORDER_PADDING_10)); // Add padding


        // Cart Table
        String[] columnNames = {"Item", "Qty", "Subtotal"}; // Shorter column name for quantity
        cartTableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table cells not editable
            }
        };
        cartTable = new JTable(cartTableModel);
        cartTable.setFont(FONT_PRIMARY);
        cartTable.setForeground(COLOR_TEXT_DARK);
        cartTable.setBackground(COLOR_PASTEL_MEDIUM); // Table background color
        cartTable.getTableHeader().setFont(FONT_BOLD);
        cartTable.getTableHeader().setForeground(COLOR_TEXT_DARK);
        cartTable.getTableHeader().setBackground(COLOR_PASTEL_MEDIUM);
        cartTable.setRowHeight(25); // Adjust row height
        cartTable.setGridColor(COLOR_PASTEL_LIGHT); // Lighter grid lines
        cartTable.setShowVerticalLines(false); // Hide vertical lines


        JScrollPane scrollPane = new JScrollPane(cartTable);
        scrollPane.setBackground(COLOR_PASTEL_MEDIUM);
        cartPanel.add(scrollPane, BorderLayout.CENTER);

        // Cart Controls and Total
        JPanel cartControlPanel = new JPanel(new BorderLayout()); // Use BorderLayout for controls and total
        cartControlPanel.setBackground(COLOR_PASTEL_LIGHT);
        cartControlPanel.setBorder(BORDER_PADDING_10); // Add padding

        JPanel quantityRemovePanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); // Panel for quantity/remove buttons
        quantityRemovePanel.setBackground(COLOR_PASTEL_LIGHT);
        // TODO: Add quantity update and remove buttons here

        totalLabel = new JLabel("Total: $0.00");
        totalLabel.setFont(FONT_LARGE_BOLD);
        totalLabel.setForeground(COLOR_TEXT_DARK);
        totalLabel.setHorizontalAlignment(SwingConstants.RIGHT); // Align total text to the right

        cartControlPanel.add(quantityRemovePanel, BorderLayout.WEST); // Add quantity/remove panel
        cartControlPanel.add(totalLabel, BorderLayout.EAST); // Add total label

        cartPanel.add(cartControlPanel, BorderLayout.SOUTH);

        // Checkout Button
        checkoutButton = createStyledButton("Checkout");
        checkoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (cartItems.isEmpty()) {
                    JOptionPane.showMessageDialog(OrderView.this, "Cart is empty. Add items before checking out.", "Empty Cart", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                try {
                    // Get employee ID from the logged-in account
                    int accountId = Session.getInstance().getAccount().getId();
                    Employee employee = employeeDAO.findByIdAccount(accountId);

                    if (employee == null) {
                        JOptionPane.showMessageDialog(OrderView.this, "Employee not found for the logged-in account.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // 1. Create a new Bill
                    Bill bill = new Bill();
                    bill.setEmployeeId(employee.getId());
                    bill.setTotalPrice(calculateTotal());
                    bill.setCreatedDate(LocalDateTime.now());
                    bill.setOrderDate(java.time.LocalDate.now());

                    int billId = billDAO.create(bill);

                    if (billId > 0) {
                        // 2. Save Bill Details
                        for (CartItem item : cartItems) {
                            BillDetail billDetail = new BillDetail();
                            billDetail.setBillId(billId);
                            billDetail.setDrinkId(item.getDrink().getDrinkId());
                            billDetail.setQuantity(item.getQuantity());
                            billDetail.setPrice(item.getDrink().getPrice()); // Price per item
                            billDetail.setNotes(""); // Optional notes
                            billDetail.setCreatedDate(LocalDateTime.now());

                            billDetailDAO.create(billDetail);
                        }

                        // 3. Clear the cart and update UI
                        cartItems.clear();
                        updateCartTable();
                        updateTotal();

                        JOptionPane.showMessageDialog(OrderView.this, "Checkout successful! Bill ID: " + billId, "Success", JOptionPane.INFORMATION_MESSAGE);

                    } else {
                        JOptionPane.showMessageDialog(OrderView.this, "Failed to create bill.", "Error", JOptionPane.ERROR_MESSAGE);
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(OrderView.this, "An unexpected error occurred during checkout: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT)); // Panel to hold checkout button aligned right
        southPanel.setBackground(COLOR_PASTEL_LIGHT);
        southPanel.add(checkoutButton);

        // Add components to the main panel
        add(productPanel, BorderLayout.CENTER);
        add(cartPanel, BorderLayout.EAST);
        add(southPanel, BorderLayout.SOUTH);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text) {
            private static final int CORNER_RADIUS = 8; // Slightly rounded corners

            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Paint button background with rounded corners
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), CORNER_RADIUS, CORNER_RADIUS);

                super.paintComponent(g);
                g2.dispose();
            }

            @Override
            protected void paintBorder(Graphics g) {
                // Paint rounded border
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getForeground()); // Use foreground color for border
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, CORNER_RADIUS, CORNER_RADIUS);
                g2.dispose();
            }

            @Override
            public void updateUI() {
                super.updateUI();
                // Ensure button properties are set after UI update
                setOpaque(false);
                setContentAreaFilled(false);
                setFocusPainted(false);
                setBorderPainted(true); // Ensure border is painted
            }
        };
        button.setFont(FONT_LARGE_BOLD); // Use large bold font for checkout button
        button.setBackground(COLOR_ACCENT); // Use accent color for buttons
        button.setForeground(COLOR_BUTTON_TEXT); // White text for contrast
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // Padding

        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(COLOR_PASTEL_DARK); // Darken on hover
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(COLOR_ACCENT); // Revert on exit
            }
        });

        return button;
    }

    private JButton createAddToCartButton(String text) {
         JButton button = new JButton(text);
         button.setFont(FONT_PRIMARY);
         button.setBackground(COLOR_PASTEL_DARK); // Use a darker pastel for add to cart
         button.setForeground(Color.WHITE);
         button.setCursor(new Cursor(Cursor.HAND_CURSOR));
         button.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); // Smaller padding

         // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(COLOR_ACCENT); // Change on hover
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(COLOR_PASTEL_DARK); // Revert on exit
            }
        });

         return button;
    }


    private void loadProducts() {
        productPanel.removeAll(); // Clear existing products
        try {
            List<Drink> drinks = drinkDAO.getAllDrinks();
            if (drinks.isEmpty()) {
                productPanel.add(new JLabel("No products available."));
            } else {
                for (Drink drink : drinks) {
                    addProduct(drink);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database error
            productPanel.add(new JLabel("Error loading products."));
        }
        productPanel.revalidate();
        productPanel.repaint();
    }

    private void addProduct(Drink drink) {
        JPanel productItemPanel = new JPanel();
        productItemPanel.setLayout(new BoxLayout(productItemPanel, BoxLayout.Y_AXIS));
        productItemPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_PASTEL_DARK), // Border color
                BorderFactory.createEmptyBorder(10, 10, 10, 10))); // Padding
        productItemPanel.setBackground(COLOR_PASTEL_MEDIUM); // Item background color
        productItemPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        productItemPanel.setMaximumSize(new Dimension(200, 250)); // Set a max size for consistency
        productItemPanel.setPreferredSize(new Dimension(180, 230)); // Set a preferred size


        // Product Image
        JLabel imageLabel = new JLabel();
        imageLabel.setPreferredSize(new Dimension(100, 100)); // Slightly smaller image
        imageLabel.setMaximumSize(new Dimension(120, 120));
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        imageLabel.setVerticalAlignment(JLabel.CENTER);

        // Attempt to load image
        if (drink.getImage() != null && !drink.getImage().isEmpty()) {
            try {
                java.net.URL imageUrl = getClass().getResource("/" + drink.getImage());
                if (imageUrl != null) {
                    ImageIcon originalIcon = new ImageIcon(imageUrl);
                    if (originalIcon.getImageLoadStatus() == MediaTracker.COMPLETE) {
                         Image scaledImage = originalIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH); // Scale to new size
                         imageLabel.setIcon(new ImageIcon(scaledImage));
                         imageLabel.setText("");
                    } else {
                        imageLabel.setText("No Image");
                    }
                } else {
                    imageLabel.setText("No Image");
                }
            } catch (Exception e) {
                e.printStackTrace();
                imageLabel.setText("No Image");
            }
        } else {
            imageLabel.setText("No Image");
        }
        imageLabel.setFont(FONT_PRIMARY);
        imageLabel.setForeground(COLOR_TEXT_DARK);


        // Product Info
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(COLOR_PASTEL_MEDIUM);
        infoPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        infoPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0)); // Add vertical padding


        JLabel nameLabel = new JLabel(drink.getDrinkName());
        nameLabel.setFont(FONT_BOLD);
        nameLabel.setForeground(COLOR_TEXT_DARK);
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel priceLabel = new JLabel(String.format("$%.2f", drink.getPrice()));
        priceLabel.setFont(FONT_PRIMARY);
        priceLabel.setForeground(COLOR_TEXT_DARK);
        priceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        infoPanel.add(nameLabel);
        infoPanel.add(priceLabel);


        // Add to Cart Button
        JButton addToCartButton = createAddToCartButton("Add to Cart"); // Use the new method
        addToCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addItemToCart(drink);
            }
        });
        addToCartButton.setAlignmentX(Component.CENTER_ALIGNMENT);


        productItemPanel.add(imageLabel);
        productItemPanel.add(Box.createVerticalStrut(5)); // Add space between image and info
        productItemPanel.add(infoPanel);
        productItemPanel.add(Box.createVerticalStrut(10)); // Add space between info and button
        productItemPanel.add(addToCartButton);


        // Add a small margin around the product item panel
        JPanel wrapperPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10)); // FlowLayout with gaps
        wrapperPanel.setBackground(COLOR_PASTEL_LIGHT);
        wrapperPanel.add(productItemPanel);

        productPanel.add(wrapperPanel); // Add the wrapper panel instead of the item panel directly
    }

    private void addItemToCart(Drink drink) {
        boolean found = false;
        for (CartItem item : cartItems) {
            if (item.getDrink().getDrinkId() == drink.getDrinkId()) {
                item.setQuantity(item.getQuantity() + 1);
                found = true;
                break;
            }
        }

        if (!found) {
            cartItems.add(new CartItem(drink, 1));
        }

        updateCartTable();
        updateTotal();
    }

    private void updateCartTable() {
        cartTableModel.setRowCount(0);
        for (CartItem item : cartItems) {
            Object[] rowData = {
                    item.getDrink().getDrinkName(),
                    item.getQuantity(),
                    String.format("$%.2f", item.getSubtotal())
            };
            cartTableModel.addRow(rowData);
        }
    }

    private void updateTotal() {
        double total = 0;
        for (CartItem item : cartItems) {
            total += item.getSubtotal();
        }
        totalLabel.setText(String.format("Total: $%.2f", total));
    }

    private double calculateTotal() {
        double total = 0;
        for (CartItem item : cartItems) {
            total += item.getSubtotal();
        }
        return total;
    }

    // Inner class to represent an item in the cart
    private static class CartItem {
        private Drink drink;
        private int quantity;

        public CartItem(Drink drink, int quantity) {
            this.drink = drink;
            this.quantity = quantity;
        }

        public Drink getDrink() {
            return drink;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public double getSubtotal() {
            return drink.getPrice() * quantity;
        }
    }


    // Method to create and show the frame (for testing purposes)
    public static void main(String[] args) {
        // Set look and feel to a modern one if available (e.g., Nimbus)
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // If Nimbus is not available, you can set the GUI to another look and feel.
        }

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Order Panel");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            try {
                frame.getContentPane().add(new OrderView());
            } catch (Exception e) {
                e.printStackTrace();
            }
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
