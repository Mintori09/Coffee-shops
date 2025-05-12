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

public class StatisticView extends JPanel {

    public StatisticView() {
        setLayout(new BorderLayout());
        setBackground(Color.decode("#F5F5F5")); // Background color

        // Header
        JLabel titleLabel = new JLabel("Coffee Shop - Statistics and Reports", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0)); // Add padding
        add(titleLabel, BorderLayout.NORTH);

        // Placeholder for the rest of the content
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setOpaque(false); // Make it transparent to show the background color

        // Filter Bar
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        filterPanel.setOpaque(false); // Make it transparent

        JButton todayButton = createFilterButton("Today");
        JButton last7DaysButton = createFilterButton("Last 7 Days");
        JButton thisMonthButton = createFilterButton("This Month");
        JButton lastMonthButton = createFilterButton("Last Month");

        filterPanel.add(todayButton);
        filterPanel.add(last7DaysButton);
        filterPanel.add(thisMonthButton);
        filterPanel.add(lastMonthButton);

        contentPanel.add(filterPanel, BorderLayout.NORTH);

        // Summary Panel
        JPanel summaryPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        summaryPanel.setOpaque(false);

        summaryPanel.add(createSummaryCard("Total Revenue", "$1,899", "+8.2%"));
        summaryPanel.add(createSummaryCard("Total Orders", "150", "+5.1%"));
        summaryPanel.add(createSummaryCard("Total Profit", "$650", "+12.5%"));

        contentPanel.add(summaryPanel, BorderLayout.NORTH); // Change to NORTH to place summary above graphs

        // Graphs Section
        JPanel graphsPanel = new JPanel(new GridLayout(1, 3, 20, 0)); // 1 row, 3 columns, 20px horizontal gap
        graphsPanel.setOpaque(false);
        graphsPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // Add padding

        graphsPanel.add(createChartPanel("Profit over Time (Area Chart)"));
        graphsPanel.add(createChartPanel("Revenue Trend (Line Chart)"));
        graphsPanel.add(createChartPanel("Order Distribution (Pie Chart)"));

        contentPanel.add(graphsPanel, BorderLayout.CENTER); // Add graphs panel to the center of contentPanel

        // Detailed Panels Section
        JPanel detailedPanels = new JPanel(new GridLayout(1, 3, 20, 0)); // Example: 3 panels side by side
        detailedPanels.setOpaque(false);
        detailedPanels.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // Add padding

        detailedPanels.add(createDetailedPanel("Best-Selling Products"));
        detailedPanels.add(createDetailedPanel("Revenue by Product"));
        detailedPanels.add(createDetailedPanel("Revenue by Staff"));

        contentPanel.add(detailedPanels, BorderLayout.SOUTH); // Add detailed panels below graphs

        // Export Button
        JButton exportButton = new JButton("Export Report");
        exportButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        exportButton.setBackground(Color.decode("#008080")); // Deep teal background
        exportButton.setForeground(Color.WHITE); // White text
        exportButton.setFocusPainted(false);
        exportButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.decode("#008080"), 1), // Deep teal border
                BorderFactory.createEmptyBorder(10, 20, 10, 20) // Padding
        ));
        exportButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        // Add rounded corners (requires custom painting or a look and feel)
        // For simplicity, we'll skip true rounded corners with standard Swing buttons

        JPanel exportPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 20));
        exportPanel.setOpaque(false);
        exportPanel.add(exportButton);

        add(exportPanel, BorderLayout.SOUTH); // Add export button to the main panel's SOUTH

        add(contentPanel, BorderLayout.CENTER);
    }

    private JButton createFilterButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        button.setBackground(Color.decode("#FFF8E1")); // Cream background
        button.setForeground(Color.decode("#4E342E")); // Dark brown text
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.decode("#D7CCC8"), 1), // Light brown border
                BorderFactory.createEmptyBorder(8, 15, 8, 15) // Padding
        ));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        // Add rounded corners (requires custom painting or a look and feel)
        // For simplicity, we'll skip true corners with standard Swing buttons
        return button;
    }

    private JPanel createSummaryCard(String label, String value, String comparison) {
        JPanel cardPanel = new JPanel(new BorderLayout(0, 5));
        cardPanel.setBackground(Color.WHITE);
        cardPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.decode("#D7CCC8"), 1), // Light brown border
                BorderFactory.createEmptyBorder(15, 15, 15, 15) // Padding
        ));
        // Add rounded corners and shadow (requires custom painting or a look and feel)
        // For simplicity, we'll skip true rounded corners and shadows with standard Swing panels

        JLabel labelLabel = new JLabel(label);
        labelLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        labelLabel.setForeground(Color.GRAY);

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        valueLabel.setForeground(Color.decode("#4E342E")); // Dark brown text

        JLabel comparisonLabel = new JLabel(comparison);
        comparisonLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        comparisonLabel.setForeground(Color.decode("#008080")); // Teal text

        cardPanel.add(labelLabel, BorderLayout.NORTH);
        cardPanel.add(valueLabel, BorderLayout.CENTER);
        cardPanel.add(comparisonLabel, BorderLayout.SOUTH);

        return cardPanel;
    }

    private JPanel createChartPanel(String title) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.decode("#D7CCC8"), 1), // Light brown border
                BorderFactory.createEmptyBorder(10, 10, 10, 10) // Padding
        ));
        // Add rounded corners (requires custom painting or a look and feel)
        // For simplicity, we'll skip true rounded corners with standard Swing panels

        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        panel.add(titleLabel, BorderLayout.NORTH);

        JFreeChart chart = null;

        if ("Profit over Time (Area Chart)".equals(title)) {
            CategoryDataset dataset = createProfitOverTimeDataset();
            chart = createProfitOverTimeChart(dataset);
        } else if ("Revenue Trend (Line Chart)".equals(title)) {
            CategoryDataset dataset = createRevenueTrendDataset();
            chart = createRevenueTrendChart(dataset);
        } else if ("Order Distribution (Pie Chart)".equals(title)) {
            PieDataset dataset = createOrderDistributionDataset();
            chart = createOrderDistributionChart(dataset);
        }

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

    private JPanel createDetailedPanel(String title) {
        JPanel detailedPanel = new JPanel(new BorderLayout());
        detailedPanel.setBackground(Color.WHITE);
        detailedPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.decode("#D7CCC8"), 1), // Light brown border
                BorderFactory.createEmptyBorder(10, 10, 10, 10) // Padding
        ));
        // Add rounded corners (requires custom painting or a look and feel)
        // For simplicity, we'll skip true rounded corners with standard Swing panels

        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        detailedPanel.add(titleLabel, BorderLayout.NORTH);

        // Placeholder for detailed content (e.g., table, list)
        JTextArea contentPlaceholder = new JTextArea("Placeholder for " + title);
        contentPlaceholder.setEditable(false);
        contentPlaceholder.setLineWrap(true);
        contentPlaceholder.setWrapStyleWord(true);
        contentPlaceholder.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        detailedPanel.add(new JScrollPane(contentPlaceholder), BorderLayout.CENTER); // Use JScrollPane for potential scrolling

        return detailedPanel;
    }

    private CategoryDataset createProfitOverTimeDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(100, "Profit", "Mon");
        dataset.addValue(150, "Profit", "Tue");
        dataset.addValue(120, "Profit", "Wed");
        dataset.addValue(180, "Profit", "Thu");
        dataset.addValue(200, "Profit", "Fri");
        dataset.addValue(250, "Profit", "Sat");
        dataset.addValue(220, "Profit", "Sun");
        return dataset;
    }

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

    private CategoryDataset createRevenueTrendDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(500, "Revenue", "Mon");
        dataset.addValue(600, "Revenue", "Tue");
        dataset.addValue(550, "Revenue", "Wed");
        dataset.addValue(700, "Revenue", "Thu");
        dataset.addValue(750, "Revenue", "Fri");
        dataset.addValue(800, "Revenue", "Sat");
        dataset.addValue(780, "Revenue", "Sun");
        return dataset;
    }

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

    private PieDataset createOrderDistributionDataset() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("Coffee", 40);
        dataset.setValue("Pastries", 30);
        dataset.setValue("Sandwiches", 20);
        dataset.setValue("Drinks", 10);
        return dataset;
    }

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

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new StatisticView());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
