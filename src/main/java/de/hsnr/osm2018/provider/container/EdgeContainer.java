package de.hsnr.osm2018.provider.container;

import de.hsnr.osm2018.data.graph.Edge;
import de.hsnr.osm2018.data.graph.Graph;
import de.hsnr.osm2018.data.graph.Node;
import de.hsnr.osm2018.data.utils.EdgeTypeUtils;
import org.openstreetmap.osmosis.core.store.StoreClassRegister;
import org.openstreetmap.osmosis.core.store.StoreReader;
import org.openstreetmap.osmosis.core.store.StoreWriter;
import org.openstreetmap.osmosis.core.store.Storeable;

public class EdgeContainer implements Storeable {

    private Edge mEdge;

    public EdgeContainer(Edge edge) {
        this.mEdge = edge;
    }

    public EdgeContainer(Graph graph, Node start, StoreReader storeReader, StoreClassRegister storeClassRegister) {
        this(new Edge(start, graph.getNode(storeReader.readLong()), storeReader.readInteger(), (short) storeReader.readInteger(), EdgeTypeUtils.evaluateEdgeTypeByOSMTagName(storeReader.readString())));
    }

    public Edge getEdge() {
        return mEdge;
    }

    @Override
    public void store(StoreWriter storeWriter, StoreClassRegister storeClassRegister) {
        storeWriter.writeLong(mEdge.getDestinationNode().getId());
        storeWriter.writeInteger(mEdge.getLength());
        storeWriter.writeInteger(mEdge.getSpeed());
        storeWriter.writeString(mEdge.getType().getName());
    }
}