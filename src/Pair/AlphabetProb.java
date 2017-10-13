package Pair;

/**
 * TAI, October 2017
 *
 * Assignment 1 - Finite-context model and automatic text generation
 *
 * @author Bárbara Jael, 73241, barbara.jael@ua.pt
 * @author Miriam Cardoso, 72181, miriamcardoso@ua.pt
 * @author Tiago Faria, 73714, tiagohpf@ua.pt
 */

public class AlphabetProb {
    private String letter;
    private double prob;

    /**
     * Constructor
     *
     * @param letter
     * @param prob
     */
    public AlphabetProb(String letter, double prob) {
        this.letter = letter;
        this.prob = prob;
    }

    /**
     * Get letter
     *
     * @return letter
     */
    public String getLetter() {
        return letter;
    }

    /**
     * Get probability
     *
     * @return prob
     */
    public double getProb() {
        return prob;
    }

    /**
     * Print the object
     *
     * @return string
     */
    public String toString() {
        return "{" + letter + ", " + prob + "}";
    }
}
