package com.project.app.service;

import com.project.app.dao.AccountDAO;
import com.project.app.model.Account;
import com.project.app.session.Session;

import java.sql.SQLException;

public class AuthService {
    private final AccountDAO accountDAO;

    public AuthService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    public Account login(String username, String password) throws SQLException {
        Account user = accountDAO.findByUsername(username);
        if (user != null && password.equals(user.getPassword())) {
            return user;
        }
        return null;
    }
}
