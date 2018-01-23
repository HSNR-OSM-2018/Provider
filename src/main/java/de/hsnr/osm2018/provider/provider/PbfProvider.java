package de.hsnr.osm2018.provider.provider;

import de.hsnr.osm2018.data.provider.FilteredDataProvider;
import de.hsnr.osm2018.data.graph.Graph;
import de.hsnr.osm2018.data.graph.Node;
import de.hsnr.osm2018.provider.pbfReader.complexReader.ComplexPbfReader;
import de.hsnr.osm2018.provider.pbfReader.complexReader.InitSink;

import java.util.HashMap;

public class PbfProvider extends FilteredDataProvider {

    public PbfProvider(String fileName) {
        super();
        //PBFNodeSink sink = new NodeSink(mGraph);
        InitSink initsink = new InitSink();
        ComplexPbfReader reader = new ComplexPbfReader(fileName, initsink, mGraph);
        reader.run();
        mGraph = new Graph((HashMap<Long, Node>) reader.getMainSink().getNodes());
    }
}
