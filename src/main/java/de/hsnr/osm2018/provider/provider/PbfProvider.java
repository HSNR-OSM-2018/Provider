package de.hsnr.osm2018.provider.provider;

import de.hsnr.osm2018.data.data.FilteredDataProvider;
import de.hsnr.osm2018.provider.pbfReader.PBFReader;
import de.hsnr.osm2018.provider.pbfReader.PBFSink;

public class PbfProvider extends FilteredDataProvider {

    public PbfProvider(String fileName) {
        super();
        PBFSink sink = new PBFSink(mGraph);
        PBFReader reader = new PBFReader(fileName, sink);
        reader.run();
    }
}
