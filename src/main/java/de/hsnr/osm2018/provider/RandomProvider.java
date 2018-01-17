package de.hsnr.osm2018.provider;

import de.hsnr.osm2018.data.data.FilteredDataProvider;
import de.hsnr.osm2018.data.graph.EdgeType;
import de.hsnr.osm2018.data.graph.Node;

import java.util.Random;

public class RandomProvider extends FilteredDataProvider {

    private static final Double mLeft = 6.1702D;
    private static final Double mTop = 51.4634D;
    private static final Double mRight = 7.2482D;
    private static final Double mBottom = 51.0794D;

    public RandomProvider(int nodeSize) {
        this(nodeSize, 4);
    }

    public RandomProvider(int nodeSize, int halfDensity) {
        super();
        double minLatitude = Math.min(mTop, mBottom);
        double rangeLatitude = Math.max(mTop, mBottom) - minLatitude;
        double minLongitude = Math.min(mLeft, mRight);
        double rangeLongitude = Math.max(mLeft, mRight) - minLongitude;
        Random random = new Random();
        for (int i = 0; i < nodeSize; i++) {
            mGraph.add(new Node((long) i, minLatitude + rangeLatitude * random.nextDouble(), minLongitude + rangeLongitude * random.nextDouble()));
        }
        for (int start = 0; start < nodeSize; start++) {
            int tempDensity = halfDensity / 2 + random.nextInt(halfDensity);
            for (int j = 0; j < tempDensity; j++) {
                Long dest;
                do {
                    dest = (long) random.nextInt(nodeSize);
                } while (dest.intValue() == start);
                int length = 10 * (random.nextInt(490) + 10);
                short speed = (short) (10 * (random.nextInt(11) + 2));
                double typeRand = random.nextDouble();
                EdgeType type;
                if (typeRand < 0.1D) {
                    type = EdgeType.MOTORWAY;
                } else if (typeRand < 0.13D) {
                    type = EdgeType.MOTORWAY_LINK;
                } else if (typeRand < 0.28D) {
                    type = EdgeType.TRUNK;
                } else if (typeRand < 0.32D) {
                    type = EdgeType.TRUNK_LINK;
                } else if (typeRand < 0.52D) {
                    type = EdgeType.PRIMARY;
                } else if (typeRand < 0.55D) {
                    type = EdgeType.PRIMARY_LINK;
                } else {
                    type = EdgeType.RESIDENTIAL;
                }
                mGraph.getNode((long) start).addEdge(mGraph.getNode(dest), length, speed, type);
                if (random.nextDouble() < 0.9D) { //Create reverse route for 90% of "roads"
                    mGraph.getNode(dest).addEdge(mGraph.getNode((long) start), length, speed, type);
                }
            }
        }
    }
}