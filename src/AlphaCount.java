public class AlphaCount {
    private String letter;
    private int number;
    public AlphaCount (String letter, int number) {
        this.letter = letter;
        this.number = number;
    }

    public String getLetter() {
        return letter;
    }

    public int getNumber() {
        return number;
    }

    public String toString() {
        return "{" + letter + ", " + number + "}";
    }
}
