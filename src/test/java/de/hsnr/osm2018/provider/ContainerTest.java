package de.hsnr.osm2018.provider;

import de.hsnr.osm2018.data.graph.Graph;
import de.hsnr.osm2018.provider.provider.RandomProvider;
import org.junit.Test;

public class ContainerTest {

    @Test
    public void generateSave() {
        Graph graph = new RandomProvider(10).getGraph();

    }
}