package de.hsnr.osm2018.provider.pbfReader;

import crosby.binary.osmosis.OsmosisReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.logging.Logger;

public class PBFReader {

    private static final Logger LOGGER = Logger.getLogger(PBFReader.class.getSimpleName());

    private OsmosisReader osmosisReader;
    private MySink sink;

    public PBFReader(String filename, MySink sink) {
        File pbfFile;
        FileInputStream pbfFileStream = null;
        try {
            pbfFile = new File(this.getClass().getClassLoader().getResource(filename).getFile());
            pbfFileStream = new FileInputStream(pbfFile);
            LOGGER.info("FilePath: " + pbfFile.getPath());
            LOGGER.info("File size: " + pbfFile.length() + "Bytes");
        } catch(FileNotFoundException e) {
            LOGGER.severe(e.getMessage());
        }
        this.osmosisReader = new OsmosisReader(pbfFileStream);
        this.sink = sink;
        this.osmosisReader.setSink(sink);
    }

    public void run() {
        this.osmosisReader.run();
    }

    public MySink getSink() {
        return this.sink;
    }

}
