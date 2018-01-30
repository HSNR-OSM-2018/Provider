package de.hsnr.osm2018.provider.pbfReader.common;

import de.hsnr.osm2018.data.graph.Node;
import org.openstreetmap.osmosis.core.task.v0_6.Sink;

import java.util.HashMap;
import java.util.Map;

public interface IMainSink extends Sink {

    public Map<Long, Node> getNodes();

}
