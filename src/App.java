/*
 * This class contains the main method of the application
 */

import java.util.*;

public class App {

    public static void main(String[] args) {

        if (args.length != 1) {
            System.out.println("Usage: arun <PNGfilename>");
            System.exit(1);
        }

        String filename = args[0];
        System.out.println("Reading " + filename);

        
        try {
            ArrayList<String> metadata = PNG.getMetadata(filename);

            for (String item : metadata) {
                System.out.println(item.split(":")[0] + " " + item.split(":")[1]);
            }
        
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Usage: arun <PNGfilename>");
            System.exit(1);
        }
    }
}
