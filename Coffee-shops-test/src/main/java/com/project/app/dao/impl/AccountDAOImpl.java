package com.project.app.dao.impl;

import com.project.app.dao.AccountDAO;
import com.project.app.database.DatabaseConnection;
import com.project.app.model.Account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountDAOImpl implements AccountDAO {
    @Override
    public Account findById() {
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
}
