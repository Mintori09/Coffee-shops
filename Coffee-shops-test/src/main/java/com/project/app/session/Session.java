package com.project.app.session;

import com.project.app.model.Account;

public class Session {
    private static Session instance;
    private static Account account;

    private Session() {}
    public static Session getInstance() {
        if (instance == null) {
            instance = new Session();
        }
        return instance;
    }
    public Account getAccount() {
        return account;
    }
    public void setAccount(Account account) {
        this.account = account;
    }
}
