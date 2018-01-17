package de.hsnr.osm2018.provider.container;

import de.hsnr.osm2018.data.graph.Edge;
import de.hsnr.osm2018.data.graph.Node;
import org.openstreetmap.osmosis.core.store.StoreClassRegister;
import org.openstreetmap.osmosis.core.store.StoreReader;
import org.openstreetmap.osmosis.core.store.StoreWriter;
import org.openstreetmap.osmosis.core.store.Storeable;

import java.util.Collection;

public class NodeContainer implements Storeable {

    private Node mNode;

    public NodeContainer(Node node) {
        this.mNode = node;
    }

    public NodeContainer(StoreReader storeReader, StoreClassRegister storeClassRegister) {
        this(new Node(storeReader.readLong(), storeReader.readDouble(), storeReader.readDouble()));
        int edgeCount = storeReader.readInteger();
        for (int i = 0; i < edgeCount; i++) {
            //mNode.addEdge(new EdgeContainer(mNode, storeReader, storeClassRegister).getEdge());
        }
    }

    public Node getNode() {
        return mNode;
    }

    @Override
    public void store(StoreWriter storeWriter, StoreClassRegister storeClassRegister) {
        storeWriter.writeLong(mNode.getId());
        storeWriter.writeDouble(mNode.getLatitude());
        storeWriter.writeDouble(mNode.getLongitude());
        Collection<Edge> edges = mNode.getEdges();
        storeWriter.writeInteger(edges.size());
        for(Edge edge : edges) {
            new EdgeContainer(edge).store(storeWriter, storeClassRegister);
        }
    }
}