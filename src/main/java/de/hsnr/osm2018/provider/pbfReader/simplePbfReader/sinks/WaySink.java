package de.hsnr.osm2018.provider.pbfReader.simplePbfReader.sinks;

import de.hsnr.osm2018.data.graph.Graph;
import de.hsnr.osm2018.data.graph.Node;
import de.hsnr.osm2018.provider.pbfReader.simplePbfReader.OsmWayUtils;
import org.openstreetmap.osmosis.core.container.v0_6.EntityContainer;
import org.openstreetmap.osmosis.core.container.v0_6.WayContainer;
import org.openstreetmap.osmosis.core.domain.v0_6.Way;
import org.openstreetmap.osmosis.core.task.v0_6.Sink;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class WaySink implements Sink {

    private static Logger logger = Logger.getLogger(WaySink.class.getName());

    private Map<Long, Node> nodes;
    private Way currentOsmWay;

    public WaySink(Map<Long, Node> nodeMap) {
        this.nodes = nodeMap;
    }

    public WaySink(Graph graph) {
        this.nodes = graph.getNodes();
    }

    @Override
    public void process(EntityContainer entityContainer) {
        if(entityContainer instanceof WayContainer) {
            this.currentOsmWay = (Way) entityContainer.getEntity();
            this.processCurrentOsmWay();
        }
    }

    @Override
    public void initialize(Map<String, Object> map) {
        logger.info("WaySink - Initialize...");
    }

    @Override
    public void complete() {
        logger.info("WaySink - Completed!");
    }

    @Override
    public void release() {
        logger.info("WaySink - Released!");
    }

    private void processCurrentOsmWay() {
        Node startNode = this.nodes.get(this.currentOsmWay.getWayNodes().get(0).getNodeId());
        Node endNode = this.nodes.get(this.currentOsmWay.getWayNodes().get(this.currentOsmWay.getWayNodes().size() - 1).getNodeId());
        String oneWayTag = OsmWayUtils.retrieveTag(this.currentOsmWay, "oneway");
        if(oneWayTag.equals("yes")) {
            startNode.addEdge(OsmWayUtils.osmWayAndNodesToEdge(currentOsmWay, startNode, endNode));
        } else if(oneWayTag.equals("no")) {
            startNode.addEdge(OsmWayUtils.osmWayAndNodesToEdge(currentOsmWay, startNode, endNode));
            endNode.addEdge(OsmWayUtils.osmWayAndNodesToEdge(currentOsmWay, endNode, startNode));
        } else if(oneWayTag.equals("-1")) {
            endNode.addEdge(OsmWayUtils.osmWayAndNodesToEdge(currentOsmWay, endNode, startNode));
        }
    }

    public Map<Long, Node> getNodes() {
        return this.nodes;
    }
}
