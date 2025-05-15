package com.project.app.dao;

import com.project.app.model.HoaDon;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.List;

import static com.project.app.database.TestDatabaseConnection.connection;
import static com.project.app.database.TestDatabaseConnection.getConnection;
import static com.project.app.database.TestDatabaseConnection.closeConnection;
import static org.junit.jupiter.api.Assertions.*;

class HoaDonDAOTest {

    private HoaDonDAO hoaDonDAO;

    @BeforeEach
    void setUp() throws SQLException, NoSuchFieldException, IllegalAccessException {
        getConnection(); // Establish the test database connection
        createTables(connection); // Create tables in the test database

        // Use reflection to set the static connection in BaseDAO for testing
        Field connectionField = AccountDAO.class.getDeclaredField("connection");
        connectionField.setAccessible(true);
        connectionField.set(null, connection); // Set the static field to the test connection

        hoaDonDAO = new HoaDonDAO(); // Instantiate the DAO
    }

    @AfterEach
    void tearDown() throws SQLException {
        closeConnection(); // Close the test database connection
    }

    private void createTables(Connection conn) throws SQLException {
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("DROP TABLE IF EXISTS HoaDon;");
            stmt.execute("DROP TABLE IF EXISTS NhanVien;");

            stmt.execute("CREATE TABLE NhanVien (" +
                         "MaNhanVien VARCHAR(255) PRIMARY KEY," +
                         "TenNhanVien VARCHAR(255)," +
                         "ChucVu VARCHAR(255)," +
                         "SoDienThoai VARCHAR(20)," +
                         "Email VARCHAR(255)," +
                         "ThoiGianTao DATETIME DEFAULT CURRENT_TIMESTAMP," +
                         "ThoiGianCapNhat DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP" +
                         ");");

            stmt.execute("CREATE TABLE HoaDon (" +
                         "MaHoaDon INT AUTO_INCREMENT PRIMARY KEY," +
                         "MaNhanVien VARCHAR(255)," +
                         "ThoiGianThanhToan DATETIME," +
                         "TongTienPhaiTra DECIMAL(10, 2)," +
                         "TrangThai VARCHAR(50)," +
                         "GhiChu TEXT," +
                         "ThoiGianTao DATETIME DEFAULT CURRENT_TIMESTAMP," +
                         "FOREIGN KEY (MaNhanVien) REFERENCES NhanVien(MaNhanVien)" +
                         ");");
        }
    }

    @Test
    void testGetAllHoaDon() throws SQLException {
        // Insert test data
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("INSERT INTO NhanVien (MaNhanVien, TenNhanVien, ChucVu, SoDienThoai, Email) VALUES ('NV001', 'Nguyen Van A', 'Quan Ly', '0123456789', 'anv@example.com')");
            stmt.execute("INSERT INTO HoaDon (MaHoaDon, MaNhanVien, ThoiGianThanhToan, TongTienPhaiTra, TrangThai, GhiChu) VALUES (1, 'NV001', '2023-10-26 10:00:00', 50000.00, 'HoanThanh', 'Thanh toan tien mat')");
            stmt.execute("INSERT INTO HoaDon (MaHoaDon, MaNhanVien, ThoiGianThanhToan, TongTienPhaiTra, TrangThai, GhiChu) VALUES (2, 'NV001', NULL, 75000.00, 'DangXuLy', 'Khach hang cho lay hang')");
        }

        List<HoaDon> hoaDonList = hoaDonDAO.getAllHoaDon();

        assertNotNull(hoaDonList);
        assertEquals(2, hoaDonList.size());

        HoaDon hd1 = hoaDonList.get(0);
        assertEquals(1, hd1.getMaHoaDon());
        assertEquals("NV001", hd1.getMaNhanVien());
        assertNotNull(hd1.getThoiGianThanhToan());
        assertEquals(new BigDecimal("50000.00"), hd1.getTongTienPhaiTra());
        assertEquals("HoanThanh", hd1.getTrangThai());
        assertEquals("Thanh toan tien mat", hd1.getGhiChu());
        assertEquals("Nguyen Van A", hd1.getTenNhanVien());

        HoaDon hd2 = hoaDonList.get(1);
        assertEquals(2, hd2.getMaHoaDon());
        assertEquals("NV001", hd2.getMaNhanVien());
        assertNull(hd2.getThoiGianThanhToan());
        assertEquals(new BigDecimal("75000.00"), hd2.getTongTienPhaiTra());
        assertEquals("DangXuLy", hd2.getTrangThai());
        assertEquals("Khach hang cho lay hang", hd2.getGhiChu());
        assertEquals("Nguyen Van A", hd2.getTenNhanVien());
    }

    @Test
    void testGetHoaDonById() throws SQLException {
        // Insert test data
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("INSERT INTO NhanVien (MaNhanVien, TenNhanVien, ChucVu, SoDienThoai, Email) VALUES ('NV001', 'Nguyen Van A', 'Quan Ly', '0123456789', 'anv@example.com')");
            stmt.execute("INSERT INTO HoaDon (MaHoaDon, MaNhanVien, ThoiGianThanhToan, TongTienPhaiTra, TrangThai, GhiChu) VALUES (1, 'NV001', '2023-10-26 10:00:00', 50000.00, 'HoanThanh', 'Thanh toan tien mat')");
        }

        HoaDon hd = hoaDonDAO.getHoaDonById(1);

        assertNotNull(hd);
        assertEquals(1, hd.getMaHoaDon());
        assertEquals("NV001", hd.getMaNhanVien());
        assertNotNull(hd.getThoiGianThanhToan());
        assertEquals(new BigDecimal("50000.00"), hd.getTongTienPhaiTra());
        assertEquals("HoanThanh", hd.getTrangThai());
        assertEquals("Thanh toan tien mat", hd.getGhiChu());
        assertEquals("Nguyen Van A", hd.getTenNhanVien());
    }

    @Test
    void testAddHoaDon() throws SQLException {
        // Insert test data for foreign key
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("INSERT INTO NhanVien (MaNhanVien, TenNhanVien, ChucVu, SoDienThoai, Email) VALUES ('NV001', 'Nguyen Van A', 'Quan Ly', '0123456789', 'anv@example.com')");
        }

        HoaDon newHd = new HoaDon(0, "NV001", LocalDateTime.now(), new BigDecimal("100000.00"), "DangXuLy", "Online order");
        boolean result = hoaDonDAO.addHoaDon(newHd);

        assertTrue(result);

        // Verify the data was inserted
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM HoaDon");
            rs.next();
            assertEquals(1, rs.getInt(1));
        }
    }

    @Test
    void testUpdateHoaDon() throws SQLException {
        // Insert initial data
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("INSERT INTO NhanVien (MaNhanVien, TenNhanVien, ChucVu, SoDienThoai, Email) VALUES ('NV001', 'Nguyen Van A', 'Quan Ly', '0123456789', 'anv@example.com')");
            stmt.execute("INSERT INTO HoaDon (MaHoaDon, MaNhanVien, ThoiGianThanhToan, TongTienPhaiTra, TrangThai, GhiChu) VALUES (1, 'NV001', '2023-10-26 10:00:00', 50000.00, 'HoanThanh', 'Thanh toan tien mat')");
        }

        HoaDon updatedHd = new HoaDon(1, "NV001", LocalDateTime.now(), new BigDecimal("60000.00"), "DaHuy", "Changed mind");
        boolean result = hoaDonDAO.updateHoaDon(updatedHd);

        assertTrue(result);

        // Verify the data was updated
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT * FROM HoaDon WHERE MaHoaDon = 1");
            assertTrue(rs.next());
            assertEquals(new BigDecimal("60000.00"), rs.getBigDecimal("TongTienPhaiTra"));
            assertEquals("DaHuy", rs.getString("TrangThai"));
            assertEquals("Changed mind", rs.getString("GhiChu"));
        }
    }

    @Test
    void testDeleteHoaDon() throws SQLException {
        // Insert initial data
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("INSERT INTO NhanVien (MaNhanVien, TenNhanVien, ChucVu, SoDienThoai, Email) VALUES ('NV001', 'Nguyen Van A', 'Quan Ly', '0123456789', 'anv@example.com')");
            stmt.execute("INSERT INTO HoaDon (MaHoaDon, MaNhanVien, ThoiGianThanhToan, TongTienPhaiTra, TrangThai, GhiChu) VALUES (1, 'NV001', '2023-10-26 10:00:00', 50000.00, 'HoanThanh', 'Thanh toan tien mat')");
        }

        boolean result = hoaDonDAO.deleteHoaDon(1);

        assertTrue(result);

        // Verify the data was deleted
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM HoaDon");
            rs.next();
            assertEquals(0, rs.getInt(1));
        }
    }

    @Test
    void testGetHoaDonByNhanVien() throws SQLException {
        // Insert test data
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("INSERT INTO NhanVien (MaNhanVien, TenNhanVien, ChucVu, SoDienThoai, Email) VALUES ('NV001', 'Nguyen Van A', 'Quan Ly', '0123456789', 'anv@example.com')");
            stmt.execute("INSERT INTO NhanVien (MaNhanVien, TenNhanVien, ChucVu, SoDienThoai, Email) VALUES ('NV002', 'Tran Thi B', 'Nhan Vien', '0987654321', 'btt@example.com')");
            stmt.execute("INSERT INTO HoaDon (MaHoaDon, MaNhanVien, ThoiGianThanhToan, TongTienPhaiTra, TrangThai, GhiChu) VALUES (1, 'NV001', '2023-10-26 10:00:00', 50000.00, 'HoanThanh', 'Thanh toan tien mat')");
            stmt.execute("INSERT INTO HoaDon (MaHoaDon, MaNhanVien, ThoiGianThanhToan, TongTienPhaiTra, TrangThai, GhiChu) VALUES (2, 'NV002', NULL, 75000.00, 'DangXuLy', 'Khach hang cho lay hang')");
            stmt.execute("INSERT INTO HoaDon (MaHoaDon, MaNhanVien, ThoiGianThanhToan, TongTienPhaiTra, TrangThai, GhiChu) VALUES (3, 'NV001', '2023-10-26 11:00:00', 30000.00, 'HoanThanh', NULL)");
        }

        List<HoaDon> hoaDonList = hoaDonDAO.getHoaDonByNhanVien("NV001");

        assertNotNull(hoaDonList);
        assertEquals(2, hoaDonList.size());

        // Verify the correct orders are returned
        boolean foundOrder1 = false;
        boolean foundOrder3 = false;
        for (HoaDon hd : hoaDonList) {
            if (hd.getMaHoaDon() == 1) {
                foundOrder1 = true;
            } else if (hd.getMaHoaDon() == 3) {
                foundOrder3 = true;
            }
        }
        assertTrue(foundOrder1);
        assertTrue(foundOrder3);
    }
}
