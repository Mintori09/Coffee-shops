package com.project.app.model;

import java.time.LocalDateTime;

public class Drink {
    private int drinkId;
    private String drinkName;
    private String drinkCategoryId;
    private Double price;
    private String description;
    private String image; // Path to image
    private LocalDateTime createdDate;

    public Drink() {
    }

    public Drink(int drinkId, String drinkName, String drinkCategoryId, Double price, String description, String image, LocalDateTime createdDate) {
        this.drinkId = drinkId;
        this.drinkName = drinkName;
        this.drinkCategoryId = drinkCategoryId;
        this.price = price;
        this.description = description;
        this.image = image;
        this.createdDate = createdDate;
    }

    public int getDrinkId() {
        return drinkId;
    }

    public void setDrinkId(int drinkId) {
        this.drinkId = drinkId;
    }

    public String getDrinkName() {
        return drinkName;
    }

    public void setDrinkName(String drinkName) {
        this.drinkName = drinkName;
    }

    public String getDrinkCategoryId() {
        return drinkCategoryId;
    }

    public void setDrinkCategoryId(String drinkCategoryId) {
        this.drinkCategoryId = drinkCategoryId;
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
