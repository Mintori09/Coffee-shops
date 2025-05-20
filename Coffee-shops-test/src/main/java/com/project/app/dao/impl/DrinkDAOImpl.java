package com.project.app.dao.impl;

import com.project.app.dao.DrinkDAO;
import com.project.app.model.Drink;

public class DrinkDAOImpl implements DrinkDAO {
    @Override
    public boolean create(Drink drink) {
        String sql = "INSERT INTO drinks (drink_name, drink_category_id, price, description, image, created_date) VALUES (?, ?, ?, ?, ?, ?)";
        try (java.sql.Connection conn = com.project.app.database.DatabaseConnection.getConnection();
             java.sql.PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, drink.getDrinkName()); // Assuming getDrinkName() is the correct getter for drinkName
            stmt.setString(2, drink.getDrinkCategoryId()); // Assuming getDrinkCategoryId() is the correct getter for drinkCategoryId
            stmt.setDouble(3, drink.getPrice());
            stmt.setString(4, drink.getDescription());
            stmt.setString(5, drink.getImage());
            stmt.setTimestamp(6, java.sql.Timestamp.valueOf(drink.getCreatedDate()));
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Drink findById(int drinkId) {
        return null;
    }
}
