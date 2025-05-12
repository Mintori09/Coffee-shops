package com.project.app.dao;

import com.project.app.model.HoaDon;
import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class HoaDonDAO extends BaseDAO {

    public List<HoaDon> getAllHoaDon() throws SQLException {
        List<HoaDon> hoaDonList = new ArrayList<>();
        String sql = "SELECT h.*, n.TenNhanVien FROM HoaDon h LEFT JOIN NhanVien n ON h.MaNhanVien = n.MaNhanVien";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                HoaDon hd = new HoaDon(
                    rs.getInt("MaHoaDon"),
                    rs.getString("MaNhanVien"),
                    rs.getTimestamp("ThoiGianThanhToan") != null ? 
                        rs.getTimestamp("ThoiGianThanhToan").toLocalDateTime() : null,
                    rs.getBigDecimal("TongTienPhaiTra"),
                    rs.getString("TrangThai"),
                    rs.getString("GhiChu")
                );
                hd.setThoiGianTao(rs.getTimestamp("ThoiGianTao").toLocalDateTime());
                hd.setTenNhanVien(rs.getString("TenNhanVien"));
                hoaDonList.add(hd);
            }
        }
        return hoaDonList;
    }

    public HoaDon getHoaDonById(int maHoaDon) throws SQLException {
        String sql = "SELECT h.*, n.TenNhanVien FROM HoaDon h LEFT JOIN NhanVien n ON h.MaNhanVien = n.MaNhanVien WHERE h.MaHoaDon = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, maHoaDon);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                HoaDon hd = new HoaDon(
                    rs.getInt("MaHoaDon"),
                    rs.getString("MaNhanVien"),
                    rs.getTimestamp("ThoiGianThanhToan") != null ? 
                        rs.getTimestamp("ThoiGianThanhToan").toLocalDateTime() : null,
                    rs.getBigDecimal("TongTienPhaiTra"),
                    rs.getString("TrangThai"),
                    rs.getString("GhiChu")
                );
                hd.setThoiGianTao(rs.getTimestamp("ThoiGianTao").toLocalDateTime());
                hd.setTenNhanVien(rs.getString("TenNhanVien"));
                return hd;
            }
        }
        return null;
    }

    public boolean addHoaDon(HoaDon hd) throws SQLException {
        String sql = "INSERT INTO HoaDon (MaHoaDon, MaNhanVien, ThoiGianThanhToan, TongTienPhaiTra, TrangThai, GhiChu) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, hd.getMaHoaDon());
            pstmt.setString(2, hd.getMaNhanVien());
            pstmt.setTimestamp(3, hd.getThoiGianThanhToan() != null ? 
                Timestamp.valueOf(hd.getThoiGianThanhToan()) : null);
            pstmt.setBigDecimal(4, hd.getTongTienPhaiTra());
            pstmt.setString(5, hd.getTrangThai());
            pstmt.setString(6, hd.getGhiChu());

            return pstmt.executeUpdate() > 0;
        }
    }

    public boolean updateHoaDon(HoaDon hd) throws SQLException {
        String sql = "UPDATE HoaDon SET MaNhanVien = ?, ThoiGianThanhToan = ?, TongTienPhaiTra = ?, TrangThai = ?, GhiChu = ? WHERE MaHoaDon = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, hd.getMaNhanVien());
            pstmt.setTimestamp(2, hd.getThoiGianThanhToan() != null ? 
                Timestamp.valueOf(hd.getThoiGianThanhToan()) : null);
            pstmt.setBigDecimal(3, hd.getTongTienPhaiTra());
            pstmt.setString(4, hd.getTrangThai());
            pstmt.setString(5, hd.getGhiChu());
            pstmt.setInt(6, hd.getMaHoaDon());

            return pstmt.executeUpdate() > 0;
        }
    }

    public boolean deleteHoaDon(int maHoaDon) throws SQLException {
        String sql = "DELETE FROM HoaDon WHERE MaHoaDon = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, maHoaDon);
            return pstmt.executeUpdate() > 0;
        }
    }

    public List<HoaDon> getHoaDonByNhanVien(String maNhanVien) throws SQLException {
        List<HoaDon> hoaDonList = new ArrayList<>();
        String sql = "SELECT h.*, n.TenNhanVien FROM HoaDon h LEFT JOIN NhanVien n ON h.MaNhanVien = n.MaNhanVien WHERE h.MaNhanVien = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, maNhanVien);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                HoaDon hd = new HoaDon(
                    rs.getInt("MaHoaDon"),
                    rs.getString("MaNhanVien"),
                    rs.getTimestamp("ThoiGianThanhToan") != null ? 
                        rs.getTimestamp("ThoiGianThanhToan").toLocalDateTime() : null,
                    rs.getBigDecimal("TongTienPhaiTra"),
                    rs.getString("TrangThai"),
                    rs.getString("GhiChu")
                );
                hd.setThoiGianTao(rs.getTimestamp("ThoiGianTao").toLocalDateTime());
                hd.setTenNhanVien(rs.getString("TenNhanVien"));
                hoaDonList.add(hd);
            }
        }
        return hoaDonList;
    }
}
