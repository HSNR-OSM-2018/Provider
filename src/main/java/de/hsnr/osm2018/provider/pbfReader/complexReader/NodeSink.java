package de.hsnr.osm2018.provider.pbfReader.complexReader;

import de.hsnr.osm2018.data.graph.Edge;
import de.hsnr.osm2018.data.graph.EdgeType;
import de.hsnr.osm2018.data.graph.Graph;
import de.hsnr.osm2018.data.graph.Node;
import de.hsnr.osm2018.data.utils.EdgeTypeUtils;
import de.hsnr.osm2018.data.utils.OSMMaxSpeedUtils;
import de.hsnr.osm2018.provider.pbfReader.INodeSink;
import org.openstreetmap.osmosis.core.container.v0_6.EntityContainer;
import org.openstreetmap.osmosis.core.domain.v0_6.Tag;
import org.openstreetmap.osmosis.core.domain.v0_6.Way;
import org.openstreetmap.osmosis.core.domain.v0_6.WayNode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class NodeSink implements INodeSink {

    private final Logger logger = Logger.getLogger(NodeSink.class.getSimpleName());

    private Set<Long> relevantNodes;
    private Map<Long, Node> nodes;

    private org.openstreetmap.osmosis.core.domain.v0_6.Node currentNode;
    private Way currentWay;

    private long startTimestamp;
    private long endTimestamp;

    private boolean processingWays;
    private boolean processingNodes;

    public NodeSink(Set<Long> relevantNodes) {
        this.relevantNodes = relevantNodes;
        this.nodes = new HashMap<>();
        this.processingWays = false;
        this.processingNodes = false;
    }

    public NodeSink(Set<Long> relevantNodes, Graph graph) {
        this.relevantNodes = relevantNodes;
        this.nodes = graph.getNodes();
        this.processingWays = false;
        this.processingNodes = false;
    }

    @Override
    public void process(EntityContainer entityContainer) {
        if(this.processingNodes == false) {
            logger.info("Process Nodes...");
            processingNodes = true;
        }
        if(entityContainer.getEntity() instanceof org.openstreetmap.osmosis.core.domain.v0_6.Node && relevantNodes.contains(entityContainer.getEntity().getId())) {
            this.relevantNodes.remove(entityContainer.getEntity().getId());
            this.currentNode = (org.openstreetmap.osmosis.core.domain.v0_6.Node) entityContainer.getEntity();
            this.processCurrentNode();
        } else if(entityContainer.getEntity() instanceof Way) {
            this.currentWay = (Way) entityContainer.getEntity();
            this.processCurrentWay();
        }
    }

    private void processCurrentWay() {
        if(this.processingWays == false) {
            logger.info("Process Ways...");
            this.processingWays = true;
        }
        if (this.currentWay.getWayNodes().size() >= 2) {
            List<WayNode> wayNodes = this.currentWay.getWayNodes();
            Node startNode = this.nodes.get(wayNodes.get(0).getNodeId());
            long endNodeId;
            Node endNode;
            //Node tempNode;
            Edge currentEdge;

            if(startNode == null)
                return;

            Map<String, Tag> tags = this.currentWay.getTags().stream().filter(tag -> tag.getKey().equals("maxspeed") ||
                    tag.getKey().equals("length") || tag.getKey().equals("highway") || tag.getKey().equals("oneway") || tag.getKey().equals("junction")).collect(Collectors.toMap(t -> t.getKey(), t -> t));
            double distance = 0D;

            //tempNode = startNode;
            for(int i = 1; i < wayNodes.size(); i++) {
                endNodeId = wayNodes.get(i).getNodeId();

                //distance += endNode.getRealDistance(endNode);
                //tempNode = endNode;
                if(this.nodes.containsKey(endNodeId)){
                    endNode = this.nodes.get(endNodeId);
                    // TODO: Länge wird nun für Teilwege falsch sein
                    currentEdge = new Edge(startNode, endNode, (int)startNode.getRealDistance(endNode), tags.containsKey("maxspeed") ? this.evaluateMaxSpeed(tags.get("maxspeed")) : 0, tags.containsKey("highway") ? EdgeTypeUtils.evaluateEdgeTypeByOSMTagName(tags.get("highway").getValue()) : EdgeType.UNKNOWN);
                    startNode.addEdge(currentEdge);
                    if (!tags.containsKey("oneway")) {
                        endNode.addEdge(currentEdge.getStartNode(), currentEdge.getLength(), currentEdge.getSpeed(), currentEdge.getType());
                    } else if (!tags.get("oneway").getValue().equals("yes")) {
                        endNode.addEdge(currentEdge.getStartNode(), currentEdge.getLength(), currentEdge.getSpeed(), currentEdge.getType());
                    }
                    startNode = endNode;
                    distance = 0D;
                }
            }
        }
    }


    private void processCurrentNode() {
        this.nodes.put(this.currentNode.getId(), new Node(this.currentNode.getId(), this.currentNode.getLatitude(), this.currentNode.getLongitude()));
    }

    @Override
    public void initialize(Map<String, Object> map) {
        this.logger.info("Initialize NodeSink");
        startTimestamp = System.currentTimeMillis();
    }

    @Override
    public void complete() {
        this.logger.info("Completed NodeSink");
        endTimestamp = System.currentTimeMillis();
        logger.info("Execution Time: " + (endTimestamp - startTimestamp));
    }

    @Override
    public void release() {
        this.logger.info("Release NodeSink");
    }

    @Override
    public Map<Long, Node> getNodes() {
        return this.nodes;
    }

    private short evaluateMaxSpeed(Tag maxSpeed) {
        short result = 0;
        if(maxSpeed != null) {
            try {
                result = Short.parseShort(maxSpeed.getValue());
            } catch (NumberFormatException ex) {
                result = OSMMaxSpeedUtils.convertOsmMaxSpeedTagToShort(maxSpeed.getValue());
            }
        }
        return result;
    }

    private Integer evaluateLength(Tag length, List<WayNode> wayNodes) {
        Integer result = null;
        if(length != null) {
            try {
                result = Integer.parseInt(length.getValue());
            } catch(NumberFormatException ex) {
                result = this.evaluateLength(wayNodes);
            }
        }
        return result;
    }

    // Formula: d = sqrt(dx * dx + dy * dy)
    // dx = 111.3*(lat1-lat2)
    // dy = 71.5*(lon1-lon2)
    private Integer evaluateLength(List<WayNode> wayNodes) {
        Double wayDistance = 0d;
        Double lastLat = null;
        Double lastLon = null;
        for(WayNode wayNode : wayNodes) {
            Node currentNode = this.nodes.get(wayNode.getNodeId());
            if (currentNode != null) {
                Double currentLat = currentNode.getLatitude();
                Double currentLon = currentNode.getLongitude();
                if (lastLat != null && lastLon != null) {
                    double dx = 111.3 * currentLat;
                    double dy = 71.5 * currentLon;
                    wayDistance += Math.sqrt(dx * dx - dy * dy);
                }
                lastLat = currentLat;
                lastLon = currentLon;
            }
        }
        return wayDistance.intValue();
    }
}
