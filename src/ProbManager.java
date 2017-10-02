import java.util.ArrayList;
import java.util.List;

public class ProbManager {
    List<Pair<String, AlphabetCount>> words;        //words of file
    List<Pair<String, Integer>> counts;               //count of each words
    List<Pair<String, AlphabetCount>> probs;        //probabilities
    public ProbManager(List<Pair<String, AlphabetCount>> words) {
        this.words = words;
        this.counts = new ArrayList<Pair<String, Integer>>();
        this.probs = new ArrayList<Pair<String, AlphabetCount>>();
    }
}
