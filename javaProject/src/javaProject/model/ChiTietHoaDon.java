package javaProject.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javaProject.DatabaseConnection.BaseDAO;

public class ChiTietHoaDon extends BaseDAO {
    private int maChiTietHD;
    private int maHoaDon;
    private int maMon;
    private String tenMon;
    private int soLuong;
    private double donGiaLucDat;
    private double thanhTien;
    private String ghiChuMon;
    private String thoiGianTao;
    private String thoiGianCapNhat;

    public ChiTietHoaDon() {
        super();
    }

    public ChiTietHoaDon(int maChiTietHD, int maHoaDon, int maMon, int soLuong, double donGiaLucDat, double thanhTien,
            String ghiChuMon) {
        this.maChiTietHD = maChiTietHD;
        this.maHoaDon = maHoaDon;
        this.maMon = maMon;
        this.soLuong = soLuong;
        this.donGiaLucDat = donGiaLucDat;
        this.thanhTien = thanhTien;
        this.ghiChuMon = ghiChuMon;
    }

    // Getters and Setters
    public int getMaChiTietHD() {
        return maChiTietHD;
    }

    public void setMaChiTietHD(int maChiTietHD) {
        this.maChiTietHD = maChiTietHD;
    }

    public int getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(int maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public int getMaMon() {
        return maMon;
    }

    public void setMaMon(int maMon) {
        this.maMon = maMon;
    }

    public String getTenMon() {
        return tenMon;
    }

    public void setTenMon(String tenMon) {
        this.tenMon = tenMon;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public double getDonGiaLucDat() {
        return donGiaLucDat;
    }

    public void setDonGiaLucDat(double donGiaLucDat) {
        this.donGiaLucDat = donGiaLucDat;
    }

    public double getThanhTien() {
        return thanhTien;
    }

    public void setThanhTien(double thanhTien) {
        this.thanhTien = thanhTien;
    }

    public String getGhiChuMon() {
        return ghiChuMon;
    }

    public void setGhiChuMon(String ghiChuMon) {
        this.ghiChuMon = ghiChuMon;
    }

    public String getThoiGianTao() {
        return thoiGianTao;
    }

    public void setThoiGianTao(String thoiGianTao) {
        this.thoiGianTao = thoiGianTao;
    }

    public String getThoiGianCapNhat() {
        return thoiGianCapNhat;
    }

    public void setThoiGianCapNhat(String thoiGianCapNhat) {
        this.thoiGianCapNhat = thoiGianCapNhat;
    }

    // CRUD Operations
    public boolean themChiTietHoaDon() {
        String sql = "INSERT INTO ChiTietHoaDon (MaChiTietHD, MaHoaDon, MaMon, SoLuong, DonGiaLucDat, " +
                "ThanhTien, GhiChuMon) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maChiTietHD);
            ps.setInt(2, maHoaDon);
            ps.setInt(3, maMon);
            ps.setInt(4, soLuong);
            ps.setDouble(5, donGiaLucDat);
            ps.setDouble(6, thanhTien);
            ps.setString(7, ghiChuMon);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean capNhatChiTietHoaDon() {
        String sql = "UPDATE ChiTietHoaDon SET SoLuong = ?, DonGiaLucDat = ?, ThanhTien = ?, " +
                "GhiChuMon = ?, ThoiGianCapNhat = GETDATE() WHERE MaChiTietHD = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, soLuong);
            ps.setDouble(2, donGiaLucDat);
            ps.setDouble(3, thanhTien);
            ps.setString(4, ghiChuMon);
            ps.setInt(5, maChiTietHD);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean xoaChiTietHoaDon() {
        String sql = "DELETE FROM ChiTietHoaDon WHERE MaChiTietHD = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maChiTietHD);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ChiTietHoaDon timChiTietHoaDonTheoMa(int maChiTietHD) {
        String sql = "SELECT c.*, t.TenMon FROM ChiTietHoaDon c JOIN ThucDon t ON c.MaMon = t.MaMon WHERE c.MaChiTietHD = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maChiTietHD);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                ChiTietHoaDon chiTiet = new ChiTietHoaDon();
                chiTiet.setMaChiTietHD(rs.getInt("MaChiTietHD"));
                chiTiet.setMaHoaDon(rs.getInt("MaHoaDon"));
                chiTiet.setMaMon(rs.getInt("MaMon"));
                chiTiet.setTenMon(rs.getString("TenMon"));
                chiTiet.setSoLuong(rs.getInt("SoLuong"));
                chiTiet.setDonGiaLucDat(rs.getDouble("DonGiaLucDat"));
                chiTiet.setThanhTien(rs.getDouble("ThanhTien"));
                chiTiet.setGhiChuMon(rs.getString("GhiChuMon"));
                chiTiet.setThoiGianTao(rs.getString("ThoiGianTao"));
                chiTiet.setThoiGianCapNhat(rs.getString("ThoiGianCapNhat"));
                return chiTiet;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<ChiTietHoaDon> layChiTietHoaDonTheoMaHoaDon(int maHoaDon) {
        List<ChiTietHoaDon> danhSach = new ArrayList<>();
        String sql = "SELECT c.*, t.TenMon FROM ChiTietHoaDon c JOIN ThucDon t ON c.MaMon = t.MaMon WHERE c.MaHoaDon = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maHoaDon);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ChiTietHoaDon chiTiet = new ChiTietHoaDon();
                chiTiet.setMaChiTietHD(rs.getInt("MaChiTietHD"));
                chiTiet.setMaHoaDon(rs.getInt("MaHoaDon"));
                chiTiet.setMaMon(rs.getInt("MaMon"));
                chiTiet.setTenMon(rs.getString("TenMon"));
                chiTiet.setSoLuong(rs.getInt("SoLuong"));
                chiTiet.setDonGiaLucDat(rs.getDouble("DonGiaLucDat"));
                chiTiet.setThanhTien(rs.getDouble("ThanhTien"));
                chiTiet.setGhiChuMon(rs.getString("GhiChuMon"));
                chiTiet.setThoiGianTao(rs.getString("ThoiGianTao"));
                chiTiet.setThoiGianCapNhat(rs.getString("ThoiGianCapNhat"));
                danhSach.add(chiTiet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return danhSach;
    }

    public List<ChiTietHoaDon> layTatCaChiTietHoaDon() {
        List<ChiTietHoaDon> danhSach = new ArrayList<>();
        String sql = "SELECT c.*, t.TenMon FROM ChiTietHoaDon c JOIN ThucDon t ON c.MaMon = t.MaMon";
        try (PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                ChiTietHoaDon chiTiet = new ChiTietHoaDon();
                chiTiet.setMaChiTietHD(rs.getInt("MaChiTietHD"));
                chiTiet.setMaHoaDon(rs.getInt("MaHoaDon"));
                chiTiet.setMaMon(rs.getInt("MaMon"));
                chiTiet.setTenMon(rs.getString("TenMon"));
                chiTiet.setSoLuong(rs.getInt("SoLuong"));
                chiTiet.setDonGiaLucDat(rs.getDouble("DonGiaLucDat"));
                chiTiet.setThanhTien(rs.getDouble("ThanhTien"));
                chiTiet.setGhiChuMon(rs.getString("GhiChuMon"));
                chiTiet.setThoiGianTao(rs.getString("ThoiGianTao"));
                chiTiet.setThoiGianCapNhat(rs.getString("ThoiGianCapNhat"));
                danhSach.add(chiTiet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return danhSach;
    }
}
