package com.project.app.dao.impl;

import com.project.app.dao.BillDAO;
import com.project.app.model.Bill;

public class BillDAOImpl implements BillDAO {

    @Override
    public boolean create(Bill bill) {
        String sql = "INSERT INTO bills (order_date, total_price, notes, created_date, employee_id) VALUES (?, ?, ?, ?, ?)";
        try (java.sql.Connection conn = com.project.app.database.DatabaseConnection.getConnection();
             java.sql.PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, java.sql.Date.valueOf(bill.getOrderDate()));
            stmt.setDouble(2, bill.getTotalPrice());
            stmt.setString(3, bill.getNotes());
            stmt.setTimestamp(4, java.sql.Timestamp.valueOf(bill.getCreatedDate()));
            stmt.setInt(5, bill.getEmployeeId());
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Bill findById(int billId) {
        return null;
    }
}
