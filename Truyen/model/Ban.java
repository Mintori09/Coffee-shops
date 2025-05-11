package javaProject.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javaProject.DatabaseConnection.BaseDAO;

public class Ban extends BaseDAO {
    private int maBan;
    private String tenBan;
    private int soGhe;
    private String trangThai;
    private String viTri;
    private String thoiGianTao;
    private String thoiGianCapNhat;

    public Ban() {
        super();
    }

    // Getters and Setters
    public int getMaBan() { return maBan; }
    public void setMaBan(int maBan) { this.maBan = maBan; }
    public String getTenBan() { return tenBan; }
    public void setTenBan(String tenBan) { this.tenBan = tenBan; }
    public int getSoGhe() { return soGhe; }
    public void setSoGhe(int soGhe) { this.soGhe = soGhe; }
    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }
    public String getViTri() { return viTri; }
    public void setViTri(String viTri) { this.viTri = viTri; }
    public String getThoiGianTao() { return thoiGianTao; }
    public void setThoiGianTao(String thoiGianTao) { this.thoiGianTao = thoiGianTao; }
    public String getThoiGianCapNhat() { return thoiGianCapNhat; }
    public void setThoiGianCapNhat(String thoiGianCapNhat) { this.thoiGianCapNhat = thoiGianCapNhat; }

    // CRUD Operations
    public boolean themBan() {
        String sql = "INSERT INTO Ban (MaBan, TenBan, SoGhe, TrangThai, ViTri) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maBan);
            ps.setString(2, tenBan);
            ps.setInt(3, soGhe);
            ps.setString(4, trangThai);
            ps.setString(5, viTri);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean capNhatBan() {
        String sql = "UPDATE Ban SET TenBan = ?, SoGhe = ?, TrangThai = ?, ViTri = ?, " +
                    "ThoiGianCapNhat = GETDATE() WHERE MaBan = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, tenBan);
            ps.setInt(2, soGhe);
            ps.setString(3, trangThai);
            ps.setString(4, viTri);
            ps.setInt(5, maBan);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean xoaBan() {
        String sql = "DELETE FROM Ban WHERE MaBan = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maBan);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Ban timBanTheoMa(int maBan) {
        String sql = "SELECT * FROM Ban WHERE MaBan = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maBan);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Ban ban = new Ban();
                ban.setMaBan(rs.getInt("MaBan"));
                ban.setTenBan(rs.getString("TenBan"));
                ban.setSoGhe(rs.getInt("SoGhe"));
                ban.setTrangThai(rs.getString("TrangThai"));
                ban.setViTri(rs.getString("ViTri"));
                ban.setThoiGianTao(rs.getString("ThoiGianTao"));
                ban.setThoiGianCapNhat(rs.getString("ThoiGianCapNhat"));
                return ban;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Ban> layTatCaBan() {
        List<Ban> danhSach = new ArrayList<>();
        String sql = "SELECT * FROM Ban";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Ban ban = new Ban();
                ban.setMaBan(rs.getInt("MaBan"));
                ban.setTenBan(rs.getString("TenBan"));
                ban.setSoGhe(rs.getInt("SoGhe"));
                ban.setTrangThai(rs.getString("TrangThai"));
                ban.setViTri(rs.getString("ViTri"));
                ban.setThoiGianTao(rs.getString("ThoiGianTao"));
                ban.setThoiGianCapNhat(rs.getString("ThoiGianCapNhat"));
                danhSach.add(ban);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return danhSach;
    }

    public List<Ban> timBanTheoTrangThai(String trangThai) {
        List<Ban> danhSach = new ArrayList<>();
        String sql = "SELECT * FROM Ban WHERE TrangThai = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, trangThai);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Ban ban = new Ban();
                ban.setMaBan(rs.getInt("MaBan"));
                ban.setTenBan(rs.getString("TenBan"));
                ban.setSoGhe(rs.getInt("SoGhe"));
                ban.setTrangThai(rs.getString("TrangThai"));
                ban.setViTri(rs.getString("ViTri"));
                ban.setThoiGianTao(rs.getString("ThoiGianTao"));
                ban.setThoiGianCapNhat(rs.getString("ThoiGianCapNhat"));
                danhSach.add(ban);
            }
            System.out.println("Số bàn trạng thái '" + trangThai + "': " + danhSach.size());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return danhSach;
    }
} 