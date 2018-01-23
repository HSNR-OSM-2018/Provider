package de.hsnr.osm2018.provider.pbfReader;

import de.hsnr.osm2018.data.graph.Node;
import org.openstreetmap.osmosis.core.task.v0_6.Sink;

import java.util.HashMap;
import java.util.Map;

public interface INodeSink extends Sink {

    public Map<Long, Node> getNodes();

}
