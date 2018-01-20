package de.hsnr.osm2018.provider.provider;

import de.hsnr.osm2018.data.data.FilteredDataProvider;
import de.hsnr.osm2018.data.graph.Graph;

import java.io.*;

public class SerializeProvider extends FilteredDataProvider {

    public SerializeProvider(String fileName) throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File(this.getClass().getClassLoader().getResource(fileName).getFile())));
        mGraph = (Graph) ois.readObject();
        ois.close();
    }

    public SerializeProvider(Graph graph) {
        mGraph = graph;
    }

    public void save(String fileName) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(fileName)));
        oos.writeObject(mGraph);
        oos.close();
    }
}