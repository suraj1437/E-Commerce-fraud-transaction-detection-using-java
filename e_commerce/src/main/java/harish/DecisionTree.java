package harish;

import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.core.converters.CSVLoader;
import weka.classifiers.Evaluation;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.JFrame;
import java.io.File;

public class DecisionTree {

    public static void main(String[] args) throws Exception {
        // Load CSV file with modified data
        CSVLoader loader = new CSVLoader();
        loader.setSource(new File("e_commerce/src/main/resources/CUSTOMER_DATA.csv.csv"));
        Instances data = loader.getDataSet();

        // Set the class index
        data.setClassIndex(data.numAttributes() - 1);

        // Initialize and build the classifier (J48 decision tree)
        J48 tree = new J48();
        tree.buildClassifier(data);

        // Print the decision tree
        System.out.println("Decision Tree:");
        System.out.println(tree.graph());

        // Evaluate the model
        Evaluation eval = new Evaluation(data);
        eval.evaluateModel(tree, data);

        // Print the evaluation results
        System.out.println(eval.toSummaryString());

        // Print the class predictions
        System.out.println("Predictions:");
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        double threshold = 0.5; // Threshold for classifying as fraud or not fraud
        for (int i = 0; i < data.numInstances(); i++) {
            double actual = data.instance(i).classValue();
            double[] distribution = tree.distributionForInstance(data.instance(i));
            String predictedClass = (distribution[1] >= threshold) ? "fraud" : "not fraud"; // Use index 1 for fraud class
            System.out.println("Instance " + (i + 1) + ": Actual=" + actual + ", Predicted=" + predictedClass);

            // Add to dataset for line chart
            dataset.addValue(actual, "Actual", String.valueOf(i + 1));
            dataset.addValue(distribution[1], "Predicted", String.valueOf(i + 1)); // Use predicted probability for line chart
        }

        // Create line chart
        JFreeChart lineChart = ChartFactory.createLineChart(
                "Actual vs Predicted",
                "Instance",
                "Probability",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        // Create and display the chart in a JFrame
        JFrame frame = new JFrame("Actual vs Predicted");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ChartPanel chartPanel = new ChartPanel(lineChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(800, 600));
        frame.setContentPane(chartPanel);
        frame.pack();
        frame.setLocationRelativeTo(null); // Center the frame
        frame.setVisible(true);
    }
}
