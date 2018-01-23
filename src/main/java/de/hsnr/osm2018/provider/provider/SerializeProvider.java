package de.hsnr.osm2018.provider.provider;

import de.hsnr.osm2018.data.data.FilteredDataProvider;
import de.hsnr.osm2018.data.graph.Graph;

import java.io.*;

public class SerializeProvider extends FilteredDataProvider {

    public SerializeProvider(String fileName) throws IOException {
        DataInputStream dis = new DataInputStream(new FileInputStream(new File(this.getClass().getClassLoader().getResource(fileName).getFile())));
        mGraph = new Graph(dis);
        dis.close();
    }

    public SerializeProvider(Graph graph) {
        mGraph = graph;
    }

    public void save(String fileName) throws IOException {
        DataOutputStream dos = new DataOutputStream(new FileOutputStream(new File(this.getClass().getClassLoader().getResource("").getFile(), fileName)));
        mGraph.write(dos);
        dos.close();
    }
}