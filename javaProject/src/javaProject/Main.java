package javaProject;

import javaProject.gui.DatBanForm;
import javaProject.gui.DatMonForm;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            JFrame mainFrame = new JFrame("Quản lý quán cà phê");
            mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            mainFrame.setSize(800, 600);
            mainFrame.setLocationRelativeTo(null);

            JPanel mainPanel = new JPanel(new GridLayout(2, 2, 10, 10));
            mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

            // Nút đặt bàn
            JButton btnDatBan = new JButton("Đặt bàn");
            btnDatBan.setFont(new Font("Arial", Font.BOLD, 20));
            btnDatBan.addActionListener(e -> {
                new DatBanForm().setVisible(true);
            });

            // Nút đặt món
            JButton btnDatMon = new JButton("Đặt món");
            btnDatMon.setFont(new Font("Arial", Font.BOLD, 20));
            btnDatMon.addActionListener(e -> {
                mainFrame.setVisible(false); // Ẩn chính JFrame hiện tại
                new DatMonForm().setVisible(true); // Mở form mới
            });

            // Nút quản lý nhân viên
            JButton btnQuanLyNhanVien = new JButton("Quản lý nhân viên");
            btnQuanLyNhanVien.setFont(new Font("Arial", Font.BOLD, 20));
            btnQuanLyNhanVien.addActionListener(e -> {
                JOptionPane.showMessageDialog(mainFrame, "Chức năng đang được phát triển!");
            });

            // Nút quản lý thực đơn
            JButton btnQuanLyThucDon = new JButton("Quản lý thực đơn");
            btnQuanLyThucDon.setFont(new Font("Arial", Font.BOLD, 20));
            btnQuanLyThucDon.addActionListener(e -> {
                JOptionPane.showMessageDialog(mainFrame, "Chức năng đang được phát triển!");
            });

            mainPanel.add(btnDatBan);
            mainPanel.add(btnDatMon);
            mainPanel.add(btnQuanLyNhanVien);
            mainPanel.add(btnQuanLyThucDon);

            mainFrame.add(mainPanel);
            mainFrame.setVisible(true);
        });
    }
} 