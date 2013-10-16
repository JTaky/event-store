package ua.taky.eventstore.service;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import ua.taky.eventstore.dao.IEventsRepository;
import ua.taky.eventstore.domain.Event;

public class EventsServiceTest {

	private EventsService eventsService;
	
	private IEventsRepository eventsRepository;
	
	@Before
	public void setUp(){
		eventsService = new EventsService();
		eventsRepository = mock(IEventsRepository.class);
		eventsService.setEventsRepository(eventsRepository);
	}
	
	@Test
	public void serarchEventsTest(){
		List<Event> someEvents 
			= eventsService.searchEvents(mock(EventsSearchRequest.class));
		assertNotNull(someEvents);
	}
	
	@Test
	public void serarchEventsPassRequestToDao() throws IOException{
		EventsSearchRequest mockRequest = mock(EventsSearchRequest.class);
		eventsService.searchEvents(mockRequest);
		verify(eventsRepository, Mockito.atLeastOnce()).searchEvents(mockRequest);
	}
	
}
