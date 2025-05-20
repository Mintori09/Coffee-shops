package com.project.app.view;

import com.project.app.dao.AccountDAO;
import com.project.app.dao.impl.AccountDAOImpl;
import com.project.app.model.Account;
import com.project.app.service.AuthService;
import com.project.app.session.Session;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

public class LoginForm extends JFrame implements ActionListener, FocusListener {
    // Components
    private final AuthService authService = new AuthService(new AccountDAOImpl());
    private JLabel titleLabel, userLabel, passwordLabel, messageLabel;
    private JTextField userTextField;
    private JPasswordField passwordField;
    private JButton loginButton;

    // Colors (Warm Cafe Theme)
    private static final Color BG_PRIMARY = new Color(245, 235, 220);
    private static final Color COLOR_TEXT = new Color(50, 40, 30);
    private static final Color COLOR_BUTTON = new Color(210, 105, 30);
    private static final Color COLOR_BUTTON_TEXT = Color.WHITE;
    private static final Color COLOR_BORDER_FOCUS = new Color(144, 238, 144);
    private static final Color COLOR_ERROR = new Color(255, 99, 71);

    // Fonts
    private static final Font FONT_TITLE = new Font("Segoe UI", Font.BOLD, 28);
    private static final Font FONT_LABEL = new Font("Segoe UI", Font.PLAIN, 14);
    private static final Font FONT_INPUT = new Font("Segoe UI", Font.PLAIN, 14);
    private static final Font FONT_BUTTON = new Font("Segoe UI", Font.BOLD, 16);
    private static final Font FONT_MESSAGE = new Font("Segoe UI", Font.ITALIC, 12);

    // Borders
    private final Border BORDER_DEFAULT = BorderFactory.createLineBorder(BG_PRIMARY, 1);
    private final Border BORDER_FOCUS = BorderFactory.createLineBorder(COLOR_BORDER_FOCUS, 1);
    private final Border BORDER_ERROR = BorderFactory.createLineBorder(COLOR_ERROR, 1);
    private final Border BORDER_PADDING = BorderFactory.createEmptyBorder(20, 30, 20, 30);

    public LoginForm() {
        setupFrame();
        setContentPane(buildMainPanel());
    }

    private void setupFrame() {
        setTitle("Đăng nhập - Quán Cafe");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 450);
        setResizable(false);
        setLocationRelativeTo(null);
    }

    private JPanel buildMainPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(BG_PRIMARY);
        mainPanel.setBorder(BORDER_PADDING);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setOpaque(false);
        centerPanel.add(buildTitle());
        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(buildFormPanel());

        mainPanel.add(centerPanel, BorderLayout.CENTER);
        return mainPanel;
    }

    private JLabel buildTitle() {
        titleLabel = new JLabel("Đăng nhập", SwingConstants.CENTER);
        titleLabel.setFont(FONT_TITLE);
        titleLabel.setForeground(COLOR_TEXT);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        return titleLabel;
    }

    private JPanel buildFormPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(BG_PRIMARY);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 5, 10, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Username
        userLabel = createLabel("Tên đăng nhập:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        panel.add(userLabel, gbc);

        userTextField = createTextField();
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        panel.add(userTextField, gbc);

        // Password
        passwordLabel = createLabel("Mật khẩu:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        panel.add(passwordLabel, gbc);

        passwordField = createPasswordField();
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        panel.add(passwordField, gbc);

        // Message
        messageLabel = new JLabel("", SwingConstants.CENTER);
        messageLabel.setFont(FONT_MESSAGE);
        messageLabel.setForeground(COLOR_ERROR);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(messageLabel, gbc);

        // Login Button
        loginButton = createLoginButton();
        gbc.gridy = 3;
        gbc.insets = new Insets(20, 5, 10, 5);
        panel.add(loginButton, gbc);

        return panel;
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(FONT_LABEL);
        label.setForeground(COLOR_TEXT);
        return label;
    }

    private JTextField createTextField() {
        JTextField tf = new JTextField(50);
        tf.setFont(FONT_INPUT);
        tf.setForeground(COLOR_TEXT);
        tf.setBorder(compoundBorder(BORDER_DEFAULT));
        tf.addFocusListener(this);
        return tf;
    }

    private JPasswordField createPasswordField() {
        JPasswordField pf = new JPasswordField(50);
        pf.setFont(FONT_INPUT);
        pf.setForeground(COLOR_TEXT);
        pf.setBorder(compoundBorder(BORDER_DEFAULT));
        pf.addFocusListener(this);
        pf.addActionListener(e -> {
            try {
                handleLogin();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        return pf;
    }

    private JButton createLoginButton() {
        JButton btn = new JButton("Đăng nhập");
        btn.setMaximumSize(new Dimension(30, 30));
        btn.setFont(FONT_BUTTON);
        btn.setBackground(COLOR_BUTTON);
        btn.setForeground(COLOR_BUTTON_TEXT);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.addActionListener(this);
        return btn;
    }

    private Border compoundBorder(Border base) {
        return BorderFactory.createCompoundBorder(base, BorderFactory.createEmptyBorder(5, 10, 5, 10));
    }

    private void showMessage(String msg, Color color) {
        messageLabel.setText(msg);
        messageLabel.setForeground(color);
        messageLabel.setVisible(true);
    }

    private void highlightFields(boolean userEmpty, boolean passEmpty) {
        if (userEmpty) userTextField.setBorder(compoundBorder(BORDER_ERROR));
        if (passEmpty) passwordField.setBorder(compoundBorder(BORDER_ERROR));
    }

    private void resetBorders() {
        userTextField.setBorder(compoundBorder(BORDER_DEFAULT));
        passwordField.setBorder(compoundBorder(BORDER_DEFAULT));
    }

    private void handleLogin() throws SQLException {
        String user = userTextField.getText().trim();
        String pass = new String(passwordField.getPassword()).trim();
        resetBorders();

        if (user.isEmpty() || pass.isEmpty()) {
            showMessage("Vui lòng nhập tên đăng nhập và mật khẩu.", COLOR_ERROR);
            highlightFields(user.isEmpty(), pass.isEmpty());
        } else {
            Account account = authService.login(user, pass);
            if (account != null) {
                Session.getInstance().setAccount(account);
                showMessage("Đăng nhập thành công!", new Color(34, 139, 34));

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                setSize(700, 1000);
                MainWindow dashboard = new MainWindow();
                dashboard.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                dashboard.setSize(900, 600);
                dashboard.setLocationRelativeTo(null);
                dashboard.setVisible(true);

                this.dispose();

            } else {
                showMessage("Tên đăng nhập hoặc mật khẩu không đúng.", COLOR_ERROR);
                highlightFields(true, true);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            try {
                handleLogin();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    @Override
    public void focusGained(FocusEvent e) {
        if (e.getSource() == userTextField)
            userTextField.setBorder(compoundBorder(BORDER_FOCUS));
        else if (e.getSource() == passwordField)
            passwordField.setBorder(compoundBorder(BORDER_FOCUS));
        messageLabel.setVisible(false);
    }

    @Override
    public void focusLost(FocusEvent e) {
        if (e.getSource() == userTextField)
            userTextField.setBorder(compoundBorder(BORDER_DEFAULT));
        else if (e.getSource() == passwordField)
            passwordField.setBorder(compoundBorder(BORDER_DEFAULT));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginForm().setVisible(true));
    }
}
