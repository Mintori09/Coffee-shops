package com.project.app.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
	private static final String URL = "jdbc:sqlserver://DESKTOP-SVSMN8V:1433;databaseName=coffee;integratedSecurity=true;encrypt=true;trustServerCertificate=true";

    public static Connection connection;

    private DatabaseConnection() throws SQLException {
        getConnection();
    }

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                connection = DriverManager.getConnection(URL);
            } catch (ClassNotFoundException e) {
                throw new SQLException("SQL Server JDBC Driver not found", e);
            }
        }
		return connection;
    }

    public static void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    public static boolean checkConnection() {
        try {
            getConnection();
            if (connection != null && !connection.isClosed()) {
                System.out.println("✅ Kết nối đến SQL Server thành công (Windows Auth).");
                return true;
            }
        } catch (SQLException e) {
            System.err.println("❌ Lỗi kết nối SQL Server: " + e.getMessage());
        }
        return false;
    }

    public static void main(String[] args) throws SQLException {
        if (DatabaseConnection.checkConnection()) {
            System.out.println("Connected");
        } else {
            System.out.println("Connect failed!");
        }
    }
}
