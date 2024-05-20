package harish;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class CSVStats {

    public static void main(String[] args) throws IOException {
        // Path to your CSV file
        String csvFile = "/Users/suraj/Desktop/ANALYSIS_JAVA/e_commerce/src/main/resources/CUSTOMER_DATA.csv.csv";

        // Read CSV file
        Reader reader = new FileReader(csvFile);
        CSVParser csvParser = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader);

        // Get column headers
        String[] headers = csvParser.getHeaderMap().keySet().toArray(new String[0]);

        // Initialize DescriptiveStatistics for each column
        DescriptiveStatistics[] columnStats = new DescriptiveStatistics[headers.length];
        for (int i = 0; i < headers.length; i++) {
            columnStats[i] = new DescriptiveStatistics();
        }

        // Iterate through CSV records
        for (CSVRecord record : csvParser) {
            for (int i = 0; i < headers.length; i++) {
                try {
                    double value = Double.parseDouble(record.get(headers[i]));
                    columnStats[i].addValue(value);
                } catch (NumberFormatException e) {
                    // Handle the case where the value is not a number (e.g., missing or malformed data)
                    System.err.println("Skipping non-numeric value: " + record.get(headers[i]));
                }
            }
        }

        // Print statistics for each column
        for (int i = 0; i < headers.length; i++) {
            System.out.println("Statistics for column '" + headers[i] + "':");
            System.out.println("Mean: " + columnStats[i].getMean());
            System.out.println("Median: " + columnStats[i].getPercentile(50));
            System.out.println("Standard Deviation: " + columnStats[i].getStandardDeviation());
            System.out.println("Minimum: " + columnStats[i].getMin());
            System.out.println("Maximum: " + columnStats[i].getMax());
            System.out.println("Variance: " + columnStats[i].getVariance());
            System.out.println("Skewness: " + columnStats[i].getSkewness());
            System.out.println("Kurtosis: " + columnStats[i].getKurtosis());
            System.out.println();
        }

        // Close resources
        csvParser.close();
        reader.close();
    }
}
