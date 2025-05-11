package javaProject.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javaProject.DatabaseConnection.BaseDAO;

public class NhanVien extends BaseDAO {
    private int maNhanVien;
    private String tenNhanVien;
    private String soDienThoai;
    private String email;
    private String chucVu;
    private String thoiGianTao;
    private String thoiGianCapNhat;

    public NhanVien() {
        super();
    }

    // Getters and Setters
    public int getMaNhanVien() { return maNhanVien; }
    public void setMaNhanVien(int maNhanVien) { this.maNhanVien = maNhanVien; }
    public String getTenNhanVien() { return tenNhanVien; }
    public void setTenNhanVien(String tenNhanVien) { this.tenNhanVien = tenNhanVien; }
    public String getSoDienThoai() { return soDienThoai; }
    public void setSoDienThoai(String soDienThoai) { this.soDienThoai = soDienThoai; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getChucVu() { return chucVu; }
    public void setChucVu(String chucVu) { this.chucVu = chucVu; }
    public String getThoiGianTao() { return thoiGianTao; }
    public void setThoiGianTao(String thoiGianTao) { this.thoiGianTao = thoiGianTao; }
    public String getThoiGianCapNhat() { return thoiGianCapNhat; }
    public void setThoiGianCapNhat(String thoiGianCapNhat) { this.thoiGianCapNhat = thoiGianCapNhat; }

    // CRUD Operations
    public boolean themNhanVien() {
        String sql = "INSERT INTO NhanVien (MaNhanVien, TenNhanVien, SoDienThoai, Email, ChucVu) " +
                    "VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maNhanVien);
            ps.setString(2, tenNhanVien);
            ps.setString(3, soDienThoai);
            ps.setString(4, email);
            ps.setString(5, chucVu);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean capNhatNhanVien() {
        String sql = "UPDATE NhanVien SET TenNhanVien = ?, SoDienThoai = ?, Email = ?, ChucVu = ?, " +
                    "ThoiGianCapNhat = GETDATE() WHERE MaNhanVien = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, tenNhanVien);
            ps.setString(2, soDienThoai);
            ps.setString(3, email);
            ps.setString(4, chucVu);
            ps.setInt(5, maNhanVien);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean xoaNhanVien() {
        String sql = "DELETE FROM NhanVien WHERE MaNhanVien = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maNhanVien);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public NhanVien timNhanVienTheoMa(int maNhanVien) {
        String sql = "SELECT * FROM NhanVien WHERE MaNhanVien = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maNhanVien);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                NhanVien nv = new NhanVien();
                nv.setMaNhanVien(rs.getInt("MaNhanVien"));
                nv.setTenNhanVien(rs.getString("TenNhanVien"));
                nv.setSoDienThoai(rs.getString("SoDienThoai"));
                nv.setEmail(rs.getString("Email"));
                nv.setChucVu(rs.getString("ChucVu"));
                nv.setThoiGianTao(rs.getString("ThoiGianTao"));
                nv.setThoiGianCapNhat(rs.getString("ThoiGianCapNhat"));
                return nv;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<NhanVien> layTatCaNhanVien() {
        List<NhanVien> danhSach = new ArrayList<>();
        String sql = "SELECT * FROM NhanVien";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                NhanVien nv = new NhanVien();
                nv.setMaNhanVien(rs.getInt("MaNhanVien"));
                nv.setTenNhanVien(rs.getString("TenNhanVien"));
                nv.setSoDienThoai(rs.getString("SoDienThoai"));
                nv.setEmail(rs.getString("Email"));
                nv.setChucVu(rs.getString("ChucVu"));
                nv.setThoiGianTao(rs.getString("ThoiGianTao"));
                nv.setThoiGianCapNhat(rs.getString("ThoiGianCapNhat"));
                danhSach.add(nv);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return danhSach;
    }

    public List<NhanVien> timNhanVienTheoChucVu(String chucVu) {
        List<NhanVien> danhSach = new ArrayList<>();
        String sql = "SELECT * FROM NhanVien WHERE ChucVu = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, chucVu);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                NhanVien nv = new NhanVien();
                nv.setMaNhanVien(rs.getInt("MaNhanVien"));
                nv.setTenNhanVien(rs.getString("TenNhanVien"));
                nv.setSoDienThoai(rs.getString("SoDienThoai"));
                nv.setEmail(rs.getString("Email"));
                nv.setChucVu(rs.getString("ChucVu"));
                nv.setThoiGianTao(rs.getString("ThoiGianTao"));
                nv.setThoiGianCapNhat(rs.getString("ThoiGianCapNhat"));
                danhSach.add(nv);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return danhSach;
    }
} 