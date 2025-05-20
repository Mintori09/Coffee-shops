package com.project.app.dao;

import com.project.app.model.Bill;

public interface BillDAO {
    public boolean create(Bill bill);
    public Bill findById(int billId);
}
