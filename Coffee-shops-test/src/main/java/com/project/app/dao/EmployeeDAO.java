package com.project.app.dao;

import com.project.app.model.Employee;
import java.util.List;

public interface EmployeeDAO {
    List<Employee> getAllEmployees();
    Employee findById(int id);
    boolean create(Employee employee);
    boolean updateEmployee(Employee employee);
    boolean delete(int id);

    List<Object[]> getAllStaffDetails();

    Employee findByIdAccount(int accountId);
}
