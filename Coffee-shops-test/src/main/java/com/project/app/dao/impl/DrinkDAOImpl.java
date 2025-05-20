package com.project.app.dao.impl;

import com.project.app.dao.DrinkDAO;
import com.project.app.database.DatabaseConnection;
import com.project.app.model.Drink;
import com.project.app.model.Employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DrinkDAOImpl implements DrinkDAO {
    @Override
    public boolean create(Drink drink) {
        String sql = "INSERT INTO drinks (drink_name, drink_category_id, price, description, image, created_date) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, drink.getDrinkName()); // Assuming getDrinkName() is the correct getter for drinkName
            stmt.setString(2, drink.getDrinkCategoryId()); // Assuming getDrinkCategoryId() is the correct getter for drinkCategoryId
            stmt.setDouble(3, drink.getPrice());
            stmt.setString(4, drink.getDescription());
            stmt.setString(5, drink.getImage());
            stmt.setTimestamp(6, java.sql.Timestamp.valueOf(drink.getCreatedDate()));
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Drink findById(int drinkId) {
        String sql = "SELECT * FROM drinks WHERE drink_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, drinkId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Drink drink = new Drink();
                drink.setDrinkId(rs.getInt("drink_id"));
                drink.setDrinkName(rs.getString("drink_name"));
                drink.setDrinkCategoryId(rs.getString("drink_category_id")); // Assuming setter takes String
                drink.setPrice(rs.getDouble("price"));
                drink.setDescription(rs.getString("description"));
                drink.setImage(rs.getString("image"));
                drink.setCreatedDate(rs.getTimestamp("created_date").toLocalDateTime()); // Assuming setter takes LocalDateTime
                return drink;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Drink> getAllDrinks() throws SQLException {
        List<Drink> drinks = new ArrayList<>();
        String sql = "SELECT drink_id, drink_name, drink_category_id, price, description, image, created_date FROM drinks";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Drink drink = new Drink();
                drink.setDrinkId(rs.getInt("drink_id"));
                drink.setDrinkName(rs.getString("drink_name"));
                drink.setDrinkCategoryId(rs.getString("drink_category_id")); // Assuming setter takes String
                drink.setPrice(rs.getDouble("price"));
                drink.setDescription(rs.getString("description"));
                drink.setImage(rs.getString("image"));
                drink.setCreatedDate(rs.getTimestamp("created_date").toLocalDateTime()); // Assuming setter takes LocalDateTime
                drinks.add(drink);
            }
        }
        return drinks;
    }
}
