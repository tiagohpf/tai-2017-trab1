package Probabilities;

import Alphabet.AlphabetCount;
import Alphabet.AlphabetProb;
import Utils.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * TAI, October 2017
 *
 * Assignment 1 - Finite-context model and automatic text generation
 *
 * @author Bárbara Jael, 73241, barbara.jael@ua.pt
 * @author Miriam Cardoso, 72181, miriamcardoso@ua.pt
 * @author Tiago Faria, 73714, tiagohpf@ua.pt
 */

public class ProbManager {
    private List<Pair<String, AlphabetCount>> words;
    private List<Pair<String, AlphabetCount>> associations;
    private List<Pair<String, Integer>> wordCounts;
    private List<Pair<String, AlphabetProb>> wordProbs;
    private List<Pair<String, Integer>> assocCounts;
    private List<Pair<String, AlphabetProb>> assocProbs;
    private List<String> combinations;
    private List<String> alphabet;
    private double alpha;
    private int order;

    public ProbManager(List<Pair<String, AlphabetCount>> words, List<String> combinations,
                       double alpha, List<String> alphabet, List<Pair<String, AlphabetCount>> associations,
                       int order) {
        this.words = words;
        wordCounts = new ArrayList<>();
        wordProbs = new ArrayList<>();
        assocCounts = new ArrayList<>();
        assocProbs = new ArrayList<>();
        this.combinations = combinations;
        this.alpha = alpha;
        this.alphabet = alphabet;
        this.associations = associations;
        this.order = order;
        sumOfColumns();
        getAssociationsSum();
        calculateProbabilities();
        calculateAssocProbabilities();

        //TODO: Delete, test only
        /*System.out.println("Counts: " + wordCounts);
        System.out.println("Probs: " + wordProbs);*/
    }

    public double getEntropy() {
        int totalOccurrences = sumTotalOcurrences();
        double entropy = 0;
        for (Pair<String, Integer> wordOccurrences : wordCounts) {
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

    public List<Pair<String, AlphabetProb>> getWordProbs() {
        return wordProbs;
    }

    public List<Pair<String, AlphabetProb>> getAssocProbs() {
        return assocProbs;
    }

    public List<Pair<String, Integer>> getWordCounts() {
        return wordCounts;
    }

    public List<Pair<String, Integer>> getAssocCounts() {
        return assocCounts;
    }

    private void sumOfColumns() {
        for (int i = 0; i < combinations.size(); i++) {
            String word = combinations.get(i);
            List<Pair<String, AlphabetCount>> filter = filterWordCollectionInCount(word);
            int sum = 0;
            for (Pair<String, AlphabetCount> pair : filter) {
                sum += pair.getValue().getNumber();
            }
            wordCounts.add(new Pair<>(word, sum));
        }
    }

    public void getAssociationsSum() {
        for (int i = 0; i < alphabet.size(); i++) {
            int sum = 0;
            String word = alphabet.get(i);
            List<Pair<String, AlphabetCount>> filter = filterAssocCollectionInCount(word);
            for(Pair<String, AlphabetCount> pair : filter) {
                sum += pair.getValue().getNumber();
            }
            assocCounts.add(new Pair<>(word, sum));
        }
    }

    private void calculateProbabilities() {
        for (Pair<String, Integer> wordOccurrences : wordCounts) {
            String word = wordOccurrences.getKey();
            List<Pair<String, AlphabetCount>> filter = filterWordCollectionInCount(word);
            for (Pair<String, AlphabetCount> letterOccurrences : filter) {
                String letter = letterOccurrences.getValue().getLetter();
                int number = letterOccurrences.getValue().getNumber();
                double prob = (number + alpha) / (wordOccurrences.getValue() + alphabet.size() * alpha);
                wordProbs.add(new Pair<>(word, new AlphabetProb(letter, prob)));
            }
        }
    }

    private void calculateAssocProbabilities() {
        for (Pair<String, Integer> wordOccurrences : assocCounts) {
            String word = wordOccurrences.getKey();
            List<Pair<String, AlphabetCount>> filter = filterAssocCollectionInCount(word);
            for(Pair<String, AlphabetCount> letterOccurrences : filter) {
                String letter = letterOccurrences.getValue().getLetter();
                int number = letterOccurrences.getValue().getNumber();
                double prob = (number + alpha) / (wordOccurrences.getValue() + alphabet.size() * alpha);
                assocProbs.add(new Pair<>(word, new AlphabetProb(letter, prob)));
            }
        }
    }

    private int sumTotalOcurrences() {
        int sum = 0;
        for (Pair<String, Integer> wordOccurrences : wordCounts)
            sum += wordOccurrences.getValue();
        return sum;
    }

    private List<Pair<String, AlphabetCount>> filterWordCollectionInCount(String word) {
        List<Pair<String, AlphabetCount>> filter =
                words.stream()
                        .filter(line -> line.getKey().equals(word))
                        .collect(Collectors.toList());
        return filter;
    }

    private List<Pair<String, AlphabetCount>> filterAssocCollectionInCount(String word) {
        List<Pair<String, AlphabetCount>> filter =
                associations.stream()
                        .filter(line -> line.getKey().equals(word))
                        .collect(Collectors.toList());
        return filter;
    }

    private List<Pair<String, AlphabetProb>> filterCollectionInProb(String word) {
        List<Pair<String, AlphabetProb>> filter =
                wordProbs.stream()
                        .filter(line -> line.getKey().equals(word))
                        .collect(Collectors.toList());
        return filter;
    }
}
