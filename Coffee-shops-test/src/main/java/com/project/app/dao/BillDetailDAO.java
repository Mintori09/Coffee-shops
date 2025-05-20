package com.project.app.dao;

import com.project.app.model.BillDetail;

import java.util.List;

public interface BillDetailDAO {
    public boolean create(BillDetail billDetail);
    public BillDetail findByBillIdAndDrinkId(int billId, int drinkId);
    public List<BillDetail> getBillDetailsByBillId(int billId);
    public boolean updateBillDetail(BillDetail billDetail); // Add updateBillDetail method
}
