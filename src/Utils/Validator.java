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

// Class that helps validate certain elements
public final class Validator {

    /**
     * Verify if the argument is a valid Integer
     *
     * @param argument
     * @return if is a valid Integer
     */
    public static boolean isIntegerValid(String argument) {
        return isInteger(argument) && Integer.parseInt(argument) > 0;
    }

    /**
     * Verify if alpha is valid
     *
     * @param argument
     * @return if alpha is valid
     */
    public static boolean isAlphaValid(String argument) {
        return isDouble(argument)
                && (Double.parseDouble(argument) >= 0.001 && Double.parseDouble(argument) <= 1);
    }

    /**
     * Verify if the argument is an Integer
     *
     * @param s
     * @return successful conversion to Integer
     */
    private static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    /**
     * Verify if the argument is a Double
     *
     * @param s
     * @return successful conversion to Double
     */
    private static boolean isDouble(String s) {
        try {
            Double.parseDouble(s);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }
}
