package com.project.app.dao.impl;

import com.project.app.dao.AccountDAO;
import com.project.app.database.DatabaseConnection;
import com.project.app.model.Account;

import java.sql.*;

public class AccountDAOImpl implements AccountDAO {
    @Override
    public int create(Account account) throws SQLException {
        String sql = "INSERT INTO accounts (username, password, role) VALUES (?, ?, ?)";
        Connection conn = DatabaseConnection.getConnection();
        int generatedId = -1;
        try(PreparedStatement ps =  conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, account.getUsername());
            ps.setString(2, account.getPassword());
            ps.setString(3, account.getRole());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                generatedId = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e; // Re-throw the exception to be handled by the caller
        }
        return generatedId;
    }

    @Override
    public Account findById(int id) throws SQLException {
        String sql = "SELECT * FROM accounts WHERE id = ?";
        Account account = null;
        Connection conn = DatabaseConnection.getConnection();
        try(PreparedStatement ps =  conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                account = new Account();
                account.setId(rs.getInt("id"));
                account.setUsername(rs.getString("username"));
                account.setPassword(rs.getString("password"));
                account.setRole(rs.getString("role"));
            }
        }
        return account;
    }
    @Override
    public Account findByUsername(String username) throws SQLException {
        String sql = "SELECT * FROM accounts WHERE username=?";
        Connection conn = DatabaseConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Account account = new Account();
                account.setId(rs.getInt("id"));
                account.setUsername(rs.getString("username"));
                account.setPassword(rs.getString("password"));
                account.setRole(rs.getString("role"));
                return account;
            } else {
                return null;
            }
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

    @Override
    public boolean updateAccount(Account account) throws SQLException {
        String sql = "UPDATE accounts SET username = ?, password = ?, role = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, account.getUsername());
            stmt.setString(2, account.getPassword());
            stmt.setString(3, account.getRole());
            stmt.setInt(4, account.getId());
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public boolean deleteAccountById(int accountId) throws SQLException {
        String sql = "DELETE FROM accounts WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, accountId);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }
}
