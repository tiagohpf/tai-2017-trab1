package Probabilities;

import Pair.AlphabetCount;
import Pair.AlphabetProb;
import Utils.Filter;
import Utils.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * TAI, October 2017
 *
 * Assignment 1 - Finite-context model and automatic text generation
 *
 * @author Bárbara Jael, 73241, barbara.jael@ua.pt
 * @author Miriam Cardoso, 72181, miriamcardoso@ua.pt
 * @author Tiago Faria, 73714, tiagohpf@ua.pt
 */

// Class that count and calculates probabilities of context
public class ProbManager {
    private List<Pair<String, AlphabetCount>> context;
    private List<Pair<String, AlphabetCount>> associations;
    private List<Pair<String, Integer>> contextCounter;
    private List<Pair<String, AlphabetProb>> contextProbs;
    private List<Pair<String, Integer>> assocCounter;
    private List<Pair<String, AlphabetProb>> assocProbs;
    private List<String> combinations;
    private List<String> alphabet;
    private double alpha;

    /**
     * Constructor
     *
     * @param context
     * @param combinations
     * @param alpha
     * @param alphabet
     * @param associations
     */
    public ProbManager(List<Pair<String, AlphabetCount>> context, List<String> combinations,
                       double alpha, List<String> alphabet, List<Pair<String, AlphabetCount>> associations) {
        this.context = context;
        contextCounter = new ArrayList<>();
        contextProbs = new ArrayList<>();
        assocCounter = new ArrayList<>();
        assocProbs = new ArrayList<>();
        this.combinations = combinations;
        this.alpha = alpha;
        this.alphabet = alphabet;
        this.associations = associations;
        sumOccurrences(combinations, contextCounter, context);
        sumOccurrences(alphabet, assocCounter, associations);
        calculateProbabilities(contextCounter, context, contextProbs);
        calculateProbabilities(assocCounter, associations, assocProbs);
    }

    /**
     * Calculate entropy
     *
     * @return entropy
     */
    public double getEntropy() {
        // Get number of total combinations created
        int totalCombinations = getNumberOfCombinationsInContext();
        double entropy = 0;
        for (Pair<String, Integer> wordOccurrences : contextCounter) {
            String word = wordOccurrences.getKey();
            // Get number of occurrences of certain word
            int occurrences = wordOccurrences.getValue();
            double h = 0;
            // Get probabilities of certain word
            List<Pair<String, AlphabetProb>> filter = Filter.filterContextProbs(contextProbs, word);
            // Calculate entropy row by row
            for (Pair<String, AlphabetProb> letterProb : filter) {
                double prob = letterProb.getValue().getProb();
                // log a (x) = log b (x) / log b (a)
                h += (prob * (Math.log10(prob) / Math.log10(2))) * (-1);
            }
            entropy += h * (occurrences * 1.0 / totalCombinations);
        }
        return entropy;
    }

    /**
     * Get all probabilities from context
     *
     * @return probabilities of context
     */
    public List<Pair<String, AlphabetProb>> getContextProbs() {
        return contextProbs;
    }

    /**
     * Get all probabilities from associations
     *
     * @return probabilities of associations
     */
    public List<Pair<String, AlphabetProb>> getAssocProbs() {
        return assocProbs;
    }

    /**
     * Get number of occurrences in each word of context
     *
     * @return number of occurrences in context
     */
    public List<Pair<String, Integer>> getContextCounter() {
        return contextCounter;
    }

    /**
     * Get number of occurrences in each association
     *
     * @return number of occurrences in associations
     */
    public List<Pair<String, Integer>> getAssocCounter() {
        return assocCounter;
    }

    // Calculate the total number of occurrences, row by row
    private void sumOccurrences(List<String> domain, List<Pair<String, Integer>> counter,
                                List<Pair<String, AlphabetCount>> context) {
        for (int i = 0; i < domain.size(); i++) {
            int sum = 0;
            String word = domain.get(i);
            List<Pair<String, AlphabetCount>> filter = Filter.filterContext(context ,word);
            for(Pair<String, AlphabetCount> pair : filter)
                sum += pair.getValue().getNumber();
            counter.add(new Pair<>(word, sum));
        }
    }

    // Calculate probabilities in contex
    private void calculateProbabilities(List<Pair<String, Integer>> counter,
                                        List<Pair<String, AlphabetCount>> context,
                                        List<Pair<String, AlphabetProb>> probabilities) {
        for (Pair<String, Integer> wordOccurrences : counter) {
            String word = wordOccurrences.getKey();
            List<Pair<String, AlphabetCount>> filter = Filter.filterContext(context ,word);
            for(Pair<String, AlphabetCount> letterOccurrences : filter) {
                String letter = letterOccurrences.getValue().getLetter();
                int number = letterOccurrences.getValue().getNumber();
                double prob = (number + alpha) / (wordOccurrences.getValue() + alphabet.size() * alpha);
                probabilities.add(new Pair<>(word, new AlphabetProb(letter, prob)));
            }
        }
    }

    /**
     * Return total number of combinations create in context
     *
     * @return total number of combinations in context
     */
    private int getNumberOfCombinationsInContext() {
        int sum = 0;
        for (Pair<String, Integer> wordOccurrences : contextCounter)
            sum += wordOccurrences.getValue();
        return sum;
    }
}
