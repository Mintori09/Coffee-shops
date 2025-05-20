package com.project.app.dao.impl;

import com.project.app.dao.BillDetailDAO;
import com.project.app.model.BillDetail;

public class BillDetailDAOImpl implements BillDetailDAO {
    @Override
    public boolean create(BillDetail billDetail) {
        return false;
    }

    @Override
    public BillDetail findByBillIdAndDrinkId(int billId, int drinkId) {
        return null;
    }
}
