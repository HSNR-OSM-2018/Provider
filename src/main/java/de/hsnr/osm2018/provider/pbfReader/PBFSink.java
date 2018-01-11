package de.hsnr.osm2018.provider.pbfReader;

import de.hsnr.osm2018.data.graph.Edge;
import de.hsnr.osm2018.data.graph.EdgeType;
import de.hsnr.osm2018.data.graph.Node;
import de.hsnr.osm2018.data.utils.EdgeTypeUtils;
import org.openstreetmap.osmosis.core.container.v0_6.EntityContainer;
import org.openstreetmap.osmosis.core.container.v0_6.NodeContainer;
import org.openstreetmap.osmosis.core.container.v0_6.WayContainer;
import org.openstreetmap.osmosis.core.domain.v0_6.Tag;
import org.openstreetmap.osmosis.core.domain.v0_6.Way;
import org.openstreetmap.osmosis.core.domain.v0_6.WayNode;
import org.openstreetmap.osmosis.core.task.v0_6.Sink;

import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class PBFSink implements Sink {

    private static Logger LOGGER = Logger.getLogger(PBFSink.class.getSimpleName());
    private Map<Long, Node> nodes;
    private List<Edge> ways;

    private org.openstreetmap.osmosis.core.domain.v0_6.Node currentOsmNode;
    private Way currentWay;

    public PBFSink() {
        this.nodes = new HashMap();
        this.ways = new ArrayList<>();
    }

    @Override
    public void process(EntityContainer entityContainer) {
        if(entityContainer instanceof NodeContainer) {
            this.currentOsmNode = ((NodeContainer) entityContainer).getEntity();
            this.processCurrentNode();
        } else if(entityContainer instanceof WayContainer) {
            this.currentWay = ((WayContainer) entityContainer).getEntity();
            this.processCurrentWay();
        }
    }

    private void processCurrentNode() {
        Node currentNode = new Node(this.currentOsmNode.getId(), this.currentOsmNode.getLatitude(), this.currentOsmNode.getLongitude(), Collections.EMPTY_LIST);
        this.nodes.put(currentNode.getId(), currentNode);
    }

    private void processCurrentWay() {
        if(this.currentWay.getWayNodes().size() >= 2) {
            List<WayNode> wayNodes = this.currentWay.getWayNodes();
            Node startNode = this.nodes.get(wayNodes.get(0).getNodeId());
            Node endNode = this.nodes.get(wayNodes.get(wayNodes.size() - 1).getNodeId());
            Map<String, Tag> tags = this.currentWay.getTags().stream().filter(tag -> tag.getKey().equals("maxspeed") || tag.getKey().equals("length") || tag.getKey().equals("highway")).collect(Collectors.toMap(t -> t.getKey(), t->t));
            Edge currentWay = new Edge(startNode, endNode, tags.containsKey("length") ? this.evaluateLength(tags.get("length")) : null, tags.containsKey("maxspeed") ? this.evaluateMaxSpeed(tags.get("maxspeed")) : null, tags.containsKey("highway") ? EdgeTypeUtils.evaluateEdgeTypeByOSMTagName(tags.get("highway").getValue()) : EdgeType.UNKNOWN);
            this.ways.add(currentWay);
        }
    }

    private Short evaluateMaxSpeed(Tag maxSpeed) {
        Short result = null;
        if(maxSpeed != null) {
            try {
                result = Short.parseShort(maxSpeed.getValue());
            } catch (NumberFormatException ex) {
                //Intentionally left blank
            }
        }
        return result;
    }

    private Integer evaluateLength(Tag length) {
        Integer result = null;
        if(length != null) {
            try {
                result = Integer.parseInt(length.getValue());
            } catch(NumberFormatException ex) {
                //Intentionally left blank
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
            Double currentLat = currentNode.getLatitude();
            Double currentLon = currentNode.getLongitute();
            if(lastLat != null && lastLon != null) {
                double dx = 111.3 * currentLat;
                double dy = 71.5 * currentLon;
                wayDistance += Math.sqrt(dx*dx - dy*dy);
            }
            lastLat = currentLat;
            lastLon = currentLon;
        }
        return null;
    }

    @Override
    public void initialize(Map<String, Object> map) {
        LOGGER.info("Processing PBF-File...");
    }

    @Override
    public void complete() {
    }

    @Override
    public void release() {
        LOGGER.info("Finished.");
    }

    public Map<Long, Node> getNodes() {
        return nodes;
    }

    public List<Edge> getWays() {
        return ways;
    }
}
