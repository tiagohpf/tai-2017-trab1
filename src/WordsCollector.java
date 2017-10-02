import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
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


/**
 * Collect statistical information about texts, using finite-context models
 */
public class WordsCollector {
    private File file;
    private int order;
    private double alpha;
    private List<Pair<String, AlphabetCount>> words;
    private List<String> alphabet;
    public static Scanner sc;

    public WordsCollector(String path, int order, double alpha) {
        this.words = new ArrayList<Pair<String, AlphabetCount>>();
        this.alphabet = new ArrayList<>();
        this.order = order;
        this.alpha = alpha;
        generateAlphabet();
        generateCombinations(order);
        readFile(path);

        // TODO: delete, test only
        System.out.println("##############################");
        System.out.println(words);
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
            //Remove all special characters
            String line = sc.nextLine().replaceAll("[-+.^:,'!?_]", "");
            line = line.replaceAll("[ñ]","n");
            line = line.replaceAll("[ç]","c");
            line = line.replaceAll("[èéêë]","e");
            line = line.replaceAll("[ûùü]","u");
            line = line.replaceAll("[ïî]","i");
            line = line.replaceAll("[õòòô]","a");
            line = line.replaceAll("[àâãá]","a");
            line = line.replaceAll("[ÈÉÊË]","E");
            line = line.replaceAll("[ÛÙÜ]","U");
            line = line.replaceAll("[ÏÎ]","I");
            line = line.replaceAll("[ÀÂÃ]","A");
            line = line.replaceAll("ÔÕÓÒ","O");
            System.out.println(line);
            //First, get the character in text
            for (int i = order; i < line.length(); i++) {
                String word = new String();
                char letter = line.charAt(i);
                //Second, get the context give an order
                for (int j = i - order; j < i; j++) {
                    word += line.charAt(j);
                }
                incrementOccurrence(word.toUpperCase(), Character.toUpperCase(letter));
                System.out.println("letter " + letter + ", " + "word " + word.toUpperCase());
            }
        }
    }

    private void generateAlphabet() {
        for (char c = 'A'; c <= 'Z'; c++) {
            alphabet.add("" + c);
        }
        //Add whitespace
        alphabet.add(" ");
    }

    private void generateCombinations(int order) {
        List<String> combinations = new ArrayList<String>();
        for (int i = 0; i < order; i++) {
            for (String character : alphabet) {
                if (i == 0) {
                    //Add k-times the letter
                    for (int j = 0; j < alphabet.size(); j++) {
                        combinations.add(character);
                    }
                } else {
                    //Add letter from k to k
                    for (int j = alphabet.indexOf(character); j < combinations.size(); j += alphabet.size()) {
                        String word = combinations.get(j) + character;
                        combinations.set(j, word);
                    }
                }
            }
        }
        createInstancesInMap(combinations);
    }

    private void createInstancesInMap(List<String> combinations) {
        for (String combination : combinations) {
            for (String character : alphabet) {
                AlphabetCount letter = new AlphabetCount(character, 0);
                words.add(new Pair<String, AlphabetCount>(combination.toString(), letter));
            }
        }
    }

    private void incrementOccurrence(String word, char letter) {
        List<Pair<String, AlphabetCount>> filter =
                words.stream()                                // convert list to stream
                .filter(line -> line.getKey().equals(word))  // compare context
                .collect(Collectors.toList());              // convert streams to List
        for (Pair<String, AlphabetCount> row : filter) {
            String letterValue = row.getValue().getLetter();
            int letterNumber = row.getValue().getNumber();
            if (letterValue.equals("" + letter)) {        // convert (char)letter to String and compare
                row.getValue().setNumber(letterNumber + 1);
            }
        }
    }

    public ArrayList<Pair<String, AlphabetCount>> getWords() {
        return (ArrayList<Pair<String, AlphabetCount>>) words;
    }
}
