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

/**
 * StatisticView class displays various statistics and reports for the coffee shop.
 * It includes filter buttons, summary cards, charts, and detailed panels.
 */
public class StatisticView extends JPanel {

    // Colors used in the view
    private static final Color BACKGROUND_COLOR = Color.decode("#F5F5F5");
    private static final Color DEEP_TEAL = Color.decode("#008080");
    private static final Color CREAM = Color.decode("#FFF8E1");
    private static final Color DARK_BROWN = Color.decode("#4E342E");
    private static final Color LIGHT_BROWN = Color.decode("#D7CCC8");

    /**
     * Constructs a new StatisticView.
     */
    public StatisticView() {
        setLayout(new BorderLayout());
        setBackground(BACKGROUND_COLOR);

        // Initialize and add components
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
        // Add rounded corners (requires custom painting or a look and feel)
        // For simplicity, we'll skip true corners with standard Swing buttons
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

        summaryPanel.add(createSummaryCard("Total Revenue", "$1,899", "+8.2%"));
        summaryPanel.add(createSummaryCard("Total Orders", "150", "+5.1%"));
        summaryPanel.add(createSummaryCard("Total Profit", "$650", "+12.5%"));

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
        CategoryDataset dataset = createProfitOverTimeDataset();
        JFreeChart chart = createProfitOverTimeChart(dataset);
        return createChartPanel("Profit over Time (Area Chart)", chart);
    }

    /**
     * Creates the dataset for the Profit over Time chart.
     *
     * @return The CategoryDataset for profit over time.
     */
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
        CategoryDataset dataset = createRevenueTrendDataset();
        JFreeChart chart = createRevenueTrendChart(dataset);
        return createChartPanel("Revenue Trend (Line Chart)", chart);
    }

    /**
     * Creates the dataset for the Revenue Trend chart.
     *
     * @return The CategoryDataset for revenue trend.
     */
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
        PieDataset dataset = createOrderDistributionDataset();
        JFreeChart chart = createOrderDistributionChart(dataset);
        return createChartPanel("Order Distribution (Pie Chart)", chart);
    }

    /**
     * Creates the dataset for the Order Distribution chart.
     *
     * @return The PieDataset for order distribution.
     */
    private PieDataset createOrderDistributionDataset() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("Coffee", 40);
        dataset.setValue("Pastries", 30);
        dataset.setValue("Sandwiches", 20);
        dataset.setValue("Drinks", 10);
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

        detailedPanels.add(createDetailedPanel("Best-Selling Products"));
        detailedPanels.add(createDetailedPanel("Revenue by Product"));
        detailedPanels.add(createDetailedPanel("Revenue by Staff"));

        return detailedPanels;
    }

    /**
     * Creates a generic panel for detailed content (e.g., tables, lists).
     *
     * @param title The title of the detailed panel.
     * @return The JPanel for detailed content.
     */
    private JPanel createDetailedPanel(String title) {
        JPanel detailedPanel = new JPanel(new BorderLayout());
        detailedPanel.setBackground(Color.WHITE);
        detailedPanel.setBorder(createStandardBorder());
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

    /**
     * Creates the panel containing the export button.
     *
     * @return The export JPanel.
     */
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
     * Main method to create and display the StatisticView in a JFrame.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new StatisticView());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
