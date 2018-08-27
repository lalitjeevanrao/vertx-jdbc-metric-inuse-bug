package rao.lalit.verticle;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.jdbc.JDBCClient;
import rao.lalit.enums.EventBusQueue;
import rao.lalit.eventbus.consumer.JDBCEventMessageConsumer;

public class JDBCVerticle extends AbstractVerticle {
	public static final Logger LOG = LoggerFactory.getLogger(JDBCVerticle.class);
	private final Vertx vertx;
	private final JDBCEventMessageConsumer dbEventConsumer;
	
	public JDBCVerticle(Vertx vertx, JDBCEventMessageConsumer dbEventConsumer) {
		Objects.requireNonNull(vertx);
		this.vertx = vertx;
		this.dbEventConsumer = dbEventConsumer;
	}

	@Override
    public void start(Future<Void> startFuture) throws Exception {
		final JDBCClient client = JDBCClient.createShared(this.vertx, new JsonObject()
		        .put("url", "jdbc:hsqldb:mem:test?shutdown=true")
		        .put("driver_class", "org.hsqldb.jdbcDriver")
		        .put("max_pool_size", 30)
		        .put("user", "SA")
		        .put("password", ""));
		client.getConnection(ar -> {
            ar.result().close();
            if (ar.failed()) {
            	    LOG.error("Could not open a database connection", ar.cause());
                startFuture.fail(ar.cause());
            } else {
            	    LOG.info("Database connection established.");
                ar.result().close();
                LOG.info(String.format("JDBCEventMessageConsumer consuming events from %s", EventBusQueue.MYSQLDBQUEUE.name()));
                startFuture.complete();
            }
        });
		this.vertx.eventBus().consumer(EventBusQueue.MYSQLDBQUEUE.name(), dbEventConsumer::onMessage);
	}
	
	@Override
    public void stop(Future<Void> stopFuture) throws Exception {
	}
}
