package de.hsnr.osm2018.provider;

import de.hsnr.osm2018.data.data.FilteredDataProvider;
import de.hsnr.osm2018.data.graph.EdgeType;
import de.hsnr.osm2018.data.graph.Graph;

public class RandomProvider implements FilteredDataProvider {

    private Graph mGraph;

    public RandomProvider() {
        this.mGraph = new Graph();
        //TODO: fill graph
    }

    /**
     * Get a subset of the full @{@link Graph} managed by this data-provider.
     * The subset contains all relevant nodes and edges that are contained within a rectangular bound by the start and destination points.
     * It is furthermore widen by the vicinityRage used to be able for including slight differences from the air line.
     * Within the radius of the start- and destination positions edges of all @{@link EdgeType}s are included. The remaining area contains only edges of certain @{@link EdgeType}s (e.g. motorway, trunk and primary). Nodes without edges of these types will be omitted as well.
     * Building this subset of the graph is based on the assumption that only within a certain range all types of roads are used for traveling. For traveling longer distances only motorways and other high-speed roads are used.
     *
     * @param startLatitude        Latitude of start position
     * @param startLongitude       Longitude of start position
     * @param destinationLatitude  Latitude of destination position
     * @param destinationLongitude Longitude of destination position
     * @param globalVicinityRange  Tolerance range that will widen the bounding box. value should be interpreted as degree
     * @param localVicinityRadius  Subset local radius around start- and destination positions. value should be interpreted as degree
     * @return Graph subset of graph with nodes and edges within the bound box
     */
    @Override
    public Graph getGraph(double startLatitude, double startLongitude, double destinationLatitude, double destinationLongitude, double globalVicinityRange, double localVicinityRadius) {
        return mGraph;
    }

    /**
     * Get a subset of the full @{@link Graph} managed by this data-provider.
     * The subset contains all nodes and edges that are contained within a rectangular bound by the start and destination points.
     * It is furthermore widen by the vicinityRage used to be able for including slight differences from the air line.
     *
     * @param startLatitude        Latitude of start position
     * @param startLongitude       Longitude of start position
     * @param destinationLatitude  Latitude of destination position
     * @param destinationLongitude Longitude of destination position
     * @param vicinityRange        Tolerance range that will widen the bounding box. value should be interpreted as degree
     * @return Graph subset of graph with nodes and edges within the bound box
     */
    @Override
    public Graph getGraph(double startLatitude, double startLongitude, double destinationLatitude, double destinationLongitude, double vicinityRange) {
        return mGraph;
    }

    /**
     * Get a subset of the full @{@link Graph} managed by this data-provider.
     * The subset contains all nodes and edges that are contained within an circle around the point. The circle radius is defined by the parameter vicinityRange.
     *
     * @param pointLatitude  Latitude of center point
     * @param pointLongitude Longitude of center point
     * @param vicinityRadius Subset radius. value should be interpreted as degree
     * @return Graph subset of graph with nodes and edges within the bound circle
     */
    @Override
    public Graph getGraph(double pointLatitude, double pointLongitude, double vicinityRadius) {
        return mGraph;
    }

    /**
     * Get the complete @{@link Graph} managed by this data-provider
     *
     * @return Graph
     */
    @Override
    public Graph getGraph() {
        return mGraph;
    }
}
