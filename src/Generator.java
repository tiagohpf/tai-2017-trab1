/**
 * TAI, October 2017
 *
 * Assignment 1 - Finite-context model and automatic text generation
 *
 * @author Bárbara Jael, 73241, barbara.jael@ua.pt
 * @author Miriam Cardoso, 72181, miriamcardoso@ua.pt
 * @author Tiago Faria, 73714, tiagohpf@ua.pt
 */


import Pair.AlphabetProb;
import Utils.Pair;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Automatic text generation
 */
public class Generator {
    private int size;
    private List<Pair<String, AlphabetProb>> wordProbs;
    private List<Pair<String, AlphabetProb>> assocProbs;
    private List<Pair<String, Integer>> wordCounts;
    private List<Pair<String, Integer>> assocCounts;
    private int order;

    public Generator(int size,
                     List<Pair<String, Integer>> wordCounts,
                     List<Pair<String, Integer>> assocCounts,
                     List<Pair<String, AlphabetProb>> wordProbs,
                     List<Pair<String, AlphabetProb>> assocProbs, int order) {
        this.size = size;
        this.order = order;
        this.wordProbs = wordProbs;
        this.assocProbs = assocProbs;
        this.wordCounts = wordCounts;
        this.assocCounts = assocCounts;
        //sortCollection(wordProbs);
        //sortCollection(assocProbs);
        //sortCollectionCounter(wordCounts);
        //sortCollectionCounter(assocCounts);
        /*System.out.println("################################");
        System.out.println("Words Probs: " + wordProbs);
        System.out.println("Associations Probs: " + assocProbs);
        System.out.println("Word Counts: " + wordCounts);
        System.out.println("Assoc Counts: " + assocCounts);*/
    }

    public String generateText() {
        StringBuilder text = generateFirstContext();
        do{
            String word = text.substring(text.length() - order, text.length());
            String res = new String();
            if (termIsInWords(word)) {
                res += getNextContext(word);
            } else {
                int index = word.length() - 1;
                res += getNexLetter(Character.toString(word.charAt(index)));
            }
            text.append(res);
        } while (text.length() < size);
        return text.toString();
    }

    private String getNextContext(String word) {
        List<Pair<String, AlphabetProb>> filter = filterCollectionInProb(word);
        String res = new String();
        double randProb = new Random().nextDouble();
        double sumProb = 0;
        boolean exit = false;
        for (int i = 0; i < filter.size(); i++) {
            sumProb += filter.get(i).getValue().getProb();
            if (randProb <= sumProb) {
                res += filter.get(i).getValue().getLetter();
                exit = true;
            }
            if (exit)
                break;
        }
        return res;
    }

    private String getNexLetter(String letter) {
        List<Pair<String, AlphabetProb>> filter = filterAssocInProb(letter);
        String res = new String();
        double randProb = new Random().nextDouble();
        double sumProb = 0;
        boolean exit = false;
        for (int i = 0; i < filter.size(); i++) {
            sumProb += filter.get(i).getValue().getProb();
            if (randProb <= sumProb) {
                res += filter.get(i).getValue().getLetter();
                exit = true;
            }
            if (exit)
                break;
        }
        return res;
    }

    private void sortCollection(List<Pair<String, AlphabetProb>> collection) {
        Comparator<Pair<String, AlphabetProb>> comp =
                Comparator.comparing(Pair::getKey);
        Collections.sort(collection, comp);
    }

    private void sortCollectionCounter(List<Pair<String, Integer>> collection) {
        Comparator<Pair<String, Integer>> comp =
                Comparator.comparing(Pair::getKey);
        Collections.sort(collection, comp);
    }

    private boolean termIsInWords(String term) {
        boolean res = false, exit = false;
        for (Pair<String, AlphabetProb> pair : wordProbs) {
            if (pair.getKey().equals(term)) {
                res = true;
                exit = true;
            }
            if (exit)
                break;
        }
        return res;
    }

    private int getTotalCountOfWords() {
        int sum = 0;
        for (Pair<String, Integer> pair : wordCounts) {
            sum += pair.getValue();
        }
        return sum;
    }

    private StringBuilder generateFirstContext() {
        StringBuilder sb = new StringBuilder();
        int totalWords = getTotalCountOfWords();
        /*
         * Rand a number between [0, totalWords]
         * Check if number is less than the count of first word.
         * If is less, concat the word. Otherwise, sum the count of next word and check again.
         * Repeat that process to find a sum greater than the random number.
         */
        Random r = new Random();
        int randNumber = r.nextInt(totalWords + 1);
        int sumCounter = 0;
        boolean exit = false;
        for (int i = 0; i < wordCounts.size(); i++) {
            sumCounter += wordCounts.get(i).getValue();
            if (randNumber <= sumCounter) {
                sb.append(wordCounts.get(i).getKey());
                exit = true;
            }
            if (exit)
                break;
        }
        return sb;
    }

    private List<Pair<String, AlphabetProb>> filterCollectionInProb(String word) {
        List<Pair<String, AlphabetProb>> filter =
                wordProbs.stream()
                        .filter(line -> line.getKey().equals(word))
                        .collect(Collectors.toList());
        return filter;
    }

    private List<Pair<String, AlphabetProb>> filterAssocInProb(String word) {
        List<Pair<String, AlphabetProb>> filter =
                assocProbs.stream()
                        .filter(line -> line.getKey().equals(word))
                        .collect(Collectors.toList());
        return filter;
    }
}
