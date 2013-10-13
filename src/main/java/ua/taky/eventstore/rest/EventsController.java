package ua.taky.eventstore.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.glassfish.jersey.process.internal.RequestScoped;

@RequestScoped
@Path("events")
public class EventsController {
	
	@GET
	public String events(){
		return "OK";
	}

}
