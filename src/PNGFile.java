//-----------------------------------------------------------
//File:   Prog2.java
//Desc:   This program accepts a png file as a cmd argument
//        and prints out metadata in the file on the console.
//----------------------------------------------------------- 

import java.util.*;
import java.io.*;

public class PNGFile {

    private ArrayList<PNGChunk> chunks = new ArrayList<PNGChunk>();
    private boolean moreChunks;
    private boolean validFile;
    private String filename;
    private String tag;

    public PNGFile(String filename) {
        this.moreChunks = true;
        this.filename = filename;
        this.validFile = true;
    }

    // takes file name as input and creates a PNGFile object loaded with chunks of data
    // returns a PNGFile object
    public static PNGFile load(String filename) throws IOException {

        PNGFile file = new PNGFile(filename);

        try (var rd = new PNGFileReader(file)) {

            if (file.getValidFile()) {
                while (file.moreChunks) {
                    PNGChunk chunk = rd.readChunk();
                    file.addChunk(chunk);
                    if (chunk.getChunkType().equals("IEND")) {
                        file.setMoreChunks(false);
                    }
                }
            }
        }

        return file;

        // 90% level
        // try (var br = new DataInputStream (new FileInputStream(filename))) {
            
        //     byte[] tag = new byte[8];
        //     br.read(tag);
        //     String fileIdentifier = bytesToString(tag);
        //     if (!fileIdentifier.contains("PNG")) {
        //         // this needs to be handled elsewhere
        //         System.out.println(fileIdentifier);
        //         System.out.println(filename + " does not contain PNG tags.");
        //         System.exit(1);
        //     }

        //     // int length = br.readAllBytes().length - tag.length;
        //     boolean cont = true;
        //     int chunkLength = getLength(br);
        //     String chunkType = "";
        //     byte[] chunkData;
        //     int crc;
            

        //     while (cont) {
        //         chunkType = getChunkType(br);
        //         chunkData = getChunkData(br, chunkLength);
        //         crc = getCRC(br);

        //         // System.out.println(chunkLength + ":::" + chunkType);
        //         // System.out.println(chunkData + ":::" + crc);
                
        //         file.addChunk( new PNGChunk(chunkLength, chunkType, chunkData, crc));

        //         if (chunkType.equals("IEND")) {
        //             break;
        //         }
        //         else {
        //             chunkLength = getLength(br);
        //         }

        //     }
        //
        //    return file;
        //
        // }

    }

    // takes file name and new message as input and creates a PNGFile object loaded with chunks of data
    // returns a PNGFile object
    public static PNGFile load(String filename, String key, String value) throws IOException {

        PNGFile file = new PNGFile(filename);

        try (var rd = new PNGFileReader(file)) {

            boolean changed = false;

            if (file.getValidFile()) {
                while (file.moreChunks) {
                    PNGChunk chunk = rd.readChunk();

                    if (chunk.getChunkType().equals("tEXt") && changed == false) {
                        chunk.setData(key, value);
                        changed = true;
                    }

                    file.addChunk(chunk);
                    if (chunk.getChunkType().equals("IEND")) {
                        file.setMoreChunks(false);
                    }
                }
            }

            rd.PNGFileOutput(file);
        }

        return file;
    }

    // Converts an array of bytes to a String
    // Returns the String
    public static String bytesToString(byte[] data) {
        return new String(data);
    }

    // Converts an array of bytes to a integer
    // Returns the integer
    public static int bytesToSize(byte[] sizeBytes) {
        return java.nio.ByteBuffer.wrap(sizeBytes).getInt();
    }

    // Reads length of chunk
    // Returns the length as an int
    public static int getLength(DataInputStream file) throws IOException {
        byte[] length = new byte[4];
        file.read(length);
        return bytesToSize(length);
    }

    // Reads type of chunk
    // Returns type as a String
    public static String getChunkType(DataInputStream file) throws IOException {
        byte[] chunkType = new byte[4];
        file.read(chunkType);
        return bytesToString(chunkType);
    }

    // Reads data of chunk
    // Retuns data as a String
    public static byte[] getChunkData(DataInputStream file, int length) throws IOException {
        if (length == 0) {
            return new byte[1];
        } else {
            byte[] data = new byte[length];
            file.read(data);
            return data;
        }
    }

    // reads crc of chunk
    // returns crc as a String
    public static int getCRC(DataInputStream file) throws IOException {
        byte[] chunkData = new byte[4];
        file.read(chunkData);
        return bytesToSize(chunkData);
    }

    // takes PNGChunk and adds it to the ArrayList<String> of chunks
    public void addChunk(PNGChunk chunk) {
        chunks.add(chunk);
    }

    // getters and setters

    // returns ArrayList<String> of chunks
    public ArrayList<PNGChunk> getChunks() {
        return chunks;
    }

    // sets the boolean value of if the file has more chunks
    public void setMoreChunks(boolean bool) {
        moreChunks = bool;
    }

    // returns the boolean value of if the file has more chunks
    public boolean getMoreChunks() {
        return moreChunks;
    }

    // returns the name of the file as a String
    public String getName() {
        return this.filename;
    }

    // sets the boolean value of if the file is a png file
    public void setValidFile(boolean bool) {
        this.validFile = bool;
    }

    // returns the boolean value of if the file is a png file
    public boolean getValidFile() {
        return validFile;
    }

    // returns the tag of the file as a String
    public String getTag() {
        return this.tag;
    }

    // returns the tag of the file as a String
    public void setTag(String tag) {
        this.tag = tag;
    }
}
