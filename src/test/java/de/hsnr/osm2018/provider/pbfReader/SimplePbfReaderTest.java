package de.hsnr.osm2018.provider.pbfReader;

import de.hsnr.osm2018.data.graph.Edge;
import de.hsnr.osm2018.data.graph.Node;
import de.hsnr.osm2018.provider.pbfReader.simplePbfReader.SimplePbfReader;
import org.junit.Test;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SimplePbfReaderTest {

    @Test
    public void testSimplePbfReaderDdorf() {
        this.executeTest("ddorfNodes.pbf", "ddorfWays.pbf", false, false);
    }


    @Test
    public void testSimplePbfReaderMini() {
        this.executeTest("miniNodes.pbf", "miniWays.pbf", true, true);
    }

    @Test
    public void testSimplePbfReaderNRW() {
        this.executeTest("nrwNode.pbf", "nrwWay.pbf", false, false);
    }

    @Test
    public void testSimplePbfReaderGermany() {
        this.executeTest("GermanyNode.pbf", "GermanyWay.pbf", false, false);
    }
    private void executeTest(String nodeFilename, String wayFilename, boolean printOverpass, boolean printEdgeInfos) {
        SimplePbfReader reader = new SimplePbfReader(nodeFilename, wayFilename, null);
        long t1 = System.currentTimeMillis();
        reader.run();
        long t2 = System.currentTimeMillis();
        System.out.println("ExectutionTime: " + ((t2-t1) / 1000) + "Seconds");
        System.out.println("Imported Nodes: " + reader.getWaySink().getNodes().size());
        /*reader.getWaySink().getNodes().forEach((i,n) -> {
            if(n.getEdges().size() <= 0) {
                System.out.println("No Edges for: " + n.getId() + " With EdgesSize = " + n.getEdges().size());
            }
        });*/
        if(printOverpass) {
            String apiCommand = createOverpassVisualizationString(reader.getWaySink().getNodes());
            System.out.println(apiCommand);
        }
        if(printEdgeInfos) {
            String edgeString = createEdgeInfoString(reader.getWaySink().getNodes());
            System.out.println(edgeString);
        }
    }

    private String createOverpassVisualizationString(Map<Long, Node> nodes) {
        String result = "";
        for(Map.Entry<Long, Node> node : nodes.entrySet()) {
            result += "node(" + node.getValue().getId() + ");out;\n";
        }
        return result;
    }

    private String createEdgeInfoString(Map<Long, Node> nodes) {
        String result = "";
        Set<Edge> edges = new HashSet<>();
        for(Map.Entry<Long, Node> node : nodes.entrySet()) {
            edges.addAll(node.getValue().getEdges());
        }
        for(Edge edge : edges) {
                result += "Edge From " + edge.getStartNode().getId() + " to " + edge.getDestinationNode().getId() + " with maxSpeed: " + edge.getSpeed() + " And Type: " + edge.getType() + "\n";
        }
        return result;
    }
}