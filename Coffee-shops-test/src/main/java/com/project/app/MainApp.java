package com.project.app;

import com.project.app.view.LoginForm;

import javax.swing.*;

class MainApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginForm().setVisible(true));
    }
}
