package com.project.app.dao;

import com.project.app.model.Account;

import java.sql.SQLException;

public interface AccountDAO {
    public int create(Account account) throws SQLException;
    public Account findById(int id) throws SQLException;
    public Account findByUsername(String username) throws SQLException;
    public boolean updateAccount(Account account) throws SQLException;
    public boolean deleteAccountById(int accountId) throws SQLException;
}
