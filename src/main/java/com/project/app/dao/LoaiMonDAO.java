package com.project.app.dao;

import com.project.app.model.LoaiMon;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class LoaiMonDAO extends BaseDAO {

    public List<LoaiMon> getAllLoaiMon() throws SQLException {
        List<LoaiMon> loaiMonList = new ArrayList<>();
        String sql = "SELECT * FROM LoaiMon";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                LoaiMon lm = new LoaiMon(
                    rs.getInt("MaLoaiMon"),
                    rs.getString("TenLoaiMon"),
                    rs.getString("MoTa"),
                    rs.getString("PhanLoai"),
                    rs.getString("HinhAnh")
                );
                lm.setThoiGianTao(rs.getTimestamp("ThoiGianTao").toLocalDateTime());
                lm.setThoiGianCapNhat(rs.getTimestamp("ThoiGianCapNhat").toLocalDateTime());
                loaiMonList.add(lm);
            }
        }
        return loaiMonList;
    }

    public LoaiMon getLoaiMonById(int id) throws SQLException {
        String sql = "SELECT * FROM LoaiMon WHERE MaLoaiMon = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                LoaiMon lm = new LoaiMon(
                    rs.getInt("MaLoaiMon"),
                    rs.getString("TenLoaiMon"),
                    rs.getString("MoTa"),
                    rs.getString("PhanLoai"),
                    rs.getString("HinhAnh")
                );
                lm.setThoiGianTao(rs.getTimestamp("ThoiGianTao").toLocalDateTime());
                lm.setThoiGianCapNhat(rs.getTimestamp("ThoiGianCapNhat").toLocalDateTime());
                return lm;
            }
        }
        return null;
    }

    public boolean addLoaiMon(LoaiMon loai) throws SQLException {
        String sql = "INSERT INTO LoaiMon (TenLoaiMon, MoTa, PhanLoai, HinhAnh) VALUES (?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, loai.getTenLoaiMon());
            pstmt.setString(2, loai.getMoTa());
            pstmt.setString(3, loai.getPhanLoai());
            pstmt.setString(4, loai.getHinhAnh());

            return pstmt.executeUpdate() > 0;
        }
    }

    public boolean updateLoaiMon(LoaiMon loai) throws SQLException {
        String sql = "UPDATE LoaiMon SET TenLoaiMon = ?, MoTa = ?, PhanLoai = ?, HinhAnh = ? WHERE MaLoaiMon = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, loai.getTenLoaiMon());
            pstmt.setString(2, loai.getMoTa());
            pstmt.setString(3, loai.getPhanLoai());
            pstmt.setString(4, loai.getHinhAnh());
            pstmt.setInt(5, loai.getMaLoaiMon());

            return pstmt.executeUpdate() > 0;
        }
    }

    public boolean deleteLoaiMon(int id) throws SQLException {
        String sql = "DELETE FROM LoaiMon WHERE MaLoaiMon = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        }
    }
}
