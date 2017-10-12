import Pair.AlphabetCount;
import Utils.Creator;
import Utils.Pair;
import Probabilities.ProbManager;
import Collector.WordsCollector;
import Utils.Validator;

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


// Main class that runs the project
public class Fcm {
    public static void main(String[] args) {
        String filePath = new String();
        int order = 0, generateLength = 0;
        double alpha = 1;

        /*
         * Program needs at least 2 arguments: filename and order, both valid
         * It's possible to use also 3 arguments (add alpha) and 4 (add alpha and length of text generated)
         * Text is just generated when program have all 4 arguments
         * Alpha = 1 when is not inserted
         * Length of text must >= order
         * Alpha must be in [0.001, 1]
         * USAGE: <filename> <order> <alpha> <text length>
         * Examples of Usage:
         *  - "I Have a Dream.txt" 3
         *  - "I Have a Dream.txt" 2 0.4 33
         */
        if (args.length >= 2 && args.length <= 4) {
            filePath = args[0];
            if (Validator.isIntegerValid(args[1])) {
                order = Integer.parseInt(args[1]);
            } else {
                System.err.println("ERROR: Invalid argument, order must be an INTEGER > 0");
                System.exit(1);
            }
            if (args.length == 2) {
                alpha = 1;
            } else if (args.length >= 3) {
                if (Validator.isAlphaValid(args[2])) {
                    alpha = Double.parseDouble(args[2]);
                } else {
                    System.err.println("ERROR: Invalid argument, alpha must be a DOUBLE within [0.001, 1]");
                    System.exit(1);
                }
                if (args.length == 4) {
                    if (Validator.isIntegerValid(args[3])) {
                        generateLength = Integer.parseInt(args[3]);
                        if (order > generateLength)
                            System.err.println("ERROR: Order must be less or equal than text length.");
                    } else {
                        System.err.println("ERROR: Invalid argument, text length must be an INTEGER > 0");
                        System.exit(1);
                    }
                }
            }
        } else {
            System.err.println("ERROR: Invalid number of arguments");
            System.err.println("USAGE: filePath order (alpha) (length to generate text)");
            System.exit(1);
        }

        List<String> alphabet = Creator.createAlphabet();
        WordsCollector collection = new WordsCollector(filePath, order, alphabet);
        List<Pair<String, AlphabetCount>> words = collection.getContext();
        List<String> combinations = collection.getContextCombinations();
        ProbManager probabilities = new ProbManager(words, combinations, alpha, alphabet,
                collection.getAssociations());
        Generator generator = new Generator(generateLength, probabilities.getContextCounter(), probabilities.getAssocCounter(),
                probabilities.getContextProbs(), probabilities.getAssocProbs(), order);
        System.out.println(generator.generateText());
        System.out.println("Entropy: " + String.format("%.3f", probabilities.getEntropy()) + " bits");
    }
}
