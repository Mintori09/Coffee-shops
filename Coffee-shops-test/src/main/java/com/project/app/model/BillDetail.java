package com.project.app.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class BillDetail {
    private String billId;
    private String drinkId;
    private int quantity;
    private Double price;
    private String notes;
    private LocalDateTime createdDate;

    public BillDetail() {
    }

    public BillDetail(String billId, String drinkId, int quantity, Double price, String notes, LocalDateTime createdDate) {
        this.billId = billId;
        this.drinkId = drinkId;
        this.quantity = quantity;
        this.price = price;
        this.notes = notes;
        this.createdDate = createdDate;
    }
    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    public String getFoodId() {
        return drinkId;
    }

    public void setFoodId(String drinkId) {
        this.drinkId = drinkId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }
}
