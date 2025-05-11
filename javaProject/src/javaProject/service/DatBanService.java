package javaProject.service;

import javaProject.model.Ban;
import javaProject.model.HoaDon;
import javaProject.model.NhanVien;
import javaProject.model.KhachHang;
import java.sql.SQLException;
import java.util.List;

public class DatBanService {
    private Ban banModel;
    private HoaDon hoaDonModel;
    private NhanVien nhanVienModel;
    private KhachHang khachHangModel;

    public DatBanService() {
        banModel = new Ban();
        hoaDonModel = new HoaDon();
        nhanVienModel = new NhanVien();
        khachHangModel = new KhachHang();
    }

    // Kiểm tra bàn có trống không
    public boolean kiemTraBanTrong(int maBan) {
        Ban ban = banModel.timBanTheoMa(maBan);
        return ban != null && ban.getTrangThai().equals("Trống");
    }

    // Lấy danh sách bàn trống
    public List<Ban> layDanhSachBanTrong() {
        return banModel.timBanTheoTrangThai("Trống");
    }

    // Lấy danh sách nhân viên
    public List<NhanVien> layDanhSachNhanVien() {
        return nhanVienModel.layTatCaNhanVien();
    }

    // Lấy danh sách khách hàng
    public List<KhachHang> layDanhSachKhachHang() {
        return khachHangModel.layTatCaKhachHang();
    }

    // Đặt bàn
    public boolean datBan(int maBan, int maNhanVien, int maKhachHang, String ghiChu) {
        try {
            // Kiểm tra bàn có trống không
            if (!kiemTraBanTrong(maBan)) {
                return false;
            }

            // Cập nhật trạng thái bàn
            Ban ban = banModel.timBanTheoMa(maBan);
            ban.setTrangThai("Đã đặt");
            if (!ban.capNhatBan()) {
                return false;
            }

            // Tạo hóa đơn mới
            HoaDon hoaDon = new HoaDon();
            hoaDon.setMaBan(maBan);
            hoaDon.setMaNhanVien(maNhanVien);
            hoaDon.setMaKhachHang(maKhachHang);
            hoaDon.setTongTienTruocGiam(0);
            hoaDon.setSoTienGiam(0);
            hoaDon.setTongTienPhaiTra(0);
            hoaDon.setTrangThai("Chờ thanh toán");
            hoaDon.setGhiChu(ghiChu);

            return hoaDon.themHoaDon();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Hủy đặt bàn
    public boolean huyDatBan(int maHoaDon) {
        try {
            HoaDon hoaDon = hoaDonModel.timHoaDonTheoMa(maHoaDon);
            if (hoaDon == null || !hoaDon.getTrangThai().equals("Chờ thanh toán")) {
                return false;
            }

            // Cập nhật trạng thái bàn
            Ban ban = banModel.timBanTheoMa(hoaDon.getMaBan());
            ban.setTrangThai("Trống");
            if (!ban.capNhatBan()) {
                return false;
            }

            // Hủy hóa đơn
            return hoaDon.huyHoaDon();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Lấy thông tin đặt bàn
    public HoaDon layThongTinDatBan(int maHoaDon) {
        return hoaDonModel.timHoaDonTheoMa(maHoaDon);
    }

    // Lấy danh sách đặt bàn
    public List<HoaDon> layDanhSachDatBan() {
        return hoaDonModel.timHoaDonTheoTrangThai("Chờ thanh toán");
    }

    // Thêm hoặc lấy mã khách hàng theo số điện thoại
    public int themHoacLayMaKhachHang(String tenKhachHang, String soDienThoai, String email, String diaChi) {
        KhachHang kh = khachHangModel.timKhachHangTheoSDT(soDienThoai);
        if (kh != null) return kh.getMaKhachHang();
        // Nếu chưa có, thêm mới
        KhachHang newKH = new KhachHang();
        newKH.setTenKhachHang(tenKhachHang);
        newKH.setSoDienThoai(soDienThoai);
        newKH.setEmail(email);
        newKH.setDiaChi(diaChi);
        newKH.themKhachHang();
        KhachHang khMoi = khachHangModel.timKhachHangTheoSDT(soDienThoai);
        return khMoi != null ? khMoi.getMaKhachHang() : -1;
    }
} 