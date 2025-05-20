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
                return emp;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean create(Employee emp) {
        String sql = "INSERT INTO employees (id, full_name, date_of_birth, gender, phone_number,  hire_date) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, emp.getId());
            ps.setString(2, emp.getFullName());
            ps.setObject(3, emp.getDateOfBirth());
            ps.setString(4, emp.getGender());
            ps.setString(5, emp.getPhoneNumber());
            ps.setObject(6, emp.getHireDate());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateEmployee(Employee emp) {
        String sql = "UPDATE employees SET full_name=?, date_of_birth=?, gender=?, phone_number=?, role=?, hire_date=? WHERE id=?";
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
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
