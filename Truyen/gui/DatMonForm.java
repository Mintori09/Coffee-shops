package javaProject.gui;

import javaProject.model.*;
import javaProject.service.DatMonService;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class DatMonForm extends JFrame {
    private JTable tblMon;
    private JTable tblChiTietHoaDon;
    private JComboBox<String> cboLoaiMon;
    private JComboBox<String> cboHoaDon;
    private JSpinner spnSoLuong;
    private JTextArea txtGhiChu;
    private JLabel lblTongTien;
    
    private DatMonService datMonService;
    private DefaultTableModel tblModelMon;
    private DefaultTableModel tblModelChiTiet;
    
    public DatMonForm() {
        datMonService = new DatMonService();
        initComponents();
        loadData();
    }
    
    private void initComponents() {
        setTitle("Đặt Món");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Panel chính
        JPanel mainPanel = new JPanel(new BorderLayout());
        
        // Panel trái - Danh sách món
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBorder(BorderFactory.createTitledBorder("Danh sách món"));
        
        // Combo box loại món
        cboLoaiMon = new JComboBox<>();
        cboLoaiMon.addItem("Tất cả");
        cboLoaiMon.addActionListener(e -> loadDanhSachMon());
        
        // Bảng món
        String[] columnsMon = {"Mã món", "Tên món", "Loại món", "Giá", "Trạng thái"};
        tblModelMon = new DefaultTableModel(columnsMon, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblMon = new JTable(tblModelMon);
        JScrollPane scrollMon = new JScrollPane(tblMon);
        
        leftPanel.add(cboLoaiMon, BorderLayout.NORTH);
        leftPanel.add(scrollMon, BorderLayout.CENTER);
        
        // Panel phải - Chi tiết hóa đơn
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBorder(BorderFactory.createTitledBorder("Chi tiết hóa đơn"));
        
        // Combo box hóa đơn
        cboHoaDon = new JComboBox<>();
        cboHoaDon.addActionListener(e -> loadChiTietHoaDon());
        
        // Bảng chi tiết hóa đơn
        String[] columnsChiTiet = {"Mã chi tiết", "Tên món", "Số lượng", "Đơn giá", "Thành tiền"};
        tblModelChiTiet = new DefaultTableModel(columnsChiTiet, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblChiTietHoaDon = new JTable(tblModelChiTiet);
        JScrollPane scrollChiTiet = new JScrollPane(tblChiTietHoaDon);
        
        // Panel thông tin đặt món
        JPanel infoPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        infoPanel.setBorder(BorderFactory.createTitledBorder("Thông tin đặt món"));
        
        infoPanel.add(new JLabel("Số lượng:"));
        spnSoLuong = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        infoPanel.add(spnSoLuong);
        
        infoPanel.add(new JLabel("Ghi chú:"));
        txtGhiChu = new JTextArea(2, 20);
        JScrollPane scrollGhiChu = new JScrollPane(txtGhiChu);
        infoPanel.add(scrollGhiChu);
        
        // Panel nút
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton btnThem = new JButton("Thêm món");
        JButton btnXoa = new JButton("Xóa món");
        JButton btnCapNhat = new JButton("Cập nhật số lượng");
        
        btnThem.addActionListener(e -> themMon());
        btnXoa.addActionListener(e -> xoaMon());
        btnCapNhat.addActionListener(e -> capNhatSoLuong());
        
        buttonPanel.add(btnThem);
        buttonPanel.add(btnXoa);
        buttonPanel.add(btnCapNhat);
        
        // Panel tổng tiền
        JPanel totalPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        totalPanel.add(new JLabel("Tổng tiền: "));
        lblTongTien = new JLabel("0 VNĐ");
        totalPanel.add(lblTongTien);
        
        rightPanel.add(cboHoaDon, BorderLayout.NORTH);
        rightPanel.add(scrollChiTiet, BorderLayout.CENTER);
        
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(infoPanel, BorderLayout.CENTER);
        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        rightPanel.add(bottomPanel, BorderLayout.SOUTH);
        rightPanel.add(totalPanel, BorderLayout.SOUTH);
        
        // Thêm các panel vào main panel
        mainPanel.add(leftPanel, BorderLayout.CENTER);
        mainPanel.add(rightPanel, BorderLayout.EAST);
        
        add(mainPanel);
    }
    
    private void loadData() {
        // Load danh sách loại món
        List<LoaiMon> loaiMonList = datMonService.layDanhSachLoaiMon();
        for (LoaiMon loaiMon : loaiMonList) {
            cboLoaiMon.addItem(loaiMon.getTenLoaiMon());
        }
        
        // Load danh sách hóa đơn chờ thanh toán
        List<HoaDon> hoaDonList = datMonService.layDanhSachHoaDonChuaThanhToan();
        for (HoaDon hoaDon : hoaDonList) {
            cboHoaDon.addItem("Hóa đơn #" + hoaDon.getMaHoaDon());
        }
        
        loadDanhSachMon();
        loadChiTietHoaDon();
    }
    
    private void loadDanhSachMon() {
        tblModelMon.setRowCount(0);
        String selectedLoai = (String) cboLoaiMon.getSelectedItem();
        List<ThucDon> monList;
        
        if (selectedLoai.equals("Tất cả")) {
            monList = datMonService.layDanhSachMonKhaDung();
        } else {
            LoaiMon loaiMon = datMonService.timLoaiMonTheoTen(selectedLoai);
            monList = datMonService.layDanhSachMonTheoLoai(loaiMon.getMaLoaiMon());
        }
        
        for (ThucDon mon : monList) {
            tblModelMon.addRow(new Object[]{
                mon.getMaMon(),
                mon.getTenMon(),
                mon.getTenLoaiMon(),
                mon.getDonGia(),
                mon.getTrangThai()
            });
        }
    }
    
    private void loadChiTietHoaDon() {
        tblModelChiTiet.setRowCount(0);
        String selectedHoaDon = (String) cboHoaDon.getSelectedItem();
        if (selectedHoaDon == null) return;
        
        int maHoaDon = Integer.parseInt(selectedHoaDon.substring(10));
        List<ChiTietHoaDon> chiTietList = datMonService.layChiTietHoaDon(maHoaDon);
        
        double tongTien = 0;
        for (ChiTietHoaDon chiTiet : chiTietList) {
            tblModelChiTiet.addRow(new Object[]{
                chiTiet.getMaChiTietHD(),
                chiTiet.getTenMon(),
                chiTiet.getSoLuong(),
                chiTiet.getDonGiaLucDat(),
                chiTiet.getThanhTien()
            });
            tongTien += chiTiet.getThanhTien();
        }
        
        lblTongTien.setText(String.format("%.0f VNĐ", tongTien));
    }
    
    private void themMon() {
        int selectedRow = tblMon.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn món cần thêm!");
            return;
        }
        
        String selectedHoaDon = (String) cboHoaDon.getSelectedItem();
        if (selectedHoaDon == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn!");
            return;
        }
        
        int maHoaDon = Integer.parseInt(selectedHoaDon.substring(10));
        int maMon = (int) tblModelMon.getValueAt(selectedRow, 0);
        int soLuong = (int) spnSoLuong.getValue();
        String ghiChu = txtGhiChu.getText();
        
        if (datMonService.themMonVaoHoaDon(maHoaDon, maMon, soLuong, ghiChu)) {
            JOptionPane.showMessageDialog(this, "Thêm món thành công!");
            loadChiTietHoaDon();
        } else {
            JOptionPane.showMessageDialog(this, "Thêm món thất bại!");
        }
    }
    
    private void xoaMon() {
        int selectedRow = tblChiTietHoaDon.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn món cần xóa!");
            return;
        }
        
        int maChiTietHD = (int) tblModelChiTiet.getValueAt(selectedRow, 0);
        
        if (datMonService.xoaMonKhoiHoaDon(maChiTietHD)) {
            JOptionPane.showMessageDialog(this, "Xóa món thành công!");
            loadChiTietHoaDon();
        } else {
            JOptionPane.showMessageDialog(this, "Xóa món thất bại!");
        }
    }
    
    private void capNhatSoLuong() {
        int selectedRow = tblChiTietHoaDon.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn món cần cập nhật!");
            return;
        }
        
        int maChiTietHD = (int) tblModelChiTiet.getValueAt(selectedRow, 0);
        int soLuongMoi = (int) spnSoLuong.getValue();
        
        if (datMonService.capNhatSoLuongMon(maChiTietHD, soLuongMoi)) {
            JOptionPane.showMessageDialog(this, "Cập nhật số lượng thành công!");
            loadChiTietHoaDon();
        } else {
            JOptionPane.showMessageDialog(this, "Cập nhật số lượng thất bại!");
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new DatMonForm().setVisible(true);
        });
    }
} 