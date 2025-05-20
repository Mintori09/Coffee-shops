package com.project.app.dao;

import com.project.app.model.DrinkCategory;

public interface DrinkCategoryDAO {
    public boolean create(DrinkCategory drinkCategory);
    public DrinkCategory findById(int drinkCategoryId);
}
