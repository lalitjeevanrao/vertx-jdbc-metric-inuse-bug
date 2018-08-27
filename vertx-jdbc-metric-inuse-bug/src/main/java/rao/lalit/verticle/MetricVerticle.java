package rao.lalit.verticle;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.MetricRegistry;
/**
 * @author lalitrao
 * */
import io.vertx.core.AbstractVerticle;

public class MetricVerticle extends AbstractVerticle {
	public static final Logger LOG = LoggerFactory.getLogger(MetricVerticle.class);
	private final MetricRegistry registry;
	
	public MetricVerticle(MetricRegistry registry) {
		this.registry = registry;
	}
	
	@Override
    public void start() throws Exception {
		ConsoleReporter consoleReporter = ConsoleReporter.forRegistry(registry)
	            .convertRatesTo(TimeUnit.SECONDS)
	            .convertDurationsTo(TimeUnit.MILLISECONDS)
	            .build();
	    consoleReporter.start(10, TimeUnit.SECONDS);
	    LOG.info("MetricVerticle deployed successfully");
	}
}
