package de.hsnr.osm2018.provider.pbfReader.complexReader.sinks;

import de.hsnr.osm2018.provider.pbfReader.common.IInitSink;
import org.openstreetmap.osmosis.core.container.v0_6.EntityContainer;
import org.openstreetmap.osmosis.core.domain.v0_6.Way;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

public class InitSink implements IInitSink {

    Logger logger = Logger.getLogger(InitSink.class.getSimpleName());

    private Way currentWay;
    private Set<Long> relevantNodes;

    private long startTimestamp;
    private long endTimestamp;

    public InitSink() {
        relevantNodes = new HashSet<>();
        startTimestamp = 0;
        endTimestamp = 0;
    }

    @Override
    public void process(EntityContainer entityContainer) {
        if(entityContainer.getEntity() instanceof Way) {
            this.currentWay = (Way) entityContainer.getEntity();
            this.processWay();
        }
    }

    private void processWay() {
        if(this.currentWay.getWayNodes().size() >= 2) {
            this.relevantNodes.add(currentWay.getWayNodes().get(0).getNodeId());
            this.relevantNodes.add(currentWay.getWayNodes().get(currentWay.getWayNodes().size() - 1).getNodeId());
        }
    }

    @Override
    public void initialize(Map<String, Object> map) {
        startTimestamp = System.currentTimeMillis();
        logger.info("Initialize InitSink");
    }

    @Override
    public void complete() {
        logger.info("Completed InitSink");
        endTimestamp = System.currentTimeMillis();
        logger.info("Execution Time: " + (endTimestamp - startTimestamp));
    }

    @Override
    public void release() {
        logger.info("Release InitSink");
    }

    @Override
    public Set<Long> getRelevantNodes() {
        return this.relevantNodes;
    }
}
