import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Perceptron {
    int accuracy;
    int tr;
    int k;
    File trainFile;
    Map<Integer, String> map;
    File testFile;
    int threshold;
    List<Double> weightVector;
    List<Dummy> trainSet;
    List<Dummy> testSet;

    /**
     * One param constructor, creates a Perceptron instance with passed args.
     * @param args - String[] type. Holds multiple arguments passed through the param.
     */

    Perceptron(String[] args) {
        this.threshold = (int)(Math.random()*5);
        this.weightVector = new ArrayList<>();
        this.map = new HashMap<>();
        this.trainSet = new ArrayList<>();
        this.testSet = new ArrayList<>();
        this.trainFile = new File(args[1]);
        this.testFile = new File(args[2]);

    }

    /**
     * Three parameter constructor, creates a Perceptron instance with passed:
     * -List of training dummies.
     * -List of testing dummies.
     * -Map of Integer,String values.
     * It also randomizes the threshold of the Perceptron.
     *
     * @param trainSet - List to store training dummies.
     * @param testSet - List to store testing dummies.
     * @param map - Map holding values {0,1} used for distinguishing two possible outcomes.
     */
    Perceptron(List<Dummy> trainSet, List<Dummy> testSet, Map<Integer, String> map) {
        this.threshold = (int)(Math.random()*5);
        this.weightVector = new ArrayList<>();
        this.map = map;
        this.trainSet = trainSet;
        this.testSet = testSet;
    }

    /**
     * Prints out information about the current Perceptron,
     * uses the method "randomizeWeightVector()" to make a random weight vector,
     * shuffles the trainSet.
     * Trains until Perceptron accuracy reaches 100%.
     */
    public void start() {
        System.out.println("\nPerceptron [" + getName() + "] running...");
        System.out.println(map);
        randomizeWeightVector();
        System.out.println("Threshold: " + threshold);
        Collections.shuffle(trainSet);

        while(accuracy != 100) {
            training();
        }

    }

    /**
     * Randomizes the starting weight vector from values 0 to 5.
     */
    public void randomizeWeightVector() {
        int size = trainSet.get(0).vector.size();
        for(int i = 0; i < size; i++) {
            int d = (int)(Math.random()*5);
            weightVector.add((double) d);
        }
        System.out.println("Starting weight vector: " + weightVector);
    }

    /**
     * Uses the getY() method to fetch the passed dummies Y value and assigns name according to the Perceptron map.
     * @param d - Dummy object soon to be classified.
     */
    public void classify(Dummy d) {
        int y = getY(d);

        if(y == 1) {
            d.setName(map.get(1));
        }
        if(y == 0) {
            d.setName(map.get(0));
        }
    }

    /**
     * Calculates the Y value based on net >= 0.
     * @param d - Dummy object.
     * @return - int value of {0,1}.
     */
    public int getY(Dummy d) {
        int sum = 0;
        int size = d.vector.size();
        for(int i = 0; i < size; i++) {
            sum += d.vector.get(i)*weightVector.get(i);
        }
        int net = sum - threshold;

        return net >= 0 ? 1 : 0;
    }

    /**
     * Using the delta algorithm, trains the Perceptron to activate on desired class
     * and calculates the accuracy of each training session.
     */
    public void training() {
        tr+=1;
        System.out.println("\nTraining [" + tr + "]");
        float len = trainSet.size();
        int bad = 0;


        for (Dummy dummy : trainSet) {
            int y = getY(dummy);
            if (y == 1) {
                if (!dummy.name.equals(map.get(1))) {
                    bad++;
                    delta(y, dummy);
                }
            }
            if (y == 0) {
                if (dummy.name.equals(map.get(1))) {
                    bad++;
                    delta(y, dummy);
                }
            }
        }
        float down = bad+len;
        int acc = (int) ((len/down)*100);

        accuracy = acc;

        System.out.println("Training accuracy: " + acc + "%");
    }

    /**
     * Algorithm for modifying weight vector and threshold. This allows the Perceptron to calibrate and keep learning to classify each object.
     *
     * @param y - Fetched y calculated for Dummy object.
     * @param dummy - Dummy object.
     */

    public void delta(int y, Dummy dummy) {
        List<Double> newWeight = new ArrayList<>();
        int d;
        if(y == 1) {
            d = 0;
        } else if(y == 0) {
            d = 1;
        } else {
            return;
        }
        int alpha = 1;
        List<Double> P = new ArrayList<>(dummy.vector);
        int t = threshold;

        int tmp = (d-y)*alpha;

        P.replaceAll(aDouble -> aDouble * tmp);

        for(int i = 0; i < P.size(); i++) {
            double dub = P.get(i) + weightVector.get(i);
            newWeight.add(dub);
        }
        weightVector = newWeight;
        threshold = t - (d-y)*alpha;
    }

    /**
     * Shows training dummies.
     */
    public void showTrainSet() {
        trainSet.forEach(System.out::println);
    }

    /**
     * Shows testing dummies
     */
    public void showTestSet() {
        testSet.forEach(System.out::println);
    }

    /**
     * Checks if a String is parsable.
     * @param s - String type param.
     * @return - returns boolean {false,true}.
     */

    public static boolean isDouble(String s) {
        try {
            Double.parseDouble(s);
            return true;
        }catch(Exception e) {
            return false;
        }
    }

    /**
     * Allows to get Perceptrons name.
     * @return - String type name.
     */
    public String getName() {
        return map.get(1);
    }
}

