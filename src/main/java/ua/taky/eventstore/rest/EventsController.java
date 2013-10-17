package ua.taky.eventstore.rest;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import org.glassfish.jersey.process.internal.RequestScoped;

import ua.taky.eventstore.domain.Event;
import ua.taky.eventstore.service.EventsSearchRequest;
import ua.taky.eventstore.service.EventsService;

/**
 * Jersey REST Controller.
 * 
 * TODO add validation support
 * 
 * @author taky
 */
@RequestScoped
@Path("events")
@Produces("application/json")
public class EventsController {
	
	private EventsService eventsService;
	
	@Context 
	private UriInfo ui;
	
	@GET
	public String events(
			@DefaultValue("") @QueryParam("city") String city,
			@DefaultValue("") @QueryParam("day") String day,
			@DefaultValue("0") @QueryParam("budget") Integer budget){
		List<String> interests = ui.getQueryParameters().get("interest");
		interests = interests == null? new ArrayList<String>() : interests;
		List<Event> events = eventsService.searchEvents(EventsSearchRequest.builder().city(city).date(day).interests(interests.toArray(new String[0])).budget(budget).build());
		return Event.getGson().toJson(events);
	}
	
	public void setUriInfo(UriInfo ui){
		this.ui = ui;
	}

	@Inject
	public void setEventsService(EventsService eventsService) {
		this.eventsService = eventsService;
	}

}