package com.project.app.dao;

import com.project.app.model.NhanVien;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.project.app.database.DatabaseConnection.connection;

public class NhanVienDAO extends BaseDAO {
    
    public List<NhanVien> getAllNhanVien() throws SQLException {
        List<NhanVien> nhanVienList = new ArrayList<>();
        String sql = "SELECT * FROM NhanVien";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                NhanVien nv = new NhanVien(
                    rs.getString("MaNhanVien"),
                    rs.getString("TenNhanVien"),
                    rs.getString("ChucVu"),
                    rs.getString("SoDienThoai"),
                    rs.getString("Email")
                );
                nv.setThoiGianTao(rs.getTimestamp("ThoiGianTao").toLocalDateTime());
                nv.setThoiGianCapNhat(rs.getTimestamp("ThoiGianCapNhat").toLocalDateTime());
                nhanVienList.add(nv);
            }
        }
        return nhanVienList;
    }

    public NhanVien getNhanVienById(int id) throws SQLException {
        String sql = "SELECT * FROM NhanVien WHERE MaNhanVien = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                NhanVien nv = new NhanVien(
                    rs.getString("MaNhanVien"),
                    rs.getString("TenNhanVien"),
                    rs.getString("ChucVu"),
                    rs.getString("SoDienThoai"),
                    rs.getString("Email")
                );
                nv.setThoiGianTao(rs.getTimestamp("ThoiGianTao").toLocalDateTime());
                nv.setThoiGianCapNhat(rs.getTimestamp("ThoiGianCapNhat").toLocalDateTime());
                return nv;
            }
        }
        return null;
    }

    public boolean addNhanVien(NhanVien nv) throws SQLException {
        String sql = "INSERT INTO NhanVien (MaNhanVien, TenNhanVien, SoDienThoai, Email, ChucVu) VALUES (?, ?, ?, ?, ?)";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, nv.getMaNhanVien());
            pstmt.setString(2, nv.getTenNhanVien());
            pstmt.setString(3, nv.getSoDienThoai());
            pstmt.setString(4, nv.getEmail());
            pstmt.setString(5, nv.getChucVu());
            
            return pstmt.executeUpdate() > 0;
        }
    }

    public boolean updateNhanVien(NhanVien nv) throws SQLException {
        String sql = "UPDATE NhanVien SET TenNhanVien = ?, SoDienThoai = ?, Email = ?, ChucVu = ?, ThoiGianCapNhat = CURRENT_TIMESTAMP WHERE MaNhanVien = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, nv.getTenNhanVien());
            pstmt.setString(2, nv.getSoDienThoai());
            pstmt.setString(3, nv.getEmail());
            pstmt.setString(4, nv.getChucVu());
            pstmt.setString(5, nv.getMaNhanVien());
            
            return pstmt.executeUpdate() > 0;
        }
    }

    public boolean deleteNhanVien(int id) throws SQLException {
        String sql = "DELETE FROM NhanVien WHERE MaNhanVien = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        }
    }
}
