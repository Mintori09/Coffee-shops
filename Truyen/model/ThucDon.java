package javaProject.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javaProject.DatabaseConnection.BaseDAO;

public class ThucDon extends BaseDAO {
    private int maMon;
    private String tenMon;
    private String moTa;
    private double donGia;
    private String hinhAnhURL;
    private boolean trangThaiKhaDung;
    private int maLoaiMon;
    private String tenLoaiMon;
    private String trangThai;
    private String thoiGianTao;
    private String thoiGianCapNhat;

    public ThucDon() {
        super();
    }

    public ThucDon(int maMon, String tenMon, int maLoaiMon, double gia, String trangThai) {
        this.maMon = maMon;
        this.tenMon = tenMon;
        this.maLoaiMon = maLoaiMon;
        this.donGia = gia;
        this.trangThai = trangThai;
    }

    // Getters and Setters
    public int getMaMon() { return maMon; }
    public void setMaMon(int maMon) { this.maMon = maMon; }
    public String getTenMon() { return tenMon; }
    public void setTenMon(String tenMon) { this.tenMon = tenMon; }
    public String getMoTa() { return moTa; }
    public void setMoTa(String moTa) { this.moTa = moTa; }
    public double getDonGia() { return donGia; }
    public void setDonGia(double donGia) { this.donGia = donGia; }
    public String getHinhAnhURL() { return hinhAnhURL; }
    public void setHinhAnhURL(String hinhAnhURL) { this.hinhAnhURL = hinhAnhURL; }
    public boolean isTrangThaiKhaDung() { return trangThaiKhaDung; }
    public void setTrangThaiKhaDung(boolean trangThaiKhaDung) { this.trangThaiKhaDung = trangThaiKhaDung; }
    public int getMaLoaiMon() { return maLoaiMon; }
    public void setMaLoaiMon(int maLoaiMon) { this.maLoaiMon = maLoaiMon; }
    public String getTenLoaiMon() { return tenLoaiMon; }
    public void setTenLoaiMon(String tenLoaiMon) { this.tenLoaiMon = tenLoaiMon; }
    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }
    public String getThoiGianTao() { return thoiGianTao; }
    public void setThoiGianTao(String thoiGianTao) { this.thoiGianTao = thoiGianTao; }
    public String getThoiGianCapNhat() { return thoiGianCapNhat; }
    public void setThoiGianCapNhat(String thoiGianCapNhat) { this.thoiGianCapNhat = thoiGianCapNhat; }

    // CRUD Operations
    public boolean themMon() {
        String sql = "INSERT INTO ThucDon (MaMon, TenMon, MoTa, DonGia, HinhAnhURL, TrangThaiKhaDung, MaLoaiMon) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maMon);
            ps.setString(2, tenMon);
            ps.setString(3, moTa);
            ps.setDouble(4, donGia);
            ps.setString(5, hinhAnhURL);
            ps.setBoolean(6, trangThaiKhaDung);
            ps.setInt(7, maLoaiMon);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean capNhatMon() {
        String sql = "UPDATE ThucDon SET TenMon = ?, MoTa = ?, DonGia = ?, HinhAnhURL = ?, " +
                    "TrangThaiKhaDung = ?, MaLoaiMon = ?, ThoiGianCapNhat = GETDATE() WHERE MaMon = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, tenMon);
            ps.setString(2, moTa);
            ps.setDouble(3, donGia);
            ps.setString(4, hinhAnhURL);
            ps.setBoolean(5, trangThaiKhaDung);
            ps.setInt(6, maLoaiMon);
            ps.setInt(7, maMon);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean xoaMon() {
        String sql = "DELETE FROM ThucDon WHERE MaMon = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maMon);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ThucDon timMonTheoMa(int maMon) {
        String sql = "SELECT * FROM ThucDon WHERE MaMon = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maMon);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                ThucDon mon = new ThucDon();
                mon.setMaMon(rs.getInt("MaMon"));
                mon.setTenMon(rs.getString("TenMon"));
                mon.setMoTa(rs.getString("MoTa"));
                mon.setDonGia(rs.getDouble("DonGia"));
                mon.setHinhAnhURL(rs.getString("HinhAnhURL"));
                mon.setTrangThaiKhaDung(rs.getBoolean("TrangThaiKhaDung"));
                mon.setMaLoaiMon(rs.getInt("MaLoaiMon"));
                mon.setThoiGianTao(rs.getString("ThoiGianTao"));
                mon.setThoiGianCapNhat(rs.getString("ThoiGianCapNhat"));
                return mon;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<ThucDon> layTatCaMon() {
        List<ThucDon> danhSach = new ArrayList<>();
        String sql = "SELECT * FROM ThucDon";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                ThucDon mon = new ThucDon();
                mon.setMaMon(rs.getInt("MaMon"));
                mon.setTenMon(rs.getString("TenMon"));
                mon.setMoTa(rs.getString("MoTa"));
                mon.setDonGia(rs.getDouble("DonGia"));
                mon.setHinhAnhURL(rs.getString("HinhAnhURL"));
                mon.setTrangThaiKhaDung(rs.getBoolean("TrangThaiKhaDung"));
                mon.setMaLoaiMon(rs.getInt("MaLoaiMon"));
                mon.setThoiGianTao(rs.getString("ThoiGianTao"));
                mon.setThoiGianCapNhat(rs.getString("ThoiGianCapNhat"));
                danhSach.add(mon);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return danhSach;
    }

    public List<ThucDon> timMonTheoLoai(int maLoaiMon) {
        List<ThucDon> danhSach = new ArrayList<>();
        String sql = "SELECT * FROM ThucDon WHERE MaLoaiMon = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maLoaiMon);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ThucDon mon = new ThucDon();
                mon.setMaMon(rs.getInt("MaMon"));
                mon.setTenMon(rs.getString("TenMon"));
                mon.setMoTa(rs.getString("MoTa"));
                mon.setDonGia(rs.getDouble("DonGia"));
                mon.setHinhAnhURL(rs.getString("HinhAnhURL"));
                mon.setTrangThaiKhaDung(rs.getBoolean("TrangThaiKhaDung"));
                mon.setMaLoaiMon(rs.getInt("MaLoaiMon"));
                mon.setThoiGianTao(rs.getString("ThoiGianTao"));
                mon.setThoiGianCapNhat(rs.getString("ThoiGianCapNhat"));
                danhSach.add(mon);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return danhSach;
    }

    public List<ThucDon> timMonTheoTrangThai(String trangThai) {
        List<ThucDon> danhSach = new ArrayList<>();
        String sql = "SELECT * FROM ThucDon WHERE TrangThai = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, trangThai);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ThucDon mon = new ThucDon();
                mon.setMaMon(rs.getInt("MaMon"));
                mon.setTenMon(rs.getString("TenMon"));
                mon.setMoTa(rs.getString("MoTa"));
                mon.setDonGia(rs.getDouble("DonGia"));
                mon.setHinhAnhURL(rs.getString("HinhAnhURL"));
                mon.setTrangThaiKhaDung(rs.getBoolean("TrangThaiKhaDung"));
                mon.setMaLoaiMon(rs.getInt("MaLoaiMon"));
                mon.setThoiGianTao(rs.getString("ThoiGianTao"));
                mon.setThoiGianCapNhat(rs.getString("ThoiGianCapNhat"));
                danhSach.add(mon);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return danhSach;
    }

    public List<ThucDon> timMonTheoLoaiVaTrangThai(int maLoaiMon, String trangThai) {
        List<ThucDon> danhSach = new ArrayList<>();
        String sql = "SELECT * FROM ThucDon WHERE MaLoaiMon = ? AND TrangThai = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maLoaiMon);
            ps.setString(2, trangThai);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ThucDon mon = new ThucDon();
                mon.setMaMon(rs.getInt("MaMon"));
                mon.setTenMon(rs.getString("TenMon"));
                mon.setMoTa(rs.getString("MoTa"));
                mon.setDonGia(rs.getDouble("DonGia"));
                mon.setHinhAnhURL(rs.getString("HinhAnhURL"));
                mon.setTrangThaiKhaDung(rs.getBoolean("TrangThaiKhaDung"));
                mon.setMaLoaiMon(rs.getInt("MaLoaiMon"));
                mon.setThoiGianTao(rs.getString("ThoiGianTao"));
                mon.setThoiGianCapNhat(rs.getString("ThoiGianCapNhat"));
                danhSach.add(mon);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return danhSach;
    }
} 