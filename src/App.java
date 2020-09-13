/*
 * This class contains the main method of the application
 */

import java.util.*;

public class App {

    public static void main(String[] args) {

        // 90% - 100% level
        if (args.length != 1 && args.length != 3) {
            System.out.println("Usage: arun <PNGfilename> [<keyword>] [<value>]");
            System.exit(1);
        }

        String filename = args[0];
        System.out.println("Reading " + filename);

        try {

            if (args.length == 1) {
                PNGFile p = PNGFile.load(filename);

                if (!p.getValidFile()) {
                    System.out.println(p.getName() + " does not contain PNG tags.");
                    System.exit(1);
                } 
                else {
                    ArrayList<PNGChunk> chunks = p.getChunks();
    
                    System.out.println("Metadata in " + filename + ":");
                    for (PNGChunk chunk : chunks) {
                        if (chunk.getChunkType().equals("tEXt")) {
                            System.out.println(chunk.getKeyword() + ":" + chunk.getText());
                        }
                    }
                }
            } 
            else if (args.length == 3) {
                String key = args[1];
                String value = args[2];
                PNGFile p = PNGFile.load(filename, key, value);

                if (!p.getValidFile()) {
                    System.out.println(p.getName() + " does not contain PNG tags.");
                    System.exit(1);
                } 
                else {
                    

                    ArrayList<PNGChunk> chunks = p.getChunks();
    
                    System.out.println("Metadata in " + filename + ":");
                    for (PNGChunk chunk : chunks) {
                        if (chunk.getChunkType().equals("tEXt")) {
                            System.out.println(chunk.getKeyword() + ":" + chunk.getText());
                        }
                    }
                }
            }



        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Usage: arun <PNGfilename> [<keyword>] [<value>]");
            System.exit(1);
        }

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

    }
}
