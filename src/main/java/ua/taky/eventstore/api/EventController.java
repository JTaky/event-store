package ua.taky.eventstore.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.glassfish.jersey.process.internal.RequestScoped;

@RequestScoped
@Path("events")
public class EventController {
	
	@GET
	public String events(){
		return "OK";
	}

}
