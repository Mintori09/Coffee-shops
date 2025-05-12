package com.project.app.dao;

import com.project.app.model.ChiTietHoaDon;
import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.project.app.database.DatabaseConnection.connection;

public class ChiTietHoaDonDAO extends BaseDAO {

    public List<ChiTietHoaDon> getAllChiTietHD() throws SQLException {
        List<ChiTietHoaDon> chiTietList = new ArrayList<>();
        String sql = "SELECT * FROM ChiTietHoaDon";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                ChiTietHoaDon ct = new ChiTietHoaDon(
                    rs.getInt("MaHoaDon"),
                    rs.getInt("MaMon"),
                    rs.getInt("SoLuong"),
                    rs.getBigDecimal("DonGiaLucDat"),
                    rs.getBigDecimal("ThanhTien"),
                    rs.getString("GhiChuMon")
                );
                ct.setMaChiTietHD(rs.getInt("MaChiTietHD"));
                ct.setThoiGianTao(rs.getTimestamp("ThoiGianTao").toLocalDateTime());
                chiTietList.add(ct);
            }
        }
        return chiTietList;
    }

    public ChiTietHoaDon getChiTietHDById(int id) throws SQLException {
        String sql = "SELECT * FROM ChiTietHoaDon WHERE MaChiTietHD = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                ChiTietHoaDon ct = new ChiTietHoaDon(
                    rs.getInt("MaHoaDon"),
                    rs.getInt("MaMon"),
                    rs.getInt("SoLuong"),
                    rs.getBigDecimal("DonGiaLucDat"),
                    rs.getBigDecimal("ThanhTien"),
                    rs.getString("GhiChuMon")
                );
                ct.setMaChiTietHD(rs.getInt("MaChiTietHD"));
                ct.setThoiGianTao(rs.getTimestamp("ThoiGianTao").toLocalDateTime());
                return ct;
            }
        }
        return null;
    }

    public boolean addChiTietHD(ChiTietHoaDon ct) throws SQLException {
        String sql = "INSERT INTO ChiTietHoaDon (MaHoaDon, MaMon, SoLuong, DonGiaLucDat, ThanhTien, GhiChuMon) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, ct.getMaHoaDon());
            pstmt.setInt(2, ct.getMaMon());
            pstmt.setInt(3, ct.getSoLuong());
            pstmt.setBigDecimal(4, ct.getDonGiaLucDat());
            pstmt.setBigDecimal(5, ct.getThanhTien());
            pstmt.setString(6, ct.getGhiChuMon());

            return pstmt.executeUpdate() > 0;
        }
    }

    public boolean updateChiTietHD(ChiTietHoaDon ct) throws SQLException {
        String sql = "UPDATE ChiTietHoaDon SET MaHoaDon = ?, MaMon = ?, SoLuong = ?, DonGiaLucDat = ?, ThanhTien = ?, GhiChuMon = ? WHERE MaChiTietHD = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, ct.getMaHoaDon());
            pstmt.setInt(2, ct.getMaMon());
            pstmt.setInt(3, ct.getSoLuong());
            pstmt.setBigDecimal(4, ct.getDonGiaLucDat());
            pstmt.setBigDecimal(5, ct.getThanhTien());
            pstmt.setString(6, ct.getGhiChuMon());
            pstmt.setInt(7, ct.getMaChiTietHD());

            return pstmt.executeUpdate() > 0;
        }
    }

    public boolean deleteChiTietHD(int id) throws SQLException {
        String sql = "DELETE FROM ChiTietHoaDon WHERE MaChiTietHD = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        }
    }
}
