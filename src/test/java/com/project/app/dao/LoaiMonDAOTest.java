package com.project.app.dao;

import com.project.app.model.LoaiMon;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

class LoaiMonDAOTest {

    private LoaiMonDAO loaiMonDAO;

    @BeforeEach
    void setUp() throws SQLException {
        getConnection(); // Establish the test database connection
        createTables(connection); // Create tables in the test database
        loaiMonDAO = new LoaiMonDAO(); // Instantiate the DAO
    }

    @AfterEach
    void tearDown() throws SQLException {
        closeConnection(); // Close the test database connection
    }

    private void createTables(Connection conn) throws SQLException {
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("DROP TABLE IF EXISTS LoaiMon;");
            stmt.execute("CREATE TABLE LoaiMon (" +
                         "MaLoaiMon INT AUTO_INCREMENT PRIMARY KEY," +
                         "TenLoaiMon VARCHAR(255)," +
                         "MoTa TEXT," +
                         "PhanLoai VARCHAR(255)," +
                         "HinhAnh VARCHAR(255)," +
                         "ThoiGianTao DATETIME DEFAULT CURRENT_TIMESTAMP," +
                         "ThoiGianCapNhat DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP" +
                         ");");
        }
    }

    @Test
    void testGetAllLoaiMon() throws SQLException {
        // Insert test data
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("INSERT INTO LoaiMon (MaLoaiMon, TenLoaiMon, MoTa, PhanLoai, HinhAnh) VALUES (1, 'Ca Phe Den', 'Ca phe den da hoac nong', 'Do Uong', 'capheden.jpg')");
            stmt.execute("INSERT INTO LoaiMon (MaLoaiMon, TenLoaiMon, MoTa, PhanLoai, HinhAnh) VALUES (2, 'Tra Sua', 'Tra sua tran chau du loai', 'Do Uong', 'trasua.jpg')");
        }

        List<LoaiMon> loaiMonList = loaiMonDAO.getAllLoaiMon();

        assertNotNull(loaiMonList);
        assertEquals(2, loaiMonList.size());

        LoaiMon lm1 = loaiMonList.get(0);
        assertEquals(1, lm1.getMaLoaiMon());
        assertEquals("Ca Phe Den", lm1.getTenLoaiMon());
        assertEquals("Ca phe den da hoac nong", lm1.getMoTa());
        assertEquals("Do Uong", lm1.getPhanLoai());
        assertEquals("capheden.jpg", lm1.getHinhAnh());

        LoaiMon lm2 = loaiMonList.get(1);
        assertEquals(2, lm2.getMaLoaiMon());
        assertEquals("Tra Sua", lm2.getTenLoaiMon());
        assertEquals("Tra sua tran chau du loai", lm2.getMoTa());
        assertEquals("Do Uong", lm2.getPhanLoai());
        assertEquals("trasua.jpg", lm2.getHinhAnh());
    }

    @Test
    void testGetLoaiMonById() throws SQLException {
        // Insert test data
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("INSERT INTO LoaiMon (MaLoaiMon, TenLoaiMon, MoTa, PhanLoai, HinhAnh) VALUES (1, 'Ca Phe Den', 'Ca phe den da hoac nong', 'Do Uong', 'capheden.jpg')");
        }

        LoaiMon lm = loaiMonDAO.getLoaiMonById(1);

        assertNotNull(lm);
        assertEquals(1, lm.getMaLoaiMon());
        assertEquals("Ca Phe Den", lm.getTenLoaiMon());
        assertEquals("Ca phe den da hoac nong", lm.getMoTa());
        assertEquals("Do Uong", lm.getPhanLoai());
        assertEquals("capheden.jpg", lm.getHinhAnh());
    }

    @Test
    void testAddLoaiMon() throws SQLException {
        LoaiMon newLm = new LoaiMon(0, "Banh Mi", "Banh mi thit nguoi", "Do An", "banhmi.jpg"); // ID will be auto-generated
        boolean result = loaiMonDAO.addLoaiMon(newLm);

        assertTrue(result);

        // Verify the data was inserted
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM LoaiMon");
            rs.next();
            assertEquals(1, rs.getInt(1));
        }
    }

    @Test
    void testUpdateLoaiMon() throws SQLException {
        // Insert initial data
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("INSERT INTO LoaiMon (MaLoaiMon, TenLoaiMon, MoTa, PhanLoai, HinhAnh) VALUES (1, 'Ca Phe Den', 'Ca phe den da hoac nong', 'Do Uong', 'capheden.jpg')");
        }

        LoaiMon updatedLm = new LoaiMon(1, "Ca Phe Sua", "Ca phe sua da hoac nong", "Do Uong", "caphesua.jpg");
        boolean result = loaiMonDAO.updateLoaiMon(updatedLm);

        assertTrue(result);

        // Verify the data was updated
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT * FROM LoaiMon WHERE MaLoaiMon = 1");
            assertTrue(rs.next());
            assertEquals("Ca Phe Sua", rs.getString("TenLoaiMon"));
            assertEquals("Ca phe sua da hoac nong", rs.getString("MoTa"));
            assertEquals("caphesua.jpg", rs.getString("HinhAnh"));
        }
    }

    @Test
    void testDeleteLoaiMon() throws SQLException {
        // Insert initial data
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("INSERT INTO LoaiMon (MaLoaiMon, TenLoaiMon, MoTa, PhanLoai, HinhAnh) VALUES (1, 'Ca Phe Den', 'Ca phe den da hoac nong', 'Do Uong', 'capheden.jpg')");
        }

        boolean result = loaiMonDAO.deleteLoaiMon(1);

        assertTrue(result);

        // Verify the data was deleted
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM LoaiMon");
            rs.next();
            assertEquals(0, rs.getInt(1));
        }
    }
}
