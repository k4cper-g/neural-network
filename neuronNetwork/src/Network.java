import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;

public class Network {
    private final List<File> testDirs;
    private final List<File> trainDirs;
    private final List<Dummy> testDummies;
    private final List<Dummy> trainDummies;
    private final List<Perceptron> perceptrons;

    private final String[] args;

    /**
     * One parameter constructor, creates a Network instance.
     * @param args - String[] type param, holds multiple passed arguments.
     */

    Network(String[] args) {
        this.perceptrons = new ArrayList<>();
        this.trainDirs = new ArrayList<>();
        this.testDirs = new ArrayList<>();
        this.testDummies = new ArrayList<>();
        this.trainDummies = new ArrayList<>();
        this.args = args;
    }

    /**
     * Starts the Network, gathers necessary data through files.
     * Maps the labels for each Perceptron and starts them based on arguments passed in constructor.
     */
    public void start() {
        checkArgs();
        browseFiles();
        getTraining();
        trainDummies.forEach(System.out::println);

        for (File trainDir : trainDirs) {
            HashMap<Integer, String> map = new HashMap<>();
            map.put(1, trainDir.getName());
            map.put(0, "not");
            perceptrons.add(new Perceptron(trainDummies, testDummies, map));
        }
        perceptrons.forEach(Perceptron::start);
    }

    /**
     * Classifies passed Dummy through checking which neuron(Perceptron) activated.
     * If multiple/no neurons activated throws RuntimeException.
     * @param d - Dummy object.
     */
    public void classify(Dummy d) {
        List<String> active = new ArrayList<>();

        for(Perceptron p: perceptrons) {
            int y = p.getY(d);
            if(y == 1) {
                active.add(p.getName());
            }
        }
        if(active.size() != 1) {
            throw new RuntimeException("No neurons activated.");
        }
        if(active.size() == 1) {
            d.setName(active.get(0));
        }

    }

    /**
     * Checks necessary arguments.
     * Argument 0 - training directory.
     * Argument 1 - test directory.
     */
    public void checkArgs() {
        if(args.length != 2) {
            throw new ArrayIndexOutOfBoundsException("Blednie podane argumenty.");
        }
        File f1 = new File(args[0]);
        File f2 = new File(args[1]);

        if(!f1.isDirectory()) {
            throw new InvalidPathException(args[0], "Nie istnieje taka sciezka.");
        }
        if(!f2.isDirectory()) {
            throw new InvalidPathException(args[1], "Nie istnieje taka sciezka.");
        }
        File[] files1 = f1.listFiles();
        File[] files2 = f2.listFiles();
        if(files1 == null || files2 == null) {
            throw new RuntimeException("Sciezka nie zawiera zadnych plikow.");
        }
    }

    /**
     * Gets test Dummies(Files with data) and adds them to the Network list making it possible to use them.
     */
    public void getTest() {
        for(File f: testDirs) {
            File[] files = f.listFiles();

            assert files != null;

            for(File fi: files) {
                testDummies.add(new Dummy(getProportions(fi), f.getName()));
            }
        }
    }

    /**
     * Gets training Dummies(Files with data) and adds them to the Network list making it possible to use them.
     */
    public void getTraining() {
        for(File f: trainDirs) {
            File[] files = f.listFiles();

            assert files != null;

            for(File fi: files) {
                trainDummies.add(new Dummy(getProportions(fi), f.getName()));
            }
        }
    }

    /**
     * Gets all file directories and adds them to respective lists.
     */
    public void browseFiles() {
        File f1 = new File(args[0]);
        File f2 = new File(args[1]);
        File[] files1 = f1.listFiles();
        File[] files2 = f2.listFiles();

        assert files1 != null;
        assert files2 != null;

        trainDirs.addAll(Arrays.asList(files1));
        testDirs.addAll(Arrays.asList(files2));

    }

    /**
     * Gets proportions of each letter from passed file and creates a vector (List of doubles) expressing that proportion.
     * @param f - File type param.
     * @return - List of doubles.
     */
    public List<Double> getProportions(File f) {
        List<Double> vector = new ArrayList<>();
        HashMap<Character, Integer> map = new HashMap<>();
        for (char ch = 'a'; ch <= 'z'; ++ch) {
            map.put(ch, 0);
        }

        String content = getFileContent(f);
        char[] contentArr = content.toCharArray();

        for (char c : contentArr) {
            if (map.containsKey(c)) {
                map.replace(c, map.get(c) + 1);
            }
        }

        for (Map.Entry<Character, Integer> entry : map.entrySet()) {
            vector.add(Double.valueOf(entry.getValue()));
        }

        return vector;
    }

    /**
     * Gets all text from a file and filters it to only have letters from 'a' to 'z'.
     * @param f - File type param.
     * @return - String type file content.
     */

    public String getFileContent(File f) {
        StringBuilder content = new StringBuilder();
            try {
                content.append(Files.readString(Path.of(f.getAbsolutePath())).replaceAll("[^a-zA-Z_]", "").toLowerCase());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return content.toString();
    }
}
