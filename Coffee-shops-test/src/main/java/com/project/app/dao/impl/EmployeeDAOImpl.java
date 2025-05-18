package com.project.app.dao.impl;

import com.project.app.dao.EmployeeDAO;
import com.project.app.model.Employee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAOImpl implements EmployeeDAO {
    private Connection conn;

    public EmployeeDAOImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<Employee> getAllEmployees() {
        List<Employee> list = new ArrayList<>();
        String sql = "SELECT * FROM employees";
        try (Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Employee emp = new Employee();
                emp.setId(rs.getString("id"));
                emp.setFullName(rs.getString("full_name"));
                emp.setDateOfBirth(rs.getObject("date_of_birth", java.time.LocalDate.class));
                emp.setGender(rs.getString("gender"));
                emp.setPhoneNumber(rs.getString("phone_number"));
                emp.setRole(rs.getString("role"));
                emp.setHireDate(rs.getObject("hire_date", java.time.LocalDateTime.class));
                list.add(emp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Employee getEmployeeById(String id) {
        String sql = "SELECT * FROM employees WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Employee emp = new Employee();
                emp.setId(rs.getString("id"));
                emp.setFullName(rs.getString("full_name"));
                emp.setDateOfBirth(rs.getObject("date_of_birth", java.time.LocalDate.class));
                emp.setGender(rs.getString("gender"));
                emp.setPhoneNumber(rs.getString("phone_number"));
                emp.setRole(rs.getString("role"));
                emp.setHireDate(rs.getObject("hire_date", java.time.LocalDateTime.class));
                return emp;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean addEmployee(Employee emp) {
        String sql = "INSERT INTO employees (id, full_name, date_of_birth, gender, phone_number,  hire_date) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setNString(1, emp.getId());
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
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, emp.getFullName());
            ps.setObject(2, emp.getDateOfBirth());
            ps.setString(3, emp.getGender());
            ps.setString(4, emp.getPhoneNumber());
            ps.setString(5, emp.getRole());
            ps.setObject(6, emp.getHireDate());
            ps.setString(7, emp.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteEmployee(String id) {
        String sql = "DELETE FROM employees WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
