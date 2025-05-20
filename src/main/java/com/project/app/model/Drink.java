package com.project.app.model;

import java.time.LocalDateTime;

public class Drink {
    private int foodId;
    private String foodName;
    private int foodCategoryId;
    private Double price;
    private String description;
    private String image; // Image path
    private LocalDateTime createdDate;

    public Drink() {
    }

    public Drink(int foodId, String foodName, int foodCategoryId, Double price, String description, String image, LocalDateTime createdDate) {
        this.foodId = foodId;
        this.foodName = foodName;
        this.foodCategoryId = foodCategoryId;
        this.price = price;
        this.description = description;
        this.image = image;
        this.createdDate = createdDate;
    }

    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public int getFoodCategoryId() {
        return foodCategoryId;
    }

    public void setFoodCategoryId(int foodCategoryId) {
        this.foodCategoryId = foodCategoryId;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }
}
