package com.project.app.dao;

import com.project.app.model.ChiTietHoaDon;
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

class ChiTietHoaDonDAOTest {

    private ChiTietHoaDonDAO chiTietHoaDonDAO;

    @BeforeEach
    void setUp() throws SQLException, NoSuchFieldException, IllegalAccessException {
        getConnection(); // Establish the test database connection
        chiTietHoaDonDAO = new ChiTietHoaDonDAO(); // Instantiate the DAO
    }

    @AfterEach
    void tearDown() throws SQLException {
        closeConnection(); // Close the test database connection
    }

    private void createTables(Connection conn) throws SQLException {
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("DROP TABLE IF EXISTS ChiTietHoaDon;");
            stmt.execute("CREATE TABLE ChiTietHoaDon (" +
                         "MaChiTietHD INT AUTO_INCREMENT PRIMARY KEY," +
                         "MaHoaDon INT," +
                         "MaMon INT," +
                         "SoLuong INT," +
                         "DonGiaLucDat DECIMAL(10, 2)," +
                         "ThanhTien DECIMAL(10, 2)," +
                         "GhiChuMon TEXT," +
                         "ThoiGianTao DATETIME DEFAULT CURRENT_TIMESTAMP" +
                         ");");
        }
    }

    @Test
    void testGetAllChiTietHD() throws SQLException {
        // Insert test data
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("INSERT INTO ChiTietHoaDon (MaHoaDon, MaMon, SoLuong, DonGiaLucDat, ThanhTien, GhiChuMon) VALUES (1, 1, 2, 25000.00, 50000.00, 'It duong')");
            stmt.execute("INSERT INTO ChiTietHoaDon (MaHoaDon, MaMon, SoLuong, DonGiaLucDat, ThanhTien, GhiChuMon) VALUES (1, 2, 1, 30000.00, 30000.00, NULL)");
        }

        List<ChiTietHoaDon> chiTietList = chiTietHoaDonDAO.getAllChiTietHD();

        assertNotNull(chiTietList);
        assertEquals(2, chiTietList.size());

        ChiTietHoaDon ct1 = chiTietList.get(0);
        assertEquals(1, ct1.getMaHoaDon());
        assertEquals(1, ct1.getMaMon());
        assertEquals(2, ct1.getSoLuong());
        assertEquals(new BigDecimal("25000.00"), ct1.getDonGiaLucDat());
        assertEquals(new BigDecimal("50000.00"), ct1.getThanhTien());
        assertEquals("It duong", ct1.getGhiChuMon());

        ChiTietHoaDon ct2 = chiTietList.get(1);
        assertEquals(1, ct2.getMaHoaDon());
        assertEquals(2, ct2.getMaMon());
        assertEquals(1, ct2.getSoLuong());
        assertEquals(new BigDecimal("30000.00"), ct2.getDonGiaLucDat());
        assertEquals(new BigDecimal("30000.00"), ct2.getThanhTien());
        assertNull(ct2.getGhiChuMon());
    }

    @Test
    void testGetChiTietHDById() throws SQLException {
        // Insert test data
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("INSERT INTO ChiTietHoaDon (MaChiTietHD, MaHoaDon, MaMon, SoLuong, DonGiaLucDat, ThanhTien, GhiChuMon) VALUES (1, 1, 1, 2, 25000.00, 50000.00, 'It duong')");
        }

        ChiTietHoaDon ct = chiTietHoaDonDAO.getChiTietHDById(1);

        assertNotNull(ct);
        assertEquals(1, ct.getMaChiTietHD());
        assertEquals(1, ct.getMaHoaDon());
        assertEquals(1, ct.getMaMon());
        assertEquals(2, ct.getSoLuong());
        assertEquals(new BigDecimal("25000.00"), ct.getDonGiaLucDat());
        assertEquals(new BigDecimal("50000.00"), ct.getThanhTien());
        assertEquals("It duong", ct.getGhiChuMon());
    }

    @Test
    void testAddChiTietHD() throws SQLException {
        ChiTietHoaDon newCt = new ChiTietHoaDon(0, 1, 3, 1, new BigDecimal("40000.00"), new BigDecimal("40000.00"), "No ice");
        boolean result = chiTietHoaDonDAO.addChiTietHD(newCt);

        assertTrue(result);

        // Verify the data was inserted
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM ChiTietHoaDon");
            rs.next();
            assertEquals(1, rs.getInt(1));
        }
    }

    @Test
    void testUpdateChiTietHD() throws SQLException {
        // Insert initial data
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("INSERT INTO ChiTietHoaDon (MaChiTietHD, MaHoaDon, MaMon, SoLuong, DonGiaLucDat, ThanhTien, GhiChuMon) VALUES (1, 1, 1, 2, 25000.00, 50000.00, 'It duong')");
        }

        ChiTietHoaDon updatedCt = new ChiTietHoaDon(1, 1, 1, 3, new BigDecimal("25000.00"), new BigDecimal("75000.00"), "More sugar");
        updatedCt.setMaChiTietHD(1); // Set the ID for update
        boolean result = chiTietHoaDonDAO.updateChiTietHD(updatedCt);

        assertTrue(result);

        // Verify the data was updated
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT * FROM ChiTietHoaDon WHERE MaChiTietHD = 1");
            assertTrue(rs.next());
            assertEquals(3, rs.getInt("SoLuong"));
            assertEquals(new BigDecimal("75000.00"), rs.getBigDecimal("ThanhTien"));
            assertEquals("More sugar", rs.getString("GhiChuMon"));
        }
    }

    @Test
    void testDeleteChiTietHD() throws SQLException {
        // Insert initial data
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("INSERT INTO ChiTietHoaDon (MaChiTietHD, MaHoaDon, MaMon, SoLuong, DonGiaLucDat, ThanhTien, GhiChuMon) VALUES (1, 1, 1, 2, 25000.00, 50000.00, 'It duong')");
        }

        boolean result = chiTietHoaDonDAO.deleteChiTietHD(1);

        assertTrue(result);

        // Verify the data was deleted
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM ChiTietHoaDon");
            rs.next();
            assertEquals(0, rs.getInt(1));
        }
    }
}
