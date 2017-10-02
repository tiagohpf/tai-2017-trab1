package Utils;

import java.util.ArrayList;
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

public class ProbManager {
    List<Pair<String, AlphabetCount>> words;        // words of file
    List<Pair<String, Integer>> counts;             // count of each words
    List<Pair<String, AlphabetCount>> probs;        // probabilities

    public ProbManager(List<Pair<String, AlphabetCount>> words) {
        this.words = words;
        this.counts = new ArrayList<Pair<String, Integer>>();
        this.probs = new ArrayList<Pair<String, AlphabetCount>>();
    }
}
