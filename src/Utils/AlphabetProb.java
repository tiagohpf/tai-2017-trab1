package Utils;

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

    public AlphabetProb(String letter, double prob) {
        this.letter = letter;
        this.prob = prob;
    }

    public String getLetter() {
        return letter;
    }

    public double getProb() {
        return prob;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    public void setProb(int prob) {
        this.prob = prob;
    }

    public String toString() {
        return "{" + letter + ", " + prob + "}";
    }
}
