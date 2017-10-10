package Utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
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


/**
 * Collect statistical information about texts, using finite-context models
 */
public class WordsCollector {
    private File file;
    private int order;
    private List<String> alphabet;
    private List<Pair<String, AlphabetCount>> words;
    private List<Pair<String, AlphabetCount>> associations;
    private List<String> combinations;
    public static Scanner sc;

    public WordsCollector(String path, int order) {
        this.alphabet = new ArrayList<>();
        this.words = new ArrayList<Pair<String, AlphabetCount>>();
        this.associations = new ArrayList<Pair<String, AlphabetCount>>();
        this.combinations = new ArrayList<>();
        this.order = order;
        generateAlphabet();
        readFile(path);

        // TODO: delete, test only
        System.out.println("Alphabet: " + alphabet);
        System.out.println("Combinations: " + combinations);
        System.out.println("Words: " + words);
        System.out.println("Associations: " + associations);
        //System.out.println(associations.size());
    }

    public ArrayList<Pair<String, AlphabetCount>> getWords() {
        return (ArrayList<Pair<String, AlphabetCount>>) words;
    }

    public List<String> getCombinations() {
        return combinations;
    }

    public List<String> getAlphabet() {
        return alphabet;
    }

    public ArrayList<Pair<String, AlphabetCount>> getAssociations() {
        return (ArrayList<Pair<String, AlphabetCount>>) associations;
    }

    private void readFile(String path) {
        // Create the file
        file = new File(path);

        // Read the file
        try {
            sc = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.err.println("ERROR: " + path + " not found!");
        }
        while (sc.hasNext()) {
            String line = sc.nextLine();
            line = removeSpecialCharacters(line);
            // First, get the character in text
            for (int i = order; i < line.length(); i++) {
                String word = new String();
                char letter = line.charAt(i);
                //Second, get the context give an order
                for (int j = i - order; j < i; j++) {
                    word += line.charAt(j);
                }
                addOccurrence(words, word.toUpperCase(), Character.toUpperCase(letter));
            }

            // associations
            for (int i = 1; i < line.length(); i++) {
                String word = new String();
                char letter = line.charAt(i);
                //Second, get the context give an order
                for (int j = i - 1; j < i; j++) {
                    word += line.charAt(j);
                }
                addOccurrence(associations, word.toUpperCase(), Character.toUpperCase(letter));
            }
        }
    }

    private String removeSpecialCharacters(String line) {
        line = line.replaceAll("[-+.^:,;'!?_\"]", "");
        line = line.replaceAll("[ñ]", "n");
        line = line.replaceAll("[ç]", "c");
        line = line.replaceAll("[èéêë]", "e");
        line = line.replaceAll("[ûùü]", "u");
        line = line.replaceAll("[ïî]", "i");
        line = line.replaceAll("[õòòô]", "a");
        line = line.replaceAll("[àâãá]", "a");
        line = line.replaceAll("[ÈÉÊË]", "E");
        line = line.replaceAll("[ÛÙÜ]", "U");
        line = line.replaceAll("[ÏÎ]", "I");
        line = line.replaceAll("[ÀÂÃ]", "A");
        line = line.replaceAll("ÔÕÓÒ", "O");
        return line;
    }

    private void generateAlphabet() {
        for (char c = 'A'; c <= 'Z'; c++) {
            alphabet.add("" + c);
        }
        // Add whitespace
        alphabet.add(" ");
    }

    private void addOccurrence(List<Pair<String, AlphabetCount>> list, String word, char letter) {
        List<Pair<String, AlphabetCount>> filter =
                list.stream()                                      // convert list to stream
                        .filter(line -> line.getKey().equals(word)) // compare context
                        .collect(Collectors.toList());              // convert streams to List
        if (filter.size() > 0) {                                    // if already exists the word in context
            for (Pair<String, AlphabetCount> row : filter) {
                String letterValue = row.getValue().getLetter();
                int letterNumber = row.getValue().getNumber();
                if (letterValue.equals("" + letter))     // convert (char)letter to String and compare
                    row.getValue().setNumber(letterNumber + 1);
            }
        } else
            createNewInstanceInMap(list, word, letter);
    }

    private void createNewInstanceInMap(List<Pair<String, AlphabetCount>> list, String word, char letter) {
        for (String character : alphabet) {
            if (character.equals("" + letter))
                list.add(new Pair<>(word, new AlphabetCount("" + character, 1)));
            else
                list.add(new Pair<>(word, new AlphabetCount("" + character, 0)));
        }
        combinations.add(word);
    }
}
