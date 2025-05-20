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

    public int getFoodCategoryId() {
        return drinkCategoryId;
    }

    public void setFoodCategoryId(int drinkCategoryId) {
        this.drinkCategoryId = drinkCategoryId;
    }

    public String getFoodCategoryName() {
        return drinkCategoryName;
    }

    public void setFoodCategoryName(String drinkCategoryName) {
        this.drinkCategoryName = drinkCategoryName;
    }
}
