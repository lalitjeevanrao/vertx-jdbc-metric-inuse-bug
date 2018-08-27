package rao.lalit.eventbus.consumer;

import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;

public class JDBCEventMessageConsumer {
	/**
     * Method responsible for processing incoming messages on mysqldb.queue.
     * 
     * @param message
     *            A message received from the event bus.
     */
    public void onMessage(Message<JsonObject> message) {
    		//TODO
    }
}
