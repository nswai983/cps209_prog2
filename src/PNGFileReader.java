//-----------------------------------------------------------
//File:   Prog2.java
//Desc:   This program accepts a png file as a cmd argument
//        and prints out metadata in the file on the console.
//----------------------------------------------------------- 

import java.util.*;
import java.io.*;

public class PNGFileReader implements AutoCloseable {

    private DataInputStream rd;
    private DataOutputStream out;
    private String fileIdentifier;

    // PNGFileReader class takes a PNGFile as an object
    // validates that the PNGFile is a PNG file by checking the signature 
    // at the beginning of the file
    public PNGFileReader(PNGFile file) throws IOException {

        this.rd = new DataInputStream(new FileInputStream(file.getName()));

        byte[] tag = new byte[8];
        rd.read(tag);
        fileIdentifier = PNGFile.bytesToString(tag);

        if (fileIdentifier.contains("PNG")) {
            file.setValidFile(true);
            file.setTag(fileIdentifier);
        } else {
            file.setValidFile(false);
            file.setMoreChunks(false);
        }

    }

    public void PNGFileOutput(PNGFile file) throws IOException {

        this.out = new DataOutputStream(new FileOutputStream(file.getName()));

        out.flush();
        out.writeBytes(file.getTag());
        for (PNGChunk chunk : file.getChunks()) {
            out.write(chunk.getChunkLength());
            out.writeBytes(chunk.getChunkType());
            out.write(chunk.getChunkData());
            out.write(chunk.getCRC());
        }


    }

    // closes the file automatically after opening with try()
    @Override
    public void close() throws IOException {
        rd.close();
    }

    // reads a chunk of data in a png file
    // returns the data chunk as a PNGChunk object
    public PNGChunk readChunk() throws IOException {

        int chunkLength = PNGFile.getLength(rd);
        String chunkType = PNGFile.getChunkType(rd);
        byte[] chunkData = PNGFile.getChunkData(rd, chunkLength);
        int crc = PNGFile.getCRC(rd);

        return new PNGChunk(chunkLength, chunkType, chunkData, crc);

    }
}
