package Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * TAI, October 2017
 * <p>
 * Assignment 1 - Finite-context model and automatic text generation
 *
 * @author Bárbara Jael, 73241, barbara.jael@ua.pt
 * @author Miriam Cardoso, 72181, miriamcardoso@ua.pt
 * @author Tiago Faria, 73714, tiagohpf@ua.pt
 */

public class ProbManager {
    private List<Pair<String, AlphabetCount>> words;        // words of file
    private List<Pair<String, Integer>> counts;             // count of each words
    private List<Pair<String, AlphabetProb>> probs;        // probabilities
    private List<String> combinations;
    private List<String> alphabet;
    private double alpha;

    public ProbManager(List<Pair<String, AlphabetCount>> words, List<String> combinations,
                       double alpha, List<String> alphabet) {
        this.words = words;
        this.counts = new ArrayList<>();
        this.probs = new ArrayList<>();
        this.combinations = combinations;
        this.alpha = alpha;
        this.alphabet = alphabet;
        sumOfColumns();
        calculateProbabilities();

        //ToDO: Delete, test only
        System.out.println("Counts: " + counts);
        System.out.println("Probs: " + probs);
    }

    public double getEntropy() {
        int totalOccurrences = sumTotalOcurrences();
        double entropy = 0;
        for (Pair<String, Integer> wordOccurrences : counts) {
            String word = wordOccurrences.getKey();
            int occurrences = wordOccurrences.getValue();
            double h = 0;
            List<Pair<String, AlphabetProb>> filter = filterCollectionInProb(word);
            for (Pair<String, AlphabetProb> letterProb : filter) {
                double prob = letterProb.getValue().getProb();
                // log a (x) = log b (x) / log b (a)
                h += (prob * (Math.log10(prob) / Math.log10(2))) * (-1);
            }
            entropy += h * (occurrences * 1.0 / totalOccurrences);
        }
        return entropy;
    }

    private void sumOfColumns() {
        for (int i = 0; i < combinations.size(); i++) {
            String word = combinations.get(i);
            List<Pair<String, AlphabetCount>> filter = filterCollectionInCount(word);
            int sum = 0;
            for (Pair<String, AlphabetCount> pair : filter) {
                sum += pair.getValue().getNumber();
            }
            counts.add(new Pair<>(word, sum));
        }
    }

    private void calculateProbabilities() {
        for (Pair<String, Integer> wordOccurrences : counts) {
            String word = wordOccurrences.getKey();
            List<Pair<String, AlphabetCount>> filter = filterCollectionInCount(word);
            for (Pair<String, AlphabetCount> letterOccurrences : filter) {
                String letter = letterOccurrences.getValue().getLetter();
                int number = letterOccurrences.getValue().getNumber();
                double prob = (number + alpha) / (wordOccurrences.getValue() + alphabet.size() * alpha);
                probs.add(new Pair<>(word, new AlphabetProb(letter, prob)));
            }
        }
    }

    private int sumTotalOcurrences() {
        int sum = 0;
        for (Pair<String, Integer> wordOccurrences : counts)
            sum += wordOccurrences.getValue();
        return sum;
    }

    private List<Pair<String, AlphabetCount>> filterCollectionInCount(String word) {
        List<Pair<String, AlphabetCount>> filter =
                words.stream()
                        .filter(line -> line.getKey().equals(word))
                        .collect(Collectors.toList());
        return filter;
    }

    private List<Pair<String, AlphabetProb>> filterCollectionInProb(String word) {
        List<Pair<String, AlphabetProb>> filter =
                probs.stream()
                        .filter(line -> line.getKey().equals(word))
                        .collect(Collectors.toList());
        return filter;
    }
}
