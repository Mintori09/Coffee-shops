package com.project.app.model;

public class DrinkCategory {
    private String drinkCategoryId;
    private String drinkCategoryName;

    public DrinkCategory() {
    }
    public DrinkCategory(String drinkCategoryId, String drinkCategoryName) {
        this.drinkCategoryId = drinkCategoryId;
        this.drinkCategoryName = drinkCategoryName;
    }

    public String getFoodCategoryId() {
        return drinkCategoryId;
    }

    public void setFoodCategoryId(String drinkCategoryId) {
        this.drinkCategoryId = drinkCategoryId;
    }

    public String getFoodCategoryName() {
        return drinkCategoryName;
    }

    public void setFoodCategoryName(String drinkCategoryName) {
        this.drinkCategoryName = drinkCategoryName;
    }
}
