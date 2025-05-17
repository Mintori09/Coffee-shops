package com.project.app.model;

public class Account {
    private int id;
    private String username;
    private String password;
    private String employeeId;
    private String role;

    public Account() {
    }
    public Account(int id, String username, String password, String employeeId) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.employeeId = employeeId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmployeeId() {
        return employeeId;
    }
    public String getRole() {
        return role;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }
    public void setRole(String role) {
		this.role = role;
	}
}
