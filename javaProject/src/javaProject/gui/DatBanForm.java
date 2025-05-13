package javaProject.gui;

import javaProject.DatabaseConnection;
import javaProject.model.Ban;
import javaProject.model.HoaDon;
import javaProject.model.NhanVien;
import javaProject.model.KhachHang;
import javaProject.service.DatBanService;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class DatBanForm extends JFrame {
    private JTable banTable;
    private DefaultTableModel banTableModel;
    private JComboBox<String> nhanVienComboBox;
    private JTextField tenKhachHangField;
    private JTextField soDienThoaiField;
    private JTextField emailField;
    private JTextField diaChiField;
    private JTextArea ghiChuArea;
    private JButton datBanButton, huyDatBanButton, refreshButton;
    private DatBanService datBanService;

    public DatBanForm() throws SQLException {
        datBanService = new DatBanService();
        setTitle("Đặt Bàn");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        initComponents();
        loadData();
    }

    private void initComponents() throws SQLException {
        DatabaseConnection.getConnection();
        setLayout(new BorderLayout(10, 10));

        // Bảng bàn
        String[] columnNames = { "Mã Bàn", "Tên Bàn", "Số Ghế", "Trạng Thái", "Vị Trí" };
        banTableModel = new DefaultTableModel(columnNames, 0) {
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        banTable = new JTable(banTableModel);
        banTable.setRowHeight(28);
        banTable.setFont(new Font("Arial", Font.PLAIN, 16));
        banTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));
        JScrollPane scrollPane = new JScrollPane(banTable);

        // Panel nút
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        datBanButton = new JButton("Đặt Bàn");
        datBanButton.setFont(new Font("Arial", Font.BOLD, 15));
        datBanButton.setBackground(new Color(76, 175, 80));
        datBanButton.setForeground(Color.WHITE);
        huyDatBanButton = new JButton("Hủy Đặt Bàn");
        huyDatBanButton.setFont(new Font("Arial", Font.BOLD, 15));
        huyDatBanButton.setBackground(new Color(244, 67, 54));
        huyDatBanButton.setForeground(Color.WHITE);
        refreshButton = new JButton("Làm Mới");
        refreshButton.setFont(new Font("Arial", Font.BOLD, 15));
        buttonPanel.add(datBanButton);
        buttonPanel.add(huyDatBanButton);
        buttonPanel.add(refreshButton);

        // Panel nhập thông tin
        JPanel infoPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;

        // Nhân viên
        gbc.gridx = 0;
        gbc.gridy = 0;
        infoPanel.add(new JLabel("Nhân viên:"), gbc);
        gbc.gridx = 1;
        nhanVienComboBox = new JComboBox<>();
        nhanVienComboBox.setPreferredSize(new Dimension(250, 28));
        infoPanel.add(nhanVienComboBox, gbc);

        // Khách hàng (TextField)
        gbc.gridx = 0;
        gbc.gridy = 1;
        infoPanel.add(new JLabel("Tên khách hàng:"), gbc);
        gbc.gridx = 1;
        tenKhachHangField = new JTextField();
        tenKhachHangField.setPreferredSize(new Dimension(250, 28));
        infoPanel.add(tenKhachHangField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        infoPanel.add(new JLabel("Số điện thoại:"), gbc);
        gbc.gridx = 1;
        soDienThoaiField = new JTextField();
        soDienThoaiField.setPreferredSize(new Dimension(250, 28));
        infoPanel.add(soDienThoaiField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        infoPanel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        emailField = new JTextField();
        emailField.setPreferredSize(new Dimension(250, 28));
        infoPanel.add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        infoPanel.add(new JLabel("Địa chỉ:"), gbc);
        gbc.gridx = 1;
        diaChiField = new JTextField();
        diaChiField.setPreferredSize(new Dimension(250, 28));
        infoPanel.add(diaChiField, gbc);

        // Ghi chú
        gbc.gridx = 0;
        gbc.gridy = 5;
        infoPanel.add(new JLabel("Ghi chú:"), gbc);
        gbc.gridx = 1;
        ghiChuArea = new JTextArea(2, 20);
        ghiChuArea.setLineWrap(true);
        infoPanel.add(new JScrollPane(ghiChuArea), gbc);

        // Thêm các panel vào frame
        add(buttonPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(infoPanel, BorderLayout.SOUTH);

        // Sự kiện nút
        datBanButton.addActionListener(e -> datBan());
        huyDatBanButton.addActionListener(e -> huyDatBan());
        refreshButton.addActionListener(e -> loadData());
    }

    private void loadData() {
        // Load danh sách bàn
        banTableModel.setRowCount(0);
        List<Ban> danhSachBan = datBanService.layDanhSachBanTrong();
        for (Ban ban : danhSachBan) {
            banTableModel.addRow(new Object[] {
                    ban.getMaBan(), ban.getTenBan(), ban.getSoGhe(), ban.getTrangThai(), ban.getViTri()
            });
        }

        // Load danh sách nhân viên
        nhanVienComboBox.removeAllItems();
        for (NhanVien nv : datBanService.layDanhSachNhanVien()) {
            nhanVienComboBox.addItem(nv.getMaNhanVien() + " - " + nv.getTenNhanVien());
        }
    }

    private void datBan() {
        int selectedRow = banTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn bàn!");
            return;
        }
        int maBan = (int) banTableModel.getValueAt(selectedRow, 0);

        String nhanVienStr = (String) nhanVienComboBox.getSelectedItem();
        if (nhanVienStr == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn nhân viên!");
            return;
        }
        int maNhanVien = Integer.parseInt(nhanVienStr.split(" - ")[0]);

        String tenKhachHang = tenKhachHangField.getText().trim();
        String soDienThoai = soDienThoaiField.getText().trim();
        String email = emailField.getText().trim();
        String diaChi = diaChiField.getText().trim();
        if (tenKhachHang.isEmpty() || soDienThoai.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập tên và số điện thoại khách hàng!");
            return;
        }

        int maKhachHang = datBanService.themHoacLayMaKhachHang(tenKhachHang, soDienThoai, email, diaChi);

        String ghiChu = ghiChuArea.getText();

        boolean success = datBanService.datBan(maBan, maNhanVien, maKhachHang, ghiChu);
        if (success) {
            JOptionPane.showMessageDialog(this, "Đặt bàn thành công!");
            loadData();
        } else {
            JOptionPane.showMessageDialog(this, "Đặt bàn thất bại!");
        }
    }

    private void huyDatBan() {
        int selectedRow = banTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn bàn!");
            return;
        }
        int maBan = (int) banTableModel.getValueAt(selectedRow, 0);
        // TODO: Lấy mã hóa đơn từ bàn và hủy đặt bàn
        // ... giữ nguyên logic cũ hoặc bổ sung nếu cần ...
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                new DatBanForm().setVisible(true);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
