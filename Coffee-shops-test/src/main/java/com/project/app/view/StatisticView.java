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

import javax.swing.border.CompoundBorder;
import java.util.Map;
import java.util.HashMap;

/**
 * StatisticView class displays various statistics and reports for the coffee shop.
 * It includes filter buttons, summary cards, charts, and detailed panels.
 */
public class StatisticView extends JPanel {

    private static final Color BACKGROUND_COLOR = Color.decode("#F5F5F5");

    private Map<String, Number> profitData;
    private Map<String, Number> revenueData;
    private Map<String, Number> orderData;

    private String totalRevenue;
    private String totalOrders;
    private String totalProfit;

    private static final Color DEEP_TEAL = Color.decode("#008080");
    private static final Color CREAM = Color.decode("#FFF8E1");
    private static final Color DARK_BROWN = Color.decode("#4E342E");
    private static final Color LIGHT_BROWN = Color.decode("#D7CCC8");

    public StatisticView() {
        setLayout(new BorderLayout());
        setBackground(BACKGROUND_COLOR);

        add(createHeaderPanel(), BorderLayout.NORTH);
        add(createContentPanel(), BorderLayout.CENTER);
//        add(createExportPanel(), BorderLayout.SOUTH);
    }

    /**
     * Creates the header panel with the title.
     *
     * @return The header JPanel.
     */
    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        headerPanel.setOpaque(false); // Make it transparent to show the background color

        JLabel titleLabel = new JLabel("Coffee Shop - Statistics and Reports", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0)); // Add padding
        headerPanel.add(titleLabel);

        return headerPanel;
    }

    /**
     * Creates the main content panel containing filters, summaries, graphs, and detailed panels.
     *
     * @return The content JPanel.
     */
    private JPanel createContentPanel() {
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setOpaque(false); // Make it transparent to show the background color

        contentPanel.add(createFilterPanel(), BorderLayout.NORTH);
        contentPanel.add(createGraphsAndDetailedPanels(), BorderLayout.CENTER);

        return contentPanel;
    }

    /**
     * Creates the filter panel with time range buttons.
     *
     * @return The filter JPanel.
     */
    private JPanel createFilterPanel() {
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        filterPanel.setOpaque(false); // Make it transparent

        filterPanel.add(createFilterButton("Today"));
        filterPanel.add(createFilterButton("Last 7 Days"));
        filterPanel.add(createFilterButton("This Month"));
        filterPanel.add(createFilterButton("Last Month"));

        return filterPanel;
    }

    private void updateInformation(int days) {
        // TODO: Replace mock data with actual query based on 'days'

        Map<String, Number> profitData = new HashMap<>();
        Map<String, Number> revenueData = new HashMap<>();
        Map<String, Number> orderData = new HashMap<>();

        if (days == 1) {
            profitData.put("Today", 150);
            revenueData.put("Today", 600);
            orderData.put("Coffee", 20);
            orderData.put("Pastries", 10);
        } else if (days == 7) {
            profitData.put("Mon", 120);
            profitData.put("Tue", 180);
            profitData.put("Wed", 150);
            profitData.put("Thu", 200);
            profitData.put("Fri", 220);
            profitData.put("Sat", 280);
            profitData.put("Sun", 250);

            revenueData.put("Mon", 550);
            revenueData.put("Tue", 650);
            revenueData.put("Wed", 600);
            revenueData.put("Thu", 750);
            revenueData.put("Fri", 800);
            revenueData.put("Sat", 850);
            revenueData.put("Sun", 820);

            orderData.put("Coffee", 45);
            orderData.put("Pastries", 25);
            orderData.put("Sandwiches", 25);
            orderData.put("Drinks", 5);
        } else if (days == 30) {
            // Giả lập tháng này
            // Bạn có thể gom nhóm theo tuần hoặc ngày
            revenueData.put("Week 1", 1500);
            revenueData.put("Week 2", 1800);
            revenueData.put("Week 3", 1600);
            revenueData.put("Week 4", 2000);

            profitData.put("Week 1", 500);
            profitData.put("Week 2", 550);
            profitData.put("Week 3", 520);
            profitData.put("Week 4", 600);

            orderData.put("Coffee", 160);
            orderData.put("Pastries", 100);
            orderData.put("Sandwiches", 90);
            orderData.put("Drinks", 40);
        } else if (days == 60) {
            // Giả lập tháng trước
            revenueData.put("Week 1", 1400);
            revenueData.put("Week 2", 1700);
            revenueData.put("Week 3", 1500);
            revenueData.put("Week 4", 1900);

            profitData.put("Week 1", 450);
            profitData.put("Week 2", 500);
            profitData.put("Week 3", 480);
            profitData.put("Week 4", 570);

            orderData.put("Coffee", 150);
            orderData.put("Pastries", 80);
            orderData.put("Sandwiches", 85);
            orderData.put("Drinks", 35);
        }

        // Tổng kết mô phỏng
        String totalRevenue = "$2,500";
        String totalOrders = "200";
        String totalProfit = "$800";

        // Cập nhật giao diện thống kê
        updateStatistics(
                profitData,
                revenueData,
                orderData,
                totalRevenue,
                totalOrders,
                totalProfit
        );
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

        summaryPanel.add(createSummaryCard("Total Revenue", this.totalRevenue, "")); // Comparison can be made dynamic later
        summaryPanel.add(createSummaryCard("Total Orders", this.totalOrders, "")); // Comparison can be made dynamic later
        summaryPanel.add(createSummaryCard("Total Profit", this.totalProfit, "")); // Comparison can be made dynamic later

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
                BorderFactory.createEmptyBorder(15, 15, 15, 15) // Keep larger padding for summary cards
        ));
        // Add rounded corners and shadow (requires custom painting or a look and feel)
        // For simplicity, we'll skip true rounded corners and shadows with standard Swing panels

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
        JPanel graphsPanel = new JPanel(new GridLayout(1, 3, 20, 0)); // 1 row, 3 columns, 20px horizontal gap
        graphsPanel.setOpaque(false);
        graphsPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // Add padding

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
        // Add rounded corners (requires custom painting or a look and feel)
        // For simplicity, we'll skip true rounded corners with standard Swing panels

        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        panel.add(titleLabel, BorderLayout.NORTH);

        if (chart != null) {
            ChartPanel chartPanel = new ChartPanel(chart);
            chartPanel.setPreferredSize(new Dimension(200, 150)); // Set preferred size
            panel.add(chartPanel, BorderLayout.CENTER);
        } else {
            // Keep the placeholder if chart creation failed
            JPanel chartPlaceholder = new JPanel();
            chartPlaceholder.setBackground(Color.LIGHT_GRAY); // Placeholder color
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
                "Profit over Time", // Chart title
                "Day", // X-axis label
                "Profit ($)", // Y-axis label
                dataset, // Dataset
                PlotOrientation.VERTICAL,
                true, // Include legend
                true, // Tooltips
                false // URLs
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
                "Revenue Trend", // Chart title
                "Day", // X-axis label
                "Revenue ($)", // Y-axis label
                dataset, // Dataset
                PlotOrientation.VERTICAL,
                true, // Include legend
                true, // Tooltips
                false // URLs
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
                "Order Distribution", // Chart title
                dataset, // Dataset
                true, // Include legend
                true, // Tooltips
                false // URLs
        );
        return chart;
    }

    /**
     * Creates the section containing detailed panels.
     *
     * @return The JPanel for detailed panels.
     */
    private JPanel createDetailedPanelsSection() {
        JPanel detailedPanels = new JPanel(new GridLayout(1, 3, 20, 0)); // Example: 3 panels side by side
        detailedPanels.setOpaque(false);
        detailedPanels.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // Add padding

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
        // Add rounded corners (requires custom painting or a look and feel)
        // For simplicity, we'll skip true rounded corners with standard Swing buttons

        exportPanel.add(exportButton);

        return exportPanel;
    }

    /**
     * Creates a standard compound border used for various panels and components.
     *
     * @return The CompoundBorder.
     */
    private CompoundBorder createStandardBorder() {
        // Create a compound border with a light brown line and standard padding
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

        // Refresh the charts and summary panels
        // This is a simplified approach; a more robust solution might update existing charts
        // instead of recreating panels. However, for this task, recreating is sufficient
        // to demonstrate passing data.
        removeAll();
        setLayout(new BorderLayout());
        setBackground(BACKGROUND_COLOR);

        add(createHeaderPanel(), BorderLayout.NORTH);
        add(createContentPanel(), BorderLayout.CENTER);
        revalidate();
        repaint();
    }


    /**
     * Main method to create and display the StatisticView in a JFrame.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        StatisticView statisticView = new StatisticView();

        Map<String, Number> sampleProfitData = new HashMap<>();
        sampleProfitData.put("Mon", 120);
        sampleProfitData.put("Tue", 180);
        sampleProfitData.put("Wed", 150);
        sampleProfitData.put("Thu", 200);
        sampleProfitData.put("Fri", 220);
        sampleProfitData.put("Sat", 280);
        sampleProfitData.put("Sun", 250);

        Map<String, Number> sampleRevenueData = new HashMap<>();
        sampleRevenueData.put("Mon", 550);
        sampleRevenueData.put("Tue", 650);
        sampleRevenueData.put("Wed", 600);
        sampleRevenueData.put("Thu", 750);
        sampleRevenueData.put("Fri", 800);
        sampleRevenueData.put("Sat", 850);
        sampleRevenueData.put("Sun", 820);

        Map<String, Number> sampleOrderData = new HashMap<>();
        sampleOrderData.put("Coffee", 45);
        sampleOrderData.put("Pastries", 25);
        sampleOrderData.put("Sandwiches", 25);
        sampleOrderData.put("Drinks", 5);

        String sampleTotalRevenue = "$2,500";
        String sampleTotalOrders = "200";
        String sampleTotalProfit = "$800";

        statisticView.updateStatistics(sampleProfitData, sampleRevenueData, sampleOrderData, sampleTotalRevenue, sampleTotalOrders, sampleTotalProfit);

        frame.getContentPane().add(statisticView);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
