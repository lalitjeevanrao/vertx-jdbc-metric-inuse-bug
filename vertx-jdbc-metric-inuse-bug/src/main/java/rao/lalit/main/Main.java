package rao.lalit.main;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.SharedMetricRegistries;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.ext.dropwizard.DropwizardMetricsOptions;
import rao.lalit.constants.CommonConstants;
import rao.lalit.eventbus.consumer.JDBCEventMessageConsumer;
import rao.lalit.verticle.JDBCVerticle;
import rao.lalit.verticle.MetricVerticle;
/**
 * @author lalitrao
 * */
public class Main {

	public static void main(String[] args) {
		VertxOptions vertxOptions = new VertxOptions()
	            .setMetricsOptions(new DropwizardMetricsOptions()
	                .setEnabled(true)
	                .setRegistryName(CommonConstants.METRICS_REGISTRY_NAME))
	            .setMaxWorkerExecuteTime((long) 1.8e+12); // 30 minutes in nanosecond
	    Vertx vertx = Vertx.vertx(vertxOptions);
	    
	    MetricRegistry registry = SharedMetricRegistries.getOrCreate(CommonConstants.METRICS_REGISTRY_NAME);
	    MetricVerticle metricVerticle = new MetricVerticle(registry);
	    JDBCVerticle jdbcVerticle = new JDBCVerticle(vertx, new JDBCEventMessageConsumer());
	    
	    DeploymentOptions workerOptions = new DeploymentOptions()
	            .setWorker(true);
	    vertx.deployVerticle(jdbcVerticle, workerOptions);
	    vertx.deployVerticle(metricVerticle, workerOptions);
	}
}
