package javaProject.service;

import javaProject.model.*;
import java.sql.SQLException;
import java.util.List;

public class DatMonService {
    private ThucDon thucDonModel;
    private LoaiMon loaiMonModel;
    private HoaDon hoaDonModel;
    private ChiTietHoaDon chiTietHoaDonModel;

    public DatMonService() {
        thucDonModel = new ThucDon();
        loaiMonModel = new LoaiMon();
        hoaDonModel = new HoaDon();
        chiTietHoaDonModel = new ChiTietHoaDon();
    }

    // Lấy danh sách loại món
    public List<LoaiMon> layDanhSachLoaiMon() {
        return loaiMonModel.layTatCaLoaiMon();
    }

    // Tìm loại món theo tên
    public LoaiMon timLoaiMonTheoTen(String tenLoaiMon) {
        return loaiMonModel.timLoaiMonTheoTen(tenLoaiMon);
    }

    // Lấy danh sách hóa đơn chưa thanh toán
    public List<HoaDon> layDanhSachHoaDonChuaThanhToan() {
        return hoaDonModel.timHoaDonTheoTrangThai("Chờ thanh toán");
    }

    // Lấy danh sách món theo loại
    public List<ThucDon> layDanhSachMonTheoLoai(int maLoaiMon) {
        return thucDonModel.timMonTheoLoai(maLoaiMon);
    }

    // Lấy danh sách món khả dụng
    public List<ThucDon> layDanhSachMonKhaDung() {
        return thucDonModel.timMonTheoTrangThai("Khả dụng");
    }

    // Thêm món vào hóa đơn
    public boolean themMonVaoHoaDon(int maHoaDon, int maMon, int soLuong, String ghiChu) {
        try {
            // Kiểm tra hóa đơn có tồn tại và chưa thanh toán không
            HoaDon hoaDon = hoaDonModel.timHoaDonTheoMa(maHoaDon);
            if (hoaDon == null || !hoaDon.getTrangThai().equals("Chờ thanh toán")) {
                return false;
            }

            // Kiểm tra món có tồn tại và khả dụng không
            ThucDon mon = thucDonModel.timMonTheoMa(maMon);
            if (mon == null || !mon.getTrangThai().equals("Khả dụng")) {
                return false;
            }

            // Tạo chi tiết hóa đơn mới
            ChiTietHoaDon chiTiet = new ChiTietHoaDon();
            chiTiet.setMaHoaDon(maHoaDon);
            chiTiet.setMaMon(maMon);
            chiTiet.setSoLuong(soLuong);
            chiTiet.setDonGiaLucDat(mon.getDonGia());
            chiTiet.setThanhTien(mon.getDonGia() * soLuong);
            chiTiet.setGhiChuMon(ghiChu);

            if (!chiTiet.themChiTietHoaDon()) {
                return false;
            }

            // Cập nhật tổng tiền hóa đơn
            double tongTienTruocGiam = hoaDon.getTongTienTruocGiam() + chiTiet.getThanhTien();
            hoaDon.setTongTienTruocGiam(tongTienTruocGiam);
            hoaDon.setTongTienPhaiTra(tongTienTruocGiam - hoaDon.getSoTienGiam());

            return hoaDon.capNhatHoaDon();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Xóa món khỏi hóa đơn
    public boolean xoaMonKhoiHoaDon(int maChiTietHD) {
        try {
            // Kiểm tra chi tiết hóa đơn có tồn tại không
            ChiTietHoaDon chiTiet = chiTietHoaDonModel.timChiTietHoaDonTheoMa(maChiTietHD);
            if (chiTiet == null) {
                return false;
            }

            // Kiểm tra hóa đơn có tồn tại và chưa thanh toán không
            HoaDon hoaDon = hoaDonModel.timHoaDonTheoMa(chiTiet.getMaHoaDon());
            if (hoaDon == null || !hoaDon.getTrangThai().equals("Chờ thanh toán")) {
                return false;
            }

            // Xóa chi tiết hóa đơn
            if (!chiTietHoaDonModel.xoaChiTietHoaDon()) {
                return false;
            }

            // Cập nhật tổng tiền hóa đơn
            double tongTienTruocGiam = hoaDon.getTongTienTruocGiam() - chiTiet.getThanhTien();
            hoaDon.setTongTienTruocGiam(tongTienTruocGiam);
            hoaDon.setTongTienPhaiTra(tongTienTruocGiam - hoaDon.getSoTienGiam());

            return hoaDon.capNhatHoaDon();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Cập nhật số lượng món
    public boolean capNhatSoLuongMon(int maChiTietHD, int soLuongMoi) {
        try {
            // Kiểm tra chi tiết hóa đơn có tồn tại không
            ChiTietHoaDon chiTiet = chiTietHoaDonModel.timChiTietHoaDonTheoMa(maChiTietHD);
            if (chiTiet == null) {
                return false;
            }

            // Kiểm tra hóa đơn có tồn tại và chưa thanh toán không
            HoaDon hoaDon = hoaDonModel.timHoaDonTheoMa(chiTiet.getMaHoaDon());
            if (hoaDon == null || !hoaDon.getTrangThai().equals("Chờ thanh toán")) {
                return false;
            }

            // Cập nhật số lượng và thành tiền
            double thanhTienCu = chiTiet.getThanhTien();
            chiTiet.setSoLuong(soLuongMoi);
            chiTiet.setThanhTien(chiTiet.getDonGiaLucDat() * soLuongMoi);

            if (!chiTietHoaDonModel.capNhatChiTietHoaDon()) {
                return false;
            }

            // Cập nhật tổng tiền hóa đơn
            double tongTienTruocGiam = hoaDon.getTongTienTruocGiam() - thanhTienCu + chiTiet.getThanhTien();
            hoaDon.setTongTienTruocGiam(tongTienTruocGiam);
            hoaDon.setTongTienPhaiTra(tongTienTruocGiam - hoaDon.getSoTienGiam());

            return hoaDon.capNhatHoaDon();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Lấy chi tiết hóa đơn
    public List<ChiTietHoaDon> layChiTietHoaDon(int maHoaDon) {
        return chiTietHoaDonModel.layChiTietHoaDonTheoMaHoaDon(maHoaDon);
    }
} 