package com.mooreb.soak;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Soak {
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public static void main(char[] argv) {

    }
    public void soakOnce() throws IOException {
        final long now = System.nanoTime();
        final Random random = new Random(now);
        final int count = Math.abs(random.nextInt());
        LOG.info("computing {} xors", count);
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
        LOG.info("computed {} xors", count);
        LOG.info("writing /tmp/{}", now);
        final Path outputPath = Paths.get("/tmp", Long.toString(now));
        Files.write(outputPath, bytesOut);
    }

    public void soak() {

        while(true) {
            try {
                soakOnce();
            }
            catch(IOException e) {
                LOG.warn("caught IOException", e);
            }
        }
    }
}
