package com.project.app.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Bill {
    private int billId;
    private LocalDate orderDate;
    private Double totalPrice;
    private String notes;
    private LocalDateTime createdDate;
    private int employeeId;

    public Bill() {
    }

    public Bill(int billId, LocalDate orderDate, Double totalPrice, String notes, LocalDateTime createdDate, int employeeId) {
        this.billId = billId;
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
        this.notes = notes;
        this.createdDate = createdDate;
        this.employeeId = employeeId;
    }

    public int getBillId() {
        return billId;
    }

    public void setBillId(int billId) {
        this.billId = billId;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
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

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }
}
