package com.project.app.dao.impl;

import com.project.app.dao.StatisticDAO;
import com.project.app.database.DatabaseConnection;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class StatisticDAOImpl implements StatisticDAO {

    public enum TimeRange {
        TODAY, LAST_7_DAYS, THIS_MONTH, LAST_MONTH
    }

    private String buildSQL(String selectExpr, TimeRange range) {
        String dateColumn = "DATE(b.order_date)";
        String startDate = "";
        String endDate = "CURDATE()";
        switch (range) {
            case TODAY:
                startDate = "CURDATE()";
                endDate = "CURDATE()";
                break;
            case LAST_7_DAYS:
                startDate = "DATE_SUB(CURDATE(), INTERVAL 6 DAY)";
                break;
            case THIS_MONTH:
                startDate = "DATE_FORMAT(CURDATE(), '%Y-%m-01')";
                break;
            case LAST_MONTH:
                startDate = "DATE_FORMAT(DATE_SUB(CURDATE(), INTERVAL 1 MONTH), '%Y-%m-01')";
                endDate = "LAST_DAY(DATE_SUB(CURDATE(), INTERVAL 1 MONTH))";
                break;
        }

        return "WITH RECURSIVE date_range AS (" +
                "  SELECT " + startDate + " AS day " +
                "  UNION ALL " +
                "  SELECT DATE_ADD(day, INTERVAL 1 DAY) FROM date_range WHERE day < " + endDate +
                ") " +
                "SELECT DATE_FORMAT(dr.day, '%Y-%m-%d') AS label, IFNULL(" + selectExpr + ", 0) AS value " +
                "FROM date_range dr " +
                "LEFT JOIN bills b ON " + dateColumn + " = dr.day " +
                (selectExpr.contains("bd.") ? "LEFT JOIN bill_details bd ON bd.bill_id = b.bill_id " : "") +
                "GROUP BY dr.day ORDER BY dr.day";
    }

    private Map<String, Number> queryDateValueMap(String sql) throws SQLException {
        Map<String, Number> result = new HashMap<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                result.put(rs.getString("label"), rs.getDouble("value"));
            }
        }
        return result;
    }

    private double querySingleValue(String sql) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getDouble(1);
            }
        }
        return 0.0;
    }

    @Override
    public Map<String, Number> getRevenueData(TimeRange range) throws SQLException {
        return queryDateValueMap(buildSQL("SUM(bd.price * bd.quantity)", range));
    }

    @Override
    public Map<String, Number> getProfitData(TimeRange range) throws SQLException {
        return queryDateValueMap(buildSQL("SUM(bd.price * bd.quantity) * 0.3", range));
    }

    @Override
    public Map<String, Number> getOrderData(TimeRange range) throws SQLException {
        return queryDateValueMap(buildSQL("COUNT(DISTINCT b.bill_id)", range));
    }

    @Override
    public Map<String, Number> getOrderDataByCategory(TimeRange range) throws SQLException {
        String dateColumn = "DATE(b.order_date)";
        String startDate = "";
        String endDate = "CURDATE()";
        switch (range) {
            case TODAY:
                startDate = "CURDATE()";
                endDate = "CURDATE()";
                break;
            case LAST_7_DAYS:
                startDate = "DATE_SUB(CURDATE(), INTERVAL 6 DAY)";
                break;
            case THIS_MONTH:
                startDate = "DATE_FORMAT(CURDATE(), '%Y-%m-01')";
                break;
            case LAST_MONTH:
                startDate = "DATE_FORMAT(DATE_SUB(CURDATE(), INTERVAL 1 MONTH), '%Y-%m-01')";
                endDate = "LAST_DAY(DATE_SUB(CURDATE(), INTERVAL 1 MONTH))";
                break;
        }

        String sql = "SELECT dc.drink_category_name AS label, SUM(bd.quantity) AS value " +
                     "FROM bill_details bd " +
                     "JOIN drinks d ON bd.drink_id = d.drink_id " +
                     "JOIN drink_categories dc ON d.drink_category_id = dc.drink_category_id " +
                     "JOIN bills b ON bd.bill_id = b.bill_id " +
                     "WHERE " + dateColumn + " BETWEEN " + startDate + " AND " + endDate +
                     " GROUP BY dc.drink_category_name ORDER BY SUM(bd.quantity) DESC";

        return queryDateValueMap(sql);
    }

    @Override
    public String getTotalRevenue(TimeRange range) throws SQLException {
        return String.format("$%,.2f", querySingleValue(buildTotalSQL("SUM(bd.price * bd.quantity)", range)));
    }

    @Override
    public String getTotalOrders(TimeRange range) throws SQLException {
        return String.valueOf((int) querySingleValue(buildTotalSQL("COUNT(DISTINCT b.bill_id)", range)));
    }

    @Override
    public String getTotalProfit(TimeRange range) throws SQLException {
        return String.format("$%,.2f", querySingleValue(buildTotalSQL("SUM(bd.price * bd.quantity) * 0.3", range)));
    }

    private String buildTotalSQL(String selectExpr, TimeRange range) {
        String dateColumn = "DATE(b.order_date)";
        String startDate = "";
        String endDate = "CURDATE()";
        switch (range) {
            case TODAY:
                startDate = "CURDATE()";
                endDate = "CURDATE()";
                break;
            case LAST_7_DAYS:
                startDate = "DATE_SUB(CURDATE(), INTERVAL 6 DAY)";
                break;
            case THIS_MONTH:
                startDate = "DATE_FORMAT(CURDATE(), '%Y-%m-01')";
                break;
            case LAST_MONTH:
                startDate = "DATE_FORMAT(DATE_SUB(CURDATE(), INTERVAL 1 MONTH), '%Y-%m-01')";
                endDate = "LAST_DAY(DATE_SUB(CURDATE(), INTERVAL 1 MONTH))";
                break;
        }

        return "SELECT " + selectExpr + " FROM bills b " +
               (selectExpr.contains("bd.") ? "JOIN bill_details bd ON bd.bill_id = b.bill_id " : "") +
               "WHERE " + dateColumn + " BETWEEN " + startDate + " AND " + endDate;
    }

    public static void main(String[] args) throws SQLException {
        StatisticDAOImpl impl = new StatisticDAOImpl();
        System.out.println("Revenue Last Month: " + impl.getRevenueData(TimeRange.LAST_MONTH));
        System.out.println("Profit Last Month: " + impl.getProfitData(TimeRange.LAST_MONTH));
        System.out.println("Orders Last Month (Bill Count): " + impl.getOrderData(TimeRange.LAST_MONTH));
        System.out.println("Orders Last Month (By Category): " + impl.getOrderDataByCategory(TimeRange.LAST_MONTH));
        System.out.println("Total Revenue Last Month: " + impl.getTotalRevenue(TimeRange.LAST_MONTH));
        System.out.println("Total Orders Last Month: " + impl.getTotalOrders(TimeRange.LAST_MONTH));
        System.out.println("Total Profit Last Month: " + impl.getTotalProfit(TimeRange.LAST_MONTH));
    }
}
