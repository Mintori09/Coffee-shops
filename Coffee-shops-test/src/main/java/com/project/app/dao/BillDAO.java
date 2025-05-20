package com.project.app.dao;

import com.project.app.model.Bill;

public interface BillDAO {
    public int create(Bill bill);
    public Bill findById(int billId);
    public java.util.List<Bill> getAllBills();
    public boolean updateBill(Bill bill); // Add updateBill method
}
