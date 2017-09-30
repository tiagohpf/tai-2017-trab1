import javafx.util.Pair;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
public class Collector {
    private File file;
    private int order;
    private double alpha;
    private List<Pair<String, AlphabetCount>> words;
    private List<String> alphabet;
    public static Scanner sc;

    public Collector(String path, int order, double alpha) {
        readFile(path);
        this.words = new ArrayList<Pair<String, AlphabetCount>>();
        this.alphabet = new ArrayList<>();
        this.order = order;
        this.alpha = alpha;
        generateAlphabet();
        generateCombinations(order);

        // TODO: delete, test only
        System.out.println(words);
        System.out.println("##############################");
        System.out.println(getWords());
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
            System.out.println(line);
        }
    }

    private void generateAlphabet() {
        for (char c = 'A'; c <= 'Z'; c++) {
            alphabet.add("" + c);
        }
        //Add whitespace
        alphabet.add(" ");
    }

    public void generateCombinations(int order) {
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

    public void createInstancesInMap(List<String> combinations) {
        for (String combination : combinations) {
            for (String character : alphabet) {
                AlphabetCount letter = new AlphabetCount(character, 0);
                words.add(new Pair<String, AlphabetCount>(combination.toString(), letter));
            }
        }
    }

    public ArrayList<Pair<String, AlphabetCount>> getWords() {
        return (ArrayList<Pair<String, AlphabetCount>>) words;
    }
}
