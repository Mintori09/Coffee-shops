package com.project.app.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mariadb://localhost:3306/coffee";
    private static final String USER = "mintori";
    private static final String PASSWORD = "123";
    public static Connection connection;

    private DatabaseConnection() throws SQLException {
        getConnection();
    }

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                Class.forName("org.mariadb.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            } catch (ClassNotFoundException e) {
                throw new SQLException("MariaDB JDBC Driver not found", e);
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
                System.out.println("✅ Kết nối đến cơ sở dữ liệu thành công.");
                return true;
            }
        } catch (SQLException e) {
            System.err.println("❌ Lỗi kết nối cơ sở dữ liệu: " + e.getMessage());
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
