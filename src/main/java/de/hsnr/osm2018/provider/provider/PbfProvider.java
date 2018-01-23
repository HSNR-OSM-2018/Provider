package de.hsnr.osm2018.provider.provider;

import de.hsnr.osm2018.data.data.FilteredDataProvider;
import de.hsnr.osm2018.provider.pbfReader.PBFReader;
import de.hsnr.osm2018.provider.pbfReader.PBFNodeSink;

public class PbfProvider extends FilteredDataProvider {

    public PbfProvider(String fileName) {
        super();
        PBFNodeSink sink = new PBFNodeSink(mGraph);
        PBFReader reader = new PBFReader(fileName, sink);
        reader.run();
    }
}
