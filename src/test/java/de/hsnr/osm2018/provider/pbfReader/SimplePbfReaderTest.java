package de.hsnr.osm2018.provider.pbfReader;

import de.hsnr.osm2018.data.graph.Node;
import de.hsnr.osm2018.provider.pbfReader.simplePbfReader.SimplePbfReader;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

public class SimplePbfReaderTest {

    @Test
    public void testSimplePbfReaderDdorf() {
        this.executeTest("ddorfNodes.pbf", "ddorfWays.pbf", false);
    }


    @Test
    public void testSimplePbfReaderMini() {
        this.executeTest("miniNode.pbf", "miniWay.pbf", true);
    }


    private void executeTest(String nodeFilename, String wayFilename, boolean printOverpass) {
        SimplePbfReader reader = new SimplePbfReader(nodeFilename, wayFilename, null);
        long t1 = System.currentTimeMillis();
        reader.run();
        long t2 = System.currentTimeMillis();
        System.out.println("ExectutionTime: " + ((t2-t1) / 1000) + "Seconds");
        System.out.println("Imported Nodes: " + reader.getWaySink().getNodes().size());
        reader.getWaySink().getNodes().forEach((i,n) -> {
            if(n.getEdges().size() <= 0) {
                System.out.println("No Edges for: " + n.getId() + " With EdgesSize = " + n.getEdges().size());
            }
        });
        if(printOverpass) {
            String apiCommand = createOverpassVisualization(reader.getWaySink().getNodes());
            System.out.println(apiCommand);
        }
    }

    private String createOverpassVisualization(Map<Long, Node> nodes) {
        String result = "";
        for(Map.Entry<Long, Node> node : nodes.entrySet()) {
            result += "node(" + node.getValue().getId() + ");out;\n";
        }
        return result;
    }
}