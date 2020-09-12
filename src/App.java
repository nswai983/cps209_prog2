/*
 * This class contains the main method of the application
 */

import java.util.*;

public class App {

    public static void main(String[] args) {

        // 80 % level
        // if (args.length != 1) {
        //     System.out.println("Usage: arun <PNGfilename>");
        //     System.exit(1);
        // }

        // String filename = args[0];
        // System.out.println("Reading " + filename);

        
        // try {
        //     ArrayList<String> metadata = PNG.getMetadata(filename);

        //     System.out.println("Metadata in " + filename + ":");
        //     // System.out.println(metadata);
        //     for (String item : metadata) {
        //         System.out.println(item);
        //         // System.out.println(item.substring(0, item.indexOf(null)) + ": " + item.substring(item.indexOf(null)));
        //     }
        
        // } catch (Exception e) {
        //     System.out.println(e);
        //     System.out.println("Usage: arun <PNGfilename>");
        //     System.exit(1);
        // }

        // 90% level
        if (args.length != 1) {
            System.out.println("Usage: arun <PNGfilename>");
            System.exit(1);
        }

        String filename = args[0];
        System.out.println("Reading " + filename);

        
        try {
            PNGFile p = PNGFile.load(filename);
            ArrayList<PNGChunk> chunks = p.getChunks();
            
            System.out.println("Metadata in " + filename + ":");
            for (PNGChunk chunk : chunks) {
                if (chunk.getChunkType().equals("tEXt")) {
                    System.out.println(chunk.getKeyword() + ":" + chunk.getText());
                }
            }
        
        } 
        catch (Exception e) {
            System.out.println(e);
            System.out.println("Usage: arun <PNGfilename>");
            System.exit(1);
        }

    }
}
