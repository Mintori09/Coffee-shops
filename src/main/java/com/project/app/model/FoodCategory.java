package com.project.app.model;

public class FoodCategory {
    private String foodCategoryId;
    private String foodCategoryName;

    public FoodCategory() {
    }
    public FoodCategory(String foodCategoryId, String foodCategoryName) {
        this.foodCategoryId = foodCategoryId;
        this.foodCategoryName = foodCategoryName;
    }

    public String getFoodCategoryId() {
        return foodCategoryId;
    }

    public void setFoodCategoryId(String foodCategoryId) {
        this.foodCategoryId = foodCategoryId;
    }

    public String getFoodCategoryName() {
        return foodCategoryName;
    }

    public void setFoodCategoryName(String foodCategoryName) {
        this.foodCategoryName = foodCategoryName;
    }
}
