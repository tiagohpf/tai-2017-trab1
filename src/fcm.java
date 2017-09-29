import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *
 * TAI, October 2017
 *
 * Assignment 1 - Finite-context model and automatic text generation
 *
 * @author Bárbara Jael
 * @author Tiago Faria
 * @author Miriam Cardoso, 72181, miriamcardoso@ua.pt
 *
 */

public class Fcm {

    public static void main(String[] args) {

        Scanner sc = null;
        String filePath = null;
        File file;
        int tempOrder;
        int order;
        double tempAlpha;
        double alpha;
        int tempGenerateLength;
        int generateLength;


        // Arguments
        if (args.length == 2) {
            filePath = args[0];

            if (isInteger(args[1])) {
                tempOrder = Integer.parseInt(args[1]);

                if (tempOrder > 0 ) {
                    order = tempOrder;
                }
                else {
                    System.out.println("ERROR: Invalid argument, order must be > 0");
                    System.exit(1);
                }
            }
            else {
                System.out.println("ERROR: Invalid argument, order must be an INTEGER");
                System.exit(1);
            }
        }
        else if (args.length == 3) {
            filePath = args[0];

            if (isInteger(args[1])) {
                tempOrder = Integer.parseInt(args[1]);

                if (tempOrder > 0 ) {
                    order = tempOrder;
                }
                else {
                    System.out.println("ERROR: Invalid argument, order must be > 0");
                    System.exit(1);
                }
            }
            else {
                System.out.println("ERROR: Invalid argument, order must be an INTEGER");
                System.exit(1);
            }

            if (isDouble(args[2])) {
                tempAlpha = Double.parseDouble(args[2]);

                if (tempAlpha >= 0.001 && tempAlpha <= 1 ) {
                    alpha = tempAlpha;
                }
                else {
                    System.out.println("ERROR: Invalid argument, 0.001 <= alpha <= 1");
                    System.exit(1);
                }
            }
            else {
                System.out.println("ERROR: Invalid argument, alpha must be a DOUBLE");
                System.exit(1);
            }
        }
        else if (args.length == 4) {
            filePath = args[0];

            if (isInteger(args[1])) {
                tempOrder = Integer.parseInt(args[1]);

                if (tempOrder > 0 ) {
                    order = tempOrder;
                }
                else {
                    System.out.println("ERROR: Invalid argument, order must be > 0");
                    System.exit(1);
                }
            }
            else {
                System.out.println("ERROR: Invalid argument, order must be an INTEGER");
                System.exit(1);
            }

            if (isDouble(args[2])) {
                tempAlpha = Double.parseDouble(args[2]);

                if (tempAlpha >= 0.001 && tempAlpha <= 1 ) {
                    alpha = tempAlpha;
                }
                else {
                    System.out.println("ERROR: Invalid argument, 0.001 <= alpha <= 1");
                    System.exit(1);
                }
            }
            else {
                System.out.println("ERROR: Invalid argument, alpha must be a DOUBLE");
                System.exit(1);
            }

            if (isInteger(args[3])) {
                tempGenerateLength = Integer.parseInt(args[3]);

                if (tempGenerateLength > 0 ) {
                    generateLength = tempGenerateLength;
                }
                else {
                    System.out.println("ERROR: Invalid argument, text length must be > 0");
                    System.exit(1);
                }
            }
            else {
                System.out.println("ERROR: Invalid argument, text length must be an INTEGER");
                System.exit(1);
            }
        }
        else {
            System.out.println("ERROR: Invalid number of arguments");
            System.exit(1);
        }


        // Create the file
        file = new File(filePath);
        
        // Verify if the file exists
        if(!file.exists()) {
            System.out.println("ERROR: " + filePath + " not found!");
            System.exit(1);
        }

        // Read the file
        try {
            sc = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // TODO: change, test only
        while(sc.hasNext()) {
            String line = sc.nextLine();
            System.out.println(line);
        }
    }


    // Auxiliary functions

    /**
     * Verify if the arg is an Integer
     *
     * @param s
     * @return
     */
    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
            return true;
        }
        catch (NumberFormatException ex) {
            return false;
        }
    }

    /**
     * Verify if the arg is a Double
     *
     * @param s
     * @return
     */
    public static boolean isDouble(String s) {
        try {
            Double.parseDouble(s);
            return true;
        }
        catch (NumberFormatException ex) {
            return false;
        }
    }
}
