import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Collecter {
    private File file;
    private int order;
    private double alpha;
    private HashMap<String, AlphaCount> words;
    private List<String> alphabet;
    public static Scanner sc;

    public Collecter(String path, int order, double alpha) {
        readFile(path);
        this.words = new HashMap<>();
        this.alphabet = new ArrayList<>();
        this.order = order;
        this.alpha = alpha;
        generateAlphabet();
        generateCombinations(order);
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
                AlphaCount letter = new AlphaCount(character, 0);
                words.put(combination.toString(), letter);
            }
        }
    }

    public HashMap<String, AlphaCount> getWords() {
        return words;
    }

}
