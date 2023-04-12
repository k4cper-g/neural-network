import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Dummy {
    static AtomicInteger nextId = new AtomicInteger();
    int id;
    String name;
    List <Double> vector;

    /**
     * Two param constructor creates a Dummy instance with a passed name and vector.
     * Automatically increments dummy id.
     * @param vector - List of Double type values necessary for operations.
     * @param name - Name of the dummy.
     */
    Dummy(List<Double> vector, String name) {
        this.id = nextId.incrementAndGet();
        this.name = name;
        this.vector = vector;
    }

    /**
     * One param constructor creates a Dummy instance only with a passed vector.
     * Automatically increments dummy id.
     * @param vector - List of Double type values necessary for operations.
     */
    Dummy(List<Double> vector) {
        this.id = nextId.incrementAndGet();
        this.name = "test" + id;
        this.vector = vector;
    }

    /**
     * Allows to print the object values easily.
     * @return - Returns a String.
     */
    public String toString() {
        return this.vector + ":" + this.name + ":" + this.id;
    }

    /**
     * Allows to set the objects name.
     * @param name - String type name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Allows to get the dummies vector.
     * @return - List of doubles acting as a vector.
     */
    public List<Double> getVector() {
        return vector;
    }

    /**
     * Allows to set the dummies vector.
     * @param newVec - List of doubles passed as a new vector.
     */
    public void setVector(List<Double> newVec) {
        this.vector = newVec;
    }
}