package com.project.app.dao;

import com.project.app.model.Account;

import java.sql.SQLException;

public interface AccountDAO {
    public boolean create(Account account);
    public Account findById(int id);
    public Account findByUsername(String username) throws SQLException;
}
