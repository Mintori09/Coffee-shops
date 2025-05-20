package com.project.app.dao.impl;

import com.project.app.dao.BillDetailDAO;
import com.project.app.model.BillDetail;

public class BillDetailDAOImpl implements BillDetailDAO {
    @Override
    public boolean create(BillDetail billDetail) {
        String sql = "INSERT INTO bill_details (bill_id, drink_id, quantity, price, notes, created_date) VALUES (?, ?, ?, ?, ?, ?)";
        try (java.sql.Connection conn = com.project.app.database.DatabaseConnection.getConnection();
             java.sql.PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, billDetail.getBillId());
            stmt.setString(2, billDetail.getDrinkId()); // Assuming getDrinkId() is the correct getter for drinkId
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
        return null;
    }
}
