//-----------------------------------------------------------
//File:   Prog2.java
//Desc:   This program accepts a png file as a cmd argument
//        and prints out metadata in the file on the console.
//----------------------------------------------------------- 

import java.util.*;
import java.io.*;

public class PNG {

    // Takes in a png file name and extracts metadata from chunks
    // Returns an ArrayList of Strings containing the file's metadata
    public static ArrayList<String> getMetadata(String filename) throws Exception {

        ArrayList<String> metadata = new ArrayList<String>();

        // implement static method
        try (var br = new DataInputStream(new FileInputStream(filename))) {
            byte[] tag = new byte[8];

            br.read(tag);

            String fileIdentifier = bytesToString(tag);
            if (!fileIdentifier.contains("PNG")) {
                System.out.println(fileIdentifier);
                System.out.println(filename + " does not contain PNG tags.");
                System.exit(1);
            }

            // int length = br.readAllBytes().length - tag.length;
            int chunkLength = getLength(br);
            String chunkType = "";
            String chunkData;
            String crc;

            while (!chunkType.equals("IEND")) {
                chunkType = getChunkType(br);

                chunkData = getChunkData(br, chunkType, chunkLength);
                crc = getCRC(br);
                // System.out.println(chunkType + "___" + chunkLength);

                if (chunkType.equals("tEXt")) {
                    metadata.add(chunkData);
                }

                chunkLength = getLength(br);
            }
        }

        return metadata;
    }

    // Converts an array of bytes to a String
    // Returns the String
    private static String bytesToString(byte[] data) {
        return new String(data);
    }

    // Converts an array of bytes to a integer
    // Returns the integer
    private static int bytesToSize(byte[] sizeBytes) {
        return java.nio.ByteBuffer.wrap(sizeBytes).getInt();
    }

    // Reads length of chunk
    // Returns the length as an int
    private static int getLength(DataInputStream file) throws IOException {
        byte[] length = new byte[4];
        file.read(length);
        return bytesToSize(length);
    }

    // Reads type of chunk
    // Returns type as a String
    private static String getChunkType(DataInputStream file) throws IOException {
        byte[] chunkType = new byte[4];
        file.read(chunkType);
        return new String(bytesToString(chunkType));
    }

    // Reads data of chunk
    // Retuns data as a String
    private static String getChunkData(DataInputStream file, String chunkType, int length) throws IOException {

        String data = "";

        if (!chunkType.equals("tEXt")) {
            byte[] chunkData = new byte[length];
            file.read(chunkData);
            if (chunkData.length > 1000) {
                return "Data too long";
            } else {
                return bytesToString(chunkData);
            }
        } else {
            byte[] chunkData = new byte[length];

            for (byte chunk : chunkData) {
                chunk = file.readByte();
                if (chunk == 0) {
                    data += ": ";
                } else {
                    data += (int) chunk;
                }
            }

            return data;
        }

    }

    // Reads crc of chunk
    // Returns crc as a String
    private static String getCRC(DataInputStream file) throws IOException {
        byte[] chunkData = new byte[4];
        file.read(chunkData);
        return bytesToString(chunkData);
    }

}
