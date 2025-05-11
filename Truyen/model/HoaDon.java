package javaProject.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javaProject.DatabaseConnection.BaseDAO;

public class HoaDon extends BaseDAO {
    private int maHoaDon;
    private int maBan;
    private int maNhanVien;
    private int maKhachHang;
    private String thoiGianDat;
    private String thoiGianThanhToan;
    private double tongTienTruocGiam;
    private double soTienGiam;
    private double tongTienPhaiTra;
    private String trangThai;
    private String ghiChu;
    private String thoiGianTao;
    private String thoiGianCapNhat;

    public HoaDon() {
        super();
    }

    // Getters and Setters
    public int getMaHoaDon() { return maHoaDon; }
    public void setMaHoaDon(int maHoaDon) { this.maHoaDon = maHoaDon; }
    public int getMaBan() { return maBan; }
    public void setMaBan(int maBan) { this.maBan = maBan; }
    public int getMaNhanVien() { return maNhanVien; }
    public void setMaNhanVien(int maNhanVien) { this.maNhanVien = maNhanVien; }
    public int getMaKhachHang() { return maKhachHang; }
    public void setMaKhachHang(int maKhachHang) { this.maKhachHang = maKhachHang; }
    public String getThoiGianDat() { return thoiGianDat; }
    public void setThoiGianDat(String thoiGianDat) { this.thoiGianDat = thoiGianDat; }
    public String getThoiGianThanhToan() { return thoiGianThanhToan; }
    public void setThoiGianThanhToan(String thoiGianThanhToan) { this.thoiGianThanhToan = thoiGianThanhToan; }
    public double getTongTienTruocGiam() { return tongTienTruocGiam; }
    public void setTongTienTruocGiam(double tongTienTruocGiam) { this.tongTienTruocGiam = tongTienTruocGiam; }
    public double getSoTienGiam() { return soTienGiam; }
    public void setSoTienGiam(double soTienGiam) { this.soTienGiam = soTienGiam; }
    public double getTongTienPhaiTra() { return tongTienPhaiTra; }
    public void setTongTienPhaiTra(double tongTienPhaiTra) { this.tongTienPhaiTra = tongTienPhaiTra; }
    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }
    public String getGhiChu() { return ghiChu; }
    public void setGhiChu(String ghiChu) { this.ghiChu = ghiChu; }
    public String getThoiGianTao() { return thoiGianTao; }
    public void setThoiGianTao(String thoiGianTao) { this.thoiGianTao = thoiGianTao; }
    public String getThoiGianCapNhat() { return thoiGianCapNhat; }
    public void setThoiGianCapNhat(String thoiGianCapNhat) { this.thoiGianCapNhat = thoiGianCapNhat; }

    // CRUD Operations
    public boolean themHoaDon() {
        String sql = "INSERT INTO HoaDon (MaHoaDon, MaBan, MaNhanVien, MaKhachHang, ThoiGianDat, " +
                    "TongTienTruocGiam, SoTienGiam, TongTienPhaiTra, TrangThai, GhiChu) " +
                    "VALUES (?, ?, ?, ?, GETDATE(), ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maHoaDon);
            ps.setInt(2, maBan);
            ps.setInt(3, maNhanVien);
            ps.setInt(4, maKhachHang);
            ps.setDouble(5, tongTienTruocGiam);
            ps.setDouble(6, soTienGiam);
            ps.setDouble(7, tongTienPhaiTra);
            ps.setString(8, trangThai);
            ps.setString(9, ghiChu);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean capNhatHoaDon() {
        String sql = "UPDATE HoaDon SET MaBan = ?, MaNhanVien = ?, MaKhachHang = ?, " +
                    "TongTienTruocGiam = ?, SoTienGiam = ?, TongTienPhaiTra = ?, " +
                    "TrangThai = ?, GhiChu = ?, ThoiGianCapNhat = GETDATE() WHERE MaHoaDon = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maBan);
            ps.setInt(2, maNhanVien);
            ps.setInt(3, maKhachHang);
            ps.setDouble(4, tongTienTruocGiam);
            ps.setDouble(5, soTienGiam);
            ps.setDouble(6, tongTienPhaiTra);
            ps.setString(7, trangThai);
            ps.setString(8, ghiChu);
            ps.setInt(9, maHoaDon);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean thanhToanHoaDon() {
        String sql = "UPDATE HoaDon SET ThoiGianThanhToan = GETDATE(), TrangThai = 'Đã thanh toán', " +
                    "ThoiGianCapNhat = GETDATE() WHERE MaHoaDon = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maHoaDon);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean huyHoaDon() {
        String sql = "UPDATE HoaDon SET TrangThai = 'Đã hủy', ThoiGianCapNhat = GETDATE() WHERE MaHoaDon = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maHoaDon);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public HoaDon timHoaDonTheoMa(int maHoaDon) {
        String sql = "SELECT * FROM HoaDon WHERE MaHoaDon = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maHoaDon);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                HoaDon hd = new HoaDon();
                hd.setMaHoaDon(rs.getInt("MaHoaDon"));
                hd.setMaBan(rs.getInt("MaBan"));
                hd.setMaNhanVien(rs.getInt("MaNhanVien"));
                hd.setMaKhachHang(rs.getInt("MaKhachHang"));
                hd.setThoiGianDat(rs.getString("ThoiGianDat"));
                hd.setThoiGianThanhToan(rs.getString("ThoiGianThanhToan"));
                hd.setTongTienTruocGiam(rs.getDouble("TongTienTruocGiam"));
                hd.setSoTienGiam(rs.getDouble("SoTienGiam"));
                hd.setTongTienPhaiTra(rs.getDouble("TongTienPhaiTra"));
                hd.setTrangThai(rs.getString("TrangThai"));
                hd.setGhiChu(rs.getString("GhiChu"));
                hd.setThoiGianTao(rs.getString("ThoiGianTao"));
                hd.setThoiGianCapNhat(rs.getString("ThoiGianCapNhat"));
                return hd;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<HoaDon> layTatCaHoaDon() {
        List<HoaDon> danhSach = new ArrayList<>();
        String sql = "SELECT * FROM HoaDon ORDER BY ThoiGianDat DESC";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                HoaDon hd = new HoaDon();
                hd.setMaHoaDon(rs.getInt("MaHoaDon"));
                hd.setMaBan(rs.getInt("MaBan"));
                hd.setMaNhanVien(rs.getInt("MaNhanVien"));
                hd.setMaKhachHang(rs.getInt("MaKhachHang"));
                hd.setThoiGianDat(rs.getString("ThoiGianDat"));
                hd.setThoiGianThanhToan(rs.getString("ThoiGianThanhToan"));
                hd.setTongTienTruocGiam(rs.getDouble("TongTienTruocGiam"));
                hd.setSoTienGiam(rs.getDouble("SoTienGiam"));
                hd.setTongTienPhaiTra(rs.getDouble("TongTienPhaiTra"));
                hd.setTrangThai(rs.getString("TrangThai"));
                hd.setGhiChu(rs.getString("GhiChu"));
                hd.setThoiGianTao(rs.getString("ThoiGianTao"));
                hd.setThoiGianCapNhat(rs.getString("ThoiGianCapNhat"));
                danhSach.add(hd);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return danhSach;
    }

    public List<HoaDon> timHoaDonTheoTrangThai(String trangThai) {
        List<HoaDon> danhSach = new ArrayList<>();
        String sql = "SELECT * FROM HoaDon WHERE TrangThai = ? ORDER BY ThoiGianDat DESC";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, trangThai);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                HoaDon hd = new HoaDon();
                hd.setMaHoaDon(rs.getInt("MaHoaDon"));
                hd.setMaBan(rs.getInt("MaBan"));
                hd.setMaNhanVien(rs.getInt("MaNhanVien"));
                hd.setMaKhachHang(rs.getInt("MaKhachHang"));
                hd.setThoiGianDat(rs.getString("ThoiGianDat"));
                hd.setThoiGianThanhToan(rs.getString("ThoiGianThanhToan"));
                hd.setTongTienTruocGiam(rs.getDouble("TongTienTruocGiam"));
                hd.setSoTienGiam(rs.getDouble("SoTienGiam"));
                hd.setTongTienPhaiTra(rs.getDouble("TongTienPhaiTra"));
                hd.setTrangThai(rs.getString("TrangThai"));
                hd.setGhiChu(rs.getString("GhiChu"));
                hd.setThoiGianTao(rs.getString("ThoiGianTao"));
                hd.setThoiGianCapNhat(rs.getString("ThoiGianCapNhat"));
                danhSach.add(hd);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return danhSach;
    }
} 