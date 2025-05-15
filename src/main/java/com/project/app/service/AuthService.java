package com.project.app.service;

import com.project.app.dao.AccountDAO;
import com.project.app.model.Account;

public class AuthService {
    private final AccountDAO accountDAO;

    public AuthService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    public Account login(String username, String password) {
        Account user = accountDAO.findByUsername(username);
        if (user != null && password.equals(user.getPassword())) {
            return user;
        }
        return null;
    }
}
