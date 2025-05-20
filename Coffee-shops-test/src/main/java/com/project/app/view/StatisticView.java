package com.project.app.view;

import javax.swing.*;
import java.awt.*;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import com.project.app.dao.StatisticDAO;
import com.project.app.dao.impl.StatisticDAOImpl;
import com.project.app.dao.impl.StatisticDAOImpl.TimeRange;

import javax.swing.border.CompoundBorder;
import java.util.Map;
import java.util.HashMap;
import java.sql.SQLException;

public class StatisticView extends JPanel {

    private static final Color BACKGROUND_COLOR = Color.decode("#F5F5F5");

    private StatisticDAO statisticDAO;

    private Map<String, Number> profitData;
    private Map<String, Number> revenueData;
    private Map<String, Number> orderData; // This will now hold category-wise order data

    private String totalRevenue;
    private String totalOrders;
    private String totalProfit;

    private static final Color DEEP_TEAL = Color.decode("#008080");
    private static final Color CREAM = Color.decode("#FFF8E1");
    private static final Color DARK_BROWN = Color.decode("#4E342E");
    private static final Color LIGHT_BROWN = Color.decode("#D7CCC8");

    public StatisticView() {
        this.statisticDAO = new StatisticDAOImpl(); // Initialize the DAO

        setLayout(new BorderLayout());
        setBackground(BACKGROUND_COLOR);

        add(createHeaderPanel(), BorderLayout.NORTH);
        add(createContentPanel(), BorderLayout.CENTER);

        // Load initial data (e.g., Last 7 Days)
        updateInformation(7);
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        headerPanel.setOpaque(false);

        JLabel titleLabel = new JLabel("Coffee Shop - Statistics and Reports", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        headerPanel.add(titleLabel);

        return headerPanel;
    }
    private JPanel createContentPanel() {
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setOpaque(false);

        contentPanel.add(createFilterPanel(), BorderLayout.NORTH);
        contentPanel.add(createGraphsAndDetailedPanels(), BorderLayout.CENTER);

        return contentPanel;
    }

    private JPanel createFilterPanel() {
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        filterPanel.setOpaque(false);

        filterPanel.add(createFilterButton("Today"));
        filterPanel.add(createFilterButton("Last 7 Days"));
        filterPanel.add(createFilterButton("This Month"));
        filterPanel.add(createFilterButton("Last Month"));

        return filterPanel;
    }

    private void updateInformation(int days) {
        TimeRange range = switch (days) {
            case 1 -> TimeRange.TODAY;
            case 7 -> TimeRange.LAST_7_DAYS;
            case 30 -> TimeRange.THIS_MONTH;
            case 60 -> TimeRange.LAST_MONTH;
            default -> TimeRange.LAST_7_DAYS; // Default to last 7 days
        };

        try {
            this.profitData = statisticDAO.getProfitData(range);
            this.revenueData = statisticDAO.getRevenueData(range);
            this.orderData = statisticDAO.getOrderDataByCategory(range); // Use the new method

            this.totalRevenue = statisticDAO.getTotalRevenue(range);
            this.totalOrders = statisticDAO.getTotalOrders(range);
            this.totalProfit = statisticDAO.getTotalProfit(range);

            updateStatistics(
                    this.profitData,
                    this.revenueData,
                    this.orderData,
                    this.totalRevenue,
                    this.totalOrders,
                    this.totalProfit
            );
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception, e.g., show an error message
            JOptionPane.showMessageDialog(this, "Error fetching statistics: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Creates a styled button for filtering.
     *
     * @param text The text for the button.
     * @return The styled JButton.
     */
    private JButton createFilterButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        button.setBackground(CREAM);
        button.setForeground(DARK_BROWN);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(LIGHT_BROWN, 1),
                BorderFactory.createEmptyBorder(8, 15, 8, 15)
        ));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        int days = switch (text) {
            case "Today" -> 1;
            case "Last 7 Days" -> 7;
            case "This Month" -> 30;
            case "Last Month" -> 60;
            default -> 0;
        };
        button.addActionListener(e -> {
            updateInformation(days);
        });
        return button;
    }


    /**
     * Creates the panel containing summary cards, graphs, and detailed panels.
     *
     * @return The JPanel containing graphs and detailed panels.
     */
    private JPanel createGraphsAndDetailedPanels() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);

        panel.add(createSummaryPanel(), BorderLayout.NORTH);
        panel.add(createGraphsPanel(), BorderLayout.CENTER);
        panel.add(createDetailedPanelsSection(), BorderLayout.SOUTH);

        return panel;
    }

    /**
     * Creates the summary panel with key metrics cards.
     *
     * @return The summary JPanel.
     */
    private JPanel createSummaryPanel() {
        JPanel summaryPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        summaryPanel.setOpaque(false);

        summaryPanel.add(createSummaryCard("Total Revenue", this.totalRevenue, ""));
        summaryPanel.add(createSummaryCard("Total Orders", this.totalOrders, ""));
        summaryPanel.add(createSummaryCard("Total Profit", this.totalProfit, ""));

        return summaryPanel;
    }

    /**
     * Creates a styled card for displaying summary information.
     *
     * @param label      The label for the card (e.g., "Total Revenue").
     * @param value      The main value to display (e.g., "$1,899").
     * @param comparison The comparison value (e.g., "+8.2%").
     * @return The styled summary JPanel card.
     */
    private JPanel createSummaryCard(String label, String value, String comparison) {
        JPanel cardPanel = new JPanel(new BorderLayout(0, 5));
        cardPanel.setBackground(Color.WHITE);
        cardPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(LIGHT_BROWN, 1),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));


        JLabel labelLabel = new JLabel(label);
        labelLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        labelLabel.setForeground(Color.GRAY);

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        valueLabel.setForeground(DARK_BROWN);

        JLabel comparisonLabel = new JLabel(comparison);
        comparisonLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        comparisonLabel.setForeground(DEEP_TEAL);

        cardPanel.add(labelLabel, BorderLayout.NORTH);
        cardPanel.add(valueLabel, BorderLayout.CENTER);
        cardPanel.add(comparisonLabel, BorderLayout.SOUTH);

        return cardPanel;
    }

    /**
     * Creates the panel containing the charts.
     *
     * @return The graphs JPanel.
     */
    private JPanel createGraphsPanel() {
        JPanel graphsPanel = new JPanel(new GridLayout(1, 3, 20, 0));
        graphsPanel.setOpaque(false);
        graphsPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        graphsPanel.add(createProfitOverTimeChartPanel());
        graphsPanel.add(createRevenueTrendChartPanel());
        graphsPanel.add(createOrderDistributionChartPanel());

        return graphsPanel;
    }

    /**
     * Creates a generic panel for hosting a chart.
     *
     * @param title The title of the chart panel.
     * @param chart The JFreeChart to display.
     * @return The JPanel containing the chart.
     */
    private JPanel createChartPanel(String title, JFreeChart chart) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(createStandardBorder());


        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        panel.add(titleLabel, BorderLayout.NORTH);

        if (chart != null) {
            ChartPanel chartPanel = new ChartPanel(chart);
            chartPanel.setPreferredSize(new Dimension(200, 150));
            panel.add(chartPanel, BorderLayout.CENTER);
        } else {

            JPanel chartPlaceholder = new JPanel();
            chartPlaceholder.setBackground(Color.LIGHT_GRAY);
            panel.add(chartPlaceholder, BorderLayout.CENTER);
        }

        return panel;
    }

    /**
     * Creates the panel for the Profit over Time area chart.
     *
     * @return The JPanel for the Profit over Time chart.
     */
    private JPanel createProfitOverTimeChartPanel() {
        CategoryDataset dataset = createProfitOverTimeDataset(this.profitData);
        JFreeChart chart = createProfitOverTimeChart(dataset);
        return createChartPanel("Profit over Time (Area Chart)", chart);
    }

    /**
     * Creates the dataset for the Profit over Time chart.
     *
     * @param profitData A map where keys are categories (e.g., days) and values are profit amounts.
     * @return The CategoryDataset for profit over time.
     */
    private CategoryDataset createProfitOverTimeDataset(java.util.Map<String, Number> profitData) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        if (profitData != null) {
            for (java.util.Map.Entry<String, Number> entry : profitData.entrySet()) {
                dataset.addValue(entry.getValue(), "Profit", entry.getKey());
            }
        }
        return dataset;
    }

    /**
     * Creates the JFreeChart for the Profit over Time area chart.
     *
     * @param dataset The dataset for the chart.
     * @return The JFreeChart for profit over time.
     */
    private JFreeChart createProfitOverTimeChart(CategoryDataset dataset) {
        JFreeChart chart = ChartFactory.createAreaChart(
                "Profit over Time",
                "Day",
                "Profit ($)",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );
        return chart;
    }

    /**
     * Creates the panel for the Revenue Trend line chart.
     *
     * @return The JPanel for the Revenue Trend chart.
     */
    private JPanel createRevenueTrendChartPanel() {
        CategoryDataset dataset = createRevenueTrendDataset(this.revenueData);
        JFreeChart chart = createRevenueTrendChart(dataset);
        return createChartPanel("Revenue Trend (Line Chart)", chart);
    }

    /**
     * Creates the dataset for the Revenue Trend chart.
     *
     * @param revenueData A map where keys are categories (e.g., days) and values are revenue amounts.
     * @return The CategoryDataset for revenue trend.
     */
    private CategoryDataset createRevenueTrendDataset(java.util.Map<String, Number> revenueData) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        if (revenueData != null) {
            for (java.util.Map.Entry<String, Number> entry : revenueData.entrySet()) {
                dataset.addValue(entry.getValue(), "Revenue", entry.getKey());
            }
        }
        return dataset;
    }

    /**
     * Creates the JFreeChart for the Revenue Trend line chart.
     *
     * @param dataset The dataset for the chart.
     * @return The JFreeChart for revenue trend.
     */
    private JFreeChart createRevenueTrendChart(CategoryDataset dataset) {
        JFreeChart chart = ChartFactory.createLineChart(
                "Revenue Trend",
                "Day",
                "Revenue ($)",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );
        return chart;
    }

    /**
     * Creates the panel for the Order Distribution pie chart.
     *
     * @return The JPanel for the Order Distribution chart.
     */
    private JPanel createOrderDistributionChartPanel() {
        PieDataset dataset = createOrderDistributionDataset(this.orderData);
        JFreeChart chart = createOrderDistributionChart(dataset);
        return createChartPanel("Order Distribution (Pie Chart)", chart);
    }

    /**
     * Creates the dataset for the Order Distribution chart.
     *
     * @param orderData A map where keys are item names and values are quantities or percentages.
     * @return The PieDataset for order distribution.
     */
    private PieDataset createOrderDistributionDataset(java.util.Map<String, Number> orderData) {
        DefaultPieDataset dataset = new DefaultPieDataset();
        if (orderData != null) {
            for (java.util.Map.Entry<String, Number> entry : orderData.entrySet()) {
                dataset.setValue(entry.getKey(), entry.getValue());
            }
        }
        return dataset;
    }

    /**
     * Creates the JFreeChart for the Order Distribution pie chart.
     *
     * @param dataset The dataset for the chart.
     * @return The JFreeChart for order distribution.
     */
    private JFreeChart createOrderDistributionChart(PieDataset dataset) {
        JFreeChart chart = ChartFactory.createPieChart(
                "Order Distribution",
                dataset,
                true,
                true,
                false
        );
        return chart;
    }

    /**
     * Creates the section containing detailed panels.
     *
     * @return The JPanel for detailed panels.
     */
    private JPanel createDetailedPanelsSection() {
        JPanel detailedPanels = new JPanel(new GridLayout(1, 3, 20, 0));
        detailedPanels.setOpaque(false);
        detailedPanels.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        return detailedPanels;
    }

    private JPanel createExportPanel() {
        JPanel exportPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 20));
        exportPanel.setOpaque(false);

        JButton exportButton = new JButton("Export Report");
        exportButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        exportButton.setBackground(DEEP_TEAL);
        exportButton.setForeground(Color.WHITE);
        exportButton.setFocusPainted(false);
        exportButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(DEEP_TEAL, 1),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        exportButton.setCursor(new Cursor(Cursor.HAND_CURSOR));


        exportPanel.add(exportButton);

        return exportPanel;
    }

    /**
     * Creates a standard compound border used for various panels and components.
     *
     * @return The CompoundBorder.
     */
    private CompoundBorder createStandardBorder() {

        return BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(LIGHT_BROWN, 1),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        );
    }

    /**
     * Updates the statistics displayed in the view.
     *
     * @param profitData   A map where keys are categories (e.g., days) and values are profit amounts.
     * @param revenueData  A map where keys are categories (e.g., days) and values are revenue amounts.
     * @param orderData    A map where keys are item names and values are quantities or percentages.
     * @param totalRevenue The total revenue string.
     * @param totalOrders  The total orders string.
     * @param totalProfit  The total profit string.
     */
    public void updateStatistics(Map<String, Number> profitData, Map<String, Number> revenueData, Map<String, Number> orderData, String totalRevenue, String totalOrders, String totalProfit) {
        this.profitData = profitData;
        this.revenueData = revenueData;
        this.orderData = orderData;
        this.totalRevenue = totalRevenue;
        this.totalOrders = totalOrders;
        this.totalProfit = totalProfit;

        removeAll();
        setLayout(new BorderLayout());
        setBackground(BACKGROUND_COLOR);

        add(createHeaderPanel(), BorderLayout.NORTH);
        add(createContentPanel(), BorderLayout.CENTER);
        revalidate();
        repaint();
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            StatisticView statisticView = new StatisticView();

            // The updateInformation method in the constructor will load initial data
            // No need for sample data here anymore

            frame.getContentPane().add(statisticView);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
