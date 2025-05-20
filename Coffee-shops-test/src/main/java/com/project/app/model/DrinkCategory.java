package com.project.app.model;

public class DrinkCategory {
    private int drinkCategoryId;
    private String drinkCategoryName;

    public DrinkCategory() {
    }
    public DrinkCategory(int drinkCategoryId, String drinkCategoryName) {
        this.drinkCategoryId = drinkCategoryId;
        this.drinkCategoryName = drinkCategoryName;
    }

    public int getDrinkCategoryId() {
        return drinkCategoryId;
    }

    public void setDrinkCategoryId(int drinkCategoryId) {
        this.drinkCategoryId = drinkCategoryId;
    }

    public String getDrinkCategoryName() {
        return drinkCategoryName;
    }

    public void setDrinkCategoryName(String drinkCategoryName) {
        this.drinkCategoryName = drinkCategoryName;
    }
}
