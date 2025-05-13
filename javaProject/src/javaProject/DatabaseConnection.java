package javaProject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DatabaseConnection {

    private static final String URL = "jdbc:mariadb://localhost:3306/CoffeeShop";
    private static final String USER = "mintori";
    private static final String PASSWORD = "123";
    private DatabaseConnection() {}
    public static Connection getConnection() {
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Kết nối thành công!");
            return conn;
        } catch (SQLException e) {
            System.err.println("Lỗi kết nối SQL Server: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public static void testConnection() {
        Connection conn = getConnection();
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("❌ Kết nối database thất bại!");
        }
    }

    // Base class for database operations
    public static class BaseDAO {
        protected Connection conn;

        public BaseDAO() {
            this.conn = getConnection();
        }

        protected void closeConnection() {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        protected void closeResources(PreparedStatement ps, ResultSet rs) {
            try {
                if (rs != null)
                    rs.close();
                if (ps != null)
                    ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
