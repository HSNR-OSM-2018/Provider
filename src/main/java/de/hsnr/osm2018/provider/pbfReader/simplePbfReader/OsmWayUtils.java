package de.hsnr.osm2018.provider.pbfReader.simplePbfReader;

import de.hsnr.osm2018.data.graph.Edge;
import de.hsnr.osm2018.data.graph.Node;
import de.hsnr.osm2018.data.utils.EdgeTypeUtils;
import org.openstreetmap.osmosis.core.domain.v0_6.Tag;
import org.openstreetmap.osmosis.core.domain.v0_6.Way;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class OsmWayUtils {

    public static Set<Tag> retrieveTags(Way osmWay) {
        Set<Tag> result = new HashSet<>();
        result.addAll(osmWay.getTags());
        return result;
    }

    public static String retrieveTag(Way osmWay, String tag) {
        for (Tag t : osmWay.getTags()) {
            if (t.getKey().equals(tag)) {
                return t.getValue();
            }
        }
        return null;
    }

    public static Edge osmWayAndNodesToEdge(Way osmWay, Node startNode, Node destinationNode) {
        String lengthTag = retrieveTag(osmWay, "length");
        String maxSpeedTag = retrieveTag(osmWay, "maxspeed");
        String highwayTag = retrieveTag(osmWay, "highway");
        return new Edge(startNode, destinationNode, Integer.valueOf(lengthTag), Short.valueOf(maxSpeedTag), EdgeTypeUtils.evaluateEdgeTypeByOSMTagName(highwayTag));
    }
}
