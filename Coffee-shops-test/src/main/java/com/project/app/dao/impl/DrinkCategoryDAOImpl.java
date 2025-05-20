package com.project.app.dao.impl;

import com.project.app.dao.DrinkCategoryDAO;
import com.project.app.model.DrinkCategory;

public class DrinkCategoryDAOImpl implements DrinkCategoryDAO {
    @Override
    public boolean create(DrinkCategory drinkCategory) {
        String sql = "INSERT INTO drink_categories (drink_category_name) VALUES (?)";
        try (java.sql.Connection conn = com.project.app.database.DatabaseConnection.getConnection();
             java.sql.PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, drinkCategory.getDrinkCategoryName()); // Assuming getDrinkCategoryName() is the correct getter for drinkCategoryName
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public DrinkCategory findById(int drinkCategoryId) {
        return null;
    }

    public void deleteAll() throws java.sql.SQLException {
        String sql = "DELETE FROM drink_categories";
        java.sql.Connection conn = com.project.app.database.DatabaseConnection.getConnection();
        try (java.sql.PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.executeUpdate();
        }

        // Reset auto-increment counter
        String resetSql = "ALTER TABLE drink_categories AUTO_INCREMENT = 1";
        try (java.sql.PreparedStatement ps = conn.prepareStatement(resetSql)) {
            ps.executeUpdate();
        }
    }
}
