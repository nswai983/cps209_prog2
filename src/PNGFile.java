//-----------------------------------------------------------
//File:   Prog2.java
//Desc:   This program accepts a png file as a cmd argument
//        and prints out metadata in the file on the console.
//----------------------------------------------------------- 

import java.util.*;
import java.io.*;

public class PNGFile {
    
    private ArrayList<PNGChunk> chunks  = new ArrayList<PNGChunk>();

    public PNGFile() {
        
    }

    public static PNGFile load(String filename) throws IOException {

        PNGFile png = new PNGFile();

        try (var br = new DataInputStream (new FileInputStream(filename))) {
            
            byte[] tag = new byte[8];
            br.read(tag);
            String fileIdentifier = bytesToString(tag);
            if (!fileIdentifier.contains("PNG")) {
                // this needs to be handled elsewhere
                System.out.println(fileIdentifier);
                System.out.println(filename + " does not contain PNG tags.");
                System.exit(1);
            }

            // int length = br.readAllBytes().length - tag.length;
            boolean cont = true;
            int chunkLength = getLength(br);
            String chunkType = "";
            byte[] chunkData;
            int crc;
            

            while (cont) {
                chunkType = getChunkType(br);
                chunkData = getChunkData(br, chunkLength);
                crc = getCRC(br);

                // System.out.println(chunkLength + ":::" + chunkType);
                // System.out.println(chunkData + ":::" + crc);
                
                png.addChunk( new PNGChunk(chunkLength, chunkType, chunkData, crc));

                if (chunkType.equals("IEND")) {
                    break;
                }
                else {
                    chunkLength = getLength(br);
                }

            }
        }

        return png;
    }

    // Converts an array of bytes to a String
    private static String bytesToString(byte[] data) {
        return new String(data);
    }

    private static int bytesToSize(byte[] sizeBytes) {
        return java.nio.ByteBuffer.wrap(sizeBytes).getInt();
    }

    // Reads length of chunk
    private static int getLength(DataInputStream file) throws IOException {
        byte[] length = new byte[4];
        file.read(length);
        return bytesToSize(length);
    }

    // Reads Type of chunk
    private static String getChunkType(DataInputStream file) throws IOException {
        byte[] chunkType = new byte[4];
        file.read(chunkType);
        return bytesToString(chunkType);
    }

    // Reads Type of chunk
    private static byte[] getChunkData(DataInputStream file, int length) throws IOException { 
        if (length == 0) {
            return new byte[1];
        }
        else {
            byte[] data = new byte[length];
            file.read(data);
            return data;
        }

    }

    // Reads Type of chunk
    private static int getCRC(DataInputStream file) throws IOException {
        byte[] chunkData = new byte[4];
        file.read(chunkData);
        return bytesToSize(chunkData);
    }

    public void addChunk(PNGChunk chunk) {
        chunks.add(chunk);
    }

    public ArrayList<PNGChunk> getChunks() {
        return chunks;
    }
}
