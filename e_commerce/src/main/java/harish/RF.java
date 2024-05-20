package harish;

import weka.classifiers.trees.RandomForest;
import weka.core.Instances;
import weka.core.converters.CSVLoader;
import weka.classifiers.Evaluation;

import java.io.File;

public class RF {
    public static void main(String[] args) throws Exception {
        // Load CSV file
        CSVLoader loader = new CSVLoader();
        loader.setSource(new File("/Users/suraj/Desktop/ANALYSIS_JAVA/e_commerce/src/main/resources/CUSTOMER_DATA.csv.csv"));
        Instances data = loader.getDataSet();
        data.setClassIndex(data.numAttributes() - 1); // Assuming the class attribute is the last one

        // Initialize and build the classifier
        RandomForest rf = new RandomForest();
        rf.buildClassifier(data);

        // Evaluate the model
        Evaluation eval = new Evaluation(data);
        eval.evaluateModel(rf, data);

        // Print the evaluation results
        System.out.println(eval.toSummaryString());

        // Print the accuracy for each instance
        for (int i = 0; i < data.numInstances(); i++) {
            double actual = data.instance(i).classValue();
            double predicted = rf.classifyInstance(data.instance(i));
            System.out.println("Instance " + (i + 1) + ": Actual=" + actual + ", Predicted=" + predicted);
        }
    }
}

