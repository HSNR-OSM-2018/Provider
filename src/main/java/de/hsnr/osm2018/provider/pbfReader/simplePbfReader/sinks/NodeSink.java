package de.hsnr.osm2018.provider.pbfReader.simplePbfReader.sinks;

import de.hsnr.osm2018.data.graph.Graph;
import de.hsnr.osm2018.data.graph.Node;
import org.openstreetmap.osmosis.core.container.v0_6.EntityContainer;
import org.openstreetmap.osmosis.core.container.v0_6.NodeContainer;
import org.openstreetmap.osmosis.core.task.v0_6.Sink;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class NodeSink implements Sink {

    private static Logger logger = Logger.getLogger(NodeSink.class.getName());

    private Map<Long, Node> nodes;
    private org.openstreetmap.osmosis.core.domain.v0_6.Node currentOsmNode;

    public NodeSink() {
        this.nodes = new HashMap<>();
    }

    public NodeSink(Graph graph) {
        this.nodes = graph.getNodes();
    }

    @Override
    public void process(EntityContainer entityContainer) {
        if(entityContainer instanceof NodeContainer) {
            this.currentOsmNode = (org.openstreetmap.osmosis.core.domain.v0_6.Node) entityContainer.getEntity();
            this.processCurrentOsmNode();
        }
    }

    @Override
    public void initialize(Map<String, Object> map) {
        logger.info("NodeSink - Initialize...");
    }

    @Override
    public void complete() {
        logger.info("NodeSink - Completed!");
    }

    @Override
    public void release() {
        logger.info("NodeSink - Released!");

    }

    private void processCurrentOsmNode() {
        this.nodes.put(this.currentOsmNode.getId(), new Node(this.currentOsmNode.getId(),this.currentOsmNode.getLatitude(), this.currentOsmNode.getLongitude()));
    }

    public Map<Long, Node> getNodes() {
        return this.nodes;
    }
}
