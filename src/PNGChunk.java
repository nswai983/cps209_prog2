//-----------------------------------------------------------
//File:   Prog2.java
//Desc:   This program accepts a png file as a cmd argument
//        and prints out metadata in the file on the console.
//----------------------------------------------------------- 

import java.util.*;
import java.io.*;

public class PNGChunk {

    // private instance variables
    private int chunkLength;
    private String chunkType;
    private byte[] chunkData;
    private int crc;

    // public constructor for PNGChunk
    public PNGChunk(int chunkLength, String chunkType, byte[] chunkData, int crc) {
        this.chunkLength = chunkLength;
        this.chunkType = chunkType;
        this.chunkData = chunkData;
        this.crc = crc;
    }

    // getters and setters

    // extracts keyword from metadata
    public String getKeyword() {

        String keyword = "";

        int index = 0;
        byte letter = chunkData[index];
        int ch = (int) letter;

        while (ch != 00) {
            keyword += (char) letter;
            index++;
            letter = chunkData[index];
            ch = (int) letter;
        }

        return keyword;
    }

    // extracts text from metadata
    public String getText() {

        String text = "";

        int index = 0;
        byte letter = chunkData[index];
        int ch = (int) letter;

        while (ch != 00) {
            index++;
            letter = chunkData[index];
            ch = (int) letter;
        }

        while (index < chunkData.length) {
            letter = chunkData[index];
            text += (char) letter;
            index++;
        }

        return text;
    }

    public void setData(String key, String value) {
        chunkLength = key.length() + value.length() + 1;
        String data = key + " " + value;
        byte[] newData = new byte[chunkLength];

        for (int i = 0; i < chunkLength; i++) {
            if (i == key.length()) {
                newData[i] = (byte) 0;
            } 
            else {
                newData[i] = (byte) data.charAt(i);
            }
            
        }

        chunkData = newData;
    }

    public int getChunkLength() {
        return chunkLength;
    }

    public String getChunkType() {
        return chunkType;
    }

    public byte[] getChunkData() {
        return chunkData;
    }

    public int getCRC() {
        return crc;
    }

    public void setChunkLength(int length) {
        chunkLength = length;
    }

    public void setChunkType(String type) {
        chunkType = type;
    }

    public void setChunkData(byte[] data) {
        chunkData = data;
    }

    public void setCRC(int length) {
        crc = length;
    }

}
