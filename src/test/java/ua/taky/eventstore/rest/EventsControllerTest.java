package ua.taky.eventstore.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.UriInfo;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import ua.taky.eventstore.domain.Event;
import ua.taky.eventstore.service.EventsSearchRequest;
import ua.taky.eventstore.service.EventsService;

import com.google.gson.GsonBuilder;


public class EventsControllerTest {
	
	private static final String city = "Kiev";
	private static final String day = "20131220";
	private static final Integer budget = 500;
	
	private EventsController eventsController;
	
	private EventsService eventsService;
	
	@Before
	public void setUp(){
		eventsController = new EventsController();
		eventsService = mock(EventsService.class);
		eventsController.setEventsService(eventsService);
		eventsController.setUriInfo(mockUriInfo());
	}
	
	private UriInfo mockUriInfo() {
		UriInfo mock = mock(UriInfo.class);
		when(mock.getQueryParameters()).thenReturn(new MultivaluedHashMap<String, String>());
		return mock;
	}

	@Test
	public void testEventsSearch(){
		String jsonAnswer = eventsController.events("", "", null);
		assertNotNull(jsonAnswer);
	}
	
	@Test
	public void testEventsSearchInvokeService(){
		eventsController.events("", "", null);
		verify(eventsService, atLeastOnce()).searchEvents(any(EventsSearchRequest.class));
	}
	
	@Test
	public void testEventsSearchPackAllParameters(){
		EventsSearchRequest req
			= EventsSearchRequest.builder().city(city).date(day).budget(budget).build();
		eventsController.events(city, day, budget);
		verify(eventsService, Mockito.atLeastOnce()).searchEvents(req);
	}
	
	@Test
	public void testEventsSearchSerializeJson(){
		Event event = new Event("title");
		List<Event> events = Arrays.asList(event);
		when(eventsService.searchEvents(any(EventsSearchRequest.class))).thenReturn(events);
		
		String actual = eventsController.events("", "", null);
		String expected = new GsonBuilder().create().toJson(events);
		
		assertEquals(expected, actual);
	}	

}