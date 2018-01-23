package de.hsnr.osm2018.provider.pbfReader;

import org.junit.Ignore;
import org.junit.Test;
import java.io.IOException;

public class PBFReaderTest {

    @Test
    public void pbfReaderTest() throws IOException, InterruptedException {
        System.out.println("Ddorf Import Test");
        long t1 = System.currentTimeMillis();
        PBFReader reader = new PBFReader("ddorf.pbf", new PBFNodeSink());
        reader.run();
        long t2 = System.currentTimeMillis();
        System.out.println("Execution Time:" + ((t2-t1)/1000) + "seconds");
        System.gc();
        System.out.println(reader.getSink().getNodes().size() + "Nodes stored.");
        reader.getSink().getNodes().forEach((i,n) -> System.out.println("Lat: " + n.getLatitude() + " / " + "Lon: " + n.getLongitude()));
        Thread.sleep(5000);
    }

    @Ignore
    @Test
    public void pbfReaderEuropaTest() throws IOException {
        System.out.println("Europa Import Test! This may take some time...");
        System.out.println("Ignore for installing.");
        long t1 = System.currentTimeMillis();
        PBFReader reader = new PBFReader("europaFilter.pbf", new PBFNodeSink());
        reader.run();
        long t2 = System.currentTimeMillis();
        System.out.println("Imported Nodes in Europe: " + reader.getSink().getNodes().size());
        System.out.println("Execution Time:" + ((t2-t1)/1000) + "seconds");

    }

    @Test
    public void miniPBFTest() throws IOException {
        System.out.println("Mini PBFTest: ");
        long t1 = System.currentTimeMillis();
        PBFReader reader = new PBFReader("output.pbf", new PBFNodeSink());
        System.out.println("Before: " + reader.getSink().getNodes().size());
        reader.run();
        long t2 = System.currentTimeMillis();
        System.out.println("After: " + reader.getSink().getNodes().size());
        System.out.println("Execution Time:" + ((t2-t1)/1000) + "seconds");
    }

}
