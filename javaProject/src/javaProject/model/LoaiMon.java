package javaProject.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javaProject.DatabaseConnection.BaseDAO;

public class LoaiMon extends BaseDAO {
    private int maLoaiMon;
    private String tenLoaiMon;
    private String moTa;
    private String thoiGianTao;
    private String thoiGianCapNhat;

    public LoaiMon() {
        super();
    }

    public LoaiMon(int maLoaiMon, String tenLoaiMon) {
        this.maLoaiMon = maLoaiMon;
        this.tenLoaiMon = tenLoaiMon;
    }

    // Getters and Setters
    public int getMaLoaiMon() {
        return maLoaiMon;
    }

    public void setMaLoaiMon(int maLoaiMon) {
        this.maLoaiMon = maLoaiMon;
    }

    public String getTenLoaiMon() {
        return tenLoaiMon;
    }

    public void setTenLoaiMon(String tenLoaiMon) {
        this.tenLoaiMon = tenLoaiMon;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
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
    public boolean themLoaiMon() {
        String sql = "INSERT INTO LoaiMon (MaLoaiMon, TenLoaiMon, MoTa) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maLoaiMon);
            ps.setString(2, tenLoaiMon);
            ps.setString(3, moTa);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean capNhatLoaiMon() {
        String sql = "UPDATE LoaiMon SET TenLoaiMon = ?, MoTa = ?, ThoiGianCapNhat = GETDATE() WHERE MaLoaiMon = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, tenLoaiMon);
            ps.setString(2, moTa);
            ps.setInt(3, maLoaiMon);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean xoaLoaiMon() {
        String sql = "DELETE FROM LoaiMon WHERE MaLoaiMon = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maLoaiMon);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public LoaiMon timLoaiMonTheoMa(int maLoaiMon) {
        String sql = "SELECT * FROM LoaiMon WHERE MaLoaiMon = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maLoaiMon);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                LoaiMon loaiMon = new LoaiMon();
                loaiMon.setMaLoaiMon(rs.getInt("MaLoaiMon"));
                loaiMon.setTenLoaiMon(rs.getString("TenLoaiMon"));
                loaiMon.setMoTa(rs.getString("MoTa"));
                loaiMon.setThoiGianTao(rs.getString("ThoiGianTao"));
                loaiMon.setThoiGianCapNhat(rs.getString("ThoiGianCapNhat"));
                return loaiMon;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public LoaiMon timLoaiMonTheoTen(String tenLoaiMon) {
        String sql = "SELECT * FROM LoaiMon WHERE TenLoaiMon = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, tenLoaiMon);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                LoaiMon loaiMon = new LoaiMon();
                loaiMon.setMaLoaiMon(rs.getInt("MaLoaiMon"));
                loaiMon.setTenLoaiMon(rs.getString("TenLoaiMon"));
                loaiMon.setMoTa(rs.getString("MoTa"));
                loaiMon.setThoiGianTao(rs.getString("ThoiGianTao"));
                loaiMon.setThoiGianCapNhat(rs.getString("ThoiGianCapNhat"));
                return loaiMon;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<LoaiMon> layTatCaLoaiMon() {
        List<LoaiMon> danhSach = new ArrayList<>();
        String sql = "SELECT * FROM LoaiMon";
        try (PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                LoaiMon loaiMon = new LoaiMon();
                loaiMon.setMaLoaiMon(rs.getInt("MaLoaiMon"));
                loaiMon.setTenLoaiMon(rs.getString("TenLoaiMon"));
                loaiMon.setMoTa(rs.getString("MoTa"));
                loaiMon.setThoiGianTao(rs.getString("ThoiGianTao"));
                loaiMon.setThoiGianCapNhat(rs.getString("ThoiGianCapNhat"));
                danhSach.add(loaiMon);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return danhSach;
    }
}
