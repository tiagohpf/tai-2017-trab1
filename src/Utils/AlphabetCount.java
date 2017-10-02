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

public class AlphabetCount {
    private String letter;
    private int number;

    public AlphabetCount(String letter, int number) {
        this.letter = letter;
        this.number = number;
    }

    public String getLetter() {
        return letter;
    }

    public int getNumber() {
        return number;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String toString() {
        return "{" + letter + ", " + number + "}";
    }
}
