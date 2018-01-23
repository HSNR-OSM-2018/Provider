package de.hsnr.osm2018.provider.pbfReader.complexReader;

import org.openstreetmap.osmosis.core.task.v0_6.Sink;
import java.util.Set;

public interface IInitSink extends Sink {

    Set<Long> getRelevantNodes();
}
