package com.project.app.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class BillDetail {
    private int billDetailId;
    private int billId;
    private int drinkId;
    private int quantity;
    private Double price;
    private String notes;
    private LocalDateTime createdDate;

    public BillDetail() {
    }

    public BillDetail(int billDetailId, int billId, int drinkId, int quantity, Double price, String notes, LocalDateTime createdDate) {
        this.billDetailId = billDetailId;
        this.billId = billId;
        this.drinkId = drinkId;
        this.quantity = quantity;
        this.price = price;
        this.notes = notes;
        this.createdDate = createdDate;
    }

    public int getBillDetailId() {
        return billDetailId;
    }

    public void setBillDetailId(int billDetailId) {
        this.billDetailId = billDetailId;
    }



    public int getBillId() {
        return billId;
    }

    public void setBillId(int billId) {
        this.billId = billId;
    }

    public int getDrinkId() {
        return drinkId;
    }

    public void setDrinkId(int drinkId) {
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
