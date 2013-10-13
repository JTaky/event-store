package ua.taky.eventstore;

import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * Entry point for an events store service.
 * Here is born the entire application
 * 
 * @author taky
 */
public class EventStoreEntryPoint {

	/**
	 * @param args server arguments parameters
	 */
	public static void main(String[] args) {
		Injector injector = Guice.createInjector(new EventStoreModule());
		injector.getInstance(HttpServer.class).start(injector);
	}

}
