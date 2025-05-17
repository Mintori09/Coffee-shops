package com.project.app.dao;

import com.project.app.model.Account;

import java.sql.SQLException;

public interface AccountDAO {
    public Account findById();
    public  Account findByUsername(String username) throws SQLException;
}
