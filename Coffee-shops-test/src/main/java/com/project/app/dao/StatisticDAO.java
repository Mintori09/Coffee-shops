package com.project.app.dao;

import com.project.app.dao.impl.StatisticDAOImpl.TimeRange;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface StatisticDAO {
    Map<String, Number> getRevenueData(TimeRange range) throws SQLException;
    Map<String, Number> getProfitData(TimeRange range) throws SQLException;
    Map<String, Number> getOrderData(TimeRange range) throws SQLException; // This will still return bill count for now, will add a new method for category-wise order data
    Map<String, Number> getOrderDataByCategory(TimeRange range) throws SQLException;

    String getTotalRevenue(TimeRange range) throws SQLException;
    String getTotalOrders(TimeRange range) throws SQLException;
    String getTotalProfit(TimeRange range) throws SQLException;
}
