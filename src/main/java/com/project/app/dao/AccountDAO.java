package com.project.app.dao;

import com.project.app.model.Account;

public interface AccountDAO {
    public Account findById();
    public  Account findByUsername(String username);
}
