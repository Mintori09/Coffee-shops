package com.project.app.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Bill {
    private String billId;
    private LocalDate orderDate;
    private Double totalPrice;
    private String notes;
    private LocalDateTime createdDate;
    private String employeeId;

    public Bill() {
    }

    public Bill(String billId, LocalDate orderDate, Double totalPrice, String notes, LocalDateTime createdDate, String employeeId) {
        this.billId = billId;
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
        this.notes = notes;
        this.createdDate = createdDate;
        this.employeeId = employeeId;
    }

    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
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

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }
}
