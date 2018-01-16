package de.hsnr.osm2018.provider.container;

import de.hsnr.osm2018.data.graph.Graph;
import de.hsnr.osm2018.data.graph.Node;
import org.openstreetmap.osmosis.core.store.StoreClassRegister;
import org.openstreetmap.osmosis.core.store.StoreReader;
import org.openstreetmap.osmosis.core.store.StoreWriter;
import org.openstreetmap.osmosis.core.store.Storeable;

import java.util.Collection;

public class GraphContainer implements Storeable {

    public Graph mGraph;

    public GraphContainer(Graph graph) {
        this.mGraph = graph;
    }

    public GraphContainer(StoreReader storeReader, StoreClassRegister storeClassRegister) {
        this(new Graph());
        int nodeCount = storeReader.readInteger();
        for (int i = 0; i < nodeCount; i++) {
            mGraph.add(new NodeContainer(storeReader, storeClassRegister).getNode());
        }
    }

    public Graph getGraph() {
        return mGraph;
    }

    @Override
    public void store(StoreWriter storeWriter, StoreClassRegister storeClassRegister) {
        Collection<Node> nodes = mGraph.getNodes().values();
        storeWriter.writeInteger(nodes.size());
        for (Node node : nodes) {
            new NodeContainer(node).store(storeWriter, storeClassRegister);
        }
    }
}