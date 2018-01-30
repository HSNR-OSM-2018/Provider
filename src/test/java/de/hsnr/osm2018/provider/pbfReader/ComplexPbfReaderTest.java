package de.hsnr.osm2018.provider.pbfReader;

import de.hsnr.osm2018.data.graph.Graph;
import de.hsnr.osm2018.data.graph.Node;
import de.hsnr.osm2018.provider.pbfReader.complexReader.ComplexPbfReader;
import de.hsnr.osm2018.provider.pbfReader.complexReader.sinks.InitSink;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Map;

@Ignore
public class ComplexPbfReaderTest {

    @Test
    public void importTestDdorf() throws InterruptedException {
        System.out.println("ComplexPbfReaderTest Düsseldorf...");
        this.executeReaderTest("ddorf.pbf");

    }

    @Test
    public void importTestNRW() throws InterruptedException {
        System.out.println("ComplexPbfReaderTest NRW...");
        this.executeReaderTest("nrw.pbf");

    }

    @Ignore
    @Test
    public void importTestEurope() throws InterruptedException {
        System.out.println("ComplexPbfReaderTest Europa...");
        this.executeReaderTest("europaFilter2.pbf");
    }

    private void executeReaderTest(String filename) throws InterruptedException {
        long t1 = System.currentTimeMillis();
        ComplexPbfReader reader = new ComplexPbfReader(filename, new InitSink(), new Graph());
        reader.run();
        long t2 = System.currentTimeMillis();
        System.out.println("Execution Time:" + ((t2-t1)/1000) + "seconds");
        System.out.println(reader.getMainSink().getNodes().size() + "Nodes stored.");
        boolean containsEmptyNodes = false;
        for(Map.Entry<Long, Node> n : reader.getMainSink().getNodes().entrySet()) {
            containsEmptyNodes = n.getValue().getEdges().isEmpty();
        }
        Thread.sleep(5000);
        Assert.assertFalse(containsEmptyNodes);
    }

}
