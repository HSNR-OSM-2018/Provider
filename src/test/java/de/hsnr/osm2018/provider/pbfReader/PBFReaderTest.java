package de.hsnr.osm2018.provider.pbfReader;

import org.junit.Test;
import java.io.IOException;

public class PBFReaderTest {

    @Test
    public void pbfReaderTest() throws IOException, InterruptedException {
        System.out.println("Ddorf Import Test");
        long t1 = System.currentTimeMillis();
        PBFReader reader = new PBFReader("ddorf.pbf", new PBFSink());
        reader.run();
        long t2 = System.currentTimeMillis();
        System.out.println("Execution Time:" + ((t2-t1)/1000) + "seconds");
    }

    @Test
    public void pbfReaderEuropaTest() throws IOException {
        System.out.println("Europa Import Test! This may take some time...");
        System.out.println("Ignore for installing.");
        long t1 = System.currentTimeMillis();
        PBFReader reader = new PBFReader("europaFilter.pbf", new PBFSink());
        reader.run();
        long t2 = System.currentTimeMillis();
        System.out.println("After: " + reader.getSink().getNodes().size());
        System.out.println("Execution Time:" + ((t2-t1)/1000) + "seconds");
    }

    @Test
    public void miniPBFTest() throws IOException {
        System.out.println("Mini PBFTest: ");
        long t1 = System.currentTimeMillis();
        PBFReader reader = new PBFReader("output.pbf", new PBFSink());
        System.out.println("Before: " + reader.getSink().getNodes().size());
        reader.run();
        long t2 = System.currentTimeMillis();
        System.out.println("After: " + reader.getSink().getNodes().size());
        System.out.println("Execution Time:" + ((t2-t1)/1000) + "seconds");
    }
}
