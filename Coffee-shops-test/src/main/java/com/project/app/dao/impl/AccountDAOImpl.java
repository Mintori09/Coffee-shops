package com.project.app.dao.impl;

import com.project.app.dao.AccountDAO;
import com.project.app.database.DatabaseConnection;
import com.project.app.model.Account;

import java.sql.*;

public class AccountDAOImpl implements AccountDAO {
    @Override
    public boolean create(Account account) throws SQLException {
        String sql = "INSERT INTO accounts (username, password, role) VALUES (?, ?, ?)";
        Connection conn = DatabaseConnection.getConnection();
        try(PreparedStatement ps =  conn.prepareStatement(sql)) {
            ps.setString(1, account.getUsername());
            ps.setString(2, account.getPassword());
            ps.setString(3, account.getRole());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Account findById(int id) {
        return null;
    }
    @Override
    public Account findByUsername(String username) throws SQLException {
        String sql = "select * from accounts where username=?";
        Connection conn =  DatabaseConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            Account account = new Account();
            if (rs.next()) {
                account.setId(rs.getInt("id"));
                account.setUsername(rs.getString("username"));
                account.setPassword(rs.getString("password"));
                account.setRole(rs.getString("role"));
            }
            System.out.println(account);
            return account;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void deleteAll() throws SQLException {
        String sql = "DELETE FROM accounts";
        Connection conn = DatabaseConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.executeUpdate();
        }

        // Reset auto-increment counter
        String resetSql = "ALTER TABLE accounts AUTO_INCREMENT = 1";
        try (PreparedStatement ps = conn.prepareStatement(resetSql)) {
            ps.executeUpdate();
        }
    }
}
