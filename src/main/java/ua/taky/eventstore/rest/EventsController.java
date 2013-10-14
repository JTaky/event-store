package ua.taky.eventstore.rest;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.glassfish.jersey.process.internal.RequestScoped;

import ua.taky.eventstore.domain.Event;
import ua.taky.eventstore.service.EventsSearchRequest;
import ua.taky.eventstore.service.EventsService;

@RequestScoped
@Path("events")
@Produces("application/json")
public class EventsController {
	
	private EventsService eventsService;
	
	@GET
	public String events(
			@DefaultValue("") @QueryParam("city") String city,
			@DefaultValue("") @QueryParam("day") String day,
			@DefaultValue("") @QueryParam("interest") String interest,
			@QueryParam("budget") Integer budget){
		List<Event> events = eventsService.searchEvents(EventsSearchRequest.buildRequest(city, day, interest, budget));
		return "OK";
	}

	@Inject
	public void setEventsService(EventsService eventsService) {
		this.eventsService = eventsService;
	}

}
