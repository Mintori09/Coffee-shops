package com.project.app.dao.impl;

import com.project.app.dao.BillDetailDAO;
import com.project.app.model.BillDetail;

import java.util.List;

public class BillDetailDAOImpl implements BillDetailDAO {
    @Override
    public boolean create(BillDetail billDetail) {
        String sql = "INSERT INTO bill_details (bill_id, drink_id, quantity, price, notes, created_date) VALUES (?, ?, ?, ?, ?, ?)";
        try (java.sql.Connection conn = com.project.app.database.DatabaseConnection.getConnection();
             java.sql.PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, billDetail.getBillId());
            stmt.setInt(2, billDetail.getDrinkId()); // Assuming getDrinkId() is the correct getter for drinkId
            stmt.setInt(3, billDetail.getQuantity());
            stmt.setDouble(4, billDetail.getPrice());
            stmt.setString(5, billDetail.getNotes());
            stmt.setTimestamp(6, java.sql.Timestamp.valueOf(billDetail.getCreatedDate()));
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public BillDetail findByBillIdAndDrinkId(int billId, int drinkId) {
        // TODO: Implement findByBillIdAndDrinkId
        return null;
    }

    @Override
    public java.util.List<BillDetail> getBillDetailsByBillId(int billId) {
        java.util.List<BillDetail> billDetails = new java.util.ArrayList<>();
        String sql = "SELECT bill_id, drink_id, quantity, price, notes, created_date FROM bill_details WHERE bill_id = ?";

        try (java.sql.Connection conn = com.project.app.database.DatabaseConnection.getConnection();
             java.sql.PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, billId);

            try (java.sql.ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    BillDetail billDetail = new BillDetail();
                    billDetail.setBillId(rs.getInt("bill_id"));
                    billDetail.setDrinkId(rs.getInt("drink_id"));
                    billDetail.setQuantity(rs.getInt("quantity"));
                    billDetail.setPrice(rs.getDouble("price"));
                    billDetail.setNotes(rs.getString("notes"));
                    java.sql.Timestamp createdDateSql = rs.getTimestamp("created_date");
                    if (createdDateSql != null) {
                        billDetail.setCreatedDate(createdDateSql.toLocalDateTime());
                    }
                    billDetails.add(billDetail);
                }
            }
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
        return billDetails;
    }

    @Override
    public boolean updateBillDetail(BillDetail billDetail) {
        String sql = "UPDATE bill_details SET bill_id = ?, drink_id = ?, quantity = ?, price = ?, notes = ?, created_date = ? WHERE bill_detail_id = ?";
        try (java.sql.Connection conn = com.project.app.database.DatabaseConnection.getConnection();
             java.sql.PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, billDetail.getBillId());
            stmt.setInt(2, billDetail.getDrinkId());
            stmt.setInt(3, billDetail.getQuantity());
            stmt.setDouble(4, billDetail.getPrice());
            stmt.setString(5, billDetail.getNotes());
            stmt.setTimestamp(6, java.sql.Timestamp.valueOf(billDetail.getCreatedDate()));
            stmt.setInt(7, billDetail.getBillDetailId());
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void deleteAll() throws java.sql.SQLException {
        String sql = "DELETE FROM bill_details";
        java.sql.Connection conn = com.project.app.database.DatabaseConnection.getConnection();
        try (java.sql.PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.executeUpdate();
        }

        // Reset auto-increment counter
        String resetSql = "ALTER TABLE bill_details AUTO_INCREMENT = 1";
        try (java.sql.PreparedStatement ps = conn.prepareStatement(resetSql)) {
            ps.executeUpdate();
        }
    }

    public static void main(String[] args) {
        BillDetailDAOImpl impl = new BillDetailDAOImpl();
        List<BillDetail> bill = impl.getBillDetailsByBillId(7);
        for (BillDetail billDetail : bill) {
            System.out.println(billDetail);
        }
    }
}
