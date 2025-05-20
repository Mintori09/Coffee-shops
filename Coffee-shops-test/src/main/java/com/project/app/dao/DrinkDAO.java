package com.project.app.dao;

import com.project.app.model.Drink;

import java.sql.SQLException;
import java.util.List;

public interface DrinkDAO {
    public boolean create(Drink drink);
    public Drink findById(int drinkId);
    public List<Drink> getAllDrinks() throws SQLException;
}
