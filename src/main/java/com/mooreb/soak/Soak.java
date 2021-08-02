package com.mooreb.soak;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;


public class Soak {

    public static void main(String[] argv) {
        final Soak soak = new Soak();
        soak.soak();
    }

    public void soakOnce() throws IOException {
        final long now = System.nanoTime();
        final Random random = new Random(now);
        final int count = Math.abs(random.nextInt());
        System.out.println("computing " + count + " xors");
        byte[] bytesIn = new byte[16];
        byte[] bytesOut = new byte[16];
        for(int i=0; i<count; i++) {
            random.nextBytes(bytesIn);
            for(int j=0; j<16; j++) {
                int in = bytesIn[j];
                int out = in ^ bytesOut[j];
                bytesOut[j] = (byte) out;
            }
        }
        System.out.println("computed " + count + " xors");
        System.out.println("writing /tmp/" + now);
        final Path outputPath = Paths.get("/tmp", Long.toString(now));
        Files.write(outputPath, bytesOut);
    }

    public void soak() {

        while(true) {
            try {
                soakOnce();
            }
            catch(IOException e) {
                System.out.println("caught IOException");
            }
        }
    }
}
