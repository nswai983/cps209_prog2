//-----------------------------------------------------------
//File:   Prog2.java
//Desc:   This program accepts a png file as a cmd argument
//        and prints out metadata in the file on the console.
//----------------------------------------------------------- 

import java.util.*;
import java.io.*;

public class PNGFileReader implements AutoCloseable {
    

    private DataInputStream rd;

    public PNGFileReader(PNGFile file) throws IOException {

        this.rd = new DataInputStream(new FileInputStream(file.getName()));

        byte[] tag = new byte[8];
        rd.read(tag);
        String fileIdentifier = PNGFile.bytesToString(tag);
        
        if (fileIdentifier.contains("PNG")) {
            file.setValidFile(true);   
        }
        else {
            file.setValidFile(false);
            file.setMoreChunks(false);
        }

    }


    @Override
    public void close() throws IOException {
        rd.close();
    }

    public PNGChunk readChunk() throws IOException{

        int chunkLength = PNGFile.getLength(rd);
        String chunkType = PNGFile.getChunkType(rd);
        byte[] chunkData = PNGFile.getChunkData(rd, chunkLength);
        int crc = PNGFile.getCRC(rd);
        
        return new PNGChunk(chunkLength, chunkType, chunkData, crc);

       
    }
}
