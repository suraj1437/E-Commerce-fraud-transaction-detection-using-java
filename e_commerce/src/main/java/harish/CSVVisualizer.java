package harish;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

public class CSVVisualizer {

    public static void main(String[] args) throws IOException {
        // Path to your CSV file
        String csvFile = "/Users/suraj/Desktop/ANALYSIS_JAVA/e_commerce/src/main/resources/COUNTRIES.csv";

        // Read CSV file
        Reader reader = new FileReader(csvFile);
        CSVParser csvParser = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader);

        // Map to store counts for each category
        Map<String, Integer> categoryCount = new HashMap<>();

        // Iterate through CSV records and count occurrences of each category
        for (CSVRecord record : csvParser) {
            // Assuming "Country" is the column you want to plot
            String category = record.get("Country");

            categoryCount.merge(category, 1, Integer::sum);
        }

        // Create a dataset for the bar chart
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        categoryCount.forEach((category, count) -> dataset.addValue(count, "Count", category));

        // Create the bar chart
        JFreeChart chart = ChartFactory.createBarChart(
                "Country Histogram",
                "Country",
                "Count",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        // Create a panel to display the chart
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(800, 600));

        // Display the chart in a JFrame
        JFrame frame = new JFrame("Country Histogram Visualization");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(chartPanel);
        frame.pack();
        frame.setVisible(true);

        // Close resources
        csvParser.close();
    }
}