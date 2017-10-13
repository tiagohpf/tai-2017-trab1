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

public class AlphabetCount {
    private String letter;
    private int number;

    /**
     * Constructor
     *
     * @param letter
     * @param number
     */
    public AlphabetCount(String letter, int number) {
        this.letter = letter;
        this.number = number;
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
     * Get number
     *
     * @return number
     */
    public int getNumber() {
        return number;
    }

    /**
     * Change the number
     *
     * @param number
     */
    public void setNumber(int number) {
        this.number = number;
    }

    /**
     * Print the object
     *
     * @return string
     */
    public String toString() {
        return "{" + letter + ", " + number + "}";
    }
}
