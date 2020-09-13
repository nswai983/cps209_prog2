//-----------------------------------------------------------
//File:   Prog2.java
//Desc:   This program accepts a png file as a cmd argument
//        and prints out metadata in the file on the console.
//----------------------------------------------------------- 

import org.junit.Test;
import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

public class PNGFileTest {

    @Test
    public void testPNGFile() throws IOException {

        PNGFile p = PNGFile.load("ice.png");
        ArrayList<PNGChunk> chunks = p.getChunks();

        assertEquals("ice.png", p.getName());
        assertEquals(true, p.getValidFile());
        assertEquals(false, p.getMoreChunks());
        assertEquals(10, chunks.size());

        for (PNGChunk chunk : chunks) {
            if (chunk.getChunkLength() > 0) {
                assertEquals(chunk.getChunkLength(), chunk.getChunkData().length);
            }

            if (chunk.getChunkType().equals("tEXt")) {

                if (chunk.getKeyword().equals("Software")) {
                    assertEquals("@(#)ImageMagick 4.2.9 99/09/01 cristy@mystic.es.dupont.com", chunk.getText().trim());
                }

                if (chunk.getKeyword().equals("Signature")) {
                    assertEquals("3248a69c033c15e46356a9ecb996c652", chunk.getText().trim());
                }

                if (chunk.getKeyword().equals("Delay")) {
                    assertEquals("100", chunk.getText().trim());
                }
            }
        }
    }
}
