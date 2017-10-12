import Pair.AlphabetProb;
import Utils.Filter;
import Utils.Pair;
import java.util.List;
import java.util.Random;

/**
 * TAI, October 2017
 *
 * Assignment 1 - Finite-context model and automatic text generation
 *
 * @author Bárbara Jael, 73241, barbara.jael@ua.pt
 * @author Miriam Cardoso, 72181, miriamcardoso@ua.pt
 * @author Tiago Faria, 73714, tiagohpf@ua.pt
 */

// Class that generates text
public class Generator {
    // Size of text to generate
    private int size;
    // List of probabilities in context
    private List<Pair<String, AlphabetProb>> contextProbs;
    // List of probabilities in associations
    private List<Pair<String, AlphabetProb>> assocProbs;
    // List of counting words after context
    private List<Pair<String, Integer>> contextCounter;
    // List of counting associations
    private List<Pair<String, Integer>> assocCounter;
    // Order of context
    private int order;

    public Generator(int size,
                     List<Pair<String, Integer>> wordCounts,
                     List<Pair<String, Integer>> assocCounter,
                     List<Pair<String, AlphabetProb>> contextProbs,
                     List<Pair<String, AlphabetProb>> assocProbs, int order) {
        this.size = size;
        this.order = order;
        this.contextProbs = contextProbs;
        this.assocProbs = assocProbs;
        this.contextCounter = wordCounts;
        this.assocCounter = assocCounter;
    }

    /**
     * Return text generated
     *
     * First, is generated a initial word with the same length as the order
     * After that, the function iterates context by context
     * If the context exists in the list, pick the next letter
     * If not, use the list of associations to get the next letter
     *
     * @return text
     */
    public String generateText() {
        // Get initial word
        StringBuilder text = generateFirstContext();
        do{
            // Get the context in text
            String word = text.substring(text.length() - order, text.length());
            String res = new String();
            // Check if context exists
            if (contextExists(word))
                res += generateNexContext(word, true);
            // If not, get next letter from associations
            else {
                int index = word.length() - 1;
                res += generateNexContext(Character.toString(word.charAt(index)), false);
            }
            text.append(res);
        } while (text.length() < size);
        return text.toString();
    }

    /**
     * Return the initial context for generated text
     *
     * @return context
     */
    private StringBuilder generateFirstContext() {
        StringBuilder sb = new StringBuilder();
        // Get the count of all words
        int totalWords = getAllOccurrencesInContext();
        /*
         * Random number between [0, totalWords]
         * Check if number is less than the count of first word
         * If is less, concat the word. Otherwise, sum the count of next word and check again
         * Repeat that process to find a sum greater than the random number
         */
        int randNumber = new Random().nextInt(totalWords + 1);
        int sumCounter = 0;
        for (int i = 0; i < contextCounter.size(); i++) {
            sumCounter += contextCounter.get(i).getValue();
            if (randNumber <= sumCounter) {
                sb.append(contextCounter.get(i).getKey());
                break;
            }
        }
        return sb;
    }

    /**
     * Return the next word. This word is added to generated text
     * Word can be a context or a single letter
     *
     * @param word
     *
     * @return context
     */
    private String generateNexContext(String word, boolean isContext) {
        List<Pair<String, AlphabetProb>> filter;
        /*
         * If context exists, add a new one
         * Otherwise, add just a new letter using probabilities of associations
         */
        if (isContext) {
            // Get all probabilities from given context
            filter  = Filter.filterContextProbs(contextProbs, word);
        }
        else {
            // Get all probabilities from given letter
            filter  = Filter.filterContextProbs(assocProbs, word);
        }

        String res = new String();
        /*
         * Random number between [0, 1]
         * Check if number is less than the probability of first letter
         * If is less, concat the word. Otherwise, sum the probability of next letter and check again
         * Repeat that process to find a sum greater than the random number
         */
        double randProb = new Random().nextDouble();
        double sumProb = 0;
        for (int i = 0; i < filter.size(); i++) {
            sumProb += filter.get(i).getValue().getProb();
            if (randProb <= sumProb) {
                res += filter.get(i).getValue().getLetter();
                break;
            }
        }
        return res;
    }

    /**
     * Check if context exists in matrix
     * 
     * @param word
     * 
     * @return if context exists
     */
    private boolean contextExists(String word) {
        boolean res = false;
        for (Pair<String, AlphabetProb> pair : contextProbs) {
            if (pair.getKey().equals(word)) {
                res = true;
                break;
            }
        }
        return res;
    }

    /**
     * Return the 
     * 
     * @return total number of words
     */
    private int getAllOccurrencesInContext() {
        int sum = 0;
        for (Pair<String, Integer> pair : contextCounter)
            sum += pair.getValue();
        return sum;
    }
}
