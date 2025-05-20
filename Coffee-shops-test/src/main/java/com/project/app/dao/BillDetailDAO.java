package com.project.app.dao;

import com.project.app.model.BillDetail;

public interface BillDetailDAO {
    public boolean create(BillDetail billDetail);
    public BillDetail findByBillIdAndDrinkId(int billId, int drinkId);
}
