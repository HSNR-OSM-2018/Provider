package de.hsnr.osm2018.provider.pbfReader.simplePbfReader;

import crosby.binary.osmosis.OsmosisReader;
import de.hsnr.osm2018.data.graph.Graph;
import de.hsnr.osm2018.provider.pbfReader.simplePbfReader.sinks.NodeSink;
import de.hsnr.osm2018.provider.pbfReader.simplePbfReader.sinks.WaySink;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.logging.Logger;

public class SimplePbfReader {

    private static Logger logger = Logger.getLogger(SimplePbfReader.class.getName());
    private OsmosisReader nodeReader;
    private OsmosisReader wayReader;
    private NodeSink nodeSink;
    private WaySink waySink;

    public SimplePbfReader(String nodeFilename, String wayFilename, Graph graph) throws FileNotFoundException {
        File nodeFile = new File(this.getClass().getClassLoader().getResource(nodeFilename).getFile());
        File wayFile = new File(this.getClass().getClassLoader().getResource(wayFilename).getFile());
        initializeReader(nodeFile, wayFile, graph);
    }

    public SimplePbfReader(File nodeFile, File wayFile, Graph graph) throws FileNotFoundException {
        initializeReader(nodeFile, wayFile, graph);
    }

    private void initializeReader(File nodeFile, File wayFile, Graph graph) throws FileNotFoundException {
        try {
            FileInputStream nodeFileInputStream = new FileInputStream(nodeFile);
            FileInputStream wayFileInputStream = new FileInputStream(wayFile);
            this.nodeReader = new OsmosisReader(nodeFileInputStream);
            this.wayReader = new OsmosisReader(wayFileInputStream);
            if(graph != null) {
                this.nodeSink = new NodeSink(graph);
                this.nodeReader.setSink(this.nodeSink);
            } else {
                this.nodeSink = new NodeSink();
                this.nodeReader.setSink(this.nodeSink);
            }
            this.waySink = new WaySink(this.nodeSink.getNodes());
            this.wayReader.setSink(this.waySink);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            logger.severe("Error: File not Found. Detailed ErrorMessage:\n" + e.getMessage());
            throw e;
        }
    }

    public void run() {
        this.nodeReader.run();
        System.gc();
        this.wayReader.run();
        System.gc();
    }

    public WaySink getWaySink() {
        return waySink;
    }
}