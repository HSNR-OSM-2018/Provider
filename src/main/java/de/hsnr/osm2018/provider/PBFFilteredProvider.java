package de.hsnr.osm2018.provider;

import de.hsnr.osm2018.data.data.SimpleDataProvider;
import de.hsnr.osm2018.provider.pbfReader.MySink;
import de.hsnr.osm2018.provider.pbfReader.PBFFilteredSink;
import de.hsnr.osm2018.provider.pbfReader.PBFReader;

public class PBFFilteredProvider extends SimpleDataProvider {
    public PBFFilteredProvider(String pbfFilename) {
        super();

        PBFReader reader = new PBFReader(pbfFilename, new PBFFilteredSink());
        reader.run();
        MySink sink = reader.getSink();

        System.out.print("Nodes: ");
        System.out.println(sink.getNodes().size());
    }

    public static void main(String[] args) {
        new PBFFilteredProvider("abbiegeverbot.pbf");
    }
}
