package ua.taky.eventstore;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.eclipse.jetty.server.AbstractNetworkConnector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;
import org.glassfish.jersey.servlet.ServletProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Injector;

@Singleton
public class HttpServer {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(HttpServer.class);
	
	private int port;
	
	@Inject
	public HttpServer(@Named("jetty.port") int port){
		this.port = port;
	}

	public void start(Injector injector) {
		Server server = null;
		try {
			server = new Server();
			AbstractNetworkConnector connector = new ServerConnector(server);
			connector.setPort(port);
			server.addConnector(connector);
			
			ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
			
            ServletHolder jerseyServletHolder = new ServletHolder(new ServletContainer());
            JerseyResourceConfig.setInjector(injector);
            jerseyServletHolder.setInitParameter(ServletProperties.JAXRS_APPLICATION_CLASS, "ua.taky.eventstore.JerseyResourceConfig");
            
            context.addServlet(jerseyServletHolder, "/*");
            
            server.setHandler(context);
            server.start();
            LOGGER.info("Server started on the port {}", connector.getPort());
            server.join();
		} catch(Exception e){
			LOGGER.error("Cannot http start server", e);
		}
	}

}
