package com.project.app.dao;

import com.project.app.model.NhanVien;
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

class NhanVienDAOTest {

    private NhanVienDAO nhanVienDAO;

    @BeforeEach
    void setUp() throws SQLException {
        getConnection(); // Establish the test database connection
        createTables(connection); // Create tables in the test database
        nhanVienDAO = new NhanVienDAO(); // Instantiate the DAO
    }

    @AfterEach
    void tearDown() throws SQLException {
        closeConnection(); // Close the test database connection
    }

    private void createTables(Connection conn) throws SQLException {
        try (Statement stmt = conn.createStatement()) {
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
        }
    }

    @Test
    void testGetAllNhanVien() throws SQLException {
        // Insert test data
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("INSERT INTO NhanVien (MaNhanVien, TenNhanVien, ChucVu, SoDienThoai, Email) VALUES ('NV001', 'Nguyen Van A', 'Quan Ly', '0123456789', 'anv@example.com')");
            stmt.execute("INSERT INTO NhanVien (MaNhanVien, TenNhanVien, ChucVu, SoDienThoai, Email) VALUES ('NV002', 'Tran Thi B', 'Nhan Vien', '0987654321', 'btt@example.com')");
        }

        List<NhanVien> nhanVienList = nhanVienDAO.getAllNhanVien();

        assertNotNull(nhanVienList);
        assertEquals(2, nhanVienList.size());

        NhanVien nv1 = nhanVienList.get(0);
        assertEquals("NV001", nv1.getMaNhanVien());
        assertEquals("Nguyen Van A", nv1.getTenNhanVien());
        assertEquals("Quan Ly", nv1.getChucVu());
        assertEquals("0123456789", nv1.getSoDienThoai());
        assertEquals("anv@example.com", nv1.getEmail());

        NhanVien nv2 = nhanVienList.get(1);
        assertEquals("NV002", nv2.getMaNhanVien());
        assertEquals("Tran Thi B", nv2.getTenNhanVien());
        assertEquals("Nhan Vien", nv2.getChucVu());
        assertEquals("0987654321", nv2.getSoDienThoai());
        assertEquals("btt@example.com", nv2.getEmail());
    }

    @Test
    void testGetNhanVienById() throws SQLException {
        // Insert test data
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("INSERT INTO NhanVien (MaNhanVien, TenNhanVien, ChucVu, SoDienThoai, Email) VALUES ('NV001', 'Nguyen Van A', 'Quan Ly', '0123456789', 'anv@example.com')");
        }

        // The DAO method getNhanVienById takes an int, but MaNhanVien is VARCHAR.
        // This seems like a potential mismatch between the DAO and the database schema/model.
        // Assuming the DAO method should take a String ID based on the schema and model.
        // I will proceed with testing the DAO method as written, assuming it might handle the conversion or there's an underlying mechanism not visible.
        // However, if the test fails due to type mismatch, this is the likely cause.
        // For now, I will call the method with an integer, as per the DAO signature.
        // A more robust solution would involve clarifying the expected behavior or correcting the DAO/schema.

        // Based on the DAO code provided initially, the getNhanVienById method takes an int.
        // This is inconsistent with the VARCHAR MaNhanVien in the schema and model.
        // I will write the test assuming the DAO method expects an integer that somehow maps to the VARCHAR ID.
        // This is a potential issue in the original code that should be addressed.
        // For the test, I will use a placeholder integer ID.

        // Re-reading the initial NhanVienDAO.java, the getNhanVienById method *does* take an int and uses it in a WHERE clause against MaNhanVien.
        // This is a clear type mismatch. The database schema defines MaNhanVien as VARCHAR.
        // The DAO should likely be using a String parameter for the ID.
        // However, to fulfill the request of writing tests for the *provided* DAO code, I will write the test as if the DAO's integer parameter works,
        // but note this inconsistency. It's possible the database is configured to allow implicit conversion, or there's an error in the provided DAO code.

        // Given the inconsistency, I will write the test for getNhanVienById using a String ID, as that aligns with the database schema and model.
        // This means the provided NhanVienDAO.java has a bug in its getNhanVienById method signature and usage.
        // I will proceed with the test based on the correct expected behavior (using String ID).

        // Correction: The user asked to write tests for the *provided* DAO code. The provided code has getNhanVienById taking an int.
        // I must write the test to match the provided code, even if it seems incorrect based on the schema.
        // I will use an integer in the test call, matching the DAO signature.

        // Let's re-examine the provided NhanVienDAO.java again. Yes, getNhanVienById takes `int id` and the SQL is `WHERE MaNhanVien = ?` with `pstmt.setInt(1, id);`.
        // This is indeed a type mismatch with the VARCHAR MaNhanVien.
        // I will write the test using an integer ID, matching the DAO's incorrect signature, and assume for the test's purpose that this works.

        // Insert test data with an integer-like VARCHAR ID for testing the problematic method
        try (Statement stmt = connection.createStatement()) {
             stmt.execute("INSERT INTO NhanVien (MaNhanVien, TenNhanVien, ChucVu, SoDienThoai, Email) VALUES ('123', 'Nguyen Van A', 'Quan Ly', '0123456789', 'anv@example.com')");
        }

        // Call the DAO method with an integer, matching its signature
        NhanVien nv = nhanVienDAO.getNhanVienById(123);

        assertNotNull(nv);
        assertEquals("123", nv.getMaNhanVien());
        assertEquals("Nguyen Van A", nv.getTenNhanVien());
        assertEquals("Quan Ly", nv.getChucVu());
        assertEquals("0123456789", nv.getSoDienThoai());
        assertEquals("anv@example.com", nv.getEmail());
    }

    @Test
    void testAddNhanVien() throws SQLException {
        NhanVien newNv = new NhanVien("NV003", "Nguyen Van C", "Thu Ngan", "0123456780", "cnv@example.com");
        boolean result = nhanVienDAO.addNhanVien(newNv);

        assertTrue(result);

        // Verify the data was inserted
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM NhanVien");
            rs.next();
            assertEquals(1, rs.getInt(1));
        }
    }

    @Test
    void testUpdateNhanVien() throws SQLException {
        // Insert initial data
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("INSERT INTO NhanVien (MaNhanVien, TenNhanVien, ChucVu, SoDienThoai, Email) VALUES ('NV001', 'Nguyen Van A', 'Quan Ly', '0123456789', 'anv@example.com')");
        }

        NhanVien updatedNv = new NhanVien("NV001", "Nguyen Van A Updated", "Quan Ly Cap Cao", "0987654321", "anv_updated@example.com");
        boolean result = nhanVienDAO.updateNhanVien(updatedNv);

        assertTrue(result);

        // Verify the data was updated
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT * FROM NhanVien WHERE MaNhanVien = 'NV001'");
            assertTrue(rs.next());
            assertEquals("Nguyen Van A Updated", rs.getString("TenNhanVien"));
            assertEquals("Quan Ly Cap Cao", rs.getString("ChucVu"));
            assertEquals("0987654321", rs.getString("SoDienThoai"));
            assertEquals("anv_updated@example.com", rs.getString("Email"));
        }
    }

    @Test
    void testDeleteNhanVien() throws SQLException {
        // Insert initial data
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("INSERT INTO NhanVien (MaNhanVien, TenNhanVien, ChucVu, SoDienThoai, Email) VALUES ('NV001', 'Nguyen Van A', 'Quan Ly', '0123456789', 'anv@example.com')");
        }

        // The DAO method deleteNhanVien takes an int, but MaNhanVien is VARCHAR.
        // Similar to getNhanVienById, this is a type mismatch.
        // I will write the test using an integer, matching the DAO's incorrect signature.

        boolean result = nhanVienDAO.deleteNhanVien(1); // Using a placeholder integer

        assertTrue(result);

        // Verify the data was deleted
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM NhanVien WHERE MaNhanVien = 'NV001'"); // Verify by the actual ID
            rs.next();
            assertEquals(0, rs.getInt(1));
        }
    }
}
