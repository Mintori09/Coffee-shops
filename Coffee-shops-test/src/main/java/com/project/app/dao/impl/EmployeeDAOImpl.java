package com.project.app.dao.impl;

import com.project.app.dao.EmployeeDAO;
import com.project.app.database.DatabaseConnection;
import com.project.app.model.Employee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAOImpl implements EmployeeDAO {
    @Override
    public List<Employee> getAllEmployees() {
        List<Employee> list = new ArrayList<>();
        String sql = "SELECT * FROM employees";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Employee emp = new Employee();
                emp.setId(rs.getInt("id"));
                emp.setFullName(rs.getString("full_name"));
                emp.setDateOfBirth(rs.getObject("date_of_birth", java.time.LocalDate.class));
                emp.setGender(rs.getString("gender"));
                emp.setPhoneNumber(rs.getString("phone_number"));
                emp.setHireDate(rs.getObject("hire_date", java.time.LocalDateTime.class));
                emp.setAccountId(rs.getInt("account_id"));
                list.add(emp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Employee findById(int id) {
        String sql = "SELECT * FROM employees WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Employee emp = new Employee();
                emp.setId(rs.getInt("id"));
                emp.setFullName(rs.getString("full_name"));
                emp.setDateOfBirth(rs.getObject("date_of_birth", java.time.LocalDate.class));
                emp.setGender(rs.getString("gender"));
                emp.setPhoneNumber(rs.getString("phone_number"));
                emp.setHireDate(rs.getObject("hire_date", java.time.LocalDateTime.class));
                emp.setAccountId(rs.getInt("account_id"));
                return emp;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Employee findByIdAccount(int id) {
        String sql = "SELECT * FROM employees WHERE account_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Employee emp = new Employee();
                emp.setId(rs.getInt("id"));
                emp.setFullName(rs.getString("full_name"));
                emp.setDateOfBirth(rs.getObject("date_of_birth", java.time.LocalDate.class));
                emp.setGender(rs.getString("gender"));
                emp.setPhoneNumber(rs.getString("phone_number"));
                emp.setHireDate(rs.getObject("hire_date", java.time.LocalDateTime.class));
                emp.setAccountId(rs.getInt("account_id"));
                return emp;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int create(Employee emp) {
        String sql = "INSERT INTO employees (full_name, date_of_birth, gender, phone_number, hire_date, account_id) VALUES (?, ?, ?, ?, ?, ?)";
        int generatedId = -1;
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, emp.getFullName());
            ps.setObject(2, emp.getDateOfBirth());
            ps.setString(3, emp.getGender());
            ps.setString(4, emp.getPhoneNumber());
            ps.setObject(5, emp.getHireDate());
            ps.setInt(6, emp.getAccountId());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                generatedId = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return generatedId;
    }

    @Override
    public boolean updateEmployee(Employee emp) {
        String sql = "UPDATE employees SET full_name=?, date_of_birth=?, gender=?, phone_number=?, hire_date=? WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, emp.getFullName());
            ps.setObject(2, emp.getDateOfBirth());
            ps.setString(3, emp.getGender());
            ps.setString(4, emp.getPhoneNumber());
            ps.setObject(5, emp.getHireDate());
            ps.setInt(6, emp.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM employees WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                System.out.println("No employee found with ID: " + id);
            }
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting employee with ID: " + id);
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Object[]> getAllStaffDetails() {
        List<Object[]> list = new ArrayList<>();
        String sql = "SELECT e.id, e.full_name, a.username, a.role, e.hire_date " +
                     "FROM employees e JOIN accounts a ON e.account_id = a.id";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Object[] row = new Object[5];
                row[0] = rs.getInt("id");
                row[1] = rs.getString("full_name");
                row[2] = rs.getString("username");
                row[3] = rs.getString("role");
                row[4] = rs.getObject("hire_date", java.time.LocalDateTime.class);
                list.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void deleteAll() throws SQLException {
        String sql = "DELETE FROM employees";
        Connection conn = DatabaseConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.executeUpdate();
        }

        // Reset auto-increment counter
        String resetSql = "ALTER TABLE employees AUTO_INCREMENT = 1";
        try (PreparedStatement ps = conn.prepareStatement(resetSql)) {
            ps.executeUpdate();
        }
    }

    public static void main(String[] args) {
        EmployeeDAOImpl dao = new EmployeeDAOImpl();
        List<Object[]> list = dao.getAllStaffDetails();
        for (Object[] row : list) {
            System.out.println(row[0]);
        }
    }
}
