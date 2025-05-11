package javaProject.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javaProject.DatabaseConnection.BaseDAO;

public class KhachHang extends BaseDAO {
    private int maKhachHang;
    private String tenKhachHang;
    private String soDienThoai;
    private String email;
    private String diaChi;
    private String thoiGianTao;
    private String thoiGianCapNhat;

    public KhachHang() {
        super();
    }

    // Getters and Setters
    public int getMaKhachHang() { return maKhachHang; }
    public void setMaKhachHang(int maKhachHang) { this.maKhachHang = maKhachHang; }
    public String getTenKhachHang() { return tenKhachHang; }
    public void setTenKhachHang(String tenKhachHang) { this.tenKhachHang = tenKhachHang; }
    public String getSoDienThoai() { return soDienThoai; }
    public void setSoDienThoai(String soDienThoai) { this.soDienThoai = soDienThoai; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getDiaChi() { return diaChi; }
    public void setDiaChi(String diaChi) { this.diaChi = diaChi; }
    public String getThoiGianTao() { return thoiGianTao; }
    public void setThoiGianTao(String thoiGianTao) { this.thoiGianTao = thoiGianTao; }
    public String getThoiGianCapNhat() { return thoiGianCapNhat; }
    public void setThoiGianCapNhat(String thoiGianCapNhat) { this.thoiGianCapNhat = thoiGianCapNhat; }

    // CRUD Operations
    public boolean themKhachHang() {
        String sql = "INSERT INTO KhachHang (MaKhachHang, TenKhachHang, SoDienThoai, Email, DiaChi) " +
                    "VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maKhachHang);
            ps.setString(2, tenKhachHang);
            ps.setString(3, soDienThoai);
            ps.setString(4, email);
            ps.setString(5, diaChi);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean capNhatKhachHang() {
        String sql = "UPDATE KhachHang SET TenKhachHang = ?, SoDienThoai = ?, Email = ?, DiaChi = ?, " +
                    "ThoiGianCapNhat = GETDATE() WHERE MaKhachHang = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, tenKhachHang);
            ps.setString(2, soDienThoai);
            ps.setString(3, email);
            ps.setString(4, diaChi);
            ps.setInt(5, maKhachHang);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean xoaKhachHang() {
        String sql = "DELETE FROM KhachHang WHERE MaKhachHang = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maKhachHang);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public KhachHang timKhachHangTheoMa(int maKhachHang) {
        String sql = "SELECT * FROM KhachHang WHERE MaKhachHang = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maKhachHang);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                KhachHang kh = new KhachHang();
                kh.setMaKhachHang(rs.getInt("MaKhachHang"));
                kh.setTenKhachHang(rs.getString("TenKhachHang"));
                kh.setSoDienThoai(rs.getString("SoDienThoai"));
                kh.setEmail(rs.getString("Email"));
                kh.setDiaChi(rs.getString("DiaChi"));
                kh.setThoiGianTao(rs.getString("ThoiGianTao"));
                kh.setThoiGianCapNhat(rs.getString("ThoiGianCapNhat"));
                return kh;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<KhachHang> layTatCaKhachHang() {
        List<KhachHang> danhSach = new ArrayList<>();
        String sql = "SELECT * FROM KhachHang";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                KhachHang kh = new KhachHang();
                kh.setMaKhachHang(rs.getInt("MaKhachHang"));
                kh.setTenKhachHang(rs.getString("TenKhachHang"));
                kh.setSoDienThoai(rs.getString("SoDienThoai"));
                kh.setEmail(rs.getString("Email"));
                kh.setDiaChi(rs.getString("DiaChi"));
                kh.setThoiGianTao(rs.getString("ThoiGianTao"));
                kh.setThoiGianCapNhat(rs.getString("ThoiGianCapNhat"));
                danhSach.add(kh);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return danhSach;
    }

    public KhachHang timKhachHangTheoSDT(String soDienThoai) {
        String sql = "SELECT * FROM KhachHang WHERE SoDienThoai = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, soDienThoai);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                KhachHang kh = new KhachHang();
                kh.setMaKhachHang(rs.getInt("MaKhachHang"));
                kh.setTenKhachHang(rs.getString("TenKhachHang"));
                kh.setSoDienThoai(rs.getString("SoDienThoai"));
                kh.setEmail(rs.getString("Email"));
                kh.setDiaChi(rs.getString("DiaChi"));
                kh.setThoiGianTao(rs.getString("ThoiGianTao"));
                kh.setThoiGianCapNhat(rs.getString("ThoiGianCapNhat"));
                return kh;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public KhachHang timKhachHangTheoTen(String tenKhachHang) {
        String sql = "SELECT * FROM KhachHang WHERE TenKhachHang = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, tenKhachHang);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                KhachHang kh = new KhachHang();
                kh.setMaKhachHang(rs.getInt("MaKhachHang"));
                kh.setTenKhachHang(rs.getString("TenKhachHang"));
                kh.setSoDienThoai(rs.getString("SoDienThoai"));
                kh.setEmail(rs.getString("Email"));
                kh.setDiaChi(rs.getString("DiaChi"));
                kh.setThoiGianTao(rs.getString("ThoiGianTao"));
                kh.setThoiGianCapNhat(rs.getString("ThoiGianCapNhat"));
                return kh;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
} 