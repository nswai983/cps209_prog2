//-----------------------------------------------------------
//File:   Prog1.java
//Desc:   This program accepts starship configurations as
//        input and prints a report in the console.
//----------------------------------------------------------- 

import java.util.*;

// import sun.reflect.generics.tree.ByteSignature;

import java.io.*;

public class PNG {

    public static ArrayList<String> getMetadata(String filename) throws Exception {

        ArrayList<String> metadata = new ArrayList<String>();

        // implement static method
        try (var br = new FileInputStream(filename)) {
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
            String chunkType;
            String chunkData;
            String crc;

            while (chunkLength > 0) {
                chunkType = getChunkType(br);
                chunkData = getChunkData(br, chunkLength);
                crc = getCRC(br);
                System.out.println(chunkType + "___" + chunkLength);

                chunkLength = getLength(br);

            }
        }

        return metadata;
    }

    // Converts an array of bytes to a String
    private static String bytesToString(byte[] data) {
        return new String(data);
    }

    private static int bytesToSize(byte[] sizeBytes) {
        // System.out.println(java.nio.ByteBuffer.wrap(sizeBytes).order(java.nio.ByteOrder.BIG_ENDIAN).getInt());
        return (sizeBytes[0] & 0xff) << 21 | (sizeBytes[1] & 0xff) << 14 | (sizeBytes[2] & 0xff) << 7
                | (sizeBytes[3] & 0xff);
    }

    // Reads length of chunk
    private static int getLength(FileInputStream file) throws IOException {
        byte[] length = new byte[4];
        file.read(length);
        return bytesToSize(length);
    }

    // Reads Type of chunk
    private static String getChunkType(FileInputStream file) throws IOException {
        byte[] chunkType = new byte[4];
        file.read(chunkType);
        return new String(bytesToString(chunkType));
    }

    // Reads Type of chunk
    private static String getChunkData(FileInputStream file, int length) throws IOException {
        byte[] chunkData = new byte[length];
        file.read(chunkData);
        if (chunkData.length > 1000) {
            return "Data too long";
        } else {
            return bytesToString(chunkData);
        }

    }

    // Reads Type of chunk
    private static String getCRC(FileInputStream file) throws IOException {
        byte[] chunkData = new byte[4];
        file.read(chunkData);
        return bytesToString(chunkData);
    }



    
}
