package Collector;

import Alphabet.AlphabetCount;
import Utils.Filter;
import Utils.Pair;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * TAI, October 2017
 * <p>
 * Assignment 1 - Finite-context model and automatic text generation
 *
 * @author Bárbara Jael, 73241, barbara.jael@ua.pt
 * @author Miriam Cardoso, 72181, miriamcardoso@ua.pt
 * @author Tiago Faria, 73714, tiagohpf@ua.pt
 */


// Class that collects statistical information about texts, using finite-context models
public class WordsCollector {
    // Name of file to read
    private File file;
    // Order of context
    private int order;
    // List of alphabet. 27 characters [A-Z] and whitespace
    private List<String> alphabet;
    // List that counts the appearance of contexts in text
    private List<Pair<String, AlphabetCount>> context;
    // List of combinations with order = 1
    private List<Pair<String, AlphabetCount>> associations;
    // List of combinations created in context
    private List<String> contextCombinations;
    private static Scanner sc;

    /**
     * Constructor
     *
     * @param path
     * @param order
     * @param alphabet
     */
    public WordsCollector(String path, int order, List<String> alphabet) {
        this.context = new ArrayList<>();
        this.associations = new ArrayList<>();
        this.contextCombinations = new ArrayList<>();
        this.alphabet = alphabet;
        this.order = order;
        readFile(path);
    }

    /**
     * Return context of a given order
     *
     * @return context
     */
    public List<Pair<String, AlphabetCount>> getContext() {
        return context;
    }

    /**
     * Return combinations of context
     *
     * @return combinations
     */
    public List<String> getContextCombinations() {
        return contextCombinations;
    }

    /**
     * Return context of order = 1
     *
     * @return context
     */
    public List<Pair<String, AlphabetCount>> getAssociations() {
        return associations;
    }

    // Read text file and create two matrix
    private void readFile(String path) {
        // Create the file
        file = new File(path);
        // Read the file
        try {
            sc = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.err.println("ERROR: " + path + " not found!");
            System.exit(1);
        }
        while (sc.hasNext()) {
            String line = sc.nextLine();
            line = Filter.removeSpecialCharacters(line);
            // First, get the character in text
            for (int i = order; i < line.length(); i++) {
                String word = new String();
                char letter = line.charAt(i);
                // Second, get the context give an order
                for (int j = i - order; j < i; j++)
                    word += line.charAt(j);
                // Add a new context
                addOccurrence(context, word.toUpperCase(), Character.toUpperCase(letter));
            }
            // First, get the character in text
            for (int i = 1; i < line.length(); i++) {
                String word = new String();
                char letter = line.charAt(i);
                //Second, get the context give an order
                for (int j = i - 1; j < i; j++)
                    word += line.charAt(j);
                // Add a new association
                addOccurrence(associations, word.toUpperCase(), Character.toUpperCase(letter));
            }
        }
    }

    /**
     * Create a new word in context or increment its value
     *
     * If the word already exists in the context, increment its value
     * If not, create a new instance
     *
     * @param context
     * @param word
     * @param letter
     */
    private void addOccurrence(List<Pair<String, AlphabetCount>> context, String word, char letter) {
        List<Pair<String, AlphabetCount>> filter = Filter.filterContext(context, word);
        if (filter.size() > 0) {
            for (Pair<String, AlphabetCount> row : filter) {
                String valueLetter = row.getValue().getLetter();
                int valueNumber = row.getValue().getNumber();
                // convert letter to String and compare
                if (valueLetter.equals("" + letter))
                    row.getValue().setNumber(valueNumber + 1);
            }
        } else
            createNewInstanceInList(context, word, letter);
    }

    /**
     * Create a new instance (row) on context
     * If the association was found in text, create with value 1. Otherwise, create with value 0
     *
     * @param list
     * @param word
     * @param letter
     */
    private void createNewInstanceInList(List<Pair<String, AlphabetCount>> list, String word, char letter) {
        for (String character : alphabet) {
            if (character.equals("" + letter))
                list.add(new Pair<>(word, new AlphabetCount("" + character, 1)));
            else
                list.add(new Pair<>(word, new AlphabetCount("" + character, 0)));
        }
        contextCombinations.add(word);
    }
}
