package de.hsnr.osm2018.provider.pbfReader.complexReader;

import crosby.binary.osmosis.OsmosisReader;
import de.hsnr.osm2018.data.graph.Graph;
import de.hsnr.osm2018.provider.pbfReader.INodeSink;
import de.hsnr.osm2018.provider.pbfReader.PBFReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Set;
import java.util.logging.Logger;

public class ComplexPbfReader {

    private static final Logger LOGGER = Logger.getLogger(PBFReader.class.getSimpleName());

    private OsmosisReader osmosisInitReader;
    private OsmosisReader osmosisMainReader;
    private IInitSink initSink;
    private NodeSink mainSink;
    private Graph graph;

    public ComplexPbfReader(String filename, IInitSink initSink, Graph graph) {
        this.graph = graph;
        File pbfFile;
        FileInputStream pbfInitFileStream = null;
        FileInputStream pbfMainFileStream = null;
        try {
            pbfFile = new File(this.getClass().getClassLoader().getResource(filename).getFile());
            pbfInitFileStream = new FileInputStream(pbfFile);
            LOGGER.info("FilePath: " + pbfFile.getPath());
            LOGGER.info("File size: " + pbfFile.length() + "Bytes");
            pbfMainFileStream = new FileInputStream(pbfFile);
        } catch(FileNotFoundException e) {
            LOGGER.severe(e.getMessage());
        }
        this.osmosisInitReader = new OsmosisReader(pbfInitFileStream);
        this.osmosisMainReader = new OsmosisReader(pbfMainFileStream);
        this.initSink = initSink;
    }

    public void run() {
        this.runInitReader();
        System.gc();
        this.runMainReader(this.initSink.getRelevantNodes());
        System.gc();
    }

    private void runInitReader() {
        this.osmosisInitReader.setSink(this.initSink);
        this.osmosisInitReader.run();
    }

    private void runMainReader(Set<Long> relevantNodes) {
        this.mainSink = new NodeSink(relevantNodes, this.graph);
        this.osmosisMainReader.setSink(this.mainSink);
        this.osmosisMainReader.run();
    }

    public INodeSink getMainSink() {
        return this.mainSink;
    }

}
