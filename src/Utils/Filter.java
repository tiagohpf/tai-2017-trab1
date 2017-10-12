package Utils;

import Pair.AlphabetCount;
import Pair.AlphabetProb;
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

// Class that filter in objects
public final class Filter {

    /**
     * Remove all special characters from text
     *
     * @param text
     */
    public static String removeSpecialCharacters(String text) {
        text = text.replaceAll("[-+.^:,;'!?_\"]", "");
        text = text.replaceAll("[ñ]", "n");
        text = text.replaceAll("[ç]", "c");
        text = text.replaceAll("[èéêë]", "e");
        text = text.replaceAll("[ûùü]", "u");
        text = text.replaceAll("[ïî]", "i");
        text = text.replaceAll("[õòòô]", "a");
        text = text.replaceAll("[àâãá]", "a");
        text = text.replaceAll("[ÈÉÊË]", "E");
        text = text.replaceAll("[ÛÙÜ]", "U");
        text = text.replaceAll("[ÏÎ]", "I");
        text = text.replaceAll("[ÀÂÃ]", "A");
        text = text.replaceAll("ÔÕÓÒ", "O");
        return text;
    }

    /**
     * Filter context given a certain word
     * @param context
     * @param word
     *
     * @return filtered context
     */
    public static List<Pair<String, AlphabetCount>> filterContext(List<Pair<String, AlphabetCount>> context, String word) {
        return  context.stream()                                // convert to stream
                .filter(line -> line.getKey().equals(word))     // filter by word
                .collect(Collectors.toList());                  // convert to list
    }

    /**
     * Filter context with probabilities given a certain word
     * @param context
     * @param word
     *
     * @return filtered context
     */
    public static List<Pair<String, AlphabetProb>> filterContextProbs(List<Pair<String, AlphabetProb>> context, String word) {
        return context.stream()
                .filter(line -> line.getKey().equals(word))
                .collect(Collectors.toList());
    }
}
