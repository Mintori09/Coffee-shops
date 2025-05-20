package com.project.app.dao;

import com.project.app.model.Drink;

public interface DrinkDAO {
    public boolean create(Drink drink);
    public Drink findById(int drinkId);
}
