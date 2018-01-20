package de.hsnr.osm2018.provider;

import de.hsnr.osm2018.data.graph.Graph;
import de.hsnr.osm2018.provider.provider.RandomProvider;
import de.hsnr.osm2018.provider.provider.SerializeProvider;
import org.junit.Assert;
import org.junit.Test;

public class SerializeProviderTest {

    private static final int RANDOM_SIZE = 1000;
    private static final String RANDOM_FILE_KEY = "random.graph";

    @Test
    public void writeRandom() throws Exception {
        Graph graph = new RandomProvider(RANDOM_SIZE).getGraph();
        SerializeProvider provider = new SerializeProvider(graph);
        provider.save(RANDOM_FILE_KEY);
    }

    @Test
    public void readRandom() throws Exception {
        SerializeProvider provider = new SerializeProvider(RANDOM_FILE_KEY);
        Graph graph = provider.getGraph();
        Assert.assertEquals(graph.getNodes().size(), RANDOM_SIZE);
    }
}
