package ua.taky.eventstore.rest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import ua.taky.eventstore.service.EventsSearchRequest;
import ua.taky.eventstore.service.EventsService;


public class EventsControllerTest {
	
	private static final String city = "Kiev";
	private static final String day = "20131220";
	private static final String interest = "Ajax FC";
	private static final Integer budget = 500;
	
	private EventsController eventsController;
	
	private EventsService eventsService;
	
	@Before
	public void setUp(){
		eventsController = new EventsController();
		eventsService = Mockito.mock(EventsService.class);
		eventsController.setEventsService(eventsService);
	}
	
	@Test
	public void testEventsSearch(){
		String jsonAnswer = eventsController.events("", "", "", null);
		Assert.assertNotNull(jsonAnswer);
	}
	
	@Test
	public void testEventsSearchInvokeService(){
		eventsController.events("", "", "", null);
		Mockito.verify(eventsService, Mockito.atLeastOnce()).searchEvents(Mockito.any(EventsSearchRequest.class));
	}
	
	@Test
	public void testEventsSearchPackAllParameters(){
		EventsSearchRequest req
			= EventsSearchRequest.buildRequest(city, day, interest, budget);
		eventsController.events(city, day, interest, budget);
		Mockito.verify(eventsService, Mockito.atLeastOnce()).searchEvents(req);
	}	

}
