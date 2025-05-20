package com.project.app.model;

public class DrinkCategory {
    private int foodCategoryId;
    private String foodCategoryName;

    public DrinkCategory() {
    }

    public DrinkCategory(int foodCategoryId, String foodCategoryName) {
        this.foodCategoryId = foodCategoryId;
        this.foodCategoryName = foodCategoryName;
    }

    public int getFoodCategoryId() {
        return foodCategoryId;
    }

    public void setFoodCategoryId(int foodCategoryId) {
        this.foodCategoryId = foodCategoryId;
    }

    public String getFoodCategoryName() {
        return foodCategoryName;
    }

    public void setFoodCategoryName(String foodCategoryName) {
        this.foodCategoryName = foodCategoryName;
    }
}
