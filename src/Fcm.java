import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * TAI, October 2017
 * <p>
 * Assignment 1 - Finite-context model and automatic text generation
 *
 * @author Bárbara Jael
 * @author Miriam Cardoso, 72181, miriamcardoso@ua.pt
 * @author Tiago Faria, 73714, tiagohpf@ua.pt
 */

public class Fcm {
    public static Scanner sc;

    public static void main(String[] args) {
        String filePath = new String();
        File file;
        int order;
        double alpha;
        int generateLength;
        boolean enableGenerator = false;

        // Arguments
        if (args.length >= 2 && args.length <= 4) {
            filePath = args[0];
            if (isIntegerValid(args[1])) {
                order = Integer.parseInt(args[1]);
            } else {
                System.err.println("ERROR: Invalid argument, order must be an INTEGER > 0");
                System.exit(1);
            }
            if (args.length == 2) {
                alpha = 1;
            } else if (args.length >= 3) {
                if (isAlphaValid(args[2])) {
                    alpha = Double.parseDouble(args[2]);
                } else {
                    System.err.println("ERROR: Invalid argument, alpha must be a DOUBLE within [0.001, 1]");
                    System.exit(1);
                }
                if (args.length == 4) {
                    if (isIntegerValid(args[3])) {
                        generateLength = Integer.parseInt(args[3]);
                    } else {
                        System.err.println("ERROR: Invalid argument, text length must be an INTEGER > 0");
                        System.exit(1);
                    }
                }
                // Create the file
                file = new File(filePath);
                // Verify if the file exists
                if (!file.exists()) {
                    System.err.println("ERROR: " + filePath + " not found!");
                    System.exit(1);
                }
                // Read the file
                try {
                    sc = new Scanner(file);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                // TODO: change, test only
                while (sc.hasNext()) {
                    String line = sc.nextLine();
                    System.out.println(line);
                }
            }
        } else {
            System.err.println("ERROR: Invalid number of arguments");
            System.err.println("USAGE: filePath order (alpha) (length to generate text)");
            System.exit(1);
        }
    }

    // Auxiliary functions

    /**
     * Verify if the argument is an valid Integer
     *
     * @param argument
     * @return
     */
    public static boolean isIntegerValid(String argument) {
        return isInteger(argument) && Integer.parseInt(argument) > 0;
    }

    /**
     * Verify if alpha is valid
     *
     * @param argument
     * @return
     */
    public static boolean isAlphaValid(String argument) {
        return isDouble(argument)
                && (Double.parseDouble(argument) >= 0.001 && Double.parseDouble(argument) <= 1);
    }

    /**
     * Verify if the argument is an Integer
     *
     * @param s
     * @return
     */
    public static boolean isInteger(String s) {
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
     * @return
     */
    public static boolean isDouble(String s) {
        try {
            Double.parseDouble(s);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }
}
