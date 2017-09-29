import java.io.File;
import java.util.HashMap;

public class Collecter {
    private File file;
    private int order;
    private double alpha;
    private int generateLengt;
    private HashMap<String, HashMap<String, Integer>> words;

    public Collecter(String path, int order, double alpha) {
        readFile(path);
        this.order = order;
        this.alpha = alpha;
        this.words = new HashMap<>();
    }

    private void readFile(String path) {

    }

    public HashMap<String, HashMap<String, Integer>> getWords() {
        return words;
    }
}
