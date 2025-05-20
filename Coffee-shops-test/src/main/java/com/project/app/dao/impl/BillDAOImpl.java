package com.project.app.dao.impl;

import com.project.app.dao.BillDAO;
import com.project.app.database.DatabaseConnection;
import com.project.app.model.Bill;

import java.sql.*;

public class BillDAOImpl implements BillDAO {

    @Override
    public int create(Bill bill) { // Change return type to int
        String sql = "INSERT INTO bills (order_date, total_price, notes, created_date, employee_id) VALUES (?, ?, ?, ?, ?)";
        try (java.sql.Connection conn = com.project.app.database.DatabaseConnection.getConnection();
             // Specify Statement.RETURN_GENERATED_KEYS to get the generated ID
             java.sql.PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            if (bill.getOrderDate() != null) {
                stmt.setDate(1, java.sql.Date.valueOf(bill.getOrderDate()));
            } else {
                stmt.setNull(1, java.sql.Types.DATE);
            }
            stmt.setDouble(2, bill.getTotalPrice());
            stmt.setString(3, bill.getNotes());
            stmt.setTimestamp(4, java.sql.Timestamp.valueOf(bill.getCreatedDate()));
            stmt.setInt(5, bill.getEmployeeId());
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                // Retrieve the generated ID
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1); // Return the generated ID
                    }
                }
            }
            return -1; // Return -1 if insertion failed or no ID generated
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
            return -1; // Return -1 on error
        }
    }

    @Override
    public Bill findById(int billId) {
        Bill bill = new Bill();
        String sql = "SELECT * FROM bills WHERE bill_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
        ) {
            ps.setInt(1, billId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                bill.setBillId(rs.getInt("bill_id"));
                // Assuming order_date is stored as DATE in DB
                java.sql.Date orderDateSql = rs.getDate("order_date");
                if (orderDateSql != null) {
                    bill.setOrderDate(orderDateSql.toLocalDate());
                }
                bill.setTotalPrice(rs.getDouble("total_price"));
                bill.setNotes(rs.getString("notes"));
                // Assuming created_date is stored as TIMESTAMP in DB
                java.sql.Timestamp createdDateSql = rs.getTimestamp("created_date");
                if (createdDateSql != null) {
                    bill.setCreatedDate(createdDateSql.toLocalDateTime());
                }
                bill.setEmployeeId(rs.getInt("employee_id"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return bill;
    }

    @Override
    public java.util.List<Bill> getAllBills() {
        java.util.List<Bill> bills = new java.util.ArrayList<>();
        String sql = "SELECT bill_id, order_date, total_price, notes, created_date, employee_id FROM bills";

        try (java.sql.Connection conn = com.project.app.database.DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Bill bill = new Bill();
                bill.setBillId(rs.getInt("bill_id"));
                // Assuming order_date is stored as DATE in DB
                java.sql.Date orderDateSql = rs.getDate("order_date");
                if (orderDateSql != null) {
                    bill.setOrderDate(orderDateSql.toLocalDate());
                }
                bill.setTotalPrice(rs.getDouble("total_price"));
                bill.setNotes(rs.getString("notes"));
                // Assuming created_date is stored as TIMESTAMP in DB
                java.sql.Timestamp createdDateSql = rs.getTimestamp("created_date");
                if (createdDateSql != null) {
                    bill.setCreatedDate(createdDateSql.toLocalDateTime());
                }
                bill.setEmployeeId(rs.getInt("employee_id"));
                bills.add(bill);
            }
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
        return bills;
    }

    @Override
    public boolean updateBill(Bill bill) {
        String sql = "UPDATE bills SET order_date = ?, total_price = ?, notes = ?, created_date = ?, employee_id = ? WHERE bill_id = ?";
        try (java.sql.Connection conn = com.project.app.database.DatabaseConnection.getConnection();
             java.sql.PreparedStatement stmt = conn.prepareStatement(sql)) {
            if (bill.getOrderDate() != null) {
                stmt.setDate(1, java.sql.Date.valueOf(bill.getOrderDate()));
            } else {
                stmt.setNull(1, java.sql.Types.DATE);
            }
            stmt.setDouble(2, bill.getTotalPrice());
            stmt.setString(3, bill.getNotes());
            stmt.setTimestamp(4, java.sql.Timestamp.valueOf(bill.getCreatedDate()));
            stmt.setInt(5, bill.getEmployeeId());
            stmt.setInt(6, bill.getBillId());
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void deleteAll() throws SQLException {
        String sql = "DELETE FROM bills";
        Connection conn = DatabaseConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.executeUpdate();
        }

        // Reset auto-increment counter
        String resetSql = "ALTER TABLE bills AUTO_INCREMENT = 1";
        try (PreparedStatement ps = conn.prepareStatement(resetSql)) {
            ps.executeUpdate();
        }
    }
}
